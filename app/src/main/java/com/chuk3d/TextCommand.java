//package com.chuk3d;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.support.v4.content.ContextCompat;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Toast;
//
///**
// * Created by Admin on 29/08/2017.
// */
//
//public class TextCommand extends Command {
//    private Context context;
//    private String text;
//    private String tag;
//    TouchView touchView;
//    private float x;
//    private float y;
//    private String type;
//
//
//    public TextCommand(Context context, String text, float x, float y, String type){
//        this.context = context;
//        this.text = text;
//        this.x = x;
//        this.y = y;
//        this.type = type;
//    }
//
//    @Override
//    public boolean execute() {
//        DesignActivity.editText.setVisibility(View.INVISIBLE);
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(DesignActivity.editText.getWindowToken(), 0);
//
//        try{
//            TouchView.texts.add(ShapeFactory.getShape(text, x, y, type, context));
//
//        }catch (IndexOutOfBoundsException e){
//            Toast.makeText(context, "Unable to perform action", Toast.LENGTH_SHORT).show();
//        }
//        TouchView.CURRENT_TEXT = TouchView.texts.size()-1;
//
//        DesignActivity.touchView.invalidate();
//        DesignActivity.editText.setText("");
//        DesignActivity.fontsBar.setVisibility(View.VISIBLE);
//        return true;
//    }
//
//    void edit(){
//        Movable previousText = TouchView.texts.get(TouchView.CURRENT_TEXT);
//        float angle = previousText.getAngle();
//        float xPos = previousText.getPosX();
//        float yPos = previousText.getPosY();
//        float scaleFactor = previousText.getScaleFactor();
//
////        Shape shape = ShapeFactory.getShape(text, x, y, type, context);
////        shape.setAngle(angle);
////        shape.setPosX(xPos);
////        shape.setPosY(yPos);
////        shape.setScaleFactor(scaleFactor);
////        TouchView.texts.set(TouchView.CURRENT_TEXT, shape);
//
////        if(DesignActivity.TcurrentFont != null && TouchView.texts.getLast().getTag().equals("topping")){
////            ToppingText toppingText = (ToppingText)TouchView.texts.getLast();
////            toppingText.setFont(DesignActivity.TcurrentFont);
////        }else if(DesignActivity.PcurrentFont != null && TouchView.texts.getLast().getTag().equals("punch")){
////            PunchText punchText = (PunchText)TouchView.texts.getLast();
////            punchText.setFont(DesignActivity.PcurrentFont);
////        }
//        DesignActivity.editText.setText("");
//        DesignActivity.editText.setVisibility(View.INVISIBLE);
//        DesignActivity.fontsBar.setVisibility(View.INVISIBLE);
//        DesignActivity.vButton.setVisibility(View.INVISIBLE);
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(DesignActivity.editText.getWindowToken(), 0);
//    }
//
//    @Override
//    public void undo() {
//        TouchView.texts.removeLast();
//        DesignActivity.touchView.invalidate();
//
//    }
//}