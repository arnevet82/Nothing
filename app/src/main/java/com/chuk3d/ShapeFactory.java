package com.chuk3d;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by Admin on 13/09/2017.
 */

public class ShapeFactory {

    public static Shape getShape(int resourceId, float posX, float posY, String tag, Context context){
        if(tag.equals("pShape")){
            return new PunchShape(resourceId, posX, posY, context);
        } else if(tag.equals("tShape")){
            ToppingShape toppingShape = new ToppingShape(resourceId, posX, posY, context);
            toppingShape.setColor(context, DesignActivity.currentColor);
            return toppingShape;
        }
        return null;
    }

    public static Text getShape(String text, float posX, float posY,  String tag, Context context){
        if(tag.equals("pText")){
            return new PunchText(text, posX, posY, context);
        } else if(tag.equals("tText")){
            return new ToppingText(text, posX, posY, context);
        }
        return null;
    }
}