package com.example.notiforex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.FileOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class Dialog_Home_Currency extends AppCompatDialogFragment {

    private Listener listener;

    int button;
    int id;

    public Dialog_Home_Currency(int id)
    {
        this.id = id;
    }

    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    RadioButton rb5;
    RadioButton rb;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_homecurrency, null);

        rb1 = view.findViewById(R.id.radioButton);
        rb2 = view.findViewById(R.id.radioButton2);
        rb3 = view.findViewById(R.id.radioButton3);
        rb4 = view.findViewById(R.id.radioButton4);
        rb5 = view.findViewById(R.id.radioButton5);

        if(id == 2)
        {
            rb1.setText("1 Hr");
            rb2.setText("6 Hrs");
            rb3.setText("12 Hrs");
            rb4.setText("24 Hrs");
            rb5.setVisibility(View.INVISIBLE);
        }
        else
        {
            rb1.setText("US Dollars (USD)");
            rb2.setText("Indian Rupees (INR)");
            rb3.setText("Pound Sterling (GBP)");
            rb4.setText("Japanese Yen (JPY)");
            rb5.setText("Australian Dollars (AUD)");

        }

        builder.setView(view)
                .setTitle("Change Home Currency")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}})
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(rb1.isChecked())
                        {
                            button = 1;
                            listener.datareturn(id, button,rb1.getText().toString());
                            startWorker(id,rb1.getText().toString());
                        }
                        else if(rb2.isChecked())
                        {
                            button = 2;
                            listener.datareturn(id, button,rb2.getText().toString());
                            startWorker(id,rb1.getText().toString());
                        }
                        else if(rb3.isChecked())
                        {
                            button = 3;
                            listener.datareturn(id, button,rb3.getText().toString());
                            startWorker(id,rb1.getText().toString());
                        }
                        else if(rb4.isChecked())
                        {
                            button = 4;
                            listener.datareturn(id, button,rb4.getText().toString());
                            startWorker(id,rb1.getText().toString());
                        }
                        else if(rb5.isChecked())
                        {
                            button = 5;
                            listener.datareturn(id, button,rb5.getText().toString());
                            startWorker(id,rb1.getText().toString());
                        }


                        // Check which button was clicked
                        // Write the appropriate "click" to the respective file

                    }
                });


        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    private void startWorker(int id, String text)
    {

    }

    public interface Listener{
        void datareturn(int id, int button,String btnText);
    }
}
