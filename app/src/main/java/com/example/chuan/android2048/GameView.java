package com.example.chuan.android2048;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;

import com.example.chuan.android2048.model.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chuan on 8/3/2015.
 */
public class GameView extends GridLayout {

    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<>();

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    private void initGameView() {

        setColumnCount(4); // GridLayout 4 columns
        setBackgroundColor(0xffbbada0);

        setOnTouchListener(new OnTouchListener() {

            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                swipeLeft();
                            } else {
                                swipeRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                swipeUp();
                            } else {
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h) - 10)/4;

        addCards(cardWidth, cardWidth);
        startGame();
    }

    private void addCards(int cardWidth, int cardHeight) {

        Card c;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWidth, cardHeight);

                cardsMap[x][y] = c;
            }
        }
    }

    private void addRandomNum() {

        emptyPoints.clear();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if(cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

        ScaleAnimation scaleAnimation =
                new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);

        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        // We must clean the animation before set a new one. If not,
        // user can not see the new animation
        cardsMap[p.x][p.y].clearAnimation();
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
        cardsMap[p.x][p.y].setAnimation(scaleAnimation);

    }

    /**
     * Start new Game
     */
    private void startGame() {
        // Clear score
        MainActivity.getMainActivity().clearScore();
        // Reset all the cards on game board
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    private void swipeLeft() {

        boolean merge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if(cardsMap[x1][y].getNum() > 0 ) {
                        if(cardsMap[x][y].getNum() <= 0 ){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--; // Recheck another time
                            merge =true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge =true;
                        }
                        break;
                    }
                }
            }
        }

        if(merge) {
            addRandomNum();
            checkGameFinish();
        }
    }

    private void swipeRight() {

        boolean merge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if(cardsMap[x1][y].getNum() > 0 ) {
                        if(cardsMap[x][y].getNum() <= 0 ){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++; // Recheck another time
                            merge =true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge =true;
                        }
                        break;
                    }
                }
            }
        }

        if(merge) {
            addRandomNum();
            checkGameFinish();
        }
    }

    private void swipeUp() {

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if(cardsMap[x][y1].getNum() > 0 ) {
                        if(cardsMap[x][y].getNum() <= 0 ){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--; // Recheck another time
                            merge =true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge =true;
                        }
                        break;
                    }
                }
            }
        }

        if(merge) {
            addRandomNum();
            checkGameFinish();
        }
    }

    private void swipeDown() {

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if(cardsMap[x][y1].getNum() > 0 ) {
                        if(cardsMap[x][y].getNum() <= 0 ){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++; // Recheck another time
                            merge =true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge =true;
                        }
                        break;
                    }
                }
            }
        }

        if(merge) {
            addRandomNum();
            checkGameFinish();
        }
    }

    private void checkGameFinish() {

        boolean isFinish = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if(cardsMap[x][y].getNum() == 0 ||
                        (x > 0 && cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x < 3 && cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y > 0 && cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y < 3 && cardsMap[x][y].equals(cardsMap[x][y+1]))) {
                    isFinish = false;
                    break ALL;
                }
            }
        }

        if(isFinish) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Hi")
                    .setMessage("Game is over")
                    .setPositiveButton("again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startGame();
                        }
                    }).show();
        }
    }
}

