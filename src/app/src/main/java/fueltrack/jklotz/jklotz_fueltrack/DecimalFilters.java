package fueltrack.jklotz.jklotz_fueltrack;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jessica on 2016-01-30.
 */

//taken from stack overflow http://stackoverflow.com/questions/5357455/limit-decimal-places-in-android-edittext on Jan-30-2016
public class DecimalFilters implements InputFilter {

    Pattern mPattern;

    //set up filter used that will be used to specify the numbers of decimals for input
    public DecimalFilters(int digitsBeforeZero,int digitsAfterZero) {
        mPattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher=mPattern.matcher(dest);
        if(!matcher.matches())
            return "";
        return null;
    }


}
