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
    public static float pivotx;
    public static float pivoty;

    public Shape(int resourceId, float posX, float posY, Context context) {
        super(posX, posY);
        Drawable drawable = ContextCompat.getDrawable(context, resourceId);
        Drawable colorDrawable = ContextCompat.getDrawable(context, resourceId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        colorDrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        this.drawable = drawable;
        this.colorDrawable = colorDrawable;
        pivotx = drawable.getIntrinsicWidth()/2;
        pivoty = drawable.getIntrinsicHeight()/2;
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

    @Override
    public void setGrayColor(Context context) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.editGraysmallShape), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(posX, posY);
        canvas.scale(scaleFactor, scaleFactor, pivotx, pivoty);
        canvas.rotate(angle,pivotx, pivoty);
        drawable.draw(canvas);
        colorDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean isClicked(MotionEvent event, float mScaleFactor, Context context) {

        float currentScaleFactor = Math.max(1f, Math.min(mScaleFactor, 1.3f));

        float x = posX*0.9f;
        float y = posY;
        float xEnd = (posX + context.getResources().getDimension(R.dimen.punch_size))*currentScaleFactor;
        float yEnd = (posY + context.getResources().getDimension(R.dimen.punch_size))*currentScaleFactor;

        if ((event.getX() >= x && event.getX() <= xEnd)
                && (event.getY() >= y && event.getY() <= yEnd)){

            if (currentScaleFactor != 0) {

                return true;
            } else {
                return false;
            }
        }

        return false;
    }
}