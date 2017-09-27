package com.chuk3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Admin on 18/09/2017.
 */

public abstract class Text extends Movable {

    protected StaticLayout sl;
    protected float pivotx;
    protected float pivoty;
    protected TextPaint textPaint;
    protected String text;

    public Text(String text, float posX, float posY, Context context) {
        super(posX, posY);
        this.text = text;

        textPaint = new TextPaint();

        setInitialColor(context);

        textPaint.setTextSize(120);
        sl = new StaticLayout(text, textPaint,800,
                Layout.Alignment.ALIGN_CENTER, 1f,0f,false);
        pivotx = sl.getWidth()/2;
        pivoty = sl.getHeight()/2;

        deltaStartX = 0;
        deltaEndX = sl.getWidth();
        deltaStartY = 0;
        deltaEndY = sl.getHeight();

    }

    public StaticLayout getSl() {
        return sl;
    }

    public void setSl(StaticLayout sl) {
        this.sl = sl;
    }
    @Override
    public float getPivotx() {
        return pivotx;
    }

    public void setPivotx(float pivotx) {
        this.pivotx = pivotx;
    }
    @Override
    public float getPivoty() {
        return pivoty;
    }

    public void setPivoty(float pivoty) {
        this.pivoty = pivoty;
    }

    public TextPaint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(TextPaint textPaint) {
        this.textPaint = textPaint;
    }

    public void setFont(Typeface typeface){
        textPaint.setTypeface(typeface);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(posX, posY);
        canvas.scale(scaleFactor, scaleFactor, pivotx, pivoty);
        canvas.rotate(angle,pivotx, pivoty);
        sl.draw(canvas);
        canvas.restore();
    }

    @Override
    public void draw(Canvas canvas, int t) {
        canvas.save();
        canvas.translate(posX, posY);
        canvas.scale(scaleFactor, scaleFactor, pivotx, pivoty);
        canvas.rotate(angle,pivotx, pivoty);
        sl.draw(canvas);
        canvas.restore();
    }


    @Override
    public boolean isClicked(MotionEvent event, float mScaleFactor, Context context) {

        float x = posX - deltaStartX;
        float y = posY - deltaStartY;
        float xEnd = posX + deltaEndX;
        float yEnd = posY + deltaEndY;

//        float currentScaleFactor = Math.max(1f, Math.min(mScaleFactor, 1.3f));

//        float xEnd = posX + currentScaleFactor*600;
//        float yEnd = posY + currentScaleFactor*300;

        if ((event.getX() >= x && event.getX() <= (xEnd))
                && (event.getY() >= y && event.getY() <= yEnd)) {

            if (scaleFactor != 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }


}
