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

    public AddCommand(Context context, int resourceId, float x, float y, String type){
        this.context = context;
        this.resourceId = resourceId;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public boolean execute() {
        try{
            Drawable drawable = ContextCompat.getDrawable(context, resourceId);
            Drawable colorDrawable = ContextCompat.getDrawable(context, resourceId);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            TouchView.shapes.add(ShapeFactory.getShape(drawable, colorDrawable, x, y, type));
            colorDrawable.setBounds(0, 0, colorDrawable.getIntrinsicWidth(), colorDrawable.getIntrinsicHeight());
            TouchView.shapes.getLast().setTag(type);
            TouchView.shapes.getLast().setInitialColor(context);
            DesignActivity.touchView.invalidate();
        }catch (IndexOutOfBoundsException e){
            Toast.makeText(context, "Unable to perform action", Toast.LENGTH_SHORT).show();
        }
        TouchView.CURRENT_SHAPE = TouchView.shapes.size()-1;

        return true;
    }

    @Override
    public void undo() {
        TouchView.shapes.removeLast();
//        TouchView.CURRENT_SHAPE = TouchView.shapes.size()-1;
        DesignActivity.touchView.invalidate();
    }
}