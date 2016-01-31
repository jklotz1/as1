package fueltrack.jklotz.jklotz_fueltrack;

import java.util.ArrayList;

/**
 * Created by Jessica on 2016-01-29.
 */
public interface FController {

    //add entry to list of fuel entries
    public void addFuelEntry(Fuel fuel);

    //edit an exisitng entry
    public void editFuelEntry(int index, Fuel newEntry);

    //get an entry from the list
    public Fuel getFuelEntry(int index);

    //get the number of entries
    public int getFuelLogCount();

    //get the array of entries
    public ArrayList<Fuel> getFuelLog();

    //set the list fo entries
    public void setFuelLog(ArrayList<Fuel> fuelLog);

}
