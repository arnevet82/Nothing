package com.chuk3d;

import android.graphics.drawable.Drawable;

/**
 * Created by Admin on 13/09/2017.
 */

public class ShapeFactory {
    public static Shape getShape(Drawable drawable, Drawable colorDrawable, float posX, float posY, String tag){
        if(tag.equals("punch")){
            return new PunchShape(drawable, colorDrawable, posX, posY);
        } else if(tag.equals("topping")){
            return new ToppingShape(drawable, colorDrawable, posX, posY);
        }
        return null;
    }

    public static Shape getShape(Drawable drawable, Drawable colorDrawable, float posX, float posY, float scaleFactor, float angle, String tag){
        if(tag.equals("punch")){
            return new PunchShape(drawable, colorDrawable, posX, posY, scaleFactor, angle, tag);
        } else if(tag.equals("topping")){
            return new ToppingShape(drawable, colorDrawable, posX, posY, scaleFactor, angle, tag);
        }
        return null;
    }
}