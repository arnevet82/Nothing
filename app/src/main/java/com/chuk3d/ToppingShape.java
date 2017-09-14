package com.chuk3d;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/**
 * Created by Admin on 13/09/2017.
 */

public class ToppingShape extends Shape {
    public ToppingShape(Drawable drawable, Drawable colorDrawable, float posX, float posY) {
        super(drawable, colorDrawable, posX, posY);
    }

    public ToppingShape(Drawable drawable, Drawable colorDrawable, float posX, float posY, float scaleFactor, float angle, String tag) {
        super(drawable, colorDrawable, posX, posY, scaleFactor, angle, tag);
    }

    @Override
    public void setColor(int color) {
        colorDrawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setClickColor(Context context) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.almostWhite),PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setInitialColor(Context context) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);
    }
}