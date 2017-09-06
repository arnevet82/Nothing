package com.chuk3d;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Admin on 29/08/2017.
 */

public class TextCommand extends Command {
    private Context context;
    private String text;
    private String tag;
    TouchView touchView;


    public TextCommand(Context context, String text, String tag, TouchView touchView) {
        this.context = context;
        this.tag = tag;
        this.text = text;
        this.touchView = touchView;
    }

    @Override
    void execute() {

        DesignActivity.editText.setVisibility(View.INVISIBLE);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(DesignActivity.editText.getWindowToken(), 0);

        TouchView.texts.add(new TextBody(text, context, tag));
        TouchView.CURRENT_TEXT = TouchView.texts.size()-1;

        if(DesignActivity.TcurrentFont != null && TouchView.texts.getLast().getTag().equals("topping")){
            TouchView.texts.getLast().getTextPaint().setTypeface(DesignActivity.TcurrentFont);
        }else if(DesignActivity.PcurrentFont != null && TouchView.texts.getLast().getTag().equals("punch")){
            TouchView.texts.getLast().getTextPaint().setTypeface(DesignActivity.PcurrentFont);
        }

        DesignActivity.editText.setText("");
        DesignActivity.fontsBar.setVisibility(View.VISIBLE);


        DesignActivity.stack.add("text");
        DesignActivity.clearStack(3);
    }


    void edit(){
        TextBody previousText = TouchView.texts.get(TouchView.CURRENT_TEXT);
        float angle = previousText.getAngle();
        float xPos = previousText.getPosX();
        float yPos = previousText.getPosY();
        float scaleFactor = previousText.getScaleFactor();

        TextBody newTextBody = new TextBody(text, context, tag);
        newTextBody.setAngle(angle);
        newTextBody.setPosX(xPos);
        newTextBody.setPosY(yPos);
        newTextBody.setScaleFactor(scaleFactor);
        TouchView.texts.set(TouchView.CURRENT_TEXT, newTextBody);

        if(DesignActivity.TcurrentFont != null && TouchView.texts.getLast().getTag().equals("topping")){
            TouchView.texts.getLast().getTextPaint().setTypeface(DesignActivity.TcurrentFont);
        }else if(DesignActivity.PcurrentFont != null && TouchView.texts.getLast().getTag().equals("punch")){
            TouchView.texts.getLast().getTextPaint().setTypeface(DesignActivity.PcurrentFont);
        }
        DesignActivity.editText.setText("");
        DesignActivity.editText.setVisibility(View.INVISIBLE);
        DesignActivity.fontsBar.setVisibility(View.INVISIBLE);
        DesignActivity.vButton.setVisibility(View.INVISIBLE);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(DesignActivity.editText.getWindowToken(), 0);
    }

    @Override
    void undo() {
        if(!TouchView.texts.isEmpty()){
            TouchView.texts.removeLast();
            DesignActivity.stack.removeLast();
            TouchView.CURRENT_TEXT--;
            touchView.invalidate();
        }
    }
}
