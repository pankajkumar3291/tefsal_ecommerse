package com.tefsalkw.utils;


/**
 * Created by jagbirsinghkang on 18/07/17.
 */

public class Contents {

    public static String baseURL = "http://tefsalkw.com/api/";
    public static String baseVideoURL = "http://tefsalkw.com/stylevideos/";


    public static boolean isBlank(CharSequence string) {
        return (string == null || string.toString().trim().length() == 0);
    }

    public static boolean isNotMatch(CharSequence string1,CharSequence string2) {

        if(!string1.equals(string2))
        {
            return true;
        }

        return false;
    }

    public static boolean isNull(CharSequence string) {
        return (string == null || string.toString().trim().length() == 0 || string.equals("null"));
    }

    public static boolean isProperEmail(String string) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches();
    }
}
