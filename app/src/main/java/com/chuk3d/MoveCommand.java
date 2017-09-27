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

    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    public float deltaStartX = 0;
    public float deltaEndX = 0;
    public float deltaStartY = 0;
    public float deltaEndY = 0;

    int heightView;
    int widthView;


    private boolean isExecute = false;

        public MoveCommand(Movable movable, int widthView, int heightView) {
            this.movable = movable;
            this.lastX = movable.getPosX();
            this.lastY = movable.getPosY();

            this.heightView = heightView;
            this.widthView = widthView;

            if(movable instanceof Shape){
                calculateDeltaPosition();

                this.minX = movable.getDeltaStartX();
                this.maxX = widthView - movable.getDeltaEndX();
                this.minY = movable.getDeltaStartY();
                this.maxY = heightView - movable.getDeltaEndY();
            }
        }

    public void setNewX(float newX) {

        if (movable instanceof Shape){
            newX = Math.max(minX, Math.min(newX, maxX));
            this.newX = newX;
        }else {
            if (newX < -widthView / 2) {
                newX = widthView / 2;
            } else if (newX > widthView - movable.getWidth()) {
                newX = widthView - movable.getWidth();
            }
            this.newX = newX;
        }
    }

    public void setNewY(float newY) {
        if(movable instanceof Shape){
            newY = Math.max(minY, Math.min(newY, maxY));
            this.newY = newY;
        }else{
            if (newY < 0) {
                newY = 0;
            }
            else if (newY > heightView - movable.getHeight()) {
                newY = heightView - movable.getHeight();
            }
            this.newY = newY;
        }
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
        }





    private void calculateDeltaPosition() {
        double radians = Math.toRadians(movable.getAngle());

        float finalMovableWidth = ((Shape) movable).getDrawable().getIntrinsicWidth() * movable.getScaleFactor();
        float deltaWidth = (finalMovableWidth - ((Shape) movable).getDrawable().getIntrinsicWidth()) / 2;

        float finalMovableHeight = ((Shape) movable).getDrawable().getIntrinsicHeight() * movable.getScaleFactor();
        float deltaHeight = (finalMovableHeight - ((Shape) movable).getDrawable().getIntrinsicHeight()) / 2;

        float cx = movable.getPosX() + ((Shape)movable).getPivotx();
        float cy = movable.getPosY() + ((Shape)movable).getPivoty();

        float x = movable.getPosX() - deltaWidth;
        float y = movable.getPosY() - deltaHeight;

        float xEnd = movable.getPosX() + (((Shape) movable).getDrawable().getIntrinsicWidth()) + deltaWidth;
        float yEnd = movable.getPosY() + (((Shape) movable).getDrawable().getIntrinsicHeight()) + deltaHeight;

        calculateDeltaPosition(x, y, cx, cy, radians);
        calculateDeltaPosition(xEnd, y, cx, cy, radians);
        calculateDeltaPosition(xEnd, yEnd, cx, cy, radians);
        calculateDeltaPosition(x, yEnd, cx, cy, radians);

        movable.setDeltaStartX(deltaStartX);
        movable.setDeltaEndX(deltaEndX);
        movable.setDeltaStartY(deltaStartY);
        movable.setDeltaEndY(deltaEndY);
    }


    private void calculateDeltaPosition(float x, float y, float cx, float cy, double radians) {

        float tempX = x - cx;
        float tempY = y - cy;

        float rotatedX = (float) (tempX*Math.cos(radians) - tempY*Math.sin(radians));
        float rotatedY = (float) (tempX*Math.sin(radians) + tempY*Math.cos(radians));

        float squarX = rotatedX + cx;
        float squarY = rotatedY + cy;

        if (squarX < movable.getPosX()) {
            float delta = movable.getPosX() - squarX;
            if (delta > deltaStartX) {
                deltaStartX = delta;
            }
        }
        else if (squarX > movable.getPosX()){
            float delta = squarX - movable.getPosX();
            if (delta > deltaEndX) {
                deltaEndX = delta;
            }
        }

        if (squarY < movable.getPosY()) {
            float delta = movable.getPosY() - squarY;
            if (delta > deltaStartY) {
                deltaStartY = delta;
            }
        }
        else if (squarY > movable.getPosY()){
            float delta = squarY - movable.getPosY();
            if (delta > deltaEndY) {
                deltaEndY = delta;
            }
        }
    }


}