package com.chuk3d;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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

import java.util.LinkedList;

import static java.security.AccessController.getContext;

/**
 * Created by Admin on 21/08/2017.
 */

public class DesignActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ToppingFragment.ToppinfFragmentItemClickCallback, PunchFragment.PunchFragmentItemClickCallback{

    public static final String POSITION_KEY = "POSITION";
    public static int position;
    public static final String MAIN_IMAGE_ROTATION = "ROTATION";
    public static float mainImageRotation;

    TextView title;
    TabLayout toppingTabLayout, punchTabLayout;
    ViewPager toppingViewPager, punchViewPager;
    ToppingTabPager toppingTabPager;
    PunchTabPager punchTabPager;
    RelativeLayout colorBar, designContainer, gridScreen, textContainer;
    LinearLayout textPunchToppingChoice;
    static LinearLayout fontsBar;
    LinearLayout bottomBar;
    NestedScrollView toppingTabs, punchTabs;
    ImageView mainImage;
    static ImageView colorImage;
    ImageButton next, color, topping, punch, text, punchText, toppingText, vText;
    Button color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12;
    public static int currentColor;
    public static TextView currentNumText;
    public static Button vButton;
    Button grid, undo, delete, rotate;
    static TouchView touchView;
    ImageView rotateCircle, rotateRuler, rotateLine;
    RelativeLayout rotationBar, resizeBar;
    Button degrees0, degrees90, degrees180, degrees270, degrees360, cm, inch;
    public static Button font1, font2, font3, font4, font5;
    public static Typeface vampiro, montserrat, alef, baloo, pacifico;
    public static Typeface currentFont;

    public static LinkedList<String> stack = new LinkedList<>();
    public static EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        position = getIntent().getIntExtra(POSITION_KEY, 1);
        mainImageRotation = getIntent().getFloatExtra(MAIN_IMAGE_ROTATION, 0);
        init();
        setUpBaseShape(position);
        onBottomBarClicked();
        setRotationRuler();
        initDeleteButton();
        initGridButton();
        touchView = new TouchView(this);
        designContainer.addView(touchView);
        undo();
        onNextButtonClicked();
    }

    public void onNextButtonClicked() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAllUIElements();
                bottomBar.setVisibility(View.INVISIBLE);
                resizeBar.setVisibility(View.VISIBLE);
                title.setText("Drag for requested size ");
                cm.setBackgroundColor(Color.parseColor("#626066"));
            }
        });
    }

    public void undo(){
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!stack.isEmpty()){
                    switch (stack.getLast()){
                        case "color":
                            ColorCommand colorCommand = new ColorCommand(colorImage, getApplication(), currentColor);
                            colorCommand.undo();
                            break;
                        case "punch":
                            PunchCommand punchCommand = new PunchCommand(touchView, 0, "punch");
                            punchCommand.undo();
                            break;
                        case "topping":
                            punchCommand = new PunchCommand(touchView, 0, "topping");
                            punchCommand.undo();
                            break;
                        case "text":
                            TextCommand textCommand = new TextCommand(getApplicationContext(), "", "", touchView);
                            textCommand.undo();
                            break;
                        default:
                            break;
                    }
                }
            }
        });

    }

    public static void clearStack(int stackSize){
        while(stack.size() > stackSize){
            stack.removeFirst();
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
        vButton.setVisibility(View.INVISIBLE);
        rotate.setVisibility(View.INVISIBLE);
        grid.setVisibility(View.INVISIBLE);
        undo.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        rotationBar.setVisibility(View.INVISIBLE);
    }

    public void cleanBarButtons(){
        text.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
        color.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
        topping.setImageDrawable(getResources().getDrawable(R.drawable.topping_icon));
        punch.setImageDrawable(getResources().getDrawable(R.drawable.punch_icon));
    }

    public void showButtons(){
        rotate.setVisibility(View.VISIBLE);
        grid.setVisibility(View.VISIBLE);
        undo.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);
    }

    public void onBottomBarClicked(){
        ImageButton[]buttons = {color, topping, punch, text};
        for(ImageButton button: buttons){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideAllUIElements();
                    switch (v.getId()){
                        case R.id.color:
                            showColorBar();
                            break;
                        case R.id.topping:
                            topping.setImageDrawable(getResources().getDrawable(R.drawable.topping_icon_green));
                            onToppingButtonClicked(getCurrentFocus());
                            break;
                        case R.id.punch:
                            punch.setImageDrawable(getResources().getDrawable(R.drawable.punch_icon_green));
                            onPunchButtonClicked(getCurrentFocus());
                            break;
                        case R.id.text:
                            text.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.lightPrimary),PorterDuff.Mode.SRC_IN);
                            textContainer.setVisibility(View.VISIBLE);
                            textPunchToppingChoice.setVisibility(View.VISIBLE);
                            View.OnClickListener textListener = new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    switch (v.getId()){
                                        case R.id.text_topping:
                                            onTextButtonClicked("topping");
                                            designEditText("topping");
                                            break;
                                        case R.id.text_punch:
                                            onTextButtonClicked("punch");
                                            designEditText("punch");
                                            break;
                                    }
                                }
                            };
                            punchText.setOnClickListener(textListener);
                            toppingText.setOnClickListener(textListener);

                            break;
                    }
                }
            });
        }
    }

    public void designEditText(String tag){
        switch (tag){
            case "punch":
                editText.setTextColor(getResources().getColor(R.color.almostWhite));
                break;
            case "topping":
                editText.setTextColor(getResources().getColor(R.color.baseShapeFirstColor));
                editText.setShadowLayer(7, 1, 3, Color.parseColor("#80000000"));
                break;
        }
        editText.setVisibility(View.VISIBLE);
    }

    public void onTextButtonClicked(String tag){
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        textPunchToppingChoice.setVisibility(View.INVISIBLE);
        initFonts();
        showButtons();
        vText.setVisibility(View.VISIBLE);
        onVTextClicked(tag);
        currentNumText.setText("T");
    }

    public void onVTextClicked(String tag){

        final String mTag = tag;

        vText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                TextCommand textCommand = new TextCommand(getApplicationContext(), text, mTag, touchView);
                textCommand.execute();
                cleanBarButtons();
                touchView.invalidate();
                vButton.setVisibility(View.VISIBLE);
                vText.setVisibility(View.INVISIBLE);
            }
        });
    }

    public static void onFontButtonClicked(View v){
        int [] buttonId = {R.id.font_1, R.id.font_2, R.id.font_3, R.id.font_4, R.id.font_5};
        Typeface []typefaces ={vampiro, montserrat, alef, baloo, pacifico};

        for(int i = 0; i < buttonId.length; i++){
            if(v.getId() == buttonId[i]) {
                changeFont(typefaces[i]);
                currentFont = typefaces[i];
                if(!TouchView.texts.isEmpty()){
                    TouchView.texts.get(TouchView.CURRENT_TEXT).getTextPaint().setTypeface(currentFont);
                }
            }

        }
        touchView.invalidate();

    }

    public static void initFonts(){

        fontsBar.setVisibility(View.VISIBLE);
        Button[]buttons={font1, font2, font3, font4, font5};
        for(Button button: buttons){
            button.setOnClickListener(new View.OnClickListener() {

                int [] buttonId = {R.id.font_1, R.id.font_2, R.id.font_3, R.id.font_4, R.id.font_5};
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
            }
        });
    }

    public void initGridButton() {
        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gridScreen.getVisibility()==View.INVISIBLE){
                    gridScreen.setVisibility(View.VISIBLE);
                }else{
                    gridScreen.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    public void clearGrayColor(){

        ColorCommand colorCommand = new ColorCommand(colorImage, getApplication(), currentColor);
        colorCommand.execute();

        touchView.invalidate();
    }

    public void showColorBar(){
        color.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.lightPrimary),PorterDuff.Mode.SRC_IN);
        colorBar.setVisibility(View.VISIBLE);
        changeColor();
    }

    public void changeColor(){
        final Button[]buttons = {color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12};
        for(final Button button:buttons){
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                int[]buttonId = {R.id.color1, R.id.color2, R.id.color3, R.id.color4, R.id.color5, R.id.color6, R.id.color7, R.id.color8, R.id.color9, R.id.color10, R.id.color11, R.id.color12};
                int[]colors={R.color.yellowBtn, R.color.orangeBtn, R.color.redBtn, R.color.pinkBtn, R.color.purpleBtn, R.color.darkeBlueBtn,
                        R.color.blueBtn, R.color.greenBtn, R.color.blackBtn, R.color.grayBtn, R.color.whiteBtn, R.color.transBtn};
                @Override
                public void onClick(View v) {
                    for(int i = 0; i < buttons.length; i++){
                        if(v.getId() == buttonId[i]){
                            colorBar.setVisibility(View.INVISIBLE);
                            buttons[i].setVisibility(View.INVISIBLE);
                            color.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
                            currentColor = colors[i];
                            ColorCommand colorCommand = new ColorCommand(colorImage, getApplication(), colors[i]);
                            colorCommand.execute();
                            showButtons();
                        }
                    }
                }
            });
        }
    }

    public void setUpBaseShape(int pos){

        int[]baseShapes = {R.drawable.g_base_shape_1, R.drawable.g_base_shape_2, R.drawable.g_base_shape_3,R.drawable.g_base_shape_4, R.drawable.g_base_shape_5, R.drawable.g_base_shape_6, R.drawable.g_base_shape_7, R.drawable.g_base_shape_8, R.drawable.g_base_shap_9, R.drawable.g_base_shape_10, R.drawable.g_base_shape_11, R.drawable.g_base_shape_12, R.drawable.g_base_shape_13, R.drawable.g_base_shape_14, R.drawable.g_base_shape_15, R.drawable.g_base_shape_16, R.drawable.g_base_shape_17, R.drawable.g_base_shape_18, R.drawable.g_base_shape_19, R.drawable.g_base_shape_20, R.drawable.g_base_shape_21, R.drawable.g_base_shape_22, R.drawable.g_base_shape_23, R.drawable.g_base_shape_24, R.drawable.g_base_shape_25, R.drawable.g_base_shape_26, R.drawable.g_base_shape_27, R.drawable.g_base_shape_28, R.drawable.g_base_shape_29, R.drawable.g_base_shape_30, R.drawable.g_base_shape_31, R.drawable.g_base_shape_32, R.drawable.g_base_shape_33, R.drawable.g_base_shape_34, R.drawable.g_base_shape_35, R.drawable.g_base_shape_36};

        mainImage.setImageDrawable(getResources().getDrawable(baseShapes[pos]));
        colorImage.setImageDrawable(getResources().getDrawable(baseShapes[pos]));
        colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);
        mainImage.setRotation(mainImageRotation);
        colorImage.setRotation(mainImageRotation);

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

    @Override
    public void onToppingButtonClicked(View view) {
        toppingTabs.setVisibility(View.VISIBLE);
        toppingTabLayout.setVisibility(View.VISIBLE);
        toppingViewPager.setVisibility(View.VISIBLE);
        int[]buttonId = {R.id.topping1, R.id.topping2, R.id.topping3, R.id.topping4, R.id.topping5, R.id.topping6, R.id.topping7, R.id.topping8, R.id.topping9, R.id.topping10, R.id.topping11, R.id.topping12, R.id.topping13, R.id.topping14, R.id.topping15, R.id.topping16, R.id.topping17, R.id.topping18, R.id.topping19, R.id.topping20, R.id.topping21, R.id.topping22, R.id.topping23, R.id.topping24, R.id.topping25, R.id.topping26, R.id.topping27, R.id.topping28, R.id.topping29, R.id.topping30, R.id.topping31, R.id.topping32, R.id.topping33, R.id.topping34, R.id.topping35, R.id.topping36};
        int pos = 0;
        for(pos = 0; pos < buttonId.length; pos++){
            if(view.getId() == buttonId[pos]){
                PunchCommand punchCommand = new PunchCommand(touchView, pos, "topping");
                punchCommand.execute();
                hideAllUIElements();
                showButtons();
                vButton.setVisibility(View.VISIBLE);
            }
        }
        Log.e("current shape", String.valueOf(TouchView.CURRENT_SHAPE));

    }

    @Override
    public void onPunchButtonClicked(View view) {
        punchTabs.setVisibility(View.VISIBLE);
        punchViewPager.setVisibility(View.VISIBLE);
        punchTabLayout.setVisibility(View.VISIBLE);
        int[]buttonId = {R.id.punch1, R.id.punch2, R.id.punch3, R.id.punch4, R.id.punch5, R.id.punch6, R.id.punch7, R.id.punch8, R.id.punch9, R.id.punch10, R.id.punch11, R.id.punch12, R.id.punch13, R.id.punch14, R.id.punch15, R.id.punch16, R.id.punch17, R.id.punch18, R.id.punch19, R.id.punch20, R.id.punch21, R.id.punch22, R.id.punch23, R.id.punch24, R.id.punch25, R.id.punch26, R.id.punch27, R.id.punch28, R.id.punch29, R.id.punch30, R.id.punch31, R.id.punch32, R.id.punch33, R.id.punch34, R.id.punch35, R.id.punch36};
        int pos = 0;
        for (pos = 0; pos < buttonId.length; pos++) {
            if (view.getId() == buttonId[pos]) {
                PunchCommand punchCommand = new PunchCommand(touchView, pos, "punch");
                punchCommand.execute();
                hideAllUIElements();
                showButtons();
                vButton.setVisibility(View.VISIBLE);
            }
        }

        Log.e("current shape", String.valueOf(TouchView.CURRENT_SHAPE));
    }


    public void initDeleteButton() {

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteCommand deleteCommand = new DeleteCommand(touchView);
                deleteCommand.execute();
                clearGrayColor();
            }
        });
    }

    public void setRotationRuler() {


        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontsBar.setVisibility(View.INVISIBLE);
                rotationBar.setVisibility(View.VISIBLE);
                rotateLine.setVisibility(View.VISIBLE);
                rotateCircle.setVisibility(View.VISIBLE);
                rotateRuler.setVisibility(View.VISIBLE);
                vButton.setVisibility(View.VISIBLE);
            }
        });

        Button[]buttons = {degrees0, degrees90, degrees180, degrees270, degrees360};
        for(Button button: buttons){
            button.setOnClickListener(new View.OnClickListener() {


                int[]buttonId = {R.id.degrees_zero, R.id.degrees_ninty, R.id.degrees_one_eighty, R.id.degrees_two_seventy, R.id.degrees_three_sixty};
                float[]degrees = {0, 90, 180, 270, 360};
                @Override
                public void onClick(View v) {
                    for(int i = 0; i < buttonId.length; i++){
                        if(v.getId() == buttonId[i]){
                            if (currentNumText.getText().equals("T")) {
                                TouchView.texts.get(TouchView.CURRENT_TEXT).setAngle(degrees[i]);
                            } else if (!TouchView.shapes.isEmpty()) {
                                TouchView.shapes.get(TouchView.CURRENT_SHAPE).setAngle(degrees[i]);
                            } else {

                            }
                            touchView.invalidate();
                        }
                    }
                }
            });
        }

        rotateRuler.setOnTouchListener(new View.OnTouchListener() {
            float x;
            float delta;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int action = motionEvent.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        x = motionEvent.getX();

                        if (currentNumText.getText().equals("T")&&!TouchView.texts.isEmpty()) {
                            delta = (x - TouchView.texts.get(TouchView.CURRENT_TEXT).getAngle());
                        } else if (!TouchView.shapes.isEmpty()) {
                            delta = (x - TouchView.shapes.get(TouchView.CURRENT_SHAPE).getAngle());
                        } else {

                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = motionEvent.getX();

                        if (currentNumText.getText().equals("T")&&!TouchView.texts.isEmpty()) {
                            TouchView.texts.get(TouchView.CURRENT_TEXT).setAngle((x - delta) / 3);
                        } else if (!TouchView.shapes.isEmpty()) {
                            TouchView.shapes.get(TouchView.CURRENT_SHAPE).setAngle((x - delta) / 3);
                        } else {

                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        if (currentNumText.getText().equals("T")&&!TouchView.texts.isEmpty()) {
                            TouchView.texts.get(TouchView.CURRENT_TEXT).setAngle((x - delta) / 3);
                        } else if (!TouchView.shapes.isEmpty()) {
                            TouchView.shapes.get(TouchView.CURRENT_SHAPE).setAngle((x - delta) / 3);

                        } else {

                        }
                        break;
                }
                touchView.invalidate();

                return true;
            }
        });
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
        }else if(punchTabs.getVisibility()==View.VISIBLE){
            punchTabs.setVisibility(View.INVISIBLE);
            punchTabLayout.setVisibility(View.INVISIBLE);
            punchViewPager.setVisibility(View.INVISIBLE);
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            while (!TouchView.shapes.isEmpty()) {
                                TouchView.shapes.remove(0);
                                TouchView.shapesForColor.remove(0);
                            }
                            TouchView.CURRENT_SHAPE = -1;
                            while (!TouchView.texts.isEmpty()) {
                                TouchView.texts.remove(0);
                            }
                            TouchView.CURRENT_TEXT = -1;
                            currentColor = 0;
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

    public void init(){

        designContainer = (RelativeLayout)findViewById(R.id.design_container);

        mainImage = (ImageView)findViewById(R.id.main_imageView);
        mainImage.setScaleX(1.6f);
        mainImage.setScaleY(1.6f);
        colorImage = (ImageView)findViewById(R.id.color_imageView);
        colorImage.setScaleX(1.6f);
        colorImage.setScaleY(1.6f);

        bottomBar = (LinearLayout) findViewById(R.id.bottom_bar);

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

            vampiro = Typeface.createFromAsset(getAssets(), "VampiroOne-Regular.ttf");
            montserrat = Typeface.createFromAsset(getAssets(), "Montserrat-Italic.ttf");
            pacifico = Typeface.createFromAsset(getAssets(), "Pacifico-Regular.ttf");
            alef = Typeface.createFromAsset(getAssets(), "Alef-Regular.ttf");
            baloo = Typeface.createFromAsset(getAssets(), "BalooBhaijaan-Regular.ttf");

            font1.setTypeface(vampiro);
            font2.setTypeface(montserrat);
            font3.setTypeface(alef);
            font4.setTypeface(baloo);
            font5.setTypeface(pacifico);

            vText = (ImageButton)findViewById(R.id.v_text);
            vText.setVisibility(View.INVISIBLE);

            next = (ImageButton)findViewById(R.id.next);
            resizeBar = (RelativeLayout)findViewById(R.id.resize_container);
            resizeBar.setVisibility(View.INVISIBLE);

            cm = (Button)findViewById(R.id.cm);
            inch = (Button)findViewById(R.id.inch);

        }



}
