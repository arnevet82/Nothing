package com.chuk3d;

/**
 * Created by Admin on 13/09/2017.
 */

public class MoveCommand extends Command {

    private Movable movable;
    private float newX;
    private float newY;
    private float lastX;
    private float lastY;
    private boolean isExecute = false;

    public MoveCommand(Movable movable) {
        this.movable = movable;
        this.lastX = movable.getPosX();
        this.lastY = movable.getPosY();
    }

    public void setNewX(float newX, int widthView) {
        if(movable instanceof Shape){
            if (newX < 0) {
                newX = 0;
            }
            else if (newX > widthView - movable.getWidth()) {
                newX = widthView - movable.getWidth();
            }
            this.newX = newX;
        }else{
            if (newX < - widthView/2) {
                newX = widthView/2;
            }
            else if (newX > widthView - movable.getWidth()) {
                newX = widthView - movable.getWidth();
            }
            this.newX = newX;
        }

    }

    public void setNewY(float newY, int heightView) {

            if (newY < 0) {
                newY = 0;
            }
            else if (newY > heightView - movable.getHeight()) {
                newY = heightView - movable.getHeight();
            }
            this.newY = newY;

    }


    public boolean isExecute() {
        return isExecute;
    }

    @Override
    public boolean execute() {
        if (newX != lastX || newY != lastY) {
            movable.setPosX(newX);
            movable.setPosY(newY);
            DesignActivity.touchView.invalidate();
            isExecute = true;
        }
        return isExecute;
    }

    @Override
    public void undo() {
        movable.setPosX(lastX);
        movable.setPosY(lastY);
        DesignActivity.touchView.invalidate();
    }
}