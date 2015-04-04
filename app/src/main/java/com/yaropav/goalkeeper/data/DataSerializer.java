package com.yaropav.goalkeeper.data;

/**
 * Created by mrbimc on 04.04.15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class DataSerializer<T> {
    private SharedPreferences prefs;
    private Gson gson;

    public final String LOG_TAG = getClass().getSimpleName();

    public DataSerializer(Context context) {
        gson = new Gson();
        prefs = context.getSharedPreferences("prefs", Context.MODE_MULTI_PROCESS);
    }


    public ArrayList<T> loadList(Class token, String key) {
        ArrayList<T> list = new ArrayList<>();
        String json = prefs.getString(key, null);
        JSONArray jsonArray = getJSONArray(json);
        if(jsonArray == null || jsonArray.length() == 0) return list;

        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                String obj = jsonArray.getJSONObject(i).toString();
                list.add((T) gson.fromJson(obj, token));
            }
            catch (Exception e) { Log.e(LOG_TAG, e.toString()); }
        }
        return list;
    }

    @Nullable
    public T loadObject(Class token, String key) {
        String json = prefs.getString(key, null);
        return (T) gson.fromJson(json, token);
    }

    public void save(ArrayList<T> data, String key) {
        String json = gson.toJson(data);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, json);
        edit.commit();
    }

    public void save(T data, String key) {
        String json = gson.toJson(data);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, json);
        edit.commit();
    }

    @Nullable
    private JSONArray getJSONArray(String json) {
        JSONArray jsonArray = null;
        try { if(json != null && json.length() != 0) jsonArray = new JSONArray(json); }
        catch (JSONException e) { Log.e(LOG_TAG, e.toString()); }
        return jsonArray;
    }
}
