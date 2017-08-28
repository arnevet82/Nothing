package com.chuk3d;

import android.content.Context;
import android.graphics.PorterDuff;
import android.widget.ImageView;

/**
 * Created by Admin on 27/08/2017.
 */

public class ColorCommand extends Command {
    private int currentColor;
    private int newColor;
    private ImageView colorImage;
    private Context context;

    public ColorCommand(ImageView colorImage, Context context, int newColor){
        this.colorImage = colorImage;
        this.context = context;
        this.newColor = newColor;
    }

    @Override
    void execute() {
        currentColor = newColor;
        colorImage.getDrawable().mutate().setColorFilter(context.getResources().getColor(newColor), PorterDuff.Mode.SRC_IN);
        if (!TouchView.shapes.isEmpty()) {
            for (Shape shape : TouchView.shapesForColor) {
                if (shape.getTag().equals("topping")) {
                    shape.getDrawable().mutate().setColorFilter(context.getResources().getColor(newColor), PorterDuff.Mode.SRC_IN);
                }
            }
        }
        if (!TouchView.texts.isEmpty()) {
            for (TextBody textBody : TouchView.texts) {
                textBody.getTextPaint().setColor(context.getResources().getColor(newColor));
            }
        }
        DesignActivity.touchView.invalidate();
    }

    @Override
    void undo() {
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
                textBody.getTextPaint().setColor(context.getResources().getColor(currentColor));
            }
        }
        DesignActivity.touchView.invalidate();
    }
}
