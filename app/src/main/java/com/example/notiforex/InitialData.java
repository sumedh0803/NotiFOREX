package com.example.notiforex;
/**
 * Written By: Sumedh Sen
 * Date: 12/26/2019
 * */

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class InitialData extends AppIntro {


    RadioGroup rghome,rginterval;
    String homedata = "";
    String intervaldata = "";
    String checkeddata = "";
    /**Here, we make use of the library AppIntro, to add fragments that take initial data like home currency
     * foreign currencies and update interval from the user
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialising the 3 fragments
        Fragment HomeCurrency,ForeignCurrency,Intervals;
        HomeCurrency = new HomeCurrency();
        ForeignCurrency = new ForeignCurrency();
        Intervals = new Intervals();

        //addSlide is a method of library AppIntro, that directly adds the fragments to our slider. Check documentation
        //of AppIntro for clarification
        addSlide(HomeCurrency);
        addSlide(ForeignCurrency);
        addSlide(Intervals);

        //Optional Parameters, defined in AppIntro library
        setBarColor(Color.parseColor("#28000000"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        //this hides skip button
        showSkipButton(false);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(final Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        /*When "Done" is pressed at the 3rd fragment, we write all the data to individual text files
        * */
        FileOutputStream fOut1,fOut2, fOut3;

        try {
            fOut1 = openFileOutput("home.txt",MODE_PRIVATE); //writing the home currency
            fOut1.write(homedata.getBytes());
            fOut1.close();

        fOut2 = openFileOutput("interval.txt",MODE_PRIVATE); //writing update interval
        fOut2.write(intervaldata.getBytes());
        fOut2.close();


        fOut3 = openFileOutput("foreign.txt",MODE_PRIVATE); //writing foreign currencies
        fOut3.write(checkeddata.getBytes());
        fOut3.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent i = new Intent(InitialData.this, HomeScreen.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onSlideChanged(@Nullable final Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
        /*when we swipe from one slide to the next, this method gets invoked
        * The previous fragment is accessible in oldFragment. at each invocation of this method, we check
        * which fragment was the last one, using instanceof. Eg (oldFragment instanceof HomeCurrency) means we swiped from
        * HomeCurrency fragment to next fragment
        * */
        if(oldFragment instanceof HomeCurrency)
        {
            rghome = oldFragment.getActivity().findViewById(R.id.rghome);
            RadioButton rb;
            Integer[] ids = {R.id.homeusd,R.id.homeinr,R.id.homegbp,R.id.homejpy,R.id.homeaud}; //array of ids of all radiobutton in the fragment HomeCurrency
            for(int id:ids) { //checking which radiobutton was selected
                rb = oldFragment.getActivity().findViewById(id);
                if (rb.isChecked()) {
                    homedata = rb.getText().toString();
                }
            }
        }
        if(oldFragment instanceof Intervals)
        {

            RadioButton rb;

            Integer[] ids = {R.id.onehr,R.id.sixhr,R.id.twelvehr,R.id.tfhr}; //array of all ids of radiobuttons in fragment Intervals
            for(int id:ids) {//checking which radiobutton was selected
                rb = oldFragment.getActivity().findViewById(id);
                if (rb.isChecked()) {
                    intervaldata = rb.getText().toString();
                }
            }

        }
        if(oldFragment instanceof ForeignCurrency)
        {

            CheckBox checkBox;
            checkeddata = "";
            Integer[] checkids = {R.id.forusd,R.id.forinr,R.id.forgbp,R.id.forjpy,R.id.foraud}; //array of all ids of checkboxes in fragment Intervals
            for (int id:checkids) {//checking which checkboxes were selected
                checkBox = oldFragment.getActivity().findViewById(id);
                if(checkBox.isChecked()) {
                    checkeddata += checkBox.getText().toString() + "\n";
                }
            }
        }
    }
}
