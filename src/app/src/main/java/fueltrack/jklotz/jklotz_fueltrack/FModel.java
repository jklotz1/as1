package fueltrack.jklotz.jklotz_fueltrack;

import java.util.ArrayList;

/**
 * Created by Jessica on 2016-01-29.
 */

//taken from fillerCreep app https://github.com/jklotz1/FillerCreepForAndroid/blob/master/app/src/main/java/es/softwareprocess/fillercreep/FModel.java
public class FModel<V extends FView> {
    //array from holding views
    private ArrayList<V> views;

    public FModel() {
        views = new ArrayList<V>();
    }

    //adding a view
    public void addView(V view) {
        if (!views.contains(view)) {
            views.add(view);
        }
    }

    //deleting a view
    public void deleteView(V view) {
        views.remove(view);
    }

    //notify view when to update
    public void notifyViews() {
        for (V view : views) {
            view.update(this);
        }
    }
}
