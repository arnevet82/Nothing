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

public class ToppingText extends Shape{
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

    public ToppingText(String text, Context context, String tag, float posX, float posY) {
        super(posX, posY);
        this.tag = tag;
        this.context = context;
        this.posX = posX;
        this.posY = posY;

        textPaint = new TextPaint();

        setInitialColor(context);
        textPaint.setShadowLayer(7, 1, 3, Color.parseColor("#80000000"));
        Typeface toppingTf = Typeface.createFromAsset(context.getAssets(), "Pacifico-Regular.ttf");
        textPaint.setTypeface(toppingTf);

        textPaint.setAntiAlias(true);
        textPaint.setTextSize(100);
        sl = new StaticLayout(text, textPaint,800,
                Layout.Alignment.ALIGN_CENTER, 1f,0f,false);
        textPivotx = sl.getWidth()/2;
        textPivoty = sl.getHeight()/2;
    }

    public ToppingText(String text, Context context, float posX, float posY, float scaleFactor, float angle, String tag) {
        super(posX, posY, scaleFactor, angle, tag);
        this.tag = tag;
        this.context = context;
        this.posX = posX;
        this.posY = posY;

        textPaint = new TextPaint();

        setInitialColor(context);
        textPaint.setShadowLayer(7, 1, 3, Color.parseColor("#80000000"));
        Typeface toppingTf = Typeface.createFromAsset(context.getAssets(), "Pacifico-Regular.ttf");
        textPaint.setTypeface(toppingTf);

        textPaint.setAntiAlias(true);
        textPaint.setTextSize(100);
        sl = new StaticLayout(text, textPaint,800,
                Layout.Alignment.ALIGN_CENTER, 1f,0f,false);
        textPivotx = sl.getWidth()/2;
        textPivoty = sl.getHeight()/2;

        setInitialFont();
    }


    public String getTag() {
        return tag;
    }

    public Paint getTextPaint() {
        return textPaint;
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
        textPaint.setColor(color);
    }

    @Override
    public void setClickColor(Context context) {
        textPaint.setColor(Color.parseColor("#f9f8f8"));
    }

    @Override
    public void setInitialColor(Context context) {
        textPaint.setColor(DesignActivity.currentColor);
    }

    @Override
    public void setGrayColor(Context context) {
        textPaint.setColor(context.getResources().getColor(R.color.background));
    }

    public void setInitialFont(){
        if(DesignActivity.TcurrentFont != null){
            textPaint.setTypeface(DesignActivity.TcurrentFont);
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