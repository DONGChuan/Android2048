package com.example.chuan.android2048.model;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.chuan.android2048.R;

/**
 * Created by Chuan on 8/3/2015.
 */
public class Card extends FrameLayout {

    private int num;
    private TextView label;

    public Card(Context context) {
        super(context);

        label = new TextView(getContext());
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);
        label.setBackgroundResource(R.color.card_null);

        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);

        setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        if (num <= 0) {
            label.setText("");
        }else{
            label.setText(num + "");
        }
        setCardColor();
    }

    /**
     * Sets card background color according to its num
     */
    private void setCardColor() {
        switch (num){
            case 2:
                label.setBackgroundResource(R.color.card_2);
                break;
            case 4:
                label.setBackgroundResource(R.color.card_4);
                break;
            case 8:
                label.setBackgroundResource(R.color.card_8);
                break;
            case 16:
                label.setBackgroundResource(R.color.card_16);
                break;
            case 32:
                label.setBackgroundResource(R.color.card_32);
                break;
            case 64:
                label.setBackgroundResource(R.color.card_64);
                break;
            case 128:
                label.setBackgroundResource(R.color.card_128);
                break;
            case 256:
                label.setBackgroundResource(R.color.card_256);
                break;
            case 512:
                label.setBackgroundResource(R.color.card_512);
                break;
            case 1024:
                label.setBackgroundResource(R.color.card_1024);
                break;
            case 2048:
                label.setBackgroundResource(R.color.card_2048);
                break;
            case 4096:
                label.setBackgroundResource(R.color.card_4096);
                break;
            default:
                label.setBackgroundResource(R.color.card_null);
                break;
        }
    }

    public boolean equals(Card o) {
        return getNum() == o.getNum();
    }

    @Override
    public int hashCode() {
        return num;
    }
}
