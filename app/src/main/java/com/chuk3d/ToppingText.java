package com.chuk3d;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import static com.chuk3d.R.attr.alpha;
import static java.security.AccessController.getContext;

/**
 * Created by Admin on 27/08/2017.
 */

public class ToppingText extends Text{


    public ToppingText(String text, float posX, float posY, Context context) {
        super(text, posX, posY, context);
        setInitialColor(context);
    }

    @Override
    public void setColor(Context context, int color) {
        int baseColor = context.getResources().getColor(R.color.transBaseShapeFirstColor);
        if(color == baseColor){
            textPaint.setColor(context.getResources().getColor(R.color.baseShapeFirstColor));
        }else{
            textPaint.setColor(color);
        }
    }

    @Override
    public void setClickColor(Context context) {
        textPaint.setColor(Color.parseColor("#f9f8f8"));
    }

    @Override
    public void setInitialColor(Context context) {
        textPaint.setShadowLayer(7, 1, 3, Color.parseColor("#80000000"));
        Typeface toppingTf = DesignActivity.currentFont;
        textPaint.setTypeface(toppingTf);

        textPaint.setAntiAlias(true);
        int baseColor = context.getResources().getColor(R.color.transBaseShapeFirstColor);
        if(DesignActivity.currentColor == baseColor){
            textPaint.setColor(context.getResources().getColor(R.color.baseShapeFirstColor));
        }else{
            textPaint.setColor(DesignActivity.currentColor);
        }

    }

    @Override
    public void setGrayColor(Context context) {
        textPaint.setColor(context.getResources().getColor(R.color.buttonGray));
    }

    public void setFont(Typeface typeface){
        textPaint.setTypeface(typeface);
    }



}