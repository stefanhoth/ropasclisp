package com.stefanhoth.ropasclisp.achievements;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public final class DAILY_STATE {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
    public static final int NO_STARTS = 0;

    public static DAILY_STATE with(Context context) {
        return new DAILY_STATE(context);
    }

    private static String PREFERENCE_NAME = "DAILY_STATE";
    private static String KEY_TODAYS_DATE = DAILY_STATE.class.getName() + ".TODAYS_DATE";
    private static String KEY_TODAYS_STARTS = DAILY_STATE.class.getName() + ".TODAYS_STARTS";

    private final Context context;

    private DAILY_STATE(Context context) {
        this.context = context;
    }

    public boolean isTodaysFirstStart() {

        if(! hasPreviousEntriesFromToday()){
            return false;
        }

        return NO_STARTS == getTodaysStarts();
    }

    int getTodaysStarts() {
        return getSharedPreferences().getInt(KEY_TODAYS_STARTS, NO_STARTS);
    }

    public void countAppStart() {
        SharedPreferences preferences = getSharedPreferences();
        int starts = getTodaysStarts();

        preferences.edit()
                .putInt(KEY_TODAYS_STARTS, starts + 1)
                .apply();
    }

    private synchronized SharedPreferences getSharedPreferences() {

        return context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
    }

    private boolean hasPreviousEntriesFromToday() {

        String currentDate = getCurrentDateString();
        SharedPreferences preferences = getSharedPreferences();

        if(currentDate.equals(preferences.getString(KEY_TODAYS_DATE, "NEVER_MATCH"))){
            return true;
        }

        cleanupOldData(preferences);
        createNewDayEntry(preferences);
        return false;
    }

    private void createNewDayEntry(SharedPreferences preferences) {
        preferences.edit()
                .putString(KEY_TODAYS_DATE, getCurrentDateString())
                .apply();
    }

    private String getCurrentDateString() {

        return DATE_FORMAT.format(new Date());
    }

    private void cleanupOldData(SharedPreferences preferences) {

        preferences.edit()
                .clear()
                .apply();
    }

}
