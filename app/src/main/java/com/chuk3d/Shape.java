package com.chuk3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Admin on 17/08/2017.
 */

public abstract class Shape extends Movable {
    protected Drawable drawable;
    protected Drawable colorDrawable;
    public float pivotx;
    public float pivoty;

    //test
//    public float x;
//    public float y;
//    public float xEnd;
//    public float yEnd;


    public Shape(int resourceId, float posX, float posY, Context context) {
        super(posX, posY);
        Drawable drawable = ContextCompat.getDrawable(context, resourceId);
        Drawable colorDrawable = ContextCompat.getDrawable(context, resourceId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        colorDrawable.setBounds(0, 0, colorDrawable.getIntrinsicWidth(), colorDrawable.getIntrinsicHeight());
        this.drawable = drawable;
        this.colorDrawable = colorDrawable;
        pivotx = drawable.getIntrinsicWidth()/2;
        pivoty = drawable.getIntrinsicHeight()/2;

        deltaStartX = 0;
        deltaEndX = drawable.getIntrinsicWidth();
        deltaStartY = 0;
        deltaEndY = drawable.getIntrinsicHeight();


    }
    @Override
    public float getPivotx() {
        return pivotx;
    }
    @Override
    public float getPivoty() {
        return pivoty;
    }


    public float getWidth(){
        return drawable.getIntrinsicWidth();
    }

    public float getHeight(){
        return drawable.getIntrinsicHeight();
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public Drawable getColorDrawable() {
        return colorDrawable;
    }

    public void setColorDrawable(Drawable colorDrawable) {
        this.colorDrawable = colorDrawable;
    }

    @Override
    public void setGrayColor(Context context) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.editGraysmallShape), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(posX, posY);
        canvas.scale(scaleFactor, scaleFactor,  pivotx, pivoty);
        canvas.rotate(angle,pivotx, pivoty);
        drawable.draw(canvas);
        colorDrawable.draw(canvas);
        canvas.restore();

        //test
//        canvas.save();
//        Paint myPaint = new Paint();
//        myPaint.setStyle(Paint.Style.STROKE);
//        myPaint.setColor(Color.BLACK);
//        canvas.drawRect(x, y, xEnd, yEnd, myPaint);
//        canvas.restore();
    }

    @Override
    public void draw(Canvas canvas, int t) {
        canvas.save();
        canvas.translate(posX, posY);
        canvas.scale(scaleFactor, scaleFactor,  pivotx, pivoty);
        canvas.rotate(angle,pivotx, pivoty);
        colorDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean isClicked(MotionEvent event, float mScaleFactor, Context context) {

        float x = posX - deltaStartX;
        float y = posY - deltaStartY;
        float xEnd = posX + deltaEndX;
        float yEnd = posY + deltaEndY;


        if ((event.getX() >= x && event.getX() <= xEnd)
                && (event.getY() >= y && event.getY() <= yEnd)){

            if (scaleFactor != 0) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }



//    @Override
//    public boolean isClicked(MotionEvent event, float mScaleFactor, Context context) {
//
//        float currentScaleFactor = Math.max(1f, Math.min(mScaleFactor, 1.3f));
//
//        float x = posX*0.9f;
//        float y = posY;
//        float xEnd = (posX + context.getResources().getDimension(R.dimen.punch_size))*currentScaleFactor;
//        float yEnd = (posY + context.getResources().getDimension(R.dimen.punch_size))*currentScaleFactor;
//
//        if ((event.getX() >= x && event.getX() <= xEnd)
//                && (event.getY() >= y && event.getY() <= yEnd)){
//
//            if (currentScaleFactor != 0) {
//
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//        return false;
//    }
}