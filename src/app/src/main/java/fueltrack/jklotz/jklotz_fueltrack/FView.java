package fueltrack.jklotz.jklotz_fueltrack;

/**
 * Created by Jessica on 2016-01-29.
 */

//taken from fillercreep https://github.com/jklotz1/FillerCreepForAndroid/blob/master/app/src/main/java/es/softwareprocess/fillercreep/FView.java
public interface FView<M> {
    //update the view
    public void update(M model);
}
