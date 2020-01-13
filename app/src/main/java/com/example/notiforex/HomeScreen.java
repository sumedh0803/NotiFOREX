package com.example.notiforex;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeScreen extends AppCompatActivity {

    String TAG = "HomeScreen.java";
    String homeCurrency = null;
    String foreignCurrency = null;
    String duration = null;
    TextView homeCurr;
    TextView foreignCurr;
    TextView rate;
    TextView trend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeCurr = findViewById(R.id.homeCurr);
        foreignCurr = findViewById(R.id.foreignCurr);
        rate = findViewById(R.id.rate);
        trend = findViewById(R.id.trend);

        getHomeCurrency();
        getForeignCurrency();
        getDuration();

        homeCurr.setText("Your Home Currency is: "+homeCurrency);
        foreignCurr.setText("You are tracking: "+foreignCurrency);
        rate.setText("Current Conversion Rate: ");
        trend.setText("Your home currency has since the past "+duration);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(HomeScreen.this,Settings.class);
                startActivity(i);
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
        homeCurr.setText("Your Home Currency is: "+homeCurrency);
        foreignCurr.setText("You are tracking: "+foreignCurrency);
        rate.setText("Current Conversion Rate: ");
        trend.setText("Your home currency has since the past "+duration);

    }
}
