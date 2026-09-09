package com.medipredict.ai.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static final String PREF_NAME = "MediPredictPrefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";

    private final SharedPreferences prefs;

    public PrefManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUser(String uid, String name, String email) {
        prefs.edit()
                .putString(KEY_UID, uid)
                .putString(KEY_NAME, name)
                .putString(KEY_EMAIL, email)
                .apply();
    }

    public String getUid() {
        return prefs.getString(KEY_UID, null);
    }

    public String getName() {
        return prefs.getString(KEY_NAME, "User");
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "");
    }

    public boolean isLoggedIn() {
        return getUid() != null;
    }

    public void logout() {
        prefs.edit().clear().apply();
    }
}
