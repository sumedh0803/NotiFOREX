package com.example.notiforex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class Settings extends AppCompatActivity implements Dialog_Home_Currency.Listener {

    String usd = "US Dollars (USD)";
    String inr = "Indian Rupees (INR)";
    String pound = "Pound Sterling (GBP)";
    String yen = "Japanese Yen (JPY)";
    String ad = "Australian Dollars (AUD)";
    String dr1 = "1 Hr";
    String dr2 = "6 Hrs";
    String dr3 = "12 Hrs";
    String dr4 = "24 Hr";
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
    public void datareturn(int id, int button) {

        FileOutputStream fout;

if(id == 0)
{
    if(button == 1)
    {
        try {
            fout = openFileOutput("home.txt",MODE_PRIVATE); //writing the home currency
            fout.write(usd.getBytes());
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
            fout.write(inr.getBytes());
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
            fout.write(pound.getBytes());
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
            fout.write(yen.getBytes());
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
            fout.write(ad.getBytes());
            fout.close();
            getHomeCurrency();
            Adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

        if(id == 1)
        {
            if(button == 1)
            {
                try {
                    fout = openFileOutput("foreign.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(usd.getBytes());
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
                    fout.write(inr.getBytes());
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
                    fout.write(pound.getBytes());
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
                    fout.write(yen.getBytes());
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
                    fout.write(ad.getBytes());
                    fout.close();
                    getForeignCurrency();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }


        if(id == 2)
        {
            if(button == 1)
            {

                try {
                    fout = openFileOutput("interval.txt",MODE_PRIVATE); //writing the home currency
                    fout.write(dr1.getBytes());
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
                    fout.write(dr2.getBytes());
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
                    fout.write(dr3.getBytes());
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
                    fout.write(dr4.getBytes());
                    fout.close();
                    getDuration();
                    Adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }


    }
}
