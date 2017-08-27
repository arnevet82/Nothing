package com.chuk3d;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
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
    public static TextView textView;
    public static StaticLayout sl;
    public static TextPaint textPaint;
    public static float angle;
    private float textPivotx;
    private float textPivoty;
    private float textPosX;
    private float textPosY;
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

        canvas.save();
        canvas.translate(textView.getX(), textView.getY());
        canvas.scale(textScaleFactor, textScaleFactor, textPivotx, textPivoty);
        canvas.rotate(angle, textPivotx, textPivoty);
        sl.draw(canvas);
        canvas.restore();
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

    private boolean clickOnText(TextView textView, MotionEvent event) {

        float xEnd = textView.getX() + textScaleFactor*200;
        float yEnd = textView.getY() + textScaleFactor*100;

        if ((event.getX() >= (textView.getX()) && event.getX() <= (xEnd))
                && (event.getY() >= (textView.getY()) && event.getY() <= yEnd)) {

            if (!(textView.getScaleX() == 0)) {
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

        mScaleDetector.onTouchEvent(ev);
        textScaleDetector.onTouchEvent(ev);

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {

                if (CURRENT_SHAPE == -1 && !DesignActivity.currentNumText.getText().equals("T")) {
                }else if(shapes.isEmpty() && DesignActivity.currentNumText.getText().equals("T")){
                    onShapeOrTextTouhched("T", "#FFFFFF");
                    final float x = ev.getX();
                    final float y = ev.getY();
                    mLastTouchX = x;
                    mLastTouchY = y;
                    mActivePointerId = ev.getPointerId(0);

                    break;

                } else{
                    for (int i = 0; i < shapes.size(); i++) {
                        if (clickOnShape(shapes.get(i), ev) && DesignActivity.vButton.getVisibility() == INVISIBLE) {
                            clickedShapes.add(i);

                        } else if (clickOnText(textView, ev) && DesignActivity.vButton.getVisibility() == INVISIBLE) {
                            onShapeOrTextTouhched("T", "#FFFFFF");
                            DesignActivity.vButton.setVisibility(VISIBLE);
//                            fillColorShapes(R.color.editGraysmallShape, R.color.editGrayBigShape, "text");
                        }
                        try{
                            CURRENT_SHAPE = clickedShapes.getLast();
                            onShapeOrTextTouhched(String.valueOf(CURRENT_SHAPE + 1), "#cfd8dc");
//                            fillColorShapes(R.color.editGraysmallShape, R.color.editGrayBigShape, "shape");
                        }catch (NoSuchElementException e){

                        }catch (NullPointerException e){

                        }
                    }

                    final float x = ev.getX();
                    final float y = ev.getY();

                    mLastTouchX = x;
                    mLastTouchY = y;
                    mActivePointerId = ev.getPointerId(0);

                    break;

                }
            }

            case MotionEvent.ACTION_MOVE: {
                if (CURRENT_SHAPE == -1 && !DesignActivity.currentNumText.getText().equals("T")) {

                } else {
                    final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                    final float x = ev.getX(pointerIndex);
                    final float y = ev.getY(pointerIndex);

                    if (!mScaleDetector.isInProgress()) {

                        final float dx = x - mLastTouchX;
                        final float dy = y - mLastTouchY;

                        if (DesignActivity.currentNumText.getText().equals("T")) {
                            float xpos = textView.getX();
                            float ypos = textView.getY();
                            textView.setX(xpos += dx);
                            textView.setY(ypos += dy);

                        } else {
                            try{
                                float xpos = shapes.get(CURRENT_SHAPE).getPosX();
                                float ypos = shapes.get(CURRENT_SHAPE).getPosY();
                                shapes.get(CURRENT_SHAPE).setPosX(xpos += dx);
                                shapes.get(CURRENT_SHAPE).setPosY(ypos += dy);

                            }catch (IndexOutOfBoundsException e){

                            }catch (NoSuchElementException e){

                            }
                        }
                        invalidate();

                    }
                    mLastTouchX = x;
                    mLastTouchY = y;

                    break;
                }
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {

                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);

                }
                break;
            }
        }


        return true;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if(DesignActivity.currentNumText.getText().equals("T")) {
                textScaleFactor *= detector.getScaleFactor();

                textScaleFactor = Math.max(0.1f, Math.min(textScaleFactor, 5.0f));
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

    public void onShapeOrTextTouhched(String currentTextNum, String textColor){
        if(currentTextNum.equals("T")){
            fillColorShapes(R.color.editGraysmallShape, R.color.editGrayBigShape, "text");

        }else{
            fillColorShapes(R.color.editGraysmallShape, R.color.editGrayBigShape, "shape");
        }

        DesignActivity.vButton.setVisibility(VISIBLE);
//        BaseActivity.delete.setVisibility(VISIBLE);
//        BaseActivity.initDeleteButton();
        DesignActivity.currentNumText.setText(currentTextNum);
        textPaint.setColor(Color.parseColor(textColor));
    }

    public void punch(int shapeToPunch, String type){

        try{
            int[]punchResources = {R.drawable.g_punch_shape_1, R.drawable.g_punch_shape_2, R.drawable.g_punch_shape_3,R.drawable.g_punch_shape_4, R.drawable.g_punch_shape_5, R.drawable.g_punch_shape_6, R.drawable.g_punch_shape_7, R.drawable.g_punch_shape_8, R.drawable.g_punch_shap_9, R.drawable.g_punch_shape_10, R.drawable.g_punch_shape_11, R.drawable.g_punch_shape_12, R.drawable.g_punch_shape_13, R.drawable.g_punch_shape_14, R.drawable.g_punch_shape_15, R.drawable.g_punch_shape_16, R.drawable.g_punch_shape_17, R.drawable.g_punch_shape_18, R.drawable.g_punch_shape_19, R.drawable.g_punch_shape_20, R.drawable.g_punch_shape_21, R.drawable.g_punch_shape_22, R.drawable.g_punch_shape_23, R.drawable.g_punch_shape_24, R.drawable.g_punch_shape_25, R.drawable.g_punch_shape_26, R.drawable.g_topping_shape_26, R.drawable.g_punch_shape_27, R.drawable.g_punch_shape_28, R.drawable.g_punch_shape_29, R.drawable.g_punch_shape_30, R.drawable.g_punch_shape_31, R.drawable.g_punch_shape_32, R.drawable.g_punch_shape_33, R.drawable.g_punch_shape_34, R.drawable.g_punch_shape_35, R.drawable.g_punch_shape_36};
            int[]toppingResources = {R.drawable.g_topping_shape_1, R.drawable.g_topping_shape_2, R.drawable.g_topping_shape_3,R.drawable.g_topping_shape_4, R.drawable.g_topping_shape_5, R.drawable.g_topping_shape_6, R.drawable.g_topping_shape_7, R.drawable.g_topping_shape_8, R.drawable.g_topping_shap_9, R.drawable.g_topping_shape_10, R.drawable.g_topping_shape_11, R.drawable.g_topping_shape_12, R.drawable.g_topping_shape_13, R.drawable.g_topping_shape_14, R.drawable.g_topping_shape_15, R.drawable.g_topping_shape_16, R.drawable.g_topping_shape_17, R.drawable.g_topping_shape_18, R.drawable.g_topping_shape_19, R.drawable.g_topping_shape_20, R.drawable.g_topping_shape_21, R.drawable.g_topping_shape_22, R.drawable.g_topping_shape_23, R.drawable.g_topping_shape_24, R.drawable.g_topping_shape_25, R.drawable.g_topping_shape_26, R.drawable.g_topping_shape_27, R.drawable.g_topping_shape_28, R.drawable.g_topping_shape_29, R.drawable.g_topping_shape_30, R.drawable.g_topping_shape_31, R.drawable.g_topping_shape_32, R.drawable.g_topping_shape_33, R.drawable.g_topping_shape_34, R.drawable.g_topping_shape_35, R.drawable.g_topping_shape_36};

            Drawable drawable = null, colorDrawable = null;
            switch (type){
                case "punch":
                    drawable = getResources().getDrawable(punchResources[shapeToPunch]);
                    colorDrawable = getResources().getDrawable(punchResources[shapeToPunch]);
                    shapesForColor.add(new Shape(colorDrawable, mPosX, mPosY));
                    shapesForColor.getLast().getDrawable().mutate().setColorFilter(getResources().getColor(R.color.transparent), PorterDuff.Mode.SRC_IN);

                    break;
                case "topping":
                    drawable = getResources().getDrawable(toppingResources[shapeToPunch]);
                    colorDrawable = getResources().getDrawable(toppingResources[shapeToPunch]);
                    shapesForColor.add(new Shape(colorDrawable, mPosX, mPosY));
                    shapesForColor.getLast().getDrawable().mutate().setColorFilter(getResources().getColor(R.color.baseShapeFirstColor), PorterDuff.Mode.SRC_IN);
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

    public void fillColorShapes(int shapesColor, int frameColor, String tag){
        DesignActivity.colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(frameColor),PorterDuff.Mode.SRC_IN);

        for(int i = 0; i < shapesForColor.size(); i++){
            Shape shape = shapesForColor.get(i);

            if(i == CURRENT_SHAPE && tag.equals("shape")){
                switch (shape.getTag()){
                    case "punch":
                        shape.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.transparent),PorterDuff.Mode.SRC_IN);

                        break;
                    case "topping":
                        shape.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.background),PorterDuff.Mode.SRC_IN);
                        break;
                }


            }else {
                try{

                    switch (shape.getTag()) {
                        case "punch":
                            shape.getDrawable().mutate().setColorFilter(getResources().getColor(shapesColor), PorterDuff.Mode.SRC_IN);
                            break;
                        case "topping":
                            shape.getDrawable().mutate().setColorFilter(getResources().getColor(frameColor), PorterDuff.Mode.SRC_IN);
                            break;
                    }

                    if(tag.equals("text")){
                        textPaint.setColor(Color.parseColor("#FFFFFF"));
                    }

                }catch (Resources.NotFoundException e){

                }catch (NullPointerException e){

                }
            }

        }

        invalidate();

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

        textPosX = widthScreen/5;
        textPosY = heightScreen/4;
        textView = new TextView(getContext());
        textView.setX(textPosX);
        textView.setY(textPosY);
        textView.setWidth(DesignActivity.editText.getWidth());
        Typeface tf;
        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(80);
//        if(BaseActivity.design.equals("doorSign")){
//            tf =Typeface.createFromAsset(getContext().getAssets(),"Pacifico-Regular.ttf");
//            textPaint.setShadowLayer(7, 1, 3, Color.parseColor("#80000000"));
//        }else {
            tf = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-ExtraBold.ttf");
            textPaint.setShadowLayer(7, 1, 3, Color.parseColor("#80000000"));
//        }

        textPaint.setTypeface(tf);
        sl=new StaticLayout("", textPaint,(int)widthScreen/2,
                Layout.Alignment.ALIGN_CENTER, 1f,0f,false);

        textPivotx = sl.getWidth()/2;
        textPivoty = sl.getHeight()/2;


    }

}
