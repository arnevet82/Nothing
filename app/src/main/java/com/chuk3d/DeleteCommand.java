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
    void execute() {


        if (DesignActivity.currentNumText.getText().equals("T")) {
            DesignActivity.editText.setText("");
            if (!TouchView.texts.isEmpty()) {

                TouchView.texts.remove(TouchView.CURRENT_TEXT);
                if (TouchView.CURRENT_TEXT == 0 && !TouchView.shapes.isEmpty()) {
                    TouchView.CURRENT_TEXT++;
                } else {
                    TouchView.CURRENT_TEXT--;
                    DesignActivity.currentNumText.setText("");
                }
            }

        } else {
            if (!TouchView.shapes.isEmpty()) {

                TouchView.shapes.remove(TouchView.CURRENT_SHAPE);
                TouchView.shapesForColor.remove(TouchView.CURRENT_SHAPE);

                if (TouchView.CURRENT_SHAPE == 0 && !TouchView.shapes.isEmpty()) {
                    TouchView.CURRENT_SHAPE++;
                } else {
                    TouchView.CURRENT_SHAPE--;
                    DesignActivity.currentNumText.setText("");
                }
            }
        }
        DesignActivity.vButton.setVisibility(View.INVISIBLE);
        DesignActivity.fontsBar.setVisibility(View.INVISIBLE);

        touchView.invalidate();



    }

    @Override
    void undo() {

    }
}
