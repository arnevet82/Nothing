package com.chuk3d;

import android.content.Context;
import android.graphics.EmbossMaskFilter;
import android.graphics.PorterDuff;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Admin on 27/08/2017.
 */

public class ColorCommand extends Command {
    private Context context;
//    private ImageView colorImage;
    private int baseNewColor;
    private int baseLastColor;
    private int newColor;
    private int lastColor;
    private boolean isExecute = false;

    public ColorCommand(Context context, int baseNewColor, int newColor){
        this.context = context;
        this.baseNewColor = baseNewColor;
        this.newColor = newColor;
    }

    public boolean isExecute() {
        return isExecute;
    }


    @Override
    public boolean execute() {

        lastColor = DesignActivity.currentColor;
        baseLastColor = DesignActivity.baseCurrentColor;

        if(lastColor != newColor){
            DesignActivity.currentColor = newColor;
            DesignActivity.baseCurrentColor = baseNewColor;

            try{
                DesignActivity.colorImage.getDrawable().mutate().setColorFilter(baseNewColor, PorterDuff.Mode.SRC_IN);
            }catch (NullPointerException e){

            }
            if (!TouchView.shapes.isEmpty()) {
                for (Movable movable : TouchView.shapes) {
                    movable.setColor(context, newColor);
                }
                DesignActivity.touchView.invalidate();
            }

            isExecute = true;
        }
        return isExecute;
    }

    @Override
    public void undo() {
        try{
            DesignActivity.colorImage.getDrawable().mutate().setColorFilter(baseLastColor, PorterDuff.Mode.SRC_IN);
        }catch (NullPointerException e){

        }
        if (!TouchView.shapes.isEmpty()) {
            for (Movable movable : TouchView.shapes) {
                movable.setColor(context, lastColor);
            }
        }

        DesignActivity.currentColor = lastColor;
        DesignActivity.baseCurrentColor = baseLastColor;

    }
}

