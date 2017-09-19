package com.chuk3d;

import android.content.Context;
import android.view.View;

/**
 * Created by Admin on 29/08/2017.
 */

public class DeleteCommand extends Command {
    private Context context;
    private Movable movable;
    private float x;
    private float y;
    private boolean isExecute = false;


    public DeleteCommand(Movable movable, Context context){
        this.movable = movable;
        this.context = context;
        this.x = movable.getPosX();
        this.y = movable.getPosY();
    }

    public boolean isExecute() {
        return isExecute;
    }


    @Override
    public boolean execute() {
        if(movable!=null){
            TouchView.shapes.remove(Movable.current_movable);
            isExecute = true;
        }

        DesignActivity.vButton.setVisibility(View.INVISIBLE);
        DesignActivity.fontsBar.setVisibility(View.INVISIBLE);

        return isExecute;
    }

    @Override
    public void undo() {
        TouchView.shapes.add(movable);
        movable.setColor(context, DesignActivity.currentColor);
        movable.setPosX(x);
        movable.setPosY(y);
    }
}
