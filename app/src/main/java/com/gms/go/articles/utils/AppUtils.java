package com.gms.go.articles.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.gms.go.articles.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AppUtils {

    public static String formatDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());
            Date date = inputFormat.parse(dateString);

            return date != null ? outputFormat.format(date) : "";
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void saveViewMode(Context context, boolean isGrid) {
        SharedPreferences prefs = context.getSharedPreferences(R.string.app_name + "_app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("isGridMode", isGrid).apply();
    }

    public static boolean getSavedViewMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(R.string.app_name + "_app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("isGridMode", false);
    }
}
