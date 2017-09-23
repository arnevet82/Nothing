package com.chuk3d;

/**
 * Created by Admin on 13/09/2017.
 */

public class ScaleCommand extends Command {

    private Movable movable;
    protected float newScaleFactor;
    protected float lastScaleFactor;
    private boolean isExecute = false;

    public ScaleCommand(Movable movable) {
        this.movable = movable;
        this.lastScaleFactor = movable.getScaleFactor();
    }

    public void setNewScaleFactor(float newScaleFactor) {
        this.newScaleFactor = newScaleFactor;
    }

    public boolean isExecute() {
        return isExecute;
    }

    @Override
    public boolean execute() {
        if (newScaleFactor != lastScaleFactor) {
            movable.setScaleFactor(newScaleFactor);
            DesignActivity.touchView.invalidate();
            isExecute = true;
        }
        return isExecute;
    }

    @Override
    public void undo() {
        movable.setScaleFactor(lastScaleFactor);
//        DesignActivity.touchView.invalidate();
    }
}
