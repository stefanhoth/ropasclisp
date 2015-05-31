package com.stefanhoth.ropasclisp;

import android.app.Application;

import com.novoda.notils.logger.simple.Log;

public class DatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.setShowLogs(true);
    }
}
