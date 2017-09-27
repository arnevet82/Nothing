package com.chuk3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Admin on 18/09/2017.
 */

public abstract class Movable {



    public static Movable current_movable = null;

    protected float posX, posY;
    protected float scaleFactor = 1.f;
    protected float angle = 0f;

    public float deltaStartX;
    public float deltaEndX;
    public float deltaStartY;
    public float deltaEndY;

    public Movable(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public float getDeltaStartX() {
        return deltaStartX;
    }

    public void setDeltaStartX(float deltaStartX) {
        this.deltaStartX = deltaStartX;
    }

    public float getDeltaEndX() {
        return deltaEndX;
    }

    public void setDeltaEndX(float deltaEndX) {
        this.deltaEndX = deltaEndX;
    }

    public float getDeltaStartY() {
        return deltaStartY;
    }

    public void setDeltaStartY(float deltaStartY) {
        this.deltaStartY = deltaStartY;
    }

    public float getDeltaEndY() {
        return deltaEndY;
    }

    public void setDeltaEndY(float deltaEndY) {
        this.deltaEndY = deltaEndY;
    }

    public static Movable getCurrent_movable(MotionEvent event, float scaleFactor, Context context) {

        if(current_movable != null){
            return current_movable;
        }else {

            for (int i = TouchView.shapes.size() - 1; i >= 0; i--) {

                Movable movable = TouchView.shapes.get(i);
                if (movable.isClicked(event, scaleFactor, context)) {
                    setCurrent_movable(movable);

                    //TODO change location
                    DesignActivity.vButton.setVisibility(View.VISIBLE);
                    ColorCommand.setGrayColor(context);

                    movable.setClickColor(context);

                    return movable;

                }
            }
        }
        return current_movable;
    }

    public static void setCurrent_movable(Movable movable) {
        current_movable = movable;
    }

    public float getWidth(){
        return 0;
    }

    public float getHeight(){
        return 0;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setColor(Context context, int color){}

    public abstract void setClickColor(Context context);

    public abstract void setInitialColor(Context context);

    public abstract void setGrayColor(Context context);

    public abstract void draw(Canvas canvas);
    public abstract void draw(Canvas canvas, int t);


    public abstract boolean isClicked(MotionEvent event, float scaleFactor, Context context);

    public abstract float getPivotx();
    public abstract float getPivoty();

}
