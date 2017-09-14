package com.chuk3d;

/**
 * Created by Admin on 13/09/2017.
 */

public class AngleCommand extends Command {

    private Shape shape;
    private float newAngle;
    private float lastAngle;
    private boolean isExecute = false;

    public AngleCommand(Shape shape) {
        if(shape!=null){
            this.shape = shape;
            this.lastAngle = shape.getAngle();
        }
    }

    public AngleCommand(Shape shape, float newAngle) {
        if(shape!=null){
            this.shape = shape;
            this.lastAngle = shape.getAngle();
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
            if (DesignActivity.currentNumText.getText().equals("T")) {
                TouchView.texts.get(TouchView.CURRENT_TEXT).setAngle(newAngle);
            } else if (!TouchView.shapes.isEmpty()) {
                shape.setAngle(newAngle);
            }
            DesignActivity.touchView.invalidate();
            isExecute = true;
        }

        return isExecute;
    }

    @Override
    public void undo() {
        if (DesignActivity.currentNumText.getText().equals("T")) {
            if(!TouchView.texts.isEmpty()&&TouchView.CURRENT_TEXT > -1){
                TouchView.texts.get(TouchView.CURRENT_TEXT).setAngle(lastAngle);
            }else{
                TouchView.texts.getLast().setAngle(lastAngle);
            }
        } else if (!TouchView.shapes.isEmpty()) {
            shape.setAngle(lastAngle);
        }
        DesignActivity.touchView.invalidate();
    }
}
