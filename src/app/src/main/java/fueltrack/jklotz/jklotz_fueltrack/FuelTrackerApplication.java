package fueltrack.jklotz.jklotz_fueltrack;

import android.app.Application;

/**
 * Created by Jessica on 2016-01-29.
 */

//modelled off of fillercreep
//https://github.com/jklotz1/FillerCreepForAndroid/blob/master/app/src/main/java/es/softwareprocess/fillercreep/FillerCreepApplication.java
public class FuelTrackerApplication extends Application {
    // Singleton
    transient private static FuelList fuelLog = null;

    public static FuelList getFuelList() {
        if (fuelLog == null) {
            fuelLog = new FuelList();
        }
        return fuelLog;
    }

    // Singleton
    transient private static TrackerController trackerController = null;

    //gets the controller
    public static TrackerController getTrackerController() {
        if (trackerController == null) {
            trackerController = new TrackerController(getFuelList());
        }
        return trackerController;
    }

}
