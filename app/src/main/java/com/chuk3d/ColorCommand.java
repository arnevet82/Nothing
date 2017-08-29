package com.chuk3d;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.widget.ImageView;

import java.util.LinkedList;

/**
 * Created by Admin on 27/08/2017.
 */

public class ColorCommand extends Command {
    private static int currentColor;
    private int newColor;
    private ImageView colorImage;
    private Context context;

    private static LinkedList<Integer>colors = new LinkedList<>();

    public ColorCommand(ImageView colorImage, Context context, int newColor){
        this.colorImage = colorImage;
        this.context = context;
        this.newColor = newColor;
    }

    @Override
    void execute() {

        if (newColor == 0) {
            changeToBackgroundColor();
        } else {
            DesignActivity.stack.add("color");
            DesignActivity.clearStack(3);
            colors.add(currentColor);
            currentColor = newColor;

            colorImage.getDrawable().mutate().setColorFilter(context.getResources().getColor(newColor), PorterDuff.Mode.SRC_IN);
            if (!TouchView.shapes.isEmpty()) {
                for (Shape shape : TouchView.shapesForColor) {
                    if (shape.getTag().equals("topping")) {
                        shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(newColor), PorterDuff.Mode.SRC_IN);
                    }else{
                        shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.transparent), PorterDuff.Mode.SRC_IN);
                    }
                }
            }
            if (!TouchView.texts.isEmpty()) {
                for (TextBody textBody : TouchView.texts) {
                    if (textBody.getTag().equals("topping")) {
                        if(newColor == R.color.transBtn){
                            textBody.getTextPaint().setColor(context.getResources().getColor(R.color.background));
                        }else{
                            textBody.getTextPaint().setColor(context.getResources().getColor(newColor));
                        }
                    }
                }
            }
            DesignActivity.touchView.invalidate();
        }
    }

    @Override
    void undo() {
        try{

            currentColor = colors.getLast();
            DesignActivity.currentColor = currentColor;

            colorImage.getDrawable().mutate().setColorFilter(context.getResources().getColor(currentColor), PorterDuff.Mode.SRC_IN);
            if (!TouchView.shapes.isEmpty()) {
                for (Shape shape : TouchView.shapesForColor) {
                    if (shape.getTag().equals("topping")) {
                        shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(currentColor), PorterDuff.Mode.SRC_IN);
                    }
                }
            }
            if (!TouchView.texts.isEmpty()) {
                for (TextBody textBody : TouchView.texts) {
                    if(currentColor == R.color.transBtn){
                        textBody.getTextPaint().setColor(context.getResources().getColor(R.color.background));
                    }else{
                        textBody.getTextPaint().setColor(context.getResources().getColor(currentColor));
                    }
                }
            }
            colors.removeLast();


        }catch (Resources.NotFoundException e){
            changeToBackgroundColor();
            DesignActivity.currentColor = 0;
        }

        DesignActivity.stack.removeLast();
        DesignActivity.touchView.invalidate();
    }


    void changeToBackgroundColor(){
        colorImage.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);


        for(int i = 0; i < TouchView.shapesForColor.size(); i++){
            Shape shape = TouchView.shapesForColor.get(i);
            switch (shape.getTag()){
                case "topping":
                    shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);
                    break;
                case "punch":
                    shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.transparent),PorterDuff.Mode.SRC_IN);
                    break;
            }
        }

        for(int i = 0; i < TouchView.texts.size(); i++){
            TextBody textBody = TouchView.texts.get(i);
            switch (textBody.getTag()){
                case "topping":
                    textBody.getTextPaint().setColor(context.getResources().getColor(R.color.baseShapeFirstColor));
                    break;
                default:
                    break;
            }
        }

    }
}
