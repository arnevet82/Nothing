package com.chuk3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by Admin on 17/08/2017.
 */

public class TouchView extends View {

    public static LinkedList<Movable> shapes = new LinkedList<>();


    private float mPosX;
    private float mPosY;
    private float tPosX;
    private float tPosY;

    private float mLastTouchX;
    private float mLastTouchY;

    private float heightScreen;
    private float widthScreen;

    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

//    public static int CURRENT_SHAPE = -1;
//    public static int CURRENT_TEXT = -1;

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

        for(Movable movable:shapes){
            movable.draw(canvas);
        }

    }

//    private boolean clickOnShape(Movable movable, MotionEvent event) {
//
//        float scaleFactor = movable.getScaleFactor();
//        scaleFactor = Math.max(1f, Math.min(mScaleFactor, 1.3f));
//
//        float x = (movable.getPosX()*0.9f);
//        float y = movable.getPosY();
//        float xEnd = (movable.getPosX() + getResources().getDimension(R.dimen.punch_size))*scaleFactor;
//        float yEnd = (movable.getPosY() + getResources().getDimension(R.dimen.punch_size))*scaleFactor;
//
//        if ((event.getX() >= x && event.getX() <= xEnd)
//                && (event.getY() >= y && event.getY() <= yEnd)){
//
//            if (!(movable.getScaleFactor() == 0)) {
//
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//        return false;
//    }

//    private boolean clickOnText(Movable movable, MotionEvent event) {
//
//        float xEnd = movable.getPosX() + textScaleFactor*250;
//        float yEnd = movable.getPosY() + textScaleFactor*150;
//
//        if ((event.getX() >= (movable.getPosX()) && event.getX() <= (xEnd))
//                && (event.getY() >= (movable.getPosY()) && event.getY() <= yEnd)) {
//
//            if (!(movable.getScaleFactor() == 0)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
//
//    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        LinkedList<Integer>clickedShapes = new LinkedList<>();
//        LinkedList<Integer>clickedTexts = new LinkedList<>();

        mScaleDetector.onTouchEvent(ev);

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                if(!shapes.isEmpty()){
                    // do nothing

                    Movable movable = Movable.getCurrent_movable(ev, mScaleFactor, getContext());

                    if (movable != null) {
                        moveCommand = new MoveCommand(movable);
                        DesignActivity.vButton.setVisibility(VISIBLE);
                        DesignActivity.showDeleteAndRotate();
                        invalidate();

                        final float x = ev.getX();
                        final float y = ev.getY();
                        mLastTouchX = x;
                        mLastTouchY = y;
                        mActivePointerId = ev.getPointerId(0);
                    }

                    }

                break;

            }


            case MotionEvent.ACTION_MOVE: {
                Movable movable = Movable.current_movable;

                if (movable != null) {
                    final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                    if (pointerIndex >= 0) {

                        final float x = ev.getX(pointerIndex);
                        final float y = ev.getY(pointerIndex);

                        if (!mScaleDetector.isInProgress()) {

                            final float dx = x - mLastTouchX;
                            final float dy = y - mLastTouchY;

                            if(moveCommand != null){

                                float xpos = movable.getPosX();
                                float ypos = movable.getPosY();

                                moveCommand.setNewX(xpos+dx, getWidth());
                                moveCommand.setNewY(ypos+dy, getHeight());
                                moveCommand.execute();
                                invalidate();
                            }
//                            if (DesignActivity.currentNumText.getText().equals("T")&& CURRENT_TEXT > -1) {
//                                float xpos = texts.get(CURRENT_TEXT).getPosX();
//                                float ypos = texts.get(CURRENT_TEXT).getPosY();
//                                texts.get(CURRENT_TEXT).setPosX(xpos += dx);
//                                texts.get(CURRENT_TEXT).setPosY(ypos += dy);
//
//                            } else {
//                                try{
//                                    float xpos = shapes.get(CURRENT_SHAPE).getPosX();
//                                    float ypos = shapes.get(CURRENT_SHAPE).getPosY();
//
//                                    if(moveCommand != null){
//                                        moveCommand.setNewX(xpos+dx);
//                                        moveCommand.setNewY(ypos+dy);
//                                        moveCommand.execute();
//                                    }
//
//                                }catch (IndexOutOfBoundsException e){
//
//                                }catch (NoSuchElementException e){
//
//                                }
//                            }

//                            invalidate();

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
                    moveCommand = null;

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
            if(DesignActivity.currentNumText.equals("T")){

            }else{
                if(Movable.current_movable != null){
                    scaleCommand = new ScaleCommand(Movable.current_movable);
                }
            }
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
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
            if(DesignActivity.currentNumText.getText().equals("T") ) {
                textScaleFactor *= detector.getScaleFactor();

                textScaleFactor = Math.max(0.1f, Math.min(textScaleFactor, 5.0f));
//                texts.get(CURRENT_TEXT).setScaleFactor(textScaleFactor);
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

//    public void fillColorShapes(){
//        DesignActivity.vButton.setVisibility(VISIBLE);
//        DesignActivity.colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.editGrayBigShape),PorterDuff.Mode.SRC_IN);
//        for(Movable movable:shapes){
//            movable.setGrayColor(getContext());
//        }
//
//    }

    public void executeAddCommand(Context context, int resourceId, String text, String type) {
        AddCommand addCommand = new AddCommand(context, resourceId, text, mPosX, mPosY, type);
        boolean isExecute = addCommand.execute();
        if (isExecute) {
            commandStack.push(addCommand);
        }
    }


    public void executeAngleCommand(Movable movable, float newAngle) {
        AngleCommand angleCommand = new AngleCommand(movable, newAngle);
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

        heightScreen = getResources().getDisplayMetrics().heightPixels;
        widthScreen = getResources().getDisplayMetrics().widthPixels;

        mPosX = widthScreen / 2.5f;
        mPosY = heightScreen / 5.3f;

        tPosX = widthScreen / 7f;
        tPosY = heightScreen / 5.3f;


        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

    }

}