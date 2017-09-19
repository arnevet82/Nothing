package com.chuk3d;

import android.content.Context;
import android.graphics.Canvas;
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
    protected float textPivotx;
    protected float textPivoty;
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
        textPivotx = sl.getWidth()/2;
        textPivoty = sl.getHeight()/2;
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

    public TextPaint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(TextPaint textPaint) {
        this.textPaint = textPaint;
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

    @Override
    public boolean isClicked(MotionEvent event, float mScaleFactor, Context context) {

        float currentScaleFactor = Math.max(1f, Math.min(mScaleFactor, 1.3f));

        float xEnd = posX + currentScaleFactor*600;
        float yEnd = posY + currentScaleFactor*300;

        if ((event.getX() >= posX && event.getX() <= (xEnd))
                && (event.getY() >= posY && event.getY() <= yEnd)) {

            if (scaleFactor != 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }


}
