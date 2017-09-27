package com.chuk3d;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Admin on 11/09/2017.
 */

public class AddCommand extends Command {

    private Context context;
    private int resourceId;
    private float x;
    private float y;
    MovableType type;
    private String text;


    public AddCommand(Context context, int resourceId, String text, float x, float y, MovableType type){
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
            case T_SHAPE:
            case P_SHAPE:
                Movable movable = ShapeFactory.getShape(resourceId,x, y, type, context);
                TouchView.shapes.add(movable);
                Movable.setCurrent_movable(movable);
                break;
            case T_TEXT:
            case P_TEXT:

                movable = ShapeFactory.getShape(text,x/3, y, type, context);
                TouchView.shapes.add(movable);
                Movable.setCurrent_movable(movable);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(DesignActivity.editText.getWindowToken(), 0);

                break;
        }

        return true;
    }

    @Override
    public void undo() {
        TouchView.shapes.removeLast();
    }
}