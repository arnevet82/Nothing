package com.chuk3d;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 21/08/2017.
 */

public class DesignActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GToppingFragment.ToppinfFragmentItemClickCallback, GPunchFragment.PunchFragmentItemClickCallback,
        OtherToppingFragment.ToppinfFragmentItemClickCallback, OtherPunchFragment.PunchFragmentItemClickCallback
{

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 99;

    public static final String POSITION_KEY = "POSITION";
    public static int position = 0;
    public static final String MAIN_IMAGE_ROTATION = "ROTATION";
    public static float mainImageRotation;
    public static final String BASE_SHAPE_ARRAY_KEY = "BASE_SHAPE";
    public static int [] baseShapes = new int[36];
    private static final String TAG = "DesignActivity";
    boolean b = true;
    private AngleCommand angleCommand = null;
    TextView title;
    TabLayout toppingTabLayout, punchTabLayout;
    ViewPager toppingViewPager, punchViewPager;
    ToppingTabPager toppingTabPager;
    PunchTabPager punchTabPager;
    RelativeLayout colorBar, designContainer, gridScreen, textContainer, bottomBar, bPaymentScreen;
    LinearLayout textPunchToppingChoice;
    static LinearLayout fontsBar;
    NestedScrollView toppingTabs, punchTabs;
    ImageView mainImage;
    static ImageView colorImage;
    ImageButton next, color, topping, punch, text, punchText, toppingText, vText;
    Button color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12;
    public static int currentColor;
    public static TextView currentNumText;
    public static Button vButton;
    public static Button grid, undo, delete, rotate;
    static TouchView touchView;
    ImageView rotateCircle, rotateRuler, rotateLine;
    RelativeLayout rotationBar, resizeBar;
    Button degrees0, degrees90, degrees180, degrees270, degrees360, cm, inch, letsChukAgain;
    public static Button font1, font2, font3, font4, font5, font6, font7, editTextBody;
    public static Typeface vampiro, montserrat, alef, hiraKaku, athelas, montserratItalic, baloo, pacifico;
    public static Typeface PcurrentFont, TcurrentFont;
    NestedScrollView punchScrollView, toppingScrollView;

    int heightScreen, widthScreen;

    public static LinkedList<String> stack = new LinkedList<>();
    public static EditText editText;
    public static boolean isInches;
    public static String sizeTerm = "cm";

    File imageFile, galleryImageFile;
    public static String fileName, galleryFileName, formattedSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        position = getIntent().getIntExtra(POSITION_KEY, 0);
        mainImageRotation = getIntent().getFloatExtra(MAIN_IMAGE_ROTATION, 0);
        baseShapes = getIntent().getIntArrayExtra(BASE_SHAPE_ARRAY_KEY);

        init();
        setUpBaseShape(position);
        touchView = new TouchView(this);
        designContainer.addView(touchView);
        currentColor = this.getResources().getColor(R.color.baseShapeFirstColor);
        setRotationRuler();
    }

    public static void showGridAndUndo(){
        grid.setVisibility(View.VISIBLE);
        undo.setVisibility(View.VISIBLE);
    }

    public static void showDeleteAndRotate(){
        delete.setVisibility(View.VISIBLE);
        rotate.setVisibility(View.VISIBLE);
    }

    public void hideDeleteAndRotate(){
        delete.setVisibility(View.INVISIBLE);
        rotate.setVisibility(View.INVISIBLE);
    }

    public void onNextButtonClicked(View v){
        if(vText.getVisibility() == View.VISIBLE){
            Toast.makeText(this, "finished? click 'done' ", Toast.LENGTH_SHORT).show();
        }else if(resizeBar.getVisibility() == View.INVISIBLE && bPaymentScreen.getVisibility() == View.INVISIBLE){
            hideAllUIElements();
            bottomBar.setVisibility(View.INVISIBLE);
            vButton.setVisibility(View.INVISIBLE);
            clearGrayColor();
            setResizeScreen();
        }else if(resizeBar.getVisibility() == View.VISIBLE){
            setUpBeforePayment();
        }else if(bPaymentScreen.getVisibility() == View.VISIBLE){
            if (checkPermissionREAD_EXTERNAL_STORAGE(getApplicationContext())) {
                takeScreenshot();
                takeScreenshotForGallery();
            }
        }
    }

    public void setUpBeforePayment(){
        resizeBar.setVisibility(View.INVISIBLE);
        designContainer.setVisibility(View.INVISIBLE);
        bPaymentScreen.setVisibility(View.VISIBLE);
        title.setText("Confirm");

        letsChukAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDesign();
                Intent intent = new Intent(getApplication(), BaseShapeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setResizeScreen(){
        resizeBar.setVisibility(View.VISIBLE);
        title.setText("Drag for requested size ");
        cm.setBackgroundColor(Color.parseColor("#626066"));
        inch.setBackgroundColor(Color.parseColor("#d8d8d8"));
        sizeTerm = "cm";
        initSizeBtns();
        resize();
    }

    public void initSizeBtns(){
        View.OnClickListener sizeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSizeBtnClicked(v);
            }
        };
        cm.setOnClickListener(sizeListener);
        inch.setOnClickListener(sizeListener);
    }

    public void onSizeBtnClicked(View v){

        switch (v.getId()){
            case R.id.cm:
                cm.setBackgroundColor(Color.parseColor("#626066"));
                inch.setBackgroundColor(Color.parseColor("#d8d8d8"));
                isInches = false;
                sizeTerm = "cm";
                break;
            case R.id.inch:
                cm.setBackgroundColor(Color.parseColor("#d8d8d8"));
                inch.setBackgroundColor(Color.parseColor("#626066"));
                isInches = true;
                sizeTerm = "inch";
                break;
        }
    }

    public void resize(){

        resizeBar.setOnTouchListener(new View.OnTouchListener() {
            TextView sizeText = (TextView)findViewById(R.id.size_text);
            ImageView resizeBall = (ImageView)findViewById(R.id.resize_circle);

            float x;
            float y;
            float newSize;
            float maxSize;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float sizeInSm = designContainer.getScaleX() * 5.73f;
                float sizeInInch = designContainer.getScaleX()*2.2559f;

                if(isInches){
                    formattedSize = String.format("%.1f", sizeInInch );
                }else{
                    formattedSize = String.format("%.0f", sizeInSm + 0.5);
                }
                newSize = sizeInSm + 0.5f;
                maxSize = 8;
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    x = event.getX();
                    y = event.getY();

                    if(event.getX() < rotateLine.getRight() && event.getX() > 100){
                        resizeBall.setX(x);
                    }


                    float scaleFactor;
                    switch (Helper.getDeviceDensity(getApplicationContext())){
                        case "3.0 xxhdpi":
                            scaleFactor = x*0.0014f;
                            break;
                        case "4.0 xxxhdpi":
                            scaleFactor = x*0.00105f;
                            break;
                        default:
                            scaleFactor = x*0.00105f;
                            break;
                    }

                    scaleFactor = Math.max(0.25f, Math.min(scaleFactor, 1.25f));

                    designContainer.setScaleX(scaleFactor);
                    designContainer.setScaleY(scaleFactor);

                    if (newSize > maxSize) {
                        sizeText.setText("");
                    } else {
                        sizeText.setText("Your keychain size: " + formattedSize + " " + sizeTerm);
                    }
                }

                return true;
            }
        });
    }

    public void undo(View v){

        if(!TouchView.commandStack.isEmpty()){
            touchView.undo();
        }
    }

    public void hideAllUIElements(){
        cleanBarButtons();
        colorBar.setVisibility(View.INVISIBLE);
        toppingTabs.setVisibility(View.INVISIBLE);
        punchTabs.setVisibility(View.INVISIBLE);
        toppingViewPager.setVisibility(View.INVISIBLE);
        toppingTabLayout.setVisibility(View.INVISIBLE);
        punchViewPager.setVisibility(View.INVISIBLE);
        punchTabLayout.setVisibility(View.INVISIBLE);
        textContainer.setVisibility(View.INVISIBLE);
        vButton.setVisibility(View.INVISIBLE);
        rotate.setVisibility(View.INVISIBLE);
        grid.setVisibility(View.INVISIBLE);
        undo.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        rotationBar.setVisibility(View.INVISIBLE);
        fontsBar.setVisibility(View.INVISIBLE);
        rotationBar.setVisibility(View.INVISIBLE);
        textPunchToppingChoice.setVisibility(View.INVISIBLE);
    }

    public void cleanBarButtons(){
        text.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
        color.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
        topping.setImageDrawable(getResources().getDrawable(R.drawable.topping_icon));
        punch.setImageDrawable(getResources().getDrawable(R.drawable.punch_icon));
    }

    public void initEditTextBody(){
        editTextBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                Shape shape = TouchView.texts.get(TouchView.CURRENT_TEXT);
                ToppingText toppingText;
                PunchText punchText;
                String tag = TouchView.texts.get(TouchView.CURRENT_TEXT).getTag();
                switch (tag){
                    case "topping":
                        toppingText = (ToppingText) TouchView.texts.get(TouchView.CURRENT_TEXT);
                        editText.setText(toppingText.getSl().getText());
                        toppingText.setSl(new StaticLayout("", new TextPaint(),800,
                                Layout.Alignment.ALIGN_CENTER, 1f,0f,false));
                        break;
                    case "punch":
                        punchText = (PunchText) TouchView.texts.get(TouchView.CURRENT_TEXT);
                        editText.setText(punchText.getSl().getText());
                        punchText.setSl(new StaticLayout("", new TextPaint(),800,
                                Layout.Alignment.ALIGN_CENTER, 1f,0f,false));
                        break;
                }

                designEditText(shape.getTag());
                touchView.invalidate();
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                vText.setVisibility(View.VISIBLE);
                currentNumText.setText("T");

                onVTextClicked(shape.getTag(), "edit");
            }
        });
    }

    public void designEditText(String tag){
        switch (tag){
            case "punch":
                editText.setTextColor(getResources().getColor(R.color.almostWhite));
                editText.setShadowLayer(1, 1, 1, Color.parseColor("#80000000"));
                if(PcurrentFont != null){
                    editText.setTypeface(PcurrentFont);
                }else {
                    editText.setTypeface(baloo);
                }
                break;
            case "topping":
                editText.setTextColor(getResources().getColor(R.color.baseShapeFirstColor));
                editText.setShadowLayer(7, 1, 3, Color.parseColor("#80000000"));
                if(TcurrentFont != null){
                    editText.setTypeface(TcurrentFont);
                }else{
                    editText.setTypeface(pacifico);
                }
                break;
        }

        editText.setVisibility(View.VISIBLE);
    }

    public void onTextButtonClicked(View v){
        if(textContainer.getVisibility()==View.VISIBLE){
            textContainer.setVisibility(View.INVISIBLE);
            text.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.almostWhite),PorterDuff.Mode.SRC_IN);
            if(!TouchView.shapes.isEmpty() || !TouchView.texts.isEmpty() || currentColor != 0){
                showGridAndUndo();
            }
        }else{
            hideAllUIElements();
            text.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.lightPrimary),PorterDuff.Mode.SRC_IN);
            textContainer.setVisibility(View.VISIBLE);
            textPunchToppingChoice.setVisibility(View.VISIBLE);
            initEditTextBody();
        }
    }

    public void onTextPunchTopClicked(View v){
        String tag = (String)v.getTag();
        designEditText(tag);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        textPunchToppingChoice.setVisibility(View.INVISIBLE);
        vText.setVisibility(View.VISIBLE);
        onVTextClicked(tag, "normal");
        currentNumText.setText("T");
    }

    public void onVTextClicked(String tag, String state){

        final String mTag = tag;
        final String mState = state;

        vText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = editText.getText().toString();
                vText.setVisibility(View.INVISIBLE);

                switch (mState){
                    case "normal":
                        touchView.executeTextCommand(getApplicationContext(), text, mTag);
                        currentNumText.setText("T");
                        cleanBarButtons();
                        break;
                    case "edit":
                        float posX = widthScreen / 6.5f;
                        float posY = heightScreen / 5.3f;
                        TextCommand textCommand = new TextCommand(getApplicationContext(), text, posX,posY, mTag);
                        textCommand.edit();
                        break;
                }
                vButton.setVisibility(View.VISIBLE);
                showDeleteAndRotate();
                showGridAndUndo();
                initFonts(mTag);
                touchView.invalidate();
            }
        });
    }

    public static void onFontButtonClicked(View v)    {
        int [] buttonId = {R.id.font_1, R.id.font_2, R.id.font_3, R.id.font_4, R.id.font_5, R.id.font_6, R.id.font_7};
        Typeface []typefaces ={hiraKaku, montserratItalic, baloo, alef, athelas, pacifico, vampiro};

        for(int i = 0; i < buttonId.length; i++){
            if(v.getId() == buttonId[i]) {
                changeFont(typefaces[i]);
                if(!typefaces[i].equals(pacifico) && !typefaces[i].equals(vampiro)){
                    PcurrentFont = typefaces[i];
                }
                if(!typefaces[i].equals(alef) && !typefaces[i].equals(athelas)){
                    TcurrentFont = typefaces[i];
                }

                if(!TouchView.texts.isEmpty()){
                    switch (TouchView.texts.get(TouchView.CURRENT_TEXT).getTag()){
                        case "topping":
                            ToppingText toppingText = (ToppingText)TouchView.texts.get(TouchView.CURRENT_TEXT);
                            toppingText.setFont(TcurrentFont);
                            break;
                        case "punch":
                            PunchText punchText = (PunchText)TouchView.texts.get(TouchView.CURRENT_TEXT);
                            punchText.setFont(PcurrentFont);
                            break;
                    }
                }
            }

        }
        touchView.invalidate();

    }

    public static void initFonts(String tag){

        switch (tag){
            case "punch":
                font6.setVisibility(View.INVISIBLE);
                font7.setVisibility(View.INVISIBLE);
                font4.setVisibility(View.VISIBLE);
                font4.setTypeface(alef);
                font5.setVisibility(View.VISIBLE);
                font5.setTypeface(athelas);
                break;
            case "topping":
                font4.setVisibility(View.INVISIBLE);
                font5.setVisibility(View.INVISIBLE);
                font6.setVisibility(View.VISIBLE);
                font7.setVisibility(View.VISIBLE);
                font6.setX(font4.getX());
                font6.setTypeface(pacifico);
                font7.setX(font5.getX());
                font7.setTypeface(vampiro);
                break;
        }
        fontsBar.setVisibility(View.VISIBLE);
        Button[]buttons={font1, font2, font3, font4, font5, font6, font7};
        for(Button button: buttons){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFontButtonClicked(v);
                }
            });
        }
    }

    public static void changeFont(Typeface typeface){
        editText.setTypeface(typeface);
    }

    public void initVButton() {
        vButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vButton.setVisibility(View.INVISIBLE);
                clearGrayColor();
                rotationBar.setVisibility(View.INVISIBLE);
                fontsBar.setVisibility(View.INVISIBLE);
                textPunchToppingChoice.setVisibility(View.INVISIBLE);
                TouchView.CURRENT_SHAPE = -1;
                TouchView.CURRENT_TEXT = -1;
                showGridAndUndo();
                hideDeleteAndRotate();
            }
        });
    }

    public void onGridBtnClicked(View v) {

        if(gridScreen.getVisibility()==View.INVISIBLE){
            gridScreen.setVisibility(View.VISIBLE);
        }else {
            gridScreen.setVisibility(View.INVISIBLE);
        }

    }

    public void clearGrayColor(){
        ColorCommand colorCommand = new ColorCommand(getApplication(), colorImage, currentColor);
        colorCommand.execute();
    }

    public void showColorBar(View v){
        if(colorBar.getVisibility() == View.VISIBLE){
            colorBar.setVisibility(View.INVISIBLE);
            color.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.almostWhite),PorterDuff.Mode.SRC_IN);
            if(!TouchView.shapes.isEmpty() || !TouchView.texts.isEmpty() || currentColor != 0){
                showGridAndUndo();
            }
        }else{
            hideAllUIElements();
            color.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.lightPrimary),PorterDuff.Mode.SRC_IN);
            colorBar.setVisibility(View.VISIBLE);
            Button[]buttons = {color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12};

            for(Button button:buttons){
                button.setVisibility(View.VISIBLE);
            }
        }
    }

    public void colorClick(View view) {

        Button b = (Button) view;
        color.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
        int newColor = ((ColorDrawable)b.getBackground()).getColor();
        ColorCommand colorCommand = new ColorCommand(this, colorImage, newColor);
        boolean isExecute = colorCommand.execute();
        if (isExecute) {
            touchView.commandStack.push(colorCommand);
        }
        colorBar.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        showGridAndUndo();
        currentColor = newColor;
    }

    public void setUpBaseShape(int pos){

        if(baseShapes[pos] == R.drawable.g_base_shape_31){
            mainImage.setImageDrawable(null);
            colorImage.setImageDrawable(null);
        }else{
            mainImage.setImageDrawable(getResources().getDrawable(baseShapes[pos]));
            colorImage.setImageDrawable(getResources().getDrawable(baseShapes[pos]));
            colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);
            mainImage.setRotation(mainImageRotation);
            colorImage.setRotation(mainImageRotation);

        }

    }

    public void initDrawerAndNavigationView(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        title = (TextView) toolbar.findViewById(R.id.title_text);
        title.setText("Create your design");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerArrowDrawable(new HamburgerDrawable(this));


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.creations) {
            // Handle the camera action
        } else if (id == R.id.branding) {

        } else if (id == R.id.special_request) {

        } else if (id == R.id.printing_services) {

        } else if (id == R.id.contact) {

        } else if (id == R.id.about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onToppingBarClicked(View view) {
        if(toppingTabs.getVisibility() == View.VISIBLE){
            toppingTabs.setVisibility(View.INVISIBLE);
            toppingTabLayout.setVisibility(View.INVISIBLE);
            toppingViewPager.setVisibility(View.INVISIBLE);
            topping.setImageDrawable(getResources().getDrawable(R.drawable.topping_icon));
            if(!TouchView.shapes.isEmpty() || !TouchView.texts.isEmpty() || currentColor != 0){
                showGridAndUndo();
            }
        }else{
            hideAllUIElements();
            toppingTabs.setVisibility(View.VISIBLE);
            toppingTabLayout.setVisibility(View.VISIBLE);
            toppingViewPager.setVisibility(View.VISIBLE);
            topping.setImageDrawable(getResources().getDrawable(R.drawable.topping_icon_green));
        }
    }

    public void onPunchBarClicked(View view) {
        if(punchTabs.getVisibility() == View.VISIBLE){
            punchTabs.setVisibility(View.INVISIBLE);
            punchTabLayout.setVisibility(View.INVISIBLE);
            punchViewPager.setVisibility(View.INVISIBLE);
            punch.setImageDrawable(getResources().getDrawable(R.drawable.topping_icon));
            if(!TouchView.shapes.isEmpty() || !TouchView.texts.isEmpty() || currentColor != 0){
                showGridAndUndo();
            }
        }else{
            hideAllUIElements();
            punchTabs.setVisibility(View.VISIBLE);
            punchTabLayout.setVisibility(View.VISIBLE);
            punchViewPager.setVisibility(View.VISIBLE);
            punch.setImageDrawable(getResources().getDrawable(R.drawable.topping_icon_green));
        }
    }

    @Override
    public void onToppingButtonClicked(View view) {
        toppingTabs.setVisibility(View.VISIBLE);
        toppingTabLayout.setVisibility(View.VISIBLE);
        toppingViewPager.setVisibility(View.VISIBLE);

        int resourceId = getResources().getIdentifier("@" + view.getTag(), "drawable", this.getPackageName());

        touchView.executeAddCommand(this, resourceId, "topping");
        currentNumText.setText("S");
        Log.e("currentNumText", currentNumText.getText().toString());
        hideAllUIElements();
        showGridAndUndo();
        showDeleteAndRotate();
        vButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPunchButtonClicked(View view) {
        punchTabs.setVisibility(View.VISIBLE);
        punchViewPager.setVisibility(View.VISIBLE);
        punchTabLayout.setVisibility(View.VISIBLE);

        int resourceId = getResources().getIdentifier("@" + view.getTag(), "drawable", this.getPackageName());
        touchView.executeAddCommand(this, resourceId, "punch");
        currentNumText.setText("S");

        hideAllUIElements();
        showGridAndUndo();
        showDeleteAndRotate();
        vButton.setVisibility(View.VISIBLE);
    }

    public void onDeleteBtnClicked(View v) {
        if(!TouchView.shapes.isEmpty() || !TouchView.texts.isEmpty()){
            DeleteCommand deleteCommand = new DeleteCommand(touchView);
            deleteCommand.execute();
            clearGrayColor();
            hideDeleteAndRotate();
        }
    }

    public void rotationRuler(View view) {
        rotationBar.setVisibility(View.VISIBLE);
        rotateLine.setVisibility(View.VISIBLE);
        rotateCircle.setVisibility(View.VISIBLE);
        rotateRuler.setVisibility(View.VISIBLE);
        vButton.setVisibility(View.VISIBLE);
        fontsBar.setVisibility(View.INVISIBLE);
    }

    public void rotationClick(View view) {
        float newAngle = Float.parseFloat(view.getTag().toString());
        if(!TouchView.shapes.isEmpty()&& TouchView.CURRENT_SHAPE > -1){
            touchView.executeAngleCommand(TouchView.shapes.get(TouchView.CURRENT_SHAPE), newAngle);
        }else{
            touchView.executeAngleCommand(null, newAngle);
        }
    }

    public void setRotationRuler() {

        rotateRuler.setOnTouchListener(new View.OnTouchListener() {
            float x;
            float delta;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int action = motionEvent.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if(!TouchView.shapes.isEmpty()&&TouchView.CURRENT_SHAPE > -1){
                            angleCommand = new AngleCommand(TouchView.shapes.get(TouchView.CURRENT_SHAPE));
                        }else{
                            angleCommand = new AngleCommand(null);
                        }
                        x = motionEvent.getX();

                        if (currentNumText.getText().equals("T")&&!TouchView.texts.isEmpty()) {
                            delta = (x - TouchView.texts.get(TouchView.CURRENT_TEXT).getAngle());
                        } else if (!TouchView.shapes.isEmpty()) {
                            delta = (x - TouchView.shapes.get(TouchView.CURRENT_SHAPE).getAngle());
                        }

                        break;
                    case MotionEvent.ACTION_MOVE:

                        x = motionEvent.getX();
                        float newAngle = (x - delta) / 3;

                        angleCommand.setNewAngle(newAngle);
                        angleCommand.execute();

                        break;
                    case MotionEvent.ACTION_UP:
                        if (angleCommand.isExecute()) {
                            TouchView.commandStack.push(angleCommand);
                        }
                        break;
                }
                return true;
            }
        });
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    this,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    private void takeScreenshotForGallery() {
        designContainer.setBackgroundColor(getResources().getColor(R.color.background));
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
/////////////// directory
            File dir = getExternalFilesDir("Chuk");
//////////////path
            String mPath = "ChukGallery"+now+".jpg";

            View v1 = getWindow().getDecorView().findViewById(R.id.design_container);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

//////////// file with directory and path
            galleryImageFile = new File(dir, mPath);

            FileOutputStream outputStream = new FileOutputStream(galleryImageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            Log.e("couldnt send email", "");
            e.printStackTrace();
        }

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Description");
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis ());
        values.put(MediaStore.Images.ImageColumns.BUCKET_ID, galleryImageFile.toString().toLowerCase(Locale.US).hashCode());
        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, galleryImageFile.getName().toLowerCase(Locale.US));
        values.put("_data", galleryImageFile.getAbsolutePath());

        ContentResolver cr = getContentResolver();
        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


        Uri uri= Uri.fromFile(galleryImageFile);
        try {
            galleryFileName = PathUtil.getPath(getApplicationContext(), uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        designContainer.setBackgroundColor(Color.TRANSPARENT);
    }

    private void takeScreenshot() {

        designContainer.setBackgroundColor(Color.WHITE);

//        ColorCommand colorCommand = new ColorCommand(colorImage, getApplication(), R.color.blackBtn);
//        colorCommand.colorBeforeScreenShot();

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
/////////////// directory
            File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//////////////path
            String mPath = "Chuk"+now+".jpg";

            View v1 = getWindow().getDecorView().findViewById(R.id.design_container);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

//            Drawable drawable = TouchView.shapesForColor.get(0).getDrawable();
//            Bitmap shapeBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
//                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);


//////////// file with directory and path
            imageFile = new File(dir, mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            Log.e("couldnt send email", "");
            e.printStackTrace();
        }

        designContainer.setBackgroundColor(Color.TRANSPARENT);
//        colorCommand = new ColorCommand(colorImage, getApplication(), currentColor);
//        colorCommand.execute();

        try {

            Uri uri= Uri.fromFile(imageFile);
            fileName = PathUtil.getPath(getApplicationContext(), uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

        Log.i("SendMailActivity", "Send Button Clicked.");

        String fromEmail = "chuk3d@gmail.com";

        String fromPassword = "ChukChukChuk";

        String toEmails = "nataliestarr82@gmail.com";

        List toEmailList = Arrays.asList(toEmails
                .split("\\s*,\\s*"));
        Log.i("SendMailActivity", "To List: " + toEmailList);
        String emailSubject = "My creation";

        String colorStr = "#"+Integer.toHexString(currentColor);
        String size = "";
        if(isInches){
            size = "inch";
        }else{
            size = "cm";
        }

        String emailBody = "design size: " + formattedSize + size +", color: " + colorStr;
        new SendMailTask(DesignActivity.this).execute(fromEmail,
                fromPassword, toEmailList, emailSubject, "", fileName, emailBody);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(colorBar.getVisibility()==View.VISIBLE){
            colorBar.setVisibility(View.INVISIBLE);
        }else if(toppingTabs.getVisibility()==View.VISIBLE){
            toppingTabs.setVisibility(View.INVISIBLE);
            toppingTabLayout.setVisibility(View.INVISIBLE);
            toppingViewPager.setVisibility(View.INVISIBLE);
        }else if(punchTabs.getVisibility()==View.VISIBLE) {
            punchTabs.setVisibility(View.INVISIBLE);
            punchTabLayout.setVisibility(View.INVISIBLE);
            punchViewPager.setVisibility(View.INVISIBLE);
        }else if(textContainer.getVisibility()==View.VISIBLE) {
            textContainer.setVisibility(View.INVISIBLE);
        }else if(resizeBar.getVisibility()==View.VISIBLE){
            resizeBar.setVisibility(View.INVISIBLE);
            bottomBar.setVisibility(View.VISIBLE);
            designContainer.setScaleX(1);
            designContainer.setScaleY(1);
            title.setText("Create your design");
            if(!TouchView.shapes.isEmpty() || !TouchView.texts.isEmpty() || currentColor != 0){
                showGridAndUndo();
            }
        }else if(bPaymentScreen.getVisibility()==View.VISIBLE) {
            bPaymentScreen.setVisibility(View.INVISIBLE);
            designContainer.setVisibility(View.VISIBLE);
            setResizeScreen();
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            resetDesign();
                            DesignActivity.super.onBackPressed();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            final AlertDialog.Builder builder = new AlertDialog.Builder(DesignActivity.this);
            builder.setMessage("Exit?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    public void resetDesign(){
        while (!TouchView.shapes.isEmpty()) {
            TouchView.shapes.remove(0);
        }
        TouchView.CURRENT_SHAPE = -1;
        while (!TouchView.texts.isEmpty()) {
            TouchView.texts.remove(0);
        }
        TouchView.CURRENT_TEXT = -1;
        currentColor = 0;
    }

    public void init(){

        heightScreen = getResources().getDisplayMetrics().heightPixels;
        widthScreen = getResources().getDisplayMetrics().widthPixels;

        designContainer = (RelativeLayout)findViewById(R.id.design_container);

        mainImage = (ImageView)findViewById(R.id.main_imageView);
        mainImage.setScaleX(1.62f);
        mainImage.setScaleY(1.62f);
        colorImage = (ImageView)findViewById(R.id.color_imageView);
        colorImage.setScaleX(1.62f);
        colorImage.setScaleY(1.62f);

        bottomBar = (RelativeLayout) findViewById(R.id.bottom_bar);

        colorBar = (RelativeLayout)findViewById(R.id.color_bar);
        colorBar.setVisibility(View.INVISIBLE);

        textContainer = (RelativeLayout)findViewById(R.id.text_container);
        textContainer.setVisibility(View.INVISIBLE);

        editText = (EditText)findViewById(R.id.edit_text);
        editText.setVisibility(View.INVISIBLE);
        montserrat = Typeface.createFromAsset(getAssets(), "Montserrat-ExtraBold.ttf");
        editText.setTypeface(montserrat);
        editText.setText("");
        editText.setTextSize(30);

        textPunchToppingChoice = (LinearLayout)findViewById(R.id.text_punch_topping_choice);
        punchText = (ImageButton)findViewById(R.id.text_punch);
        toppingText = (ImageButton)findViewById(R.id.text_topping);
        fontsBar = (LinearLayout)findViewById(R.id.fonts_bar);
        fontsBar.setVisibility(View.INVISIBLE);

        color = (ImageButton)findViewById(R.id.color);
        topping = (ImageButton)findViewById(R.id.topping);
        punch = (ImageButton)findViewById(R.id.punch);
        text = (ImageButton)findViewById(R.id.text);

        currentNumText = (TextView)findViewById(R.id.current_number);
        currentNumText.setText("");
        currentNumText.setVisibility(View.INVISIBLE);

        vButton = (Button)findViewById(R.id.v);
        vButton.setVisibility(View.INVISIBLE);

        grid = (Button)findViewById(R.id.grid);
        grid.setVisibility(View.INVISIBLE);
        undo = (Button)findViewById(R.id.undo);
        undo.setVisibility(View.INVISIBLE);
        delete = (Button)findViewById(R.id.delete);
        delete.setVisibility(View.INVISIBLE);
        rotate = (Button)findViewById(R.id.rotate);
        rotate.setVisibility(View.INVISIBLE);

        rotateRuler = (ImageView)findViewById(R.id.rotate_ruler);
        rotateCircle = (ImageView)findViewById(R.id.rotate_circle);
        rotateLine = (ImageView)findViewById(R.id.line);
        rotationBar = (RelativeLayout)findViewById(R.id.rotation_kit);
        rotationBar.setVisibility(View.INVISIBLE);

        gridScreen = (RelativeLayout) findViewById(R.id.grid_screen);
        gridScreen.setVisibility(View.INVISIBLE);

        toppingTabs = (NestedScrollView)findViewById(R.id.topping_scrollView);
        toppingTabs.setVisibility(View.INVISIBLE);
        punchTabs = (NestedScrollView)findViewById(R.id.scrollView_punch);
        punchTabs.setVisibility(View.INVISIBLE);

        initDrawerAndNavigationView();
        toppingViewPager = (ViewPager) findViewById(R.id.topping_viewpager);
        punchViewPager = (ViewPager) findViewById(R.id.punch_viewpager);

        toppingTabLayout = (TabLayout) findViewById(R.id.topping_tabs);
        punchTabLayout = (TabLayout) findViewById(R.id.punch_tabs);

        toppingTabLayout.setVisibility(View.INVISIBLE);
        punchTabLayout.setVisibility(View.INVISIBLE);

        toppingViewPager.setVisibility(View.INVISIBLE);
        punchViewPager.setVisibility(View.INVISIBLE);



        FragmentManager fm = getSupportFragmentManager();
        toppingTabPager = new ToppingTabPager(toppingViewPager, toppingTabLayout, this, fm);
        punchTabPager = new PunchTabPager(punchViewPager, punchTabLayout, this, fm);
        if(toppingViewPager != null){
            toppingTabPager.setupViewPager(toppingViewPager);
        }
        if(punchViewPager != null){
            punchTabPager.setupViewPager(punchViewPager);
        }

        toppingTabLayout.setupWithViewPager(toppingViewPager);
        punchTabLayout.setupWithViewPager(punchViewPager);

        int[]icons = {R.drawable.category_icon_8, R.drawable.category_icon_7, R.drawable.category_icon_6, R.drawable.category_icon_5, R.drawable.category_icon_4, R.drawable.category_icon_3, R.drawable.category_icon_2, R.drawable.category_icon_1};
        for (int i = 0; i < toppingTabLayout.getTabCount(); i++) {
            toppingTabLayout.getTabAt(i).setIcon(icons[i]);
            punchTabLayout.getTabAt(i).setIcon(icons[i]);
        }

        initButtons();

    }

    public void initButtons(){

        initVButton();

        degrees0 = (Button)findViewById(R.id.degrees_zero);
        degrees90 = (Button)findViewById(R.id.degrees_ninty);
        degrees180 = (Button)findViewById(R.id.degrees_one_eighty);
        degrees270 = (Button)findViewById(R.id.degrees_two_seventy);
        degrees360 = (Button)findViewById(R.id.degrees_three_sixty);


        color1 = (Button)findViewById(R.id.color1);
        color2 = (Button)findViewById(R.id.color2);
        color3 = (Button)findViewById(R.id.color3);
        color4 = (Button)findViewById(R.id.color4);
        color5 = (Button)findViewById(R.id.color5);
        color6 = (Button)findViewById(R.id.color6);
        color7 = (Button)findViewById(R.id.color7);
        color8 = (Button)findViewById(R.id.color8);
        color9 = (Button)findViewById(R.id.color9);
        color10 = (Button)findViewById(R.id.color10);
        color11 = (Button)findViewById(R.id.color11);
        color12 = (Button)findViewById(R.id.color12);

        font1 = (Button)findViewById(R.id.font_1);
        font2 = (Button)findViewById(R.id.font_2);
        font3 = (Button)findViewById(R.id.font_3);
        font4 = (Button)findViewById(R.id.font_4);
        font5 = (Button)findViewById(R.id.font_5);
        font6 = (Button)findViewById(R.id.font_6);
        font7 = (Button)findViewById(R.id.font_7);
        editTextBody = (Button)findViewById(R.id.edit_text_body);

        vampiro = Typeface.createFromAsset(getAssets(), "VampiroOne-Regular.ttf");
        montserratItalic = Typeface.createFromAsset(getAssets(), "Montserrat-Italic.ttf");
        athelas = Typeface.createFromAsset(getAssets(), "Athelas-Regular.ttf");
        alef = Typeface.createFromAsset(getAssets(), "Alef-Regular.ttf");
        hiraKaku = Typeface.createFromAsset(getAssets(), "copyfonts.com_hirakakustd-w8-opentype.otf");
        baloo = Typeface.createFromAsset(getAssets(), "BalooBhaijaan-Regular.ttf");
        pacifico = Typeface.createFromAsset(getAssets(), "Pacifico-Regular.ttf");

        font1.setTypeface(alef);
        font2.setTypeface(athelas);
        font3.setTypeface(hiraKaku);
        font4.setTypeface(montserratItalic);
        font5.setTypeface(baloo);
        font6.setTypeface(pacifico);
        font7.setTypeface(vampiro);

        vText = (ImageButton)findViewById(R.id.v_text);
        vText.setVisibility(View.INVISIBLE);

        next = (ImageButton)findViewById(R.id.next);
        resizeBar = (RelativeLayout)findViewById(R.id.resize_container);
        resizeBar.setVisibility(View.INVISIBLE);

        cm = (Button)findViewById(R.id.cm);
        inch = (Button)findViewById(R.id.inch);

        bPaymentScreen = (RelativeLayout)findViewById(R.id.before_payment);
        bPaymentScreen.setVisibility(View.INVISIBLE);

        letsChukAgain = (Button)findViewById(R.id.Lets_chuk_again);

        punchScrollView = (NestedScrollView)findViewById(R.id.scrollView_punch);
        toppingScrollView = (NestedScrollView)findViewById(R.id.topping_scrollView);


        toppingTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                toppingScrollView.scrollTo(0,0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                toppingScrollView.scrollTo(0,0);
            }
        });

        punchTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                punchScrollView.scrollTo(0,0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                punchScrollView.scrollTo(0,0);
            }
        });
    }

}