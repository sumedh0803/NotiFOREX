package com.example.notiforex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Dictionary;
import java.util.Hashtable;

public class HomeScreen extends AppCompatActivity {

    String TAG = "HomeScreen.java";
    String homeCurrency = null;
    String foreignCurrency = null;
    String homeUnit = null;
    String foreignUnit = null;
    String duration = null;
    TextView homeCurr;
    TextView foreignCurr;
    TextView trend;
    TextView from;
    TextView to;
    RequestQueue mQueue;
    String rate = null;
    ImageView updown;
    MaterialCardView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeCurr = findViewById(R.id.homeCurr);
        foreignCurr = findViewById(R.id.foreignCurr);
        trend = findViewById(R.id.trend);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        updown = findViewById(R.id.updown);

        getHomeCurrency();
        getForeignCurrency();
        getDuration();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = getApplicationContext().openFileInput("status.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        try {
            String status = bufferedReader.readLine();
            if(status == "success")
            {
                fillData("success");
            }
            else
            {
                fillData("failure");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(HomeScreen.this,Settings.class);
                startActivity(i);
                finish();
            }
        });
    }


    public void getHomeCurrency()
    {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = getApplicationContext().openFileInput("home.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        try {
            homeCurrency = bufferedReader.readLine();
            homeUnit = homeCurrency.substring(homeCurrency.length()-4,homeCurrency.length()-1);
            Log.i(TAG,homeCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getForeignCurrency()
    {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = getApplicationContext().openFileInput("foreign.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        try {
            foreignCurrency = bufferedReader.readLine();
            foreignUnit = foreignCurrency.substring(foreignCurrency.length()-4,foreignCurrency.length()-1);

            Log.i(TAG,foreignCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDuration()
    {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = getApplicationContext().openFileInput("interval.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        try {
            duration = bufferedReader.readLine();
            Log.i(TAG,duration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHomeCurrency();
        getForeignCurrency();
        getDuration();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = getApplicationContext().openFileInput("status.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


        try {
            String status = bufferedReader.readLine();
            System.out.println("status: "+status);
            if(status.equals("success"))
            {
                fillData("success");
            }
            else
            {
                fillData("failure");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillData(String status)
    {
        System.out.println(status);
        if(status.equals("success"))
        {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = openFileInput("curates.txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            try {
                String dataStr = bufferedReader.readLine();
                System.out.println("Data:"+dataStr);
                JSONObject jsonObj = new JSONObject(dataStr);
                to.setText(jsonObj.getString("new")+" "+homeUnit);

                if(Float.parseFloat(jsonObj.getString("old")) == 0.0f)
                {
                    trend.setText("--");
                    trend.setTextColor(Color.parseColor("#000000"));
                    updown.setImageResource(R.drawable.swap_vertical_bold);
                }
                else
                {
                    String difference = Float.toString(Float.parseFloat(jsonObj.getString("new")) - Float.parseFloat(jsonObj.getString("old")));
                    if(Float.parseFloat(difference) < 0)
                    {
                        //green
                        trend.setText(difference);
                        trend.setTextColor(Color.parseColor("#28A70C"));
                        updown.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                    }
                    else
                    {
                        //red
                        trend.setText(difference);
                        trend.setTextColor(Color.parseColor("#D50000"));
                        updown.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

         homeCurr.setText(homeCurrency);
         foreignCurr.setText(foreignCurrency);
         from.setText("1 "+foreignUnit);
         error = findViewById(R.id.error);
         error.setVisibility(View.INVISIBLE);

        }
        else
        {
            homeCurr.setText(homeCurrency);
            foreignCurr.setText(foreignCurrency);
            from.setText("--");
            to.setText("--");
            trend.setText("--");
            trend.setTextColor(Color.parseColor("#000000"));
            updown.setImageResource(R.drawable.swap_vertical_bold);
            error = findViewById(R.id.error);
            error.setVisibility(View.VISIBLE);
        }
    }
}


