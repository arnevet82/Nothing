package com.chuk3d;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.widget.ImageView;

import java.util.LinkedList;

/**
 * Created by Admin on 27/08/2017.
 */

public class ColorCommand extends Command {
    private Context context;
    private ImageView colorImage;
    private int newColor;
    private int lastColor;

    public ColorCommand(Context context, ImageView colorImage, int newColor){
        this.context = context;
        this.colorImage = colorImage;
        this.newColor = newColor;
    }

    @Override
    public boolean execute() {
        lastColor = DesignActivity.currentColor;
        DesignActivity.currentColor = newColor;

//        if (newColor != lastColor) {
            colorImage.getDrawable().mutate().setColorFilter(newColor, PorterDuff.Mode.SRC_IN);
            if (!TouchView.shapes.isEmpty()) {
                for (Shape shape : TouchView.shapes) {
                    shape.setColor(newColor);
                }
//            }
            if (!TouchView.texts.isEmpty()) {
                for (TextBody textBody : TouchView.texts) {
                    if(textBody.getTag().equals("topping")){
                        textBody.getTextPaint().setColor(newColor);
                    }else{
                        EmbossMaskFilter filter =
                                new EmbossMaskFilter(new float[]{0.0f, -1.0f, 0.5f}, 0.8f, 15f, 1f);
                        textBody.getTextPaint().setMaskFilter(filter);

                        textBody.getTextPaint().setColor(context.getResources().getColor(R.color.almostWhite));
                    }
                }
            }
            DesignActivity.touchView.invalidate();
            return true;
        }
        return false;
    }

    @Override
    public void undo() {
        colorImage.getDrawable().mutate().setColorFilter(lastColor, PorterDuff.Mode.SRC_IN);
        if (!TouchView.shapes.isEmpty()) {
            for (Shape shape : TouchView.shapes) {
                shape.setColor(lastColor);
            }
        }
        if (!TouchView.texts.isEmpty()) {
            for (TextBody textBody : TouchView.texts) {
                if(textBody.getTag().equals("topping")){
                    textBody.getTextPaint().setColor(lastColor);
                }else{
                    EmbossMaskFilter filter =
                            new EmbossMaskFilter(new float[]{0.0f, -1.0f, 0.5f}, 0.8f, 15f, 1f);
                    textBody.getTextPaint().setMaskFilter(filter);

                    textBody.getTextPaint().setColor(context.getResources().getColor(R.color.almostWhite));
                }
            }
        }
        DesignActivity.currentColor = lastColor;
        DesignActivity.touchView.invalidate();
    }
}



//    private static int currentColor;
//    private int newColor;
//    private ImageView colorImage;
//    private Context context;
//
//    private static LinkedList<Integer>colors = new LinkedList<>();
//
//    public ColorCommand(ImageView colorImage, Context context, int newColor){
//        this.colorImage = colorImage;
//        this.context = context;
//        this.newColor = newColor;
//    }
//
//    @Override
//    void execute() {
//
//        if (newColor == 0) {
//            changeToBackgroundColor();
//        } else {
//            DesignActivity.stack.add("color");
//            DesignActivity.clearStack(3);
//            colors.add(currentColor);
//            currentColor = newColor;
//
//            if(DesignActivity.baseShapes[DesignActivity.position] != R.drawable.g_base_shape_31) {
//                colorImage.getDrawable().mutate().setColorFilter(context.getResources().getColor(newColor), PorterDuff.Mode.SRC_IN);
//            }
//            if (!TouchView.shapes.isEmpty()) {
//                for (Shape shape : TouchView.shapesForColor) {
//                    if (shape.getTag().equals("topping")) {
//                        shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(newColor), PorterDuff.Mode.SRC_IN);
//                    }else{
//                        shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.transparent), PorterDuff.Mode.SRC_IN);
//                    }
//                }
//            }
//            if (!TouchView.texts.isEmpty()) {
//                for (TextBody textBody : TouchView.texts) {
//                    if (textBody.getTag().equals("topping")) {
//                        if(newColor == R.color.transBtn){
//                            textBody.getTextPaint().setColor(context.getResources().getColor(R.color.background));
//                        }else{
//                            textBody.getTextPaint().setColor(context.getResources().getColor(newColor));
//                        }
//                    }else{
//
//                        MaskFilter filter =
//                                new EmbossMaskFilter(new float[]{0.0f, -1.0f, 0.5f}, 0.8f, 15f, 1f);
//                        textBody.getTextPaint().setMaskFilter(filter);
//
//                        textBody.getTextPaint().setColor(context.getResources().getColor(R.color.almostWhite));
//                    }
//                }
//            }
//            DesignActivity.touchView.invalidate();
//        }
//    }
//
//
//    void colorBeforeScreenShot(){
//        if(DesignActivity.baseShapes[DesignActivity.position] != R.drawable.g_base_shape_31){
//            colorImage.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.blackBtn), PorterDuff.Mode.SRC_IN);
//        }
//        if (!TouchView.shapes.isEmpty()) {
//            for (Shape shape : TouchView.shapesForColor) {
//                if (shape.getTag().equals("topping")) {
//                    shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.grayBtn), PorterDuff.Mode.SRC_IN);
//                }else{
//                    shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
//                }
//            }
//        }
//        if (!TouchView.texts.isEmpty()) {
//            for (TextBody textBody : TouchView.texts) {
//                if (textBody.getTag().equals("topping")) {
//                    textBody.getTextPaint().setColor(context.getResources().getColor(R.color.grayBtn));
//                }
//            }
//        }
//        DesignActivity.touchView.invalidate();
//    }
//
//    void fillColorShapes(String tag){
//        if(DesignActivity.baseShapes[DesignActivity.position] != R.drawable.g_base_shape_31){
//            colorImage.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.editGrayBigShape), PorterDuff.Mode.SRC_IN);
//        }
//        if (!TouchView.shapes.isEmpty()) {
//            for (Shape shape : TouchView.shapesForColor) {
//                shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.editGraysmallShape), PorterDuff.Mode.SRC_IN);
//
//            }
//
//            if(tag.equals("shape")){
//                if(TouchView.shapesForColor.get(TouchView.CURRENT_SHAPE).getTag().equals("punch")){
//                    TouchView.shapesForColor.get(TouchView.CURRENT_SHAPE).getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.transparent),PorterDuff.Mode.SRC_IN);
//                }else{
//                    TouchView.shapesForColor.get(TouchView.CURRENT_SHAPE).getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.almostWhite),PorterDuff.Mode.SRC_IN);
//                }
//            }
//        }
//        if (!TouchView.texts.isEmpty()) {
//            for (TextBody textBody : TouchView.texts) {
//                textBody.getTextPaint().setColor(context.getResources().getColor(R.color.buttonGray));
//            }
//            if(tag.equals("text")){
//                TouchView.texts.get(TouchView.CURRENT_TEXT).getTextPaint().setColor(context.getResources().getColor(R.color.almostWhite));
//            }
//        }
//
//        DesignActivity.touchView.invalidate();
//    }
//
//
//
//    @Override
//    void undo() {
//        try{
//
//            currentColor = colors.getLast();
//            DesignActivity.currentColor = currentColor;
//
//            if(DesignActivity.baseShapes[DesignActivity.position] != R.drawable.g_base_shape_31){
//                colorImage.getDrawable().mutate().setColorFilter(context.getResources().getColor(currentColor), PorterDuff.Mode.SRC_IN);
//            }
//            if (!TouchView.shapes.isEmpty()) {
//                for (Shape shape : TouchView.shapesForColor) {
//                    if (shape.getTag().equals("topping")) {
//                        shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(currentColor), PorterDuff.Mode.SRC_IN);
//                    }
//                }
//            }
//            if (!TouchView.texts.isEmpty()) {
//                for (TextBody textBody : TouchView.texts) {
//                    if (textBody.getTag().equals("topping")) {
//                        if(currentColor == R.color.transBtn){
//                            textBody.getTextPaint().setColor(context.getResources().getColor(R.color.background));
//                        }else{
//                            textBody.getTextPaint().setColor(context.getResources().getColor(currentColor));
//                        }
//                    }else{
//
//                        MaskFilter filter =
//                                new EmbossMaskFilter(new float[]{0.0f, -1.0f, 0.5f}, 0.8f, 15f, 1f);
//                        textBody.getTextPaint().setMaskFilter(filter);
//
//                        textBody.getTextPaint().setColor(context.getResources().getColor(R.color.almostWhite));
//                    }
//                }
//            }
//            colors.removeLast();
//
//
//        }catch (Resources.NotFoundException e){
//            changeToBackgroundColor();
//            DesignActivity.currentColor = 0;
//        }
//
//        DesignActivity.stack.removeLast();
//        DesignActivity.touchView.invalidate();
//    }
//
//
//    void changeToBackgroundColor(){
//        if(DesignActivity.baseShapes[DesignActivity.position] != R.drawable.g_base_shape_31){
//            colorImage.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);
//        }
//
//        for(int i = 0; i < TouchView.shapesForColor.size(); i++){
//            Shape shape = TouchView.shapesForColor.get(i);
//            switch (shape.getTag()){
//                case "topping":
//                    shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);
//                    break;
//                case "punch":
//                    shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.transparent),PorterDuff.Mode.SRC_IN);
//                    break;
//            }
//        }
//
//        for(int i = 0; i < TouchView.texts.size(); i++){
//            TextBody textBody = TouchView.texts.get(i);
//            switch (textBody.getTag()){
//                case "topping":
//                    textBody.getTextPaint().setColor(context.getResources().getColor(R.color.baseShapeFirstColor));
//                    break;
//                default:
//                    break;
//            }
//        }
//
//    }
//}