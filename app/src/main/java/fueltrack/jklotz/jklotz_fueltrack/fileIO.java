package fueltrack.jklotz.jklotz_fueltrack;

import android.app.Activity;

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
 * Created by Jessica on 2016-01-23.
 */
public class fileIO extends Activity {

    //Constructor - takes no parameters
    public fileIO() {
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
        //} catch (FileNotFoundException e) {
        //    fuelEntries = new ArrayList<Fuel>();

        //Any other IO errors
        } catch (IOException e) {
            throw new RuntimeException();
        }

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
        //} catch (FileNotFoundException e) {
        //    throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
}
