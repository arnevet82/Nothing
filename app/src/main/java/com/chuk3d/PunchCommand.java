package com.chuk3d;

/**
 * Created by Admin on 29/08/2017.
 */

public class PunchCommand extends Command {
    private TouchView touchView;
    private int pos;
    private String tag;

    public PunchCommand(TouchView touchView, int pos, String tag){
        this.touchView = touchView;
        this.pos = pos;
        this.tag = tag;
    }

    @Override
    void execute() {
        touchView.punch(pos, tag);
        DesignActivity.currentNumText.setText("S");
        DesignActivity.stack.add(tag);
        DesignActivity.clearStack(3);
    }

    @Override
    void undo() {

        if(!TouchView.shapes.isEmpty()){
            TouchView.shapes.removeLast();
            TouchView.shapesForColor.removeLast();
            DesignActivity.stack.removeLast();
            touchView.invalidate();
        }
    }
}
