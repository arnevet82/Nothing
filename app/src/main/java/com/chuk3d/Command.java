package com.chuk3d;

/**
 * Created by Admin on 27/08/2017.
 */

public abstract class Command {
    public abstract boolean execute();
    public abstract void undo();

}