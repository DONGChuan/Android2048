package com.example.chuan.android2048;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.chuan.android2048.util.ScoreMaster;


public class MainActivity extends Activity {

    private int score = 0;
    private TextView tvScore;
    private TextView tvBest;
    private static MainActivity mainActivity;

    public MainActivity() {
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore = (TextView) findViewById(R.id.tvScore);
        tvBest = (TextView) findViewById(R.id.tvBest);
        tvBest.setText(ScoreMaster.readScore(this) + "");
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    /**
     * Clear score. Before it, check whether it's the best score.
     */
    public void clearScore() {
        ScoreMaster.saveScore(this, score);
        score = 0;
        showScore();
    }

    public void showScore() {
        tvScore.setText(score + "");
        tvBest.setText(ScoreMaster.readScore(this) + "");
    }

    public void addScore(int s) {
        score += s;
        showScore();
    }
}
