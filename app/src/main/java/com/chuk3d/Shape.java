package com.chuk3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by Admin on 17/08/2017.
 */

public abstract class Shape {

    public static float pivotx;
    public static float pivoty;

    protected Drawable drawable;
    protected Drawable colorDrawable;
    protected float posX, posY;
    protected float scaleFactor = 1.f;
    protected float angle;
    protected String tag;

    public Shape(Drawable drawable, Drawable colorDrawable, float posX, float posY){
        this.drawable = drawable;
        this.colorDrawable = colorDrawable;
        this.posX = posX;
        this.posY = posY;
        pivotx = drawable.getIntrinsicWidth()/2;
        pivoty = drawable.getIntrinsicHeight()/2;
    }

    public Shape(Drawable drawable, Drawable colorDrawable, float posX, float posY, float scaleFactor, float angle, String tag){
        this.drawable = drawable;
        this.colorDrawable = colorDrawable;
        this.posX = posX;
        this.posY = posY;
        this.scaleFactor = scaleFactor;
        this.angle = angle;
        this.tag = tag;
        pivotx = drawable.getIntrinsicWidth()/2;
        pivoty = drawable.getIntrinsicHeight()/2;
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

    public void setColor(int color){}

    public void setClickColor(Context context){}

    public void setInitialColor(Context context){}

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(posX, posY);
        canvas.scale(scaleFactor, scaleFactor, pivotx, pivoty);
        canvas.rotate(angle,pivotx, pivoty);
        drawable.draw(canvas);
        colorDrawable.draw(canvas);
        canvas.restore();
    }
}