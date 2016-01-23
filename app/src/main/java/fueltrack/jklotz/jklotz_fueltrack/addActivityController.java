package fueltrack.jklotz.jklotz_fueltrack;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jessica on 2016-01-21.
 */
public class addActivityController extends addActivity{
    private addActivityModel addModel;

    //Constructor - takes no parameters
    public addActivityController() {
    }

    //Save new fuel entry
    public void saveNewFuelEntry(Fuel fuelEntry){

        //create new instance of the addActivityModel that will save entry
        addModel = new addActivityModel();
        addModel.saveNewFuelEntry(fuelEntry);

    }
}
