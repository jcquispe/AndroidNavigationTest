package com.muvlin.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesManager {
    public static final String MyPREFERENCES = "MySettings" ;
    public static final String Margen = "margenKey";
    public static final String Total = "totalKey";

    private static final SharedPreferencesManager ourInstance = new SharedPreferencesManager();

    public static SharedPreferencesManager getInstance() {
        return ourInstance;
    }

    private SharedPreferencesManager() {
    }

    private SharedPreferences sharedpreferences;

    public void init(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public String getValueByKey(String key) {
        return sharedpreferences.getString(key, "");
    }

    public Float getMargen() {
        return sharedpreferences.getFloat(Margen, 0);
    }

}
