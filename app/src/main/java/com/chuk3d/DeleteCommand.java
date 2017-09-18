package com.chuk3d;

import android.view.View;

/**
 * Created by Admin on 29/08/2017.
 */

public class DeleteCommand extends Command {
    TouchView touchView;

    public DeleteCommand(TouchView touchView){
        this.touchView = touchView;
    }

    @Override
    public boolean execute() {
        TouchView.shapes.remove(Movable.current_movable);

        DesignActivity.vButton.setVisibility(View.INVISIBLE);
        DesignActivity.fontsBar.setVisibility(View.INVISIBLE);

        touchView.invalidate();


        return true;
    }

    @Override
    public void undo() {

    }
}
