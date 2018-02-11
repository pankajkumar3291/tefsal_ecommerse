package com.tefal.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Helper class to save and retreive preferences in Android.
 */
public class PreferencesUtil {


    private static SharedPreferences getDefaultPrefs(Context c) {

        return PreferenceManager.getDefaultSharedPreferences(c);
    }


    public static <T> T get(Context context, String key, Class<T> clazz) {
        SharedPreferences prefs = getDefaultPrefs(context);
        String value = prefs.getString(key, null);
        if (value == null) {
            return null;
        }
        return new Gson().fromJson(value, clazz);
    }

    public static <T> T get(Context context, String key, Type type) {
        SharedPreferences prefs = getDefaultPrefs(context);
        String value = prefs.getString(key, null);
        if (value == null) {
            return null;
        }
        return new Gson().fromJson(value, type);
    }

    public static <T> boolean put(Context context, String key, T obj) {
        SharedPreferences prefs = getDefaultPrefs(context);
        String value = new Gson().toJson(obj);
        return prefs.edit().putString(key, value).commit();
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences prefs = getDefaultPrefs(context);
        return prefs.contains(key);
    }

    public static long getLong(Context ctx, String key, int defaultValue) {
        SharedPreferences prefs = getDefaultPrefs(ctx);
        return prefs.getLong(key, defaultValue);
    }

    public static boolean putLong(Context ctx, String key, long value) {
        SharedPreferences prefs = getDefaultPrefs(ctx);
        return prefs.edit().putLong(key, value).commit();
    }

    public static void remove(Context ctx, String key) {
        SharedPreferences prefs = getDefaultPrefs(ctx);
        prefs.edit().remove(key).apply();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences prefs = getDefaultPrefs(ctx);
        return prefs.getString(key, defaultValue);
    }

    public static boolean putString(Context ctx, String key, String value) {
        SharedPreferences prefs = getDefaultPrefs(ctx);
        return prefs.edit().putString(key, value).commit();
    }

    public static boolean getBool(Context ctx, String key, boolean defaultValue) {
        SharedPreferences prefs = getDefaultPrefs(ctx);
        return prefs.getBoolean(key, defaultValue);
    }

    public static boolean putBool(Context ctx, String key, boolean value) {
        SharedPreferences prefs = getDefaultPrefs(ctx);
        return prefs.edit().putBoolean(key, value).commit();
    }

}
