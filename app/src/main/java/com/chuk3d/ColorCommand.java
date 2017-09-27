package com.chuk3d;

import android.content.Context;
import android.graphics.EmbossMaskFilter;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
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


    public static void setGrayColor(Context context){
        DesignActivity.vButton.setVisibility(View.VISIBLE);
        if(DesignActivity.colorImage.getDrawable()!=null){
            DesignActivity.colorImage.getDrawable().mutate().setColorFilter(context.getResources().getColor(R.color.editGrayBigShape), PorterDuff.Mode.SRC_IN);
        }

        for(Movable mMovable:TouchView.shapes){
            mMovable.setGrayColor(context);
        }
        Movable.current_movable.setClickColor(context);
        if(Movable.current_movable instanceof Text){
            DesignActivity.fontsBar.setVisibility(View.VISIBLE);
        }

    }

    public static void clearGrayColor(Context context){
        if(DesignActivity.colorImage.getDrawable() != null) {
            DesignActivity.colorImage.getDrawable().mutate().setColorFilter(DesignActivity.baseCurrentColor, PorterDuff.Mode.SRC_IN);
        }
        if (!TouchView.shapes.isEmpty()) {
            for (Movable movable : TouchView.shapes) {
                movable.setColor(context, DesignActivity.currentColor);
            }
            DesignActivity.touchView.invalidate();
        }
    }

    @Override
    public boolean execute() {

        lastColor = DesignActivity.currentColor;
        baseLastColor = DesignActivity.baseCurrentColor;

        if(lastColor != newColor){
            DesignActivity.currentColor = newColor;
            DesignActivity.baseCurrentColor = baseNewColor;

            if (DesignActivity.colorImage.getDrawable()!=null) {
                DesignActivity.colorImage.getDrawable().mutate().setColorFilter(baseNewColor, PorterDuff.Mode.SRC_IN);
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
        if(DesignActivity.colorImage.getDrawable()!=null){
            DesignActivity.colorImage.getDrawable().mutate().setColorFilter(baseLastColor, PorterDuff.Mode.SRC_IN);
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

