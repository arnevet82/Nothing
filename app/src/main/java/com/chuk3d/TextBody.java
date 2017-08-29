package com.chuk3d;

import android.content.Context;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import static java.security.AccessController.getContext;

/**
 * Created by Admin on 27/08/2017.
 */

public class TextBody {
    private Context context;
    private float posX, posY;
    private float scaleFactor = 1.f;
    private float angle;
    private StaticLayout sl;
    private float textPivotx;
    private float textPivoty;
    private TextPaint textPaint;
    private String tag;

    int heightScreen;
    int widthScreen;


    public TextBody(String text, Context context, String tag) {
        this.tag = tag;
        this.context = context;

        heightScreen = context.getResources().getDisplayMetrics().heightPixels;
        widthScreen = context.getResources().getDisplayMetrics().widthPixels;


        textPaint = new TextPaint();
        switch (tag){
            case "punch":
                MaskFilter filter =
                        new EmbossMaskFilter(new float[]{0.0f, -1.0f, 0.5f}, 0.8f, 15f, 1f);
                textPaint.setMaskFilter(filter);

                textPaint.setColor(context.getResources().getColor(R.color.background));

                break;
            case "topping":

            if(DesignActivity.currentColor!= 0){
                textPaint.setColor(context.getResources().getColor(DesignActivity.currentColor));
            }else{
                textPaint.setColor(context.getResources().getColor(R.color.baseShapeFirstColor));
            }
                textPaint.setShadowLayer(7, 1, 3, Color.parseColor("#80000000"));

                break;
            default:
                break;
        }

        textPaint.setAntiAlias(true);
        textPaint.setTextSize(80);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "Montserrat-ExtraBold.ttf");
        textPaint.setTypeface(tf);

        sl = new StaticLayout(text, textPaint,300,
                Layout.Alignment.ALIGN_CENTER, 1f,0f,false);
        textPivotx = sl.getWidth()/2;
        textPivoty = sl.getHeight()/2;
        posX = widthScreen/5;
        posY = heightScreen/4;
    }

    public String getTag() {
        return tag;
    }

    public void setTAG(String tag) {
        this.tag = tag;
    }

    public Paint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(TextPaint textPaint) {
        this.textPaint = textPaint;
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

    public StaticLayout getSl() {
        return sl;
    }

    public void setSl(StaticLayout sl) {
        this.sl = sl;
    }

    public float getTextPivotx() {
        return textPivotx;
    }

    public void setTextPivotx(float textPivotx) {
        this.textPivotx = textPivotx;
    }

    public float getTextPivoty() {
        return textPivoty;
    }

    public void setTextPivoty(float textPivoty) {
        this.textPivoty = textPivoty;
    }
}
