package com.chuk3d;

/**
 * Created by Admin on 29/08/2017.
 */

public class PunchCommand extends Command {
    private TouchView touchView;
    private int pos;
    private String tag;
    private int[]puncheResources = new int[36];

    public PunchCommand(TouchView touchView, int pos, String tag, int[]punchResources){
        this.touchView = touchView;
        this.pos = pos;
        this.tag = tag;
        this.puncheResources = punchResources;
    }

    @Override
    void execute() {
        touchView.punch(pos, tag, puncheResources);
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
