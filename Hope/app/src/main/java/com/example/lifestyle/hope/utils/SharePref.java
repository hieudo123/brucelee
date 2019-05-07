package com.example.lifestyle.hope.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.lifestyle.hope.Models.Users;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

public class SharePref {
    public static final String DB_PREFS = "RememberLogin";
    public Context activity;

    public SharePref(Context activity) {
        this.activity = activity;
    }

    public void putBoolean(String name, Boolean data) {
        SharedPreferences shared = activity.getSharedPreferences(DB_PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(name, data);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        SharedPreferences shared = activity.getSharedPreferences(DB_PREFS, Activity.MODE_PRIVATE);
        return shared.getBoolean(key, defValue);
    }

    public void putString(String name, String data) {
        SharedPreferences shared = activity.getSharedPreferences(DB_PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(name, data);
        editor.commit();

    }

    public String getString(String key, String defValue) {
        SharedPreferences shared = activity
                .getSharedPreferences(DB_PREFS, Activity.MODE_PRIVATE);
        return shared.getString(key, defValue);
    }

    public void putInt(String name, int value) {
        SharedPreferences shared = activity
                .getSharedPreferences(DB_PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public int getInt(String name, int defValue) {
        SharedPreferences shared = activity
                .getSharedPreferences(DB_PREFS, Activity.MODE_PRIVATE);
        return shared.getInt(name, defValue);

    }

    public void remove(String name) {
        SharedPreferences shared = activity
                .getSharedPreferences(DB_PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.remove(name);
        editor.commit();
    }

    public void clear() {
        SharedPreferences shared = activity
                .getSharedPreferences(DB_PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.commit();
    }

    public Users getUser() {
        SharedPreferences preferences = activity
                .getSharedPreferences(DB_PREFS, Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("user", "");
        Users user = gson.fromJson(json, Users.class);
        return user;
    }

    public Users putUser(Users user) {
        SharedPreferences preferences = activity.getSharedPreferences(DB_PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.commit();
        return user;
    }

    public JSONObject getJSONObject(String key, JSONObject defValue) {
        SharedPreferences shared = activity
                .getSharedPreferences(DB_PREFS, Activity.MODE_PRIVATE);
        try {
            return new JSONObject(
                    shared.getString(key, defValue.toString()));
        } catch (JSONException e) {
            return defValue;
        }
    }

    public void putJSONObject(String key, JSONObject value) {
        putString(key, value.toString());
    }
}
