package fueltrack.jklotz.jklotz_fueltrack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Jessica on 2016-01-30.
 */
public class FuelList extends FModel<FView>{
    //array of fuel entries
    private ArrayList<Fuel> fuelLog = new ArrayList<Fuel>();

    //constructor
    public FuelList(ArrayList<Fuel> fuelLog) {
        super();
        this.fuelLog = fuelLog;
    }

    //constructor
    public FuelList() {
        super();
        this.fuelLog = new ArrayList<Fuel>();
    }

    //add entry to list
    public void addFuelEntry(Fuel fuel){
        fuelLog.add(fuel);
    }

    //edit entry that exists
    public void editFuelEntry(int index, Fuel newEntry){
        fuelLog.set(index, newEntry);
    }

    //get a certain entry
    public Fuel getFuelEntry(int index){
        return fuelLog.get(index);
    }

    //get the number of entries
    public int getFuelLogCount(){
        return fuelLog.size();
    }

    //gets the array of entries
    public ArrayList<Fuel> getFuelLog() {
        return fuelLog;
    }

    //set the list of entries
    public void setFuelLog(ArrayList<Fuel> fuelLog) {
        this.fuelLog = fuelLog;
    }
}
