package com.chuk3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/**
 * Created by Admin on 13/09/2017.
 */

public class PunchShape extends Shape {

    protected Drawable drawable;
    protected Drawable colorDrawable;
    public static float pivotx;
    public static float pivoty;

    public PunchShape(Drawable drawable, Drawable colorDrawable, float posX, float posY) {
        super(posX, posY);
        this.drawable = drawable;
        this.colorDrawable = colorDrawable;
        pivotx = drawable.getIntrinsicWidth()/2;
        pivoty = drawable.getIntrinsicHeight()/2;
    }

    public PunchShape(Drawable drawable, Drawable colorDrawable, float posX, float posY, float scaleFactor, float angle, String tag) {
        super(posX, posY, scaleFactor, angle, tag);
        this.drawable = drawable;
        this.colorDrawable = colorDrawable;
        pivotx = drawable.getIntrinsicWidth()/2;
        pivoty = drawable.getIntrinsicHeight()/2;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.colorDrawable = drawable;
    }

    @Override
    public void setColor(Context context, int color) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.transparent),PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setGrayColor(Context context) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.editGraysmallShape),PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setClickColor(Context context) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.transparent), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setInitialColor(Context context) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.transparent),PorterDuff.Mode.SRC_IN);
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

}