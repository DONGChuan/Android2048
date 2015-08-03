package com.example.chuan.android2048;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(-1, -1);
        addView(label, lp);

        setNum(0);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        label.setText(num + "");
    }

    public boolean equals(Card o) {
        return getNum() == o.getNum();
    }

    @Override
    public int hashCode() {
        return num;
    }
}
