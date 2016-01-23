package fueltrack.jklotz.jklotz_fueltrack;

import java.util.Date;

/**
 * Created by Jessica on 2016-01-21.
 */
public class Fuel {
    private String date;
    private String station;
    private Double odometer;
    private String fuelGrade;
    private Double fuelAmount;
    private Double fuelUnitCost;
    private Double fuelCost;

    //Constructor
    //Intialize Fuel Object with all fields
    public Fuel(String date, String station, Double odometer, String fuelGrade,
                Double fuelAmount, Double fuelUnitCost, Double fuelCost) {
        this.date = date;
        this.station = station;
        this.odometer = odometer;
        this.fuelGrade = fuelGrade;
        this.fuelAmount = fuelAmount;
        this.fuelUnitCost = fuelUnitCost;
        this.fuelCost = fuelCost;
    }

    public Fuel() {
    }

    //get Date value
    public String getDate() {
        return date;
    }

    //set Date value
    public void setDate(String date) {
        this.date = date;
    }

    //get Station value
    public String getStation() {
        return station;
    }

    //set Station value
    public void setStation(String station) {
        this.station = station;
    }

    //get Odometer value
    public Double getOdometer() {
        return odometer;
    }

    //set Odometer value
    public void setOdometer(Double odometer) {
        this.odometer = odometer;
    }

    //get Fuel Grade value
    public String getFuelGrade() {
        return fuelGrade;
    }

    //set Fuel Grade value
    public void setFuelGrade(String fuelGrade) {
        this.fuelGrade = fuelGrade;
    }

    //get Fuel Amount value
    public Double getFuelAmount() {
        return fuelAmount;
    }

    //set Fuel Amount value
    public void setFuelAmount(Double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    //get Fuel Unit Cost value
    public Double getFuelUnitCost() {
        return fuelUnitCost;
    }

    //set Fuel Unit Cost
    public void setFuelUnitCost(Double fuelUnitCost) {
        this.fuelUnitCost = fuelUnitCost;
    }

    //get Fuel Cost value
    public Double getFuelCost() {
        return fuelCost;
    }

    //set Fuel Cost value
    public void setFuelCost() {
        this.fuelCost = this.fuelAmount * this.fuelUnitCost;
    }
}

