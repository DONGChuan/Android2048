package com.example.chuan.android2048.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ScoreMaster {

    private final static String SHARED_PREF = "com.exemple.chuan.android2048";
    private final static String KEY_SCORE = "score";

    public static void saveScore(Context context, String currentScore) {
        String bestScore = readScore(context);
        if (Integer.valueOf(currentScore).intValue() > Integer.valueOf(bestScore).intValue()) {
            SharedPreferences sharedPref =
                    context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(KEY_SCORE, currentScore);
            editor.commit();
        }
    }

    public static String readScore(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_SCORE, "0");
    }
}
