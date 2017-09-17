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

public class PunchText extends Shape{
    private Context context;
    private float posX, posY;
    private float scaleFactor = 1.f;
    private float angle;
    private StaticLayout sl;
    private float textPivotx;
    private float textPivoty;
    private TextPaint textPaint;
    private String tag;
    EmbossMaskFilter filter;

    int heightScreen;
    int widthScreen;

    public PunchText(String text, Context context, String tag, float posX, float posY) {
        super(posX, posY);
        this.tag = tag;
        this.context = context;
        this.posX = posX;
        this.posY = posY;

        heightScreen = context.getResources().getDisplayMetrics().heightPixels;
        widthScreen = context.getResources().getDisplayMetrics().widthPixels;

        textPaint = new TextPaint();

        filter = new EmbossMaskFilter(new float[]{0.0f, -1.0f, 0.5f}, 0.8f, 15f, 1f);
        textPaint.setMaskFilter(filter);

        textPaint.setColor(context.getResources().getColor(R.color.almostWhite));
        Typeface punchTf = Typeface.createFromAsset(context.getAssets(), "BalooBhaijaan-Regular.ttf");
        textPaint.setTypeface(punchTf);


        textPaint.setAntiAlias(true);
        textPaint.setTextSize(100);
        sl = new StaticLayout(text, textPaint,800,
                Layout.Alignment.ALIGN_CENTER, 1f,0f,false);
        textPivotx = sl.getWidth()/2;
        textPivoty = sl.getHeight()/2;

        setInitialFont();

    }

    public PunchText(String text, Context context, float posX, float posY, float scaleFactor, float angle, String tag) {
        super(posX, posY, scaleFactor, angle, tag);
        this.tag = tag;
        this.context = context;
        this.posX = posX;
        this.posY = posY;

        heightScreen = context.getResources().getDisplayMetrics().heightPixels;
        widthScreen = context.getResources().getDisplayMetrics().widthPixels;


        textPaint = new TextPaint();
        EmbossMaskFilter filter =
                new EmbossMaskFilter(new float[]{0.0f, -1.0f, 0.5f}, 0.8f, 15f, 1f);
        textPaint.setMaskFilter(filter);

        textPaint.setColor(context.getResources().getColor(R.color.almostWhite));
        Typeface punchTf = Typeface.createFromAsset(context.getAssets(), "BalooBhaijaan-Regular.ttf");
        textPaint.setTypeface(punchTf);

        textPaint.setAntiAlias(true);
        textPaint.setTextSize(100);
        sl = new StaticLayout(text, textPaint,800,
                Layout.Alignment.ALIGN_CENTER, 1f,0f,false);
        textPivotx = sl.getWidth()/2;
        textPivoty = sl.getHeight()/2;
    }


    public String getTag() {
        return tag;
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
        textPaint.setColor(context.getResources().getColor(R.color.almostWhite));
    }

    @Override
    public void setGrayColor(Context context) {
        textPaint.setColor(context.getResources().getColor(R.color.background));
    }

    public void setInitialFont(){
        if(DesignActivity.PcurrentFont != null){
            textPaint.setTypeface(DesignActivity.PcurrentFont);
        }
    }

    public void setFont(Typeface typeface){
        textPaint.setTypeface(typeface);
    }



    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(posX, posY);
        canvas.scale(scaleFactor, scaleFactor, textPivotx, textPivoty);
        canvas.rotate(angle,textPivotx, textPivoty);
        sl.draw(canvas);
        canvas.restore();
    }

}