package com.chuk3d;

import android.content.Context;
import android.graphics.PorterDuff;

/**
 * Created by Admin on 13/09/2017.
 */

public class ToppingHole extends Hole {



    public ToppingHole(int resourceId, float posX, float posY, Context context) {
        super(resourceId, posX, posY, context);
    }


    @Override
    public void setColor(Context context, int color) {
        colorDrawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setClickColor(Context context) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.almostWhite),PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setInitialColor(Context context) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.transBaseShapeFirstColor),PorterDuff.Mode.SRC_IN);
    }

}