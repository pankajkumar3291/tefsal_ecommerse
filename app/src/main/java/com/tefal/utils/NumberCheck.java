package com.tefal.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rituparna Khadka on 12/29/2017.
 */

public class NumberCheck
{
    public static boolean isInteger(String number)
    {

        // regular expression for an integer number
        String regex = "[+-]?[0-9][0-9]*";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(number);
        if(m.find() && m.group().equals(number))
        {
            return true;
        }
        else
        {
            return false;
        }




    }
}
