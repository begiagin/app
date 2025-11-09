package com.halata.blueapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.halata.blueapp.data.models.BasicSetting;
import com.halata.blueapp.viewmodels.LogViewModel;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsHelper {
    private static final String PREFS_NAME = "PuladNegaroid";
    private static final String USER_KEY = "pulad_data";
    public static LogViewModel logViewModel;

    public static void SaveSettings(Context context, BasicSetting setting){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);

        Gson json = new Gson();
        String jsonString  = json.toJson(setting);

        prefs.edit().putString(USER_KEY, jsonString).apply();
    }

    public static BasicSetting LoadSettings(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String onlineSettings = prefs.getString(USER_KEY, null);
        BasicSetting setting = new BasicSetting();
        if(onlineSettings != null){
            Gson json = new Gson();
            setting = json.fromJson(onlineSettings, BasicSetting.class);
        }

        return setting;

    }

    // Save to file
    public static void saveToFile(Context context, BasicSetting setting, String filename, LogViewModel model) {
        Gson gson = new Gson();
        String json = gson.toJson(setting);

        try (FileWriter file = new FileWriter(new File(context.getFilesDir(), filename))) {

            file.write(json);
        } catch (IOException e) {
            Toast.makeText(context, "file.json not found", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    // Load from file
    public static BasicSetting loadFromFile(Context context, String filename, LogViewModel model) {
        File file = new File(context.getFilesDir(), filename);
        //Toast.makeText(context, "Setting File not found!", Toast.LENGTH_LONG).show();


        if (!file.exists()){
            model.setText("file not found");
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            BasicSetting tempSetting = gson.fromJson(reader, BasicSetting.class);
            SaveSettings(context, tempSetting);
            return tempSetting;
        } catch (IOException e) {
            Toast.makeText(context,"load Setting file failed .", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }

    public static BasicSetting loadFromFile(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        //Toast.makeText(context, "Setting File not found!", Toast.LENGTH_LONG).show();
        if (!file.exists()){
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            BasicSetting tempSetting = gson.fromJson(reader, BasicSetting.class);

            return tempSetting;
        } catch (IOException e) {
            //Toast.makeText(context,"load Setting file failed .", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }

}
