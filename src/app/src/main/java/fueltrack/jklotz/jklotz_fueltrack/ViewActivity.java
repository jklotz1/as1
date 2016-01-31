package fueltrack.jklotz.jklotz_fueltrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewActivity extends Activity implements FView<FuelList> {

    //file to save fuel entries
    private String FILENAME = "fuel.sav";

    //array to store existing fuel entries
    //private ArrayList<Fuel> fuelEntries;
    //private FuelList fuelLog;

    //List and text view on UI
    private ListView fuelList;

    //create instance of tracker controller
    //private TrackerController trackerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewfuellog);

        fuelList = (ListView)findViewById(R.id.fuelList);

        //get the current fuel log and store in the fuelList
//        fuelLog = new FuelList(loadFuelLog(FILENAME));
//        TrackerController trackerController = FuelTrackerApplication.getTrackerController();
//        trackerController.setFuelLog(loadFuelLog(FILENAME));

        //entry has been selected to be edited
        //pass the entry number as a parameter
        fuelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Fuel entry = (Fuel) parent.getAdapter().getItem(position);
                Intent intent = new Intent(ViewActivity.this, AddActivity.class);
                intent.putExtra("type", "edit");
                intent.putExtra("entryNumber", entry.getEntryNumber());
                startActivity(intent);

            }
        });

        //add view
        FuelList fuelList = FuelTrackerApplication.getFuelList();
        fuelList.addView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //get the list of entries
        ArrayList<Fuel> fuelEntries = loadFuelLog(FILENAME);

        //set adapter so that listview displays entries
        ArrayAdapter<Fuel> adapter = new ArrayAdapter<Fuel>(this, R.layout.list_item, fuelEntries);
        fuelList.setAdapter(adapter);

        //calculate cost and updates the view
        TrackerController trackerController = FuelTrackerApplication.getTrackerController();
        trackerController.setFuelLog(loadFuelLog(FILENAME));
        trackerController.calculateTotalCost();
    }

    //when page is left then delete view
    @Override
    public void onDestroy() {
        super.onDestroy();
        FuelList fuelList = FuelTrackerApplication.getFuelList();
        fuelList.deleteView(this);
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

    //calculate the total cost from the costs from all entries
    public void getTotalCost(ArrayList<Fuel> entries){
        TextView totalCost = (TextView)findViewById(R.id.totalCost);
        double cost = 0;
        for (int i = 0; i < entries.size() ; i++) {
            cost += entries.get(i).getFuelCost();
        }
        totalCost.setText("Total Cost: $"+String.format("%.2f",cost));
    }

    //show changes to the view
    @Override
    public void update(FuelList model) {
        TrackerController trackerController = FuelTrackerApplication.getTrackerController();
        trackerController.setFuelLog(loadFuelLog(FILENAME));
        getTotalCost(trackerController.getFuelLog());
    }
}
