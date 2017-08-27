package com.chuk3d;

import android.graphics.drawable.Drawable;

/**
 * Created by Admin on 17/08/2017.
 */

public class Shape {

    private Drawable drawable;
    private Drawable colorDrawable;
    private float posX, posY;
    private float scaleFactor = 1.f;
    private float angle;
    private String tag;


    public Shape(Drawable drawable, float posX, float posY){
        this.drawable = drawable;
        this.posX = posX;
        this.posY = posY;
    }


    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.colorDrawable = drawable;
    }
    public Drawable getColorDrawable() {
        return colorDrawable;
    }

    public void setColorDrawable(Drawable colorDrawable) {
        this.colorDrawable = colorDrawable;
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
}
