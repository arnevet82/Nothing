package com.chuk3d;

import android.content.Context;
import android.graphics.EmbossMaskFilter;
import android.graphics.PorterDuff;
import android.widget.ImageView;

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

            colorImage.getDrawable().mutate().setColorFilter(newColor, PorterDuff.Mode.SRC_IN);
            if (!TouchView.shapes.isEmpty()) {
                for (Movable movable : TouchView.shapes) {
                    movable.setColor(context, newColor);
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
            for (Movable movable : TouchView.shapes) {
                movable.setColor(context, lastColor);
            }
        }
//        if (!TouchView.texts.isEmpty()) {
//            for (Movable movable : TouchView.texts) {
//                movable.setColor(context, lastColor);
//            }
//        }
        DesignActivity.currentColor = lastColor;
        DesignActivity.touchView.invalidate();
    }
}

