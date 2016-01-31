package fueltrack.jklotz.jklotz_fueltrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
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

public class AddActivity extends Activity implements FView<FuelList> {

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
    private Fuel fuelEntry = new Fuel();

    //file to save fuel entries
    private String FILENAME = "fuel.sav";

    //array to store existing fuel entries
    private FuelList fuelLog;

    //create instance of tracker controller
    private TrackerController trackerController;

    //variable to store if entry is new or an existing being edited
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initial setup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfuellog);

        //bind local variables to UI elements
        bindUIElements();

        //set filters on decimal values so only a certian number of decimal spots
        setDecimalFilters();

        //get the current fuel log and store in the fuelList
        fuelLog = new FuelList(loadFuelLog(FILENAME));
        trackerController = FuelTrackerApplication.getTrackerController();
        trackerController.setFuelLog(loadFuelLog(FILENAME));

        //parameter is sent with intent if entry is being added or edited
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        //if editing an entry set the values of the entry
        if (type.matches("edit")){
            TextView title = (TextView)findViewById(R.id.titleAdd);
            title.setText("Edit Fuel Entry");
            setValues(intent);
        }

        //set listener, if fuel amount is entered then compute total cost
        fuelAmount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                trackerController.calculateTotalCost();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //set listener, if fuel unit cost is  entered then compute total cost
        fuelUnitCost.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                trackerController.calculateTotalCost();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Save fuel entry and return to home screen when save button clicked
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //get values for elements of Fuel Object
                bindToFuelValues();

                if (type.matches("add")){
                    //save edited entry
                    saveNewFuelEntry(fuelEntry);
                    onBackPressed();
                }
                else if (type.matches("edit")) {
                    //save new fuel entry
                    saveEditedFuelEntry(fuelEntry);
                    onBackPressed();
                }
            }
        });

        //Cancel activity and return to home screen when cancel button is clicked
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelEntry(v);
            }
        });

        //add this view
        FuelList fuelList = FuelTrackerApplication.getFuelList();
        fuelList.addView(this);
    }

    //when the page is exited then remove this view
    @Override
    public void onDestroy() {
        super.onDestroy();
        FuelList fuelList = FuelTrackerApplication.getFuelList();
        fuelList.deleteView(this);
    }

    //update the view to reflect changes
    //total cost will be displayed when amount and unit cost is entered
    @Override
    public void update(FuelList fuelList) {
        updateTotalCost();
    }

    //calculates the total cost from amount and unit cost
    private void updateTotalCost(){
        String unitCost = fuelUnitCost.getText().toString();
        String amount = fuelAmount.getText().toString();
        //calculate only when amount and unit cost aren't empty
        if (!(amount.matches("")) && !(unitCost.matches(""))) {
            double cost = Double.parseDouble(fuelUnitCost.getText().toString()) / 100 *
                    Double.parseDouble(fuelAmount.getText().toString());
            fuelCost.setText(String.format("%.2f",cost));

        } else {
            double cost = 0.00;
            fuelCost.setText(String.format("%.2f", cost));
        }
    }

    //bind elements on UI to local varaibles so they can be accessed
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

    //set the decimal filter for some fields, only allow certian number of decimal spots
    private void setDecimalFilters(){
        odometer.setFilters(new InputFilter[]{new DecimalFilters(10, 1)});
        fuelAmount.setFilters(new InputFilter[]{new DecimalFilters(10, 3)});
        fuelUnitCost.setFilters(new InputFilter[]{new DecimalFilters(10, 1)});
        fuelCost.setFilters(new InputFilter[]{new DecimalFilters(10, 2)});
    }

    //set the values of the Fuel Object from the local variables
    private void bindToFuelValues() {
        fuelEntry.setDate(date.getText().toString());
        fuelEntry.setStation(station.getText().toString());
        fuelEntry.setOdometer(Double.parseDouble(odometer.getText().toString()));
        fuelEntry.setFuelGrade(fuelGrade.getText().toString());
        fuelEntry.setFuelAmount(Double.parseDouble(fuelAmount.getText().toString()));
        fuelEntry.setFuelUnitCost(Double.parseDouble(fuelUnitCost.getText().toString()));
        fuelEntry.setFuelCost();
    }

    //for editing an entry set the fields with the information of that entry
    private void setValues(Intent intent){
        //the entry number will be used to get the entry from the list of entries
        //the entry number is passed as a parameter with the intent
        fuelEntry = trackerController.getFuelEntry(intent.getIntExtra("entryNumber",0)-1);

        date.setText(String.valueOf(fuelEntry.getDate()));
        station.setText(String.valueOf(fuelEntry.getStation()));
        odometer.setText(String.valueOf(fuelEntry.getOdometer()));
        fuelGrade.setText(String.valueOf(fuelEntry.getFuelGrade()));
        fuelAmount.setText(String.valueOf(fuelEntry.getFuelAmount()));
        fuelUnitCost.setText(String.valueOf(fuelEntry.getFuelUnitCost()));
        fuelCost.setText(String.valueOf(fuelEntry.getFuelCost()));
    }

    //when entry is canceled return to previous screen
    public void cancelEntry(View view) {
        onBackPressed();
    }

    //save new entry
    public void saveNewFuelEntry(Fuel fuelEntry) {
        //set the entry number - chronological so can use size to determine the next number
        fuelEntry.setEntryNumber(trackerController.getFuelLogCount()+1);

        //add the new entry to the existing entries
        trackerController.addFuelEntry(fuelEntry);

        //save the updated array of fuel entries to file
        saveFuelEntry(trackerController.getFuelLog(),FILENAME);
    }

    //save edited entry
    public void saveEditedFuelEntry(Fuel fuelEntry){
        //replace the entry that edited with the new values
        trackerController.editFuelEntry(fuelEntry.getEntryNumber()-1, fuelEntry);

        //save the updated array of fuel entries to file
        saveFuelEntry(trackerController.getFuelLog(),FILENAME);
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

        //return the array of Fuel objects
        return fuelEntries;
    }

    //Takes an array of Fuel objects and saves it to FILENAME
    //public void saveFuelEntry(ArrayList<Fuel> fuelEntries, String FILENAME){
    public void saveFuelEntry(ArrayList<Fuel> fuelLogUpdated, String FILENAME){
        //try to save to file
        try {
            //create file stream to write to file, 0 for default write mode
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();

            //converts array of Fuel objects to Json to store in file
            //and writes to file
            gson.toJson(fuelLogUpdated, out);
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
