package com.chuk3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

/**
 * Created by Admin on 27/08/2017.
 */

public class PunchText extends Text{


    public PunchText(String text, float posX, float posY, Context context) {
        super(text, posX, posY, context);
        setInitialColor(context);
    }

    @Override
    public void setColor(Context context, int color) {
        textPaint.setColor(context.getResources().getColor(R.color.almostWhite));
    }

    @Override
    public void setClickColor(Context context) {
        textPaint.setColor(Color.parseColor("#f9f8f8"));
    }

    @Override
    public void setInitialColor(Context context) {

        EmbossMaskFilter filter = new EmbossMaskFilter(new float[]{0.0f, -1.0f, 0.5f}, 0.8f, 15f, 1f);
        textPaint.setMaskFilter(filter);

        textPaint.setColor(context.getResources().getColor(R.color.almostWhite));

        textPaint.setTypeface(DesignActivity.currentFont);

        textPaint.setAntiAlias(true);
        textPaint.setColor(DesignActivity.currentColor);
        textPaint.setColor(context.getResources().getColor(R.color.almostWhite));

    }

    @Override
    public void setGrayColor(Context context) {
        textPaint.setColor(context.getResources().getColor(R.color.buttonGray));
    }

    public void setFont(Typeface typeface){
        textPaint.setTypeface(typeface);
    }


}