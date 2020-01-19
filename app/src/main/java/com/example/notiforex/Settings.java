package com.example.notiforex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Settings extends AppCompatActivity implements Dialog_Home_Currency.Listener {

    /*String usd = "US Dollars (USD)";
    String inr = "Indian Rupees (INR)";
    String pound = "Pound Sterling (GBP)";
    String yen = "Japanese Yen (JPY)";
    String ad = "Australian Dollars (AUD)";
    String dr1 = "1 Hr";
    String dr2 = "6 Hrs";
    String dr3 = "12 Hrs";
    String dr4 = "24 Hr";*/
    String TAG = "Settings.java";
    String homeCurrency = "Home Currency: ";
    String foreignCurrency = "Foreign Currency: ";
    String duration = "Notification Duration: ";
    List<String> Titles = new ArrayList<>();
    ListView lv;
    ArrayAdapter<String> Adapter;
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;

    Data.Builder data = new Data.Builder();
    String homeUnit = null;
    String foreignUnit = null;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        lv = findViewById(R.id.lv);

        getHomeCurrency();
        getForeignCurrency();
        getDuration();

        Adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Titles);
        lv.setAdapter(Adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Dialog_Home_Currency dhome = new Dialog_Home_Currency(i);
                dhome.show(getSupportFragmentManager(),"Dialog_Home_Currency");

            }
        });








    }

    public void getHomeCurrency()
    {

        String temp;

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = getApplicationContext().openFileInput("home.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        try {
            temp = bufferedReader.readLine();
            homeUnit = temp.substring(temp.length()-4,temp.length()-1);
            data.putString("homeUnit", homeUnit);
            if(count1 != 0)
            {
                homeCurrency = "Home Currency: ";
                homeCurrency = homeCurrency+""+temp;
                Titles.remove(0);
                Titles.add(0,homeCurrency);
                Log.i(TAG, Titles.get(0));
            }
            else
            {
                homeCurrency = homeCurrency+""+temp;
                Titles.add(homeCurrency);
                Log.i(TAG, Titles.get(0));
                count1 += 1;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getForeignCurrency()
    {

        String temp;

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = getApplicationContext().openFileInput("foreign.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        try {
            temp = bufferedReader.readLine();
            foreignUnit = temp.substring(temp.length()-4,temp.length()-1);
            data.putString("foreignUnit", foreignUnit);


            if(count2 != 0)
            {
                foreignCurrency = "Foreign Currency: ";
                foreignCurrency = foreignCurrency+""+temp;
                Titles.remove(1);
                Titles.add(1,foreignCurrency);
                Log.i(TAG, Titles.get(1));
            }
            else
            {
                foreignCurrency = foreignCurrency+""+temp;
                Titles.add(foreignCurrency);
                Log.i(TAG, Titles.get(1));
                count2 += 1;

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDuration()
    {
        String temp;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = getApplicationContext().openFileInput("interval.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        try {
            temp = bufferedReader.readLine();
            time = Integer.parseInt(temp.substring(0,temp.indexOf(" ")));

            if(count3 != 0)
            {
                duration = "Notification Duration: ";
                duration = duration+""+temp;
                Titles.remove(2);
                Titles.add(2,duration);
                Log.i(TAG, Titles.get(2));
            }
            else
            {
                duration = duration+""+temp;
                Titles.add(duration);
                Log.i(TAG, Titles.get(2));
                count3 += 1;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void datareturn(int id, int button, String btnText) {

        FileOutputStream fout;

        if(id == 0)
        {
            if(button == 1)
            {
                try {
                    fout = openFileOutput("home.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getHomeCurrency();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if(button == 2)
            {
                try {
                    fout = openFileOutput("home.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getHomeCurrency();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if(button == 3)
            {

                try {
                    fout = openFileOutput("home.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getHomeCurrency();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if(button == 4)
            {

                try {
                    fout = openFileOutput("home.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getHomeCurrency();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if(button == 5)
            {
                try {
                    fout = openFileOutput("home.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getHomeCurrency();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            //data.putString("homeUnit", homeUnit)
        }

        if(id == 1)
        {
            if(button == 1)
            {
                try {
                    fout = openFileOutput("foreign.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getForeignCurrency();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if(button == 2)
            {
                try {
                    fout = openFileOutput("foreign.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getForeignCurrency();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if(button == 3)
            {

                try {
                    fout = openFileOutput("foreign.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getForeignCurrency();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if(button == 4)
            {

                try {
                    fout = openFileOutput("foreign.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getForeignCurrency();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if(button == 5)
            {
                try {
                    fout = openFileOutput("foreign.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getForeignCurrency();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            //data.putString("homeUnit", homeUnit)
        }


        if(id == 2)
        {
            if(button == 1)
            {

                try {
                    fout = openFileOutput("interval.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getDuration();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if(button == 2)
            {

                try {
                    fout = openFileOutput("interval.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getDuration();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(button == 3)
            {
                try {
                    fout = openFileOutput("interval.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getDuration();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else if(button == 4)
            {
                try {
                    fout = openFileOutput("interval.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(btnText.getBytes());
                    fout.close();
                    getDuration();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }


    }


    @Override
    public void onBackPressed() {


        super.onPause();

        final WorkManager mWorkManager = WorkManager.getInstance();
        final PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder(ForexWorker.class,time, TimeUnit.HOURS)
                .setInputData(data.build())
                .build();
        WorkManager.getInstance().cancelAllWork();
        mWorkManager.enqueue(mRequest);

        mWorkManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if (workInfo != null) {
                    String state = workInfo.getOutputData().getString("state");
                    //workInfo.getState()
                    System.out.println("state: "+workInfo.getOutputData().getString("state"));
                    //Log.i(TAG, "onChanged: state = "+state);
                }
            }
        });



        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getBaseContext()).inflate(R.layout.customdialog, viewGroup, false);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Saving Data..");
        alertDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                Settings.this.onSuperBackPressed();
            }
        },3000);
    }
    public void onSuperBackPressed(){
        super.onBackPressed();
        Intent i = new Intent(Settings.this, HomeScreen.class);
        startActivity(i);
        finish();
    }
}
