package com.chuk3d;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by Admin on 13/09/2017.
 */

public class ShapeFactory {

    public static Shape getShape(int resourceId, float posX, float posY, MovableType movableType, Context context){
        Shape shape = null;
        switch (movableType) {
            case P_SHAPE: {
                shape = new PunchShape(resourceId, posX, posY, context);
                break;
            }
            case T_SHAPE: {
                shape = new ToppingShape(resourceId, posX, posY, context);
                break;
            }
        }
        return shape;
    }

    public static Text getShape(String text, float posX, float posY, MovableType movableType, Context context){
        Text textObject = null;
        switch (movableType) {
            case P_TEXT: {
                textObject = new PunchText(text, posX, posY, context);
                break;
            }
            case T_TEXT: {
                textObject = new ToppingText(text, posX, posY, context);
                break;
            }
        }
        return textObject;
    }

    public static Hole getHole(int resourceId, float posX, float posY, MovableType movableType, Context context){
        Hole hole = null;
        switch (movableType) {
            case P_HOLE: {
                hole = new PunchHole(resourceId, posX, posY, context);
                break;
            }
            case T_HOLE: {
                hole = new ToppingHole(resourceId, posX, posY, context);
                break;
            }
        }
        return hole;
    }
}