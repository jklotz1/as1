package fueltrack.jklotz.jklotz_fueltrack;

import java.util.ArrayList;

/**
 * Created by Jessica on 2016-01-29.
 */
public class TrackerController implements FController {
    FuelList fuelList = null;

    //constructor
    public TrackerController(FuelList fuelList){
        this.fuelList = fuelList;
    }

    //calculates total cost and notifies the view to update this change
    public void calculateTotalCost(){
        fuelList.notifyViews();
    }

    //add entry to list
    public void addFuelEntry(Fuel fuel){
        fuelList.addFuelEntry(fuel);
    }

    //edit an existing entry
    public void editFuelEntry(int index, Fuel newEntry){
        fuelList.editFuelEntry(index, newEntry);
    }

    //get certain entry
    public Fuel getFuelEntry(int index){
        return fuelList.getFuelEntry(index);
    }

    //get number of entries
    public int getFuelLogCount(){
        return fuelList.getFuelLogCount();
    }

    //get the array of entries
    public ArrayList<Fuel> getFuelLog() {
        return fuelList.getFuelLog();
    }

    //sets the array of entries
    public void setFuelLog(ArrayList<Fuel> fuelLog) {
        fuelList.setFuelLog(fuelLog);
    }

}
