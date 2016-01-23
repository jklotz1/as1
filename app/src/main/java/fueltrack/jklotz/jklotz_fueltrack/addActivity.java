package fueltrack.jklotz.jklotz_fueltrack;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class addActivity extends Activity {

    //local variables corresponding to input fields
    private EditText date;
    private EditText station;
    private EditText odometer;
    private EditText fuelGrade;
    private EditText fuelAmount;
    private EditText fuelUnitCost;
    private TextView fuelCost;

    //variable for button used for implementing click action
    private Button btnSave;
    private Button btnCancel;

    //Fuel object that will store values of new entry
    private Fuel fuelEntry;

    //file to save fuel entries
    private String FILENAME = "fuel.sav";

    //array to store existing fuel entries
    ArrayList<Fuel> fuelEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initial setup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //bind local variables to UI elements
        bindUIElements();

        fuelAmount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String unitCost = fuelUnitCost.getText().toString();
                if (!(unitCost.matches(""))) {
                    double cost = Double.parseDouble(fuelUnitCost.getText().toString()) *
                            Double.parseDouble(fuelAmount.getText().toString());
                    fuelCost.setText(String.valueOf(cost));
                }
                else {
                    double cost = 0.0;
                    fuelCost.setText(String.valueOf(cost));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        fuelUnitCost.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String amount = fuelAmount.getText().toString();
                if (!(amount.matches(""))) {
                    double cost = Double.parseDouble(fuelUnitCost.getText().toString()) *
                            Double.parseDouble(fuelAmount.getText().toString());
                    fuelCost.setText(String.valueOf(cost));
                }
                else {
                    double cost = 0.0;
                    fuelCost.setText(String.valueOf(cost));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Save fuel entry and return to home screen when save button clicked
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //try {
                //    FileInputStream fis = openFileInput("fuel.sav");
                //} catch (FileNotFoundException e) {
                //    e.printStackTrace();
                //}
                //create new instance of Fuel object
                fuelEntry = new Fuel();
                //get values for elements of Fuel Object
                bindToFuelValues();

                //save new fuel entry
                saveNewFuelEntry(fuelEntry);
                onBackPressed();
            }
        });

        //Cancel activity and return to home screen when cancel button is clicked
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelEntry(v);
            }
        });
    }


    private void bindUIElements() {
        //Bind value fields on UI to local variables
        date = (EditText)findViewById(R.id.txtDate);
        station = (EditText)findViewById(R.id.txtStation);
        odometer = (EditText)findViewById(R.id.txtOdometer);
        fuelGrade = (EditText)findViewById(R.id.txtFuelGrade);
        fuelAmount = (EditText)findViewById(R.id.txtFuelAmount);
        fuelUnitCost = (EditText)findViewById(R.id.txtFuelUnitCost);
        fuelCost = (TextView)findViewById(R.id.txtFuelCost);

        //bind buttons to local variables
        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button)findViewById(R.id.btnCancel);
    }

    private void bindToFuelValues() {
        //set the values of the Fuel Object from the local variables
        fuelEntry.setDate(date.getText().toString());
        fuelEntry.setStation(station.getText().toString());
        fuelEntry.setOdometer(Double.parseDouble(odometer.getText().toString()));
        fuelEntry.setFuelGrade(fuelGrade.getText().toString());
        fuelEntry.setFuelAmount(Double.parseDouble(fuelAmount.getText().toString()));
        fuelEntry.setFuelUnitCost(Double.parseDouble(fuelUnitCost.getText().toString()));
        fuelEntry.setFuelCost();
    }

    public void cancelEntry(View view) {
        //when entry is canceled return to homescreen
        onBackPressed();
    }

    public void saveNewFuelEntry(Fuel fuelEntry) {

        //load the fuel entries that have been entered already
        fuelEntries = loadFuelLog(FILENAME);

        //add the new entry to the existing entries
        fuelEntries.add(fuelEntry);

        //save the updated array of fuel entries to file
        saveFuelEntry(fuelEntries, FILENAME);
    }

    //Read the File and store the Fuel objects into an Array and return that Array
    public ArrayList<Fuel> loadFuelLog(String FILENAME) {

        //fuel entries from file will be stored in this array
        ArrayList<Fuel> fuelEntries;

        //try to read in fuel entries from file
        try {
            //create file stream to read a file
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            //Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html Jan-21-2016
            //convert the type stored in file to an array of Fuel objects and store in fuelEntries array
            Type listType = new TypeToken<ArrayList<Fuel>>() {
            }.getType();
            fuelEntries = gson.fromJson(in, listType);

            //catching errors
            //if file does not exist then create and empty array
        } catch (Exception e){
            if (e.getClass() == FileNotFoundException.class){
                fuelEntries = new ArrayList<Fuel>();
            } else {
                throw new RuntimeException();
            }
        }
        //catch (FileNotFoundException e) {
        //    fuelEntries = new ArrayList<Fuel>();

            //Any other IO errors
        //} catch (IOException e) {
        //    throw new RuntimeException();
       //}

        //return the array of Fuel objects
        return fuelEntries;
    }

    //Takes an array of Fuel objects and saves it to FILENAME
    public void saveFuelEntry(ArrayList<Fuel> fuelEntries, String FILENAME){

        //try to save to file
        try {

            //create file stream to write to file, 0 for default write mode
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();

            //converts array of Fuel objects to Json to store in file
            //and writes to file
            gson.toJson(fuelEntries, out);
            out.flush();
            fos.close();

            //catch errors and throws runtime exceptions
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }



}
