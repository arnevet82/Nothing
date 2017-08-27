package com.chuk3d;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;

/**
 * Created by Admin on 16/08/2017.
 */

public class HamburgerDrawable extends DrawerArrowDrawable {

    public HamburgerDrawable(Context context){
        super(context);
        setColor(context.getResources().getColor(R.color.darkGray));
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        setBarLength(57.0f);
        setBarThickness(6.5f);
        setGapSize(13.0f);

    }
}