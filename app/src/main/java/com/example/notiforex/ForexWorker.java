package com.example.notiforex;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Dictionary;
import java.util.Hashtable;

import static android.content.Context.MODE_PRIVATE;

public class ForexWorker extends Worker {
    RequestQueue mQueue;
    Context context;


    public ForexWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {


        final String homeUnit = getInputData().getString("homeUnit");
        final String foreignUnit = getInputData().getString("foreignUnit");
        mQueue = Volley.newRequestQueue(getApplicationContext());
        //System.out.println(homeUnit);
        //System.out.println(foreignUnit);
        String URL = "https://www.freeforexapi.com/api/live?pairs="+foreignUnit+homeUnit;
        System.out.println(URL);
        //final Data.Builder outputData = new Data.Builder();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("code");
                            if(status == 200)
                            {
                                FileOutputStream fOut;
                                System.out.println(context);
                                fOut = context.openFileOutput("status.txt",MODE_PRIVATE);
                                fOut.write("success".getBytes());
                                fOut.close();

                                double temp = response.getJSONObject("rates").getJSONObject(foreignUnit+homeUnit).getDouble("rate");
                                System.out.println("temp: "+temp);
                                File file = new File("curates.txt");
                                System.out.println("filename: "+file);
                                if(!file.exists())
                                {
                                    System.out.println("file doesnt exist");
                                    Dictionary data = new Hashtable();
                                    data.put("old", "0");
                                    data.put("new", String.format("%.2f", temp));
                                    data.put("home",homeUnit);
                                    data.put("foreign",foreignUnit);

                                    System.out.println("context: "+context);
                                    fOut = context.openFileOutput("curates.txt",MODE_PRIVATE);
                                    fOut.write(data.toString().getBytes());
                                    showNotification("New Rates!","Rates are refreshed! Click here to check them out. ");
                                }
                                else
                                {
                                    //file exists, check if currencies are changed or not
                                    FileInputStream fileInputStream = null;
                                    fileInputStream = context.openFileInput("curates.txt");
                                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                                    String dataStr = bufferedReader.readLine();
                                    String oldrate,newrate,hometemp,foreigntemp;
                                    JSONObject jsonObj = new JSONObject(dataStr);

                                    if(jsonObj.get("home").equals(homeUnit) && jsonObj.get("foreign").equals(foreignUnit))
                                    {
                                        //currencies are the same. this means that worker is fetching the rates on its own.
                                        oldrate = jsonObj.getString("old");
                                        newrate = jsonObj.getString("new");
                                        Log.i("FOREXWORKER", "Currencies are same. rates fetched by the worker");
                                        Log.i("FOREXWORKER", "Old rate:"+oldrate);
                                        Log.i("FOREXWORKER", "New rate:"+newrate);

                                        jsonObj.put("old",newrate);
                                        jsonObj.put("new",String.format("%.2f", temp));

                                        System.out.println(context);
                                        fOut = context.openFileOutput("curates.txt",MODE_PRIVATE);
                                        fOut.write(jsonObj.toString().getBytes());
                                        Float diff = Float.parseFloat(jsonObj.getString("old"))-Float.parseFloat(jsonObj.getString("new"));
                                        showNotification("New Rates!","Rates are refreshed! Click here to check them out. "+diff);

                                    }
                                    else
                                    {

                                        //currencies are changed from the settings, new worker is assigned
                                        Dictionary data = new Hashtable();
                                        data.put("old", "0");
                                        data.put("new", String.format("%.2f", temp));
                                        data.put("home",homeUnit);
                                        data.put("foreign",foreignUnit);


                                        System.out.println(context);
                                        fOut = context.openFileOutput("curates.txt",MODE_PRIVATE);
                                        fOut.write(data.toString().getBytes());
                                        showNotification("New Rates!","Rates are refreshed! Click here to check them out. ");

                                    }
                                }

                            }
                            else
                            {
                                FileOutputStream fOut;
                                System.out.println(context);
                                fOut = context.openFileOutput("status.txt",MODE_PRIVATE);
                                fOut.write("failure".getBytes());

                            }



                    } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        mQueue.add(request);
        return Result.success();



    }

    private void showNotification(String title, String desc) {

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), HomeScreen.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = "task_channel";
        String channelName = "task_name";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingNotificationIntent)
                .setSmallIcon(R.drawable.ic_attach_money_black_24dp)
                .setAutoCancel(true);


        manager.notify(1, builder.build());

    }
}