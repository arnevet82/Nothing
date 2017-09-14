package com.chuk3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.text.Layout;
import android.text.StaticLayout;
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

    public static LinkedList<Shape> shapes = new LinkedList<>();
    public static LinkedList<Shape> shapesForColor = new LinkedList<>();

    public static LinkedList<TextBody> texts = new LinkedList<>();

    private float mPosX;
    private float mPosY;

    private float mLastTouchX;
    private float mLastTouchY;

    private float heightScreen;
    private float widthScreen;

    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    public static int CURRENT_SHAPE = -1;
    public static int CURRENT_TEXT = -1;

    public static float textScaleFactor = 1.f;

    private MoveCommand moveCommand = null;

    public static SizedStack<Command> commandStack = new SizedStack<Command>(100);


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

        for(Shape shape:shapes){
            shape.draw(canvas);
        }

        for(int i = 0; i < texts.size(); i++){
            TextBody tBody = texts.get(i);
            canvas.save();
            canvas.translate(tBody.getPosX(), tBody.getPosY());
            canvas.scale(tBody.getScaleFactor(), tBody.getScaleFactor(),tBody.getTextPivotx(), tBody.getTextPivoty());
            canvas.rotate(tBody.getAngle(),tBody.getTextPivotx(), tBody.getTextPivoty());
            tBody.getSl().draw(canvas);
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

        float xEnd = textBody.getPosX() + textScaleFactor*250;
        float yEnd = textBody.getPosY() + textScaleFactor*150;

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

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                if(shapes.isEmpty()&& texts.isEmpty()){
                    // do nothing
                }else{

                    switch (DesignActivity.vButton.getVisibility()){
                        case VISIBLE:
                            if(!shapes.isEmpty()&&CURRENT_SHAPE > -1){
                                moveCommand = new MoveCommand(shapes.get(CURRENT_SHAPE));
                            }
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
                                moveCommand = new MoveCommand(shapes.get(CURRENT_SHAPE));
                                fillColorShapes();
                                shapes.get(CURRENT_SHAPE).setClickColor(getContext());

                                DesignActivity.currentNumText.setText("S");
                                DesignActivity.vButton.setVisibility(VISIBLE);
                                DesignActivity.showDeleteAndRotate();
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
                                    DesignActivity.isTextEdited = true;
                                    DesignActivity.showDeleteAndRotate();
                                    DesignActivity.initFonts(texts.get(CURRENT_TEXT).getTag());

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
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                if (shapes.isEmpty()&& texts.isEmpty()) {
                } else {
                    final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                    if (pointerIndex >= 0) {

                        final float x = ev.getX(pointerIndex);
                        final float y = ev.getY(pointerIndex);

                        if (!mScaleDetector.isInProgress()) {

                            final float dx = x - mLastTouchX;
                            final float dy = y - mLastTouchY;


                                if (DesignActivity.currentNumText.getText().equals("T")&& CURRENT_TEXT > -1) {
                                    float xpos = texts.get(CURRENT_TEXT).getPosX();
                                    float ypos = texts.get(CURRENT_TEXT).getPosY();
                                    texts.get(CURRENT_TEXT).setPosX(xpos += dx);
                                    texts.get(CURRENT_TEXT).setPosY(ypos += dy);

                                } else {
                                    try{
                                    float xpos = shapes.get(CURRENT_SHAPE).getPosX();
                                    float ypos = shapes.get(CURRENT_SHAPE).getPosY();

                                        if(moveCommand != null){
                                            moveCommand.setNewX(xpos+dx);
                                            moveCommand.setNewY(ypos+dy);
                                            moveCommand.execute();
                                        }

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
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;

                if (moveCommand!= null && moveCommand.isExecute()) {
                    commandStack.push(moveCommand);
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                if (moveCommand!= null && moveCommand.isExecute()) {
                    commandStack.push(moveCommand);
                    moveCommand = null;
                }

                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);

                if (pointerIndex < 0) {
                    Log.e("ash", "Got ACTION_UP event but have an invalid active pointer id.");
                    return false;
                }

                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                onSecondaryPointerUp(ev);

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


        private ScaleCommand scaleCommand = null;



        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log.d("ScaleListener", "onScaleBegin");
            Log.e("currentNumText", DesignActivity.currentNumText.getText().toString());
            if(DesignActivity.currentNumText.equals("T")){

            }else{
                if(!shapes.isEmpty()&&CURRENT_SHAPE>-1)
                scaleCommand = new ScaleCommand(shapes.get(CURRENT_SHAPE));
            }
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log.d("ScaleListener", "onScaleEnd");
            if(DesignActivity.currentNumText.equals("T")){

            }else{
                if(scaleCommand != null){
                    if (scaleCommand.isExecute()) {
                        commandStack.push(scaleCommand);
                        scaleCommand = null;
                    }
                }
            }
        }
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d("ScaleListener", "onScale");
            if(DesignActivity.currentNumText.getText().equals("T")) {
                textScaleFactor *= detector.getScaleFactor();

                textScaleFactor = Math.max(0.1f, Math.min(textScaleFactor, 5.0f));
                texts.get(CURRENT_TEXT).setScaleFactor(textScaleFactor);
            }else {
                try{
                    mScaleFactor *= detector.getScaleFactor();

                    mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
                    if(scaleCommand!= null){
                        scaleCommand.setNewScaleFactor(mScaleFactor);
                        scaleCommand.execute();
                    }

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
        for(Shape shape:shapes){
            shape.getColorDrawable().mutate().setColorFilter(getResources().getColor(R.color.editGraysmallShape),PorterDuff.Mode.SRC_IN);
        }
        for(TextBody textBody: texts){
            textBody.getTextPaint().setColor(getResources().getColor(R.color.background));
        }
    }

    public void executeAddCommand(Context context, int resourceId, String type) {
        AddCommand addCommand = new AddCommand(context, resourceId, mPosX, mPosY, type);
        boolean isExecute = addCommand.execute();
        if (isExecute) {
            commandStack.push(addCommand);
        }
    }
    public void executeAngleCommand(Shape shape, float newAngle) {
        AngleCommand angleCommand = new AngleCommand(shape, newAngle);
        boolean isExecute = angleCommand.execute();
        if (isExecute) {
            commandStack.push(angleCommand);
        }
    }

    public void undo(){
        if (!commandStack.isEmpty()) {
            Command command = commandStack.pop();
            command.undo();
        }

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
            mPosX = widthScreen / 2.5f;
            mPosY = heightScreen / 5.3f;
        }

        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

    }

}