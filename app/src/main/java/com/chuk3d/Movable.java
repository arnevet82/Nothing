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

    public Movable(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public static Movable getCurrent_movable(MotionEvent event, float scaleFactor, Context context) {

        if(current_movable != null){
            return current_movable;
        }else {

            for (int i = TouchView.shapes.size() - 1; i >= 0; i--) {

                Movable movable = TouchView.shapes.get(i);
                if (movable.isClicked(event, scaleFactor, context)) {
                    setCurrent_movable(movable);

                    DesignActivity.vButton.setVisibility(View.VISIBLE);
                    try{
                        DesignActivity.colorImage.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.editGrayBigShape), PorterDuff.Mode.SRC_IN);
                    }catch (NullPointerException e){

                    }
                    for(Movable mMovable:TouchView.shapes){
                        mMovable.setGrayColor(context);
                    }
                    movable.setClickColor(context);
                    if(movable instanceof Text){
                        DesignActivity.fontsBar.setVisibility(View.VISIBLE);
                    }

                    return movable;

                }
            }
        }
        return current_movable;
    }

    public static void setCurrent_movable(Movable current_movable) {
        Movable.current_movable = current_movable;
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

    public abstract boolean isClicked(MotionEvent event, float scaleFactor, Context context);
}
