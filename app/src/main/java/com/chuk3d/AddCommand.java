package com.chuk3d;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by Admin on 11/09/2017.
 */

public class AddCommand extends Command {

    private Context context;
    private int resourceId;
    private float x;
    private float y;
    private String type;
    private String text;


    public AddCommand(Context context, int resourceId, String text, float x, float y, String type){
        this.context = context;
        this.resourceId = resourceId;
        this.text = text;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public boolean execute() {

        switch (type){
            case "tShape":
            case "pShape":

                Movable movable = ShapeFactory.getShape(resourceId,x, y, type, context);
                TouchView.shapes.add(movable);
                Movable.setCurrent_movable(movable);
                break;
            case "tText":
            case "pText":

                movable = ShapeFactory.getShape(text,x, y, type, context);
                TouchView.shapes.add(movable);
                Movable.setCurrent_movable(movable);

                break;
        }

        return true;
    }

    @Override
    public void undo() {
        TouchView.shapes.removeLast();
        DesignActivity.touchView.invalidate();
    }
}