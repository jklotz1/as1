package fueltrack.jklotz.jklotz_fueltrack;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

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


/**
 * Created by Jessica on 2016-01-21.
 */
public class addActivityModel extends addActivityController{
    private static final String FILENAME = "fuel.sav";
    private ArrayList<Fuel> fuelEntries;

    //constructor - doesn't take any parameters
    public addActivityModel() {
    }

    public void saveNewFuelEntry(Fuel fuelEntry) {


        //creates new instance of fileIO class for saving
        //fileIO io = new fileIO();
        //load the fuel entries that have been entered already
        //fuelEntries = io.loadFuelLog(FILENAME);

        //add the new entry to the existing entries
        //fuelEntries.add(fuelEntry);

        //save the updated array of fuel entries to file
        //io.saveFuelEntry(fuelEntries, FILENAME);
    }



}
