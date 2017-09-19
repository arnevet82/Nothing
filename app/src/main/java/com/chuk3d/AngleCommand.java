package com.chuk3d;

/**
 * Created by Admin on 13/09/2017.
 */

public class AngleCommand extends Command {

    private Movable movable;
    private float newAngle;
    private float lastAngle;
    private boolean isExecute = false;

    public AngleCommand(Movable movable) {
        if(movable !=null){
            this.movable = movable;
            this.lastAngle = movable.getAngle();
        }
    }

    public AngleCommand(Movable movable, float newAngle) {
        if(movable !=null){
            this.movable = movable;
            this.lastAngle = movable.getAngle();
        }
        this.newAngle = newAngle;
    }

    public void setNewAngle(float newAngle) {
        this.newAngle = newAngle;
    }

    public boolean isExecute() {
        return isExecute;
    }

    @Override
    public boolean execute() {
        if (newAngle != lastAngle) {
            if (!TouchView.shapes.isEmpty()) {
                movable.setAngle(newAngle);
            }
            DesignActivity.touchView.invalidate();
            isExecute = true;
        }

        return isExecute;
    }

    @Override
    public void undo() {

        if(!TouchView.shapes.isEmpty()) {
            movable.setAngle(lastAngle);
        }
        DesignActivity.touchView.invalidate();
    }
}
