package com.chuk3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by Admin on 17/08/2017.
 */

public abstract class Shape {

    protected float posX, posY;
    protected float scaleFactor = 1.f;
    protected float angle;
    protected String tag;

    public Shape(float posX, float posY){

        this.posX = posX;
        this.posY = posY;

    }

    public Shape(float posX, float posY, float scaleFactor, float angle, String tag){

        this.posX = posX;
        this.posY = posY;
        this.scaleFactor = scaleFactor;
        this.angle = angle;
        this.tag = tag;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setColor(Context context, int color){}

    public void setClickColor(Context context){}

    public void setInitialColor(Context context){}

    public void setGrayColor(Context context){}

    public void draw(Canvas canvas) {

    }
}