package fueltrack.jklotz.jklotz_fueltrack;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jessica on 2016-01-30.
 */
public class FuelListTest extends ActivityInstrumentationTestCase2 {
    public FuelListTest(){
        super(AddActivity.class);
    }

    //test adding entry to list
    public void TestAddFuelEntry(){
        FuelList fuelList = new FuelList();
        Fuel fuel = new Fuel("2012-12-12","Esso",12345.1,"Regular",10.0,87.4,8.74,1);

        fuelList.addFuelEntry(fuel);
        Fuel fuelEntry = fuelList.getFuelEntry(0);

        assertEquals(fuel,fuelEntry);

    }

    //test editing entry that exists
    public void testEditFuelEntry(){
        FuelList fuelList = new FuelList();
        Fuel fuel = new Fuel("2012-12-12","Esso",12345.1,"Regular",10.0,87.4,8.74,1);
        Fuel fuelEdited = new Fuel("2012-12-12","Shell",12345.0,"Regular",10.0,78.4,7.84,1);

        fuelList.addFuelEntry(fuel);
        assertEquals(fuelList.getFuelLogCount(), 1);
        fuelList.editFuelEntry(0, fuelEdited);
        assertNotSame(fuelList.getFuelEntry(0), fuel);
        assertEquals(fuelList.getFuelEntry(0), fuelEdited);
        assertEquals(fuelList.getFuelLogCount(),1);
    }

    //test getting a certain entry
    public void testGetFuelEntry(int index){
        FuelList fuelList = new FuelList();
        Fuel fuel = new Fuel("2012-12-12","Esso",12345.1,"Regular",10.0,87.4,8.74,1);

        fuelList.addFuelEntry(fuel);
        Fuel fuelEntry = fuelList.getFuelEntry(0);

        assertEquals(fuel,fuelEntry);
    }

    //test getting the number of entries
    public void testGetFuelLogCount(){
        FuelList fuelList = new FuelList();
        Fuel fuel = new Fuel("2012-12-12","Esso",12345.1,"Regular",10.0,87.4,8.74,1);
        fuelList.addFuelEntry(fuel);
        assertEquals(fuelList.getFuelLogCount(),1);

        Fuel fuel2 = new Fuel("2012-12-12","Shell",12345.0,"Regular",10.0,78.4,7.84,1);
        fuelList.addFuelEntry(fuel2);
        assertEquals(fuelList.getFuelLogCount(),2);
    }



}
