package com.example.notiforex;
/**
 * Written By: Sumedh Sen
 * Date: 12/26/2019
 * */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
/*
* This activity checks if the user is a new user or a returning user. To check this,
* we check if the file "userstatus.txt" exists or not. If the file is not present, the user is a new user. So, create the file
* and add some dummy data to it and redirect to InitialData activity. If the file exists, the user is a returning user and redirect the user to HomeScreen
* */
    private String filename = "userstatus.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checking if the file exists
        File file = getBaseContext().getFileStreamPath(filename);
        System.out.println(file.exists());
        if(file.exists())
        {
            //User is a returning user
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MainActivity.this, HomeScreen.class);
                    startActivity(i);
                    finish();
                }
            },3000);

        }

        else
        {   //User is new
            try {
                FileOutputStream fOut = openFileOutput(filename,MODE_PRIVATE);
                String data = "old";
                fOut.write(data.getBytes());
                fOut.close();

            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MainActivity.this, InitialData.class);
                    startActivity(i);
                    finish();
                }
            },3000);
        }
    }
}
