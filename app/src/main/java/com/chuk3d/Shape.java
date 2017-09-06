package com.chuk3d;

import android.graphics.drawable.Drawable;

/**
 * Created by Admin on 17/08/2017.
 */

public class Shape {

    private Drawable drawable;
    private float posX, posY;
    private float xScaleFactor = 1.f;
    private float yScaleFactor = 1.f;

    private float angle;
    private String tag;
    private float pivotX;
    private float pivotY;

    public Shape(Drawable drawable, float posX, float posY){
        this.drawable = drawable;
        this.posX = posX;
        this.posY = posY;
        pivotX = drawable.getIntrinsicWidth()/2;
        pivotY = drawable.getIntrinsicHeight()/2;

    }

    public float getPivotX() {
        return pivotX;
    }

    public void setPivotX(float pivotX) {
        this.pivotX = pivotX;
    }

    public float getPivotY() {
        return pivotY;
    }

    public void setPivotY(float pivotY) {
        this.pivotY = pivotY;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
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

    public float getxScaleFactor() {
        return xScaleFactor;
    }

    public void setxScaleFactor(float xScaleFactor) {
        this.xScaleFactor = xScaleFactor;
    }

    public float getyScaleFactor() {
        return yScaleFactor;
    }

    public void setyScaleFactor(float yScaleFactor) {
        this.yScaleFactor = yScaleFactor;
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
