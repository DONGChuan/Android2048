package com.example.chuan.android2048.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ScoreMaster {

    private final static String SHARED_PREF = "com.exemple.chuan.android2048";
    private final static String KEY_SCORE = "score";

    public static void saveScore(Context context, int currentScore) {
        int bestScore = readScore(context);
        if (currentScore > bestScore) {
            SharedPreferences sharedPref =
                    context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(KEY_SCORE, currentScore);
            editor.commit();
        }
    }

    public static int readScore(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPref.getInt(KEY_SCORE, 0);
    }
}
