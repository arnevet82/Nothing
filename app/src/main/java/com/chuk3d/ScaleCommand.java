package com.chuk3d;

/**
 * Created by Admin on 13/09/2017.
 */

public class ScaleCommand extends Command {

    private Shape shape;
    protected float newScaleFactor;
    protected float lastScaleFactor;
    private boolean isExecute = false;

    public ScaleCommand(Shape shape) {
        this.shape = shape;
        this.lastScaleFactor = shape.getScaleFactor();
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
            shape.setScaleFactor(newScaleFactor);
            DesignActivity.touchView.invalidate();
            isExecute = true;
        }
        return isExecute;
    }

    @Override
    public void undo() {
        shape.setScaleFactor(lastScaleFactor);
        DesignActivity.touchView.invalidate();
    }
}
