package com.chuk3d;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by Admin on 17/08/2017.
 */

public class TouchView extends View {

    public Drawable heartDrawable;

    public static LinkedList<Shape> shapes = new LinkedList<>();
    public static LinkedList<Shape> shapesForColor = new LinkedList<>();

    public static LinkedList<TextBody> texts = new LinkedList<>();


    private float mPosX;
    private float mPosY;

    private float mLastTouchX;
    private float mLastTouchY;

    private float heightScreen;
    private float widthScreen;

    private float pivotx;
    private float pivoty;

    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    public static int CURRENT_SHAPE = -1;
    public static int CURRENT_TEXT = -1;

    public static float textScaleFactor = 1.f;
    private ScaleGestureDetector textScaleDetector;


    public TouchView(Context context) {
        this(context, null, 0);
    }

    public TouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        setLayoutParams();


    }

    public void setLayoutParams(){

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)widthScreen, (int)heightScreen);

        lp.addRule(Gravity.CENTER);

        setLayoutParams(lp);
    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i = 0; i < shapesForColor.size(); i++){
            canvas.save();
            canvas.translate(shapes.get(i).getPosX(), shapes.get(i).getPosY());
            canvas.scale(shapes.get(i).getScaleFactor(), shapes.get(i).getScaleFactor(), pivotx, pivoty);
            canvas.rotate(shapes.get(i).getAngle(),pivotx, pivoty);
            shapes.get(i).getDrawable().draw(canvas);
            shapesForColor.get(i).getDrawable().draw(canvas);
            canvas.restore();
        }

        for(int i = 0; i < texts.size(); i++){
            canvas.save();
            canvas.translate(texts.get(i).getPosX(), texts.get(i).getPosY());
            canvas.scale(texts.get(i).getScaleFactor(), texts.get(i).getScaleFactor(),texts.get(i).getTextPivotx(), texts.get(i).getTextPivoty());
            canvas.rotate(texts.get(i).getAngle(),texts.get(i).getTextPivotx(), texts.get(i).getTextPivoty());
            texts.get(i).getSl().draw(canvas);
            canvas.restore();
        }

    }

    private boolean clickOnShape(Shape shape, MotionEvent event) {

        float scaleFactor = shape.getScaleFactor();
        scaleFactor = Math.max(1f, Math.min(mScaleFactor, 1.3f));

        float x = (shape.getPosX()*0.9f);
        float y = shape.getPosY();
        float xEnd = (shape.getPosX() + getResources().getDimension(R.dimen.punch_size))*scaleFactor;
        float yEnd = (shape.getPosY() + getResources().getDimension(R.dimen.punch_size))*scaleFactor;

        if ((event.getX() >= x && event.getX() <= xEnd)
                && (event.getY() >= y && event.getY() <= yEnd)){

            if (!(shape.getDrawable().getIntrinsicWidth() == 0)) {

                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    private boolean clickOnText(TextBody textBody, MotionEvent event) {

        float xEnd = textBody.getPosX() + textScaleFactor*200;
        float yEnd = textBody.getPosY() + textScaleFactor*100;

        if ((event.getX() >= (textBody.getPosX()) && event.getX() <= (xEnd))
                && (event.getY() >= (textBody.getPosY()) && event.getY() <= yEnd)) {

            if (!(textBody.getScaleFactor() == 0)) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {


        LinkedList<Integer>clickedShapes = new LinkedList<>();
        LinkedList<Integer>clickedTexts = new LinkedList<>();


        mScaleDetector.onTouchEvent(ev);
        textScaleDetector.onTouchEvent(ev);

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                if(shapes.isEmpty()&& texts.isEmpty()){
                    // do nothing
                }else{
                    switch (DesignActivity.vButton.getVisibility()){
                        case VISIBLE:
                            // do nothing
                            break;
                        case INVISIBLE:

                            for(int i = 0; i < shapes.size(); i++){
                                if(clickOnShape(shapes.get(i), ev)){
                                    clickedShapes.add(i);
                                }
                            }

                            if(!clickedShapes.isEmpty()){
                                CURRENT_SHAPE = clickedShapes.getLast();
                                fillColorShapes();
                                if(shapesForColor.get(CURRENT_SHAPE).getTag().equals("punch")){
                                    shapesForColor.get(CURRENT_SHAPE).getDrawable().mutate().setColorFilter(getResources().getColor(R.color.transparent),PorterDuff.Mode.SRC_IN);
                                }else{
                                    shapesForColor.get(CURRENT_SHAPE).getDrawable().mutate().setColorFilter(getResources().getColor(R.color.almostWhite),PorterDuff.Mode.SRC_IN);
                                }
                                DesignActivity.currentNumText.setText("S");
                                DesignActivity.vButton.setVisibility(VISIBLE);
                            }else{
                                for(int i = 0; i < texts.size(); i++){
                                    if(clickOnText(texts.get(i), ev)){
                                        clickedTexts.add(i);
                                    }
                                }
                                if(!clickedTexts.isEmpty()){
                                    fillColorShapes();
                                    CURRENT_TEXT = clickedTexts.getLast();
                                    texts.get(CURRENT_TEXT).getTextPaint().setColor(getResources().getColor(R.color.almostWhite));
                                    DesignActivity.currentNumText.setText("T");
                                    DesignActivity.vButton.setVisibility(VISIBLE);
                                    DesignActivity.initFonts(texts.get(CURRENT_TEXT).getTag());
                                    DesignActivity.fontsBar.setVisibility(VISIBLE);
                                }else{
                                    //do nothing
                                }
                            }

                            break;
                        default:
                            //do nothing
                            break;
                    }

                    final float x = ev.getX();
                    final float y = ev.getY();
                    mLastTouchX = x;
                    mLastTouchY = y;
                    mActivePointerId = ev.getPointerId(0);
                    invalidate();
                }
            }

            case MotionEvent.ACTION_MOVE: {
                if (CURRENT_SHAPE == -1 && !DesignActivity.currentNumText.getText().equals("T")) {

                } else {
                    final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                    if (pointerIndex >= 0) {

                        final float x = ev.getX(pointerIndex);
                        final float y = ev.getY(pointerIndex);

                        if (!mScaleDetector.isInProgress()) {

                            final float dx = x - mLastTouchX;
                            final float dy = y - mLastTouchY;

                            try{

                                if (DesignActivity.currentNumText.getText().equals("T")) {
                                    float xpos = texts.get(CURRENT_TEXT).getPosX();
                                    float ypos = texts.get(CURRENT_TEXT).getPosY();
                                    texts.get(CURRENT_TEXT).setPosX(xpos += dx);
                                    texts.get(CURRENT_TEXT).setPosY(ypos += dy);

                                } else {
                                    float xpos = shapes.get(CURRENT_SHAPE).getPosX();
                                    float ypos = shapes.get(CURRENT_SHAPE).getPosY();
                                    shapes.get(CURRENT_SHAPE).setPosX(xpos += dx);
                                    shapes.get(CURRENT_SHAPE).setPosY(ypos += dy);

                                }

                            }catch (IndexOutOfBoundsException e){

                            }catch (NoSuchElementException e){

                            }

                            invalidate();

                        }
                        mLastTouchX = x;
                        mLastTouchY = y;

                        break;
                    }
                }
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;

                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);

                if (pointerIndex < 0) {
                    Log.e("ash", "Got ACTION_UP event but have an invalid active pointer id.");
                    return false;
                }

                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                onSecondaryPointerUp(ev);
//                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
//                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
//
//                final int pointerId = ev.getPointerId(pointerIndex);
//                if (pointerId == mActivePointerId) {
//
//                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
//                    mLastTouchX = ev.getX(newPointerIndex);
//                    mLastTouchY = ev.getY(newPointerIndex);
//                    mActivePointerId = ev.getPointerId(newPointerIndex);
//
//                }
                break;
            }
        }


        return true;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastTouchX = ev.getX(newPointerIndex);
            mLastTouchY = ev.getY(newPointerIndex);
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if(DesignActivity.currentNumText.getText().equals("T")) {
                textScaleFactor *= detector.getScaleFactor();

                textScaleFactor = Math.max(0.1f, Math.min(textScaleFactor, 5.0f));
                texts.get(CURRENT_TEXT).setScaleFactor(textScaleFactor);
            }else {
                try{
                    mScaleFactor *= detector.getScaleFactor();

                    mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
                    shapes.get(CURRENT_SHAPE).setScaleFactor(mScaleFactor);
                }catch (IndexOutOfBoundsException e){

                }

            }
            invalidate();
            return true;
        }
    }

    public void fillColorShapes(){
        DesignActivity.vButton.setVisibility(VISIBLE);
        DesignActivity.colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.editGrayBigShape),PorterDuff.Mode.SRC_IN);
        for(Shape shape:shapesForColor){
            shape.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.editGraysmallShape),PorterDuff.Mode.SRC_IN);
        }
        for(TextBody textBody: texts){
            textBody.getTextPaint().setColor(getResources().getColor(R.color.background));
        }
    }



    public void punch(int shapeToPunch, String type, int[]resources){

        try{

            Drawable drawable = null, colorDrawable = null;
            switch (type){
                case "punch":
                    drawable = getResources().getDrawable(resources[shapeToPunch]);
                    colorDrawable = getResources().getDrawable(resources[shapeToPunch]);
                    shapesForColor.add(new Shape(colorDrawable, mPosX, mPosY));
                    shapesForColor.getLast().getDrawable().mutate().setColorFilter(getResources().getColor(R.color.transparent), PorterDuff.Mode.SRC_IN);

                    break;
                case "topping":
                    drawable = getResources().getDrawable(resources[shapeToPunch]);
                    colorDrawable = getResources().getDrawable(resources[shapeToPunch]);
                    shapesForColor.add(new Shape(colorDrawable, mPosX, mPosY));
                    if(DesignActivity.currentColor == 0){
                        shapesForColor.getLast().getDrawable().mutate().setColorFilter(getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);
                    }else{
                        shapesForColor.getLast().getDrawable().mutate().setColorFilter(getResources().getColor(DesignActivity.currentColor),PorterDuff.Mode.SRC_IN);
                    }
                    break;
            }

            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Shape shape = new Shape(drawable, mPosX, mPosY);
            shapes.add(shape);

            colorDrawable.setBounds(0, 0, colorDrawable.getIntrinsicWidth(), colorDrawable.getIntrinsicHeight());
            shapesForColor.getLast().setTag(type);


            invalidate();
        }catch (IndexOutOfBoundsException e){
            Toast.makeText(getContext(), "Unable to perform action", Toast.LENGTH_SHORT).show();
        }


        CURRENT_SHAPE = shapes.size()-1;
    }



    public void init(){

        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (100 * scale + 0.5f);


        heightScreen = getResources().getDisplayMetrics().heightPixels;
        widthScreen = getResources().getDisplayMetrics().widthPixels;

        if(pixels == 300) {
            mPosX = widthScreen / 2.945f;
            mPosY = heightScreen / 5f;
        }else{
            mPosX = widthScreen / 3.35f;
            mPosY = heightScreen / 5.3f;
        }

        heartDrawable = getResources().getDrawable(R.drawable.g_punch_shape_1);
        heartDrawable.setBounds(0, 0, heartDrawable.getIntrinsicWidth()/2, heartDrawable.getIntrinsicHeight()/2);

        pivotx = heartDrawable.getIntrinsicWidth()/2;
        pivoty = heartDrawable.getIntrinsicHeight()/2;

        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        textScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

    }

}
