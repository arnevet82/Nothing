package com.chuk3d;

/**
 * Created by Admin on 13/09/2017.
 */

public class MoveCommand extends Command {

    private Shape shape;
    private float newX;
    private float newY;
    private float lastX;
    private float lastY;
    private boolean isExecute = false;

    public MoveCommand(Shape shape) {
        this.shape = shape;
        this.lastX = shape.getPosX();
        this.lastY = shape.getPosY();
    }

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }

    public boolean isExecute() {
        return isExecute;
    }

    @Override
    public boolean execute() {
        if (newX != lastX || newY != lastY) {
            shape.setPosX(newX);
            shape.setPosY(newY);
            DesignActivity.touchView.invalidate();
            isExecute = true;
        }
        return isExecute;
    }

    @Override
    public void undo() {
        shape.setPosX(lastX);
        shape.setPosY(lastY);
        DesignActivity.touchView.invalidate();
    }
}