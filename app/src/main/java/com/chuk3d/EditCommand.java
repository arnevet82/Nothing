package com.chuk3d;

import android.content.Context;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Admin on 19/09/2017.
 */

public class EditCommand extends Command {


    private Text text;
    private String newText;
    private String lastText;
    private boolean isExecute = false;
    private Context context;


    public EditCommand(Movable movable, Context context) {
        this.context = context;
        this.text = (Text)movable;
        this.lastText = text.getSl().getText().toString();

        DesignActivity.editText.setVisibility(View.VISIBLE);
        DesignActivity.editText.setText(lastText);
        text.setSl(new StaticLayout("", new TextPaint(),800,
                Layout.Alignment.ALIGN_CENTER, 1f,0f,false));
        DesignActivity.touchView.invalidate();
        DesignActivity.editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public boolean isExecute() {
        return isExecute;
    }

    @Override
    public boolean execute() {

        newText = DesignActivity.editText.getText().toString();

        if(!newText.equals(lastText)){

            if (!TouchView.shapes.isEmpty()) {
                text.setSl(new StaticLayout(newText, text.getTextPaint(),800,
                        Layout.Alignment.ALIGN_CENTER, 1f,0f,false));
            }

            isExecute = true;
        }

        DesignActivity.editText.setText("");
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(DesignActivity.editText.getWindowToken(), 0);

        return isExecute;
    }

    @Override
    public void undo() {

        if(!TouchView.shapes.isEmpty()) {
            text.setSl(new StaticLayout(lastText, text.getTextPaint(),800,
                    Layout.Alignment.ALIGN_CENTER, 1f,0f,false));
        }
        DesignActivity.touchView.invalidate();
    }
}
