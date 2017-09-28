package com.chuk3d;

import android.content.Context;
import android.graphics.PorterDuff;

/**
 * Created by Admin on 13/09/2017.
 */

public class PunchHole extends Hole {


    public PunchHole(int resourceId, float posX, float posY, Context context) {
        super(resourceId,posX, posY, context);
        setInitialColor(context);
    }

    @Override
    public void setColor(Context context, int color) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.transparent),PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setClickColor(Context context) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.transparent), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setInitialColor(Context context) {
        colorDrawable.mutate().setColorFilter(context.getResources().getColor(R.color.transparent),PorterDuff.Mode.SRC_IN);
    }



}