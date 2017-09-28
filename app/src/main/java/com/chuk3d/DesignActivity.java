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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
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
import android.text.StaticLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 21/08/2017.
 */

public class DesignActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GToppingFragment.ToppinfFragmentItemClickCallback, GPunchFragment.PunchFragmentItemClickCallback,
        OtherToppingFragment.ToppinfFragmentItemClickCallback, OtherPunchFragment.PunchFragmentItemClickCallback,
        PlantToppingFragment.ToppinfFragmentItemClickCallback, PlantPunchFragment.PunchFragmentItemClickCallback{

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 99;
    public static final String CATEGORY_ID = "CATEGORY";
    public static String category;
    public static final String RESOURCE_ID_KEY = "RESOURCE_ID";
    public static int resourceId = 0;
    public static final String MAIN_IMAGE_ROTATION = "ROTATION";
    public static float mainImageRotation;
    boolean b = true;
    private AngleCommand angleCommand = null;
    TextView title;
    TabLayout toppingTabLayout, punchTabLayout;
    ViewPager toppingViewPager, punchViewPager;
    ToppingTabPager toppingTabPager;
    PunchTabPager punchTabPager;
    RelativeLayout colorBar, designContainer, gridScreen, textContainer, bPaymentScreen, bottomBar, holesBar;
    static LinearLayout fontsBar;
    NestedScrollView toppingTabs, punchTabs;
    ImageView mainImage;
    static ImageView colorImage;
    ImageButton next, color, topping, punch, text, vText, holes;
    Button color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12;
    public static int currentColor;
    public static int baseCurrentColor;
    public static Button vButton;
    public static Button grid, undo, delete, rotate;
    static TouchView touchView;
    ImageView rotateCircle, rotateRuler, rotateLine, resizeRuler;
    RelativeLayout rotationBar, resizeBar;
    Button degrees0, degrees90, degrees180, degrees270, degrees360, cm, inch, letsChukAgain;
    public static Button font1, font2, font3, font4, font5, font6, font7, font8, font9, font10, font11, editTextBody;
    public static Typeface boogaloo, komika, montserrat, nautilus, orbitron, oswald, sourceSans1, sourceSans2, troika, pacifico, grandHotel, vampiroOne, WC_Wunderbach, loaded;
    public static Typeface currentFont;
    EditCommand editCommand = null;
    NestedScrollView punchScrollView, toppingScrollView;
    int heightScreen, widthScreen;
    public static EditText editText;
    public static boolean isInches;
    public static String sizeTerm = "cm";
    File galleryImageFile;
    public static String galleryFileName, formattedSize, finalSize = "default ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        resourceId = getIntent().getIntExtra(RESOURCE_ID_KEY, 0);
        category = getIntent().getStringExtra(CATEGORY_ID);
        mainImageRotation = getIntent().getFloatExtra(MAIN_IMAGE_ROTATION, 0);

        init();

        setUpBaseShape(resourceId);

        touchView = new TouchView(this);
        designContainer.addView(touchView);
        currentColor = this.getResources().getColor(R.color.transBaseShapeFirstColor);
        baseCurrentColor = this.getResources().getColor(R.color.transBaseShapeFirstColor);
        setRotationRuler();
    }

    public static void showGridAndUndo() {
        grid.setVisibility(View.VISIBLE);
        undo.setVisibility(View.VISIBLE);
    }

    public static void showDeleteAndRotate() {
        delete.setVisibility(View.VISIBLE);
        if(Movable.current_movable instanceof Shape ||Movable.current_movable instanceof Text ){
            rotate.setVisibility(View.VISIBLE);
        }
    }

    public void hideDeleteAndRotate() {
        delete.setVisibility(View.INVISIBLE);
        rotate.setVisibility(View.INVISIBLE);
    }

    public void onNextButtonClicked(View v) {
        if (vText.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, "finished? click 'done' ", Toast.LENGTH_SHORT).show();
        } else if (resizeBar.getVisibility() == View.INVISIBLE && bPaymentScreen.getVisibility() == View.INVISIBLE) {
            hideAllUIElements();
            bottomBar.setVisibility(View.INVISIBLE);
            vButton.setVisibility(View.INVISIBLE);
            ColorCommand.clearGrayColor(this);
            setResizeScreen();
        } else if (resizeBar.getVisibility() == View.VISIBLE) {
            setUpBeforePayment();
        } else if (bPaymentScreen.getVisibility() == View.VISIBLE) {
            if (checkPermissionREAD_EXTERNAL_STORAGE(getApplicationContext())) {
                takeScreenshot();
                takeScreenshotForGallery();
            }
        }
    }

    public void setUpBeforePayment() {
        resizeBar.setVisibility(View.INVISIBLE);
        designContainer.setVisibility(View.INVISIBLE);
        bPaymentScreen.setVisibility(View.VISIBLE);
        title.setText("Confirm");

        letsChukAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDesign();
                Intent intent = new Intent(getApplication(), CategoriesActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setResizeScreen() {
        resizeBar.setVisibility(View.VISIBLE);
        title.setText("Drag for requested size ");
        cm.setBackgroundColor(Color.parseColor("#626066"));
        inch.setBackgroundColor(Color.parseColor("#d8d8d8"));
        sizeTerm = "cm";
        initSizeBtns();
        resize();
    }

    public void initSizeBtns() {
        View.OnClickListener sizeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSizeBtnClicked(v);
            }
        };
        cm.setOnClickListener(sizeListener);
        inch.setOnClickListener(sizeListener);
    }

    public void onSizeBtnClicked(View v) {

        switch (v.getId()) {
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

    public void resize() {
        resizeBar.bringToFront();
        resizeBar.setOnTouchListener(new View.OnTouchListener() {
            TextView sizeText = (TextView) findViewById(R.id.size_text);
            ImageView resizeBall = (ImageView) findViewById(R.id.resize_circle);

            float x;
            float y;
            float newSize;
            float maxSize;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float sizeInSm = designContainer.getScaleX() * 5.73f;
                float sizeInInch = designContainer.getScaleX() * 2.2559f;
                String size = "";
                if (isInches) {
                    size = " inches";
                    formattedSize = String.format("%.1f", sizeInInch);
                } else {
                    size = " cm";
                    formattedSize = String.format("%.0f", sizeInSm + 0.5);
                }
                finalSize = formattedSize + size;

                newSize = sizeInSm + 0.5f;
                maxSize = 8;
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    x = event.getX();
                    y = event.getY();

                    if (event.getX() < rotateLine.getRight() && event.getX() > 100) {
                        resizeBall.setX(x);
                    }


                    float scaleFactor;
                    switch (Helper.getDeviceDensity(getApplicationContext())) {
                        case "3.0 xxhdpi":
                            scaleFactor = x * 0.0014f;
                            break;
                        case "4.0 xxxhdpi":
                            scaleFactor = x * 0.00105f;
                            break;
                        default:
                            scaleFactor = x * 0.00105f;
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

    public void undo(View v) {
        if (!TouchView.commandStack.isEmpty()) {
            touchView.undo();
        }
    }

    public void hideAllUIElements() {
        cleanBarButtons();
        colorBar.setVisibility(View.INVISIBLE);
        toppingTabs.setVisibility(View.INVISIBLE);
        punchTabs.setVisibility(View.INVISIBLE);
        toppingViewPager.setVisibility(View.INVISIBLE);
        toppingTabLayout.setVisibility(View.INVISIBLE);
        punchViewPager.setVisibility(View.INVISIBLE);
        punchTabLayout.setVisibility(View.INVISIBLE);
        textContainer.setVisibility(View.INVISIBLE);
        holesBar.setVisibility(View.INVISIBLE);
        vButton.setVisibility(View.INVISIBLE);
        rotate.setVisibility(View.INVISIBLE);
        grid.setVisibility(View.INVISIBLE);
        undo.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        rotationBar.setVisibility(View.INVISIBLE);
        fontsBar.setVisibility(View.INVISIBLE);
        rotationBar.setVisibility(View.INVISIBLE);
    }

    public void cleanBarButtons() {
        text.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        color.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        topping.setImageDrawable(getResources().getDrawable(R.drawable.topping_icon));
        punch.setImageDrawable(getResources().getDrawable(R.drawable.punch_icon));
    }


    public void onTextButtonClicked(View v) {
        if (textContainer.getVisibility() == View.VISIBLE) {
            textContainer.setVisibility(View.INVISIBLE);
            text.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.almostWhite), PorterDuff.Mode.SRC_IN);
            if (!TouchView.shapes.isEmpty() || currentColor != 0) {
                showGridAndUndo();
            }
        } else {
            hideAllUIElements();
            text.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.lightPrimary), PorterDuff.Mode.SRC_IN);
            textContainer.setVisibility(View.VISIBLE);
            onTextPunchTopClicked(v);
        }
    }

    public void onTextPunchTopClicked(View v) {
        MovableType type = getTextType();
        designEditText();
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        vText.setVisibility(View.VISIBLE);
        onVTextClicked(type, "normal");
    }

    public void editText(View v) {
        vText.setVisibility(View.VISIBLE);

        if (Movable.current_movable != null) {

            editCommand = new EditCommand(Movable.current_movable, getApplicationContext());
        }

        onVTextClicked(null, "edit");
    }

    public void onVTextClicked(final MovableType type, String state) {

        final MovableType movableType = type;
        final String mState = state;

        vText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = editText.getText().toString();
                vText.setVisibility(View.INVISIBLE);

                switch (mState) {
                    case "normal":
                        touchView.executeAddCommand(getApplicationContext(), 0, text, type);
                        cleanBarButtons();
                        break;
                    case "edit":
                        if (editCommand != null) {
                            editCommand.execute();
                        }
                        if (editCommand.isExecute()) {
                            TouchView.commandStack.push(editCommand);
                        }

                        break;
                }
                editText.setText("");
                editText.setVisibility(View.INVISIBLE);
                vButton.setVisibility(View.VISIBLE);
                showDeleteAndRotate();
                showGridAndUndo();
                fontsBar.setVisibility(View.VISIBLE);
                touchView.invalidate();
            }
        });
    }

    public MovableType getTextType() {
        MovableType movableType = null;
        if (category.equals("coaster") || category.equals("bookmark") || category.equals("coffeeStencil")) {
            movableType = MovableType.P_TEXT;
        } else {
            movableType = MovableType.T_TEXT;
        }
        return movableType;
    }

    public void designEditText() {
        if (currentFont != null) {
            editText.setTypeface(currentFont);
        } else {
            editText.setTypeface(font1.getTypeface());
        }
        editText.setVisibility(View.VISIBLE);
    }

    public void onFontButtonClicked(View v) {
        Button b = (Button) v;
        Text text = (Text) Movable.current_movable;
        text.setFont(b.getTypeface());
        currentFont = b.getTypeface();
        touchView.invalidate();
    }


    public void onVButtonClicked(View v) {

        vButton.setVisibility(View.INVISIBLE);
        ColorCommand.clearGrayColor(this);
        rotationBar.setVisibility(View.INVISIBLE);
        fontsBar.setVisibility(View.INVISIBLE);
        Movable.current_movable = null;
        showGridAndUndo();
        hideDeleteAndRotate();
    }

    public void onGridBtnClicked(View v) {

        if (gridScreen.getVisibility() == View.INVISIBLE) {
            gridScreen.setVisibility(View.VISIBLE);
        } else {
            gridScreen.setVisibility(View.INVISIBLE);
        }

    }

    public void showColorBar(View v){
        if(colorBar.getVisibility() == View.VISIBLE){
            colorBar.setVisibility(View.INVISIBLE);

            if(!TouchView.shapes.isEmpty() || currentColor != 0){
                showGridAndUndo();
            }
        }else{
            hideAllUIElements();

            colorBar.setVisibility(View.VISIBLE);
            Button[]buttons = {color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12};

            for(Button button:buttons){
                button.setVisibility(View.VISIBLE);
            }
        }
    }

    public void colorClick(View view) {

        color.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
        Button b = (Button) view;
        int newColor;
        if(b.getId() == R.id.color12){
            newColor = -1513499;
        }else{
            newColor = ((ColorDrawable)b.getBackground()).getColor();
        }
        int baseNewColor = Integer.parseInt((String)b.getTag());

        ColorCommand colorCommand = new ColorCommand(this, baseNewColor, newColor);
        boolean isExecute = colorCommand.execute();
        if (isExecute) {
            touchView.commandStack.push(colorCommand);
        }
        colorBar.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        showGridAndUndo();
    }

    public void setUpBaseShape(int resourceId){

        if(resourceId == 555){
            mainImage.setImageDrawable(null);
            colorImage.setImageDrawable(null);
        }else {
            Drawable drawable = ContextCompat.getDrawable(this, resourceId);
            Drawable colorDrawable = ContextCompat.getDrawable(this, resourceId);
            mainImage.setImageDrawable(drawable);
            colorImage.setImageDrawable(colorDrawable);
            colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.transBaseShapeFirstColor), PorterDuff.Mode.SRC_IN);
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
            if(!TouchView.shapes.isEmpty() || currentColor != 0){
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
            if(!TouchView.shapes.isEmpty() || currentColor != 0){
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
    public void onHolesBarClicked(View view) {
        if(holesBar.getVisibility() == View.VISIBLE){
            holesBar.setVisibility(View.INVISIBLE);
            if(!TouchView.shapes.isEmpty() || currentColor != 0){
                showGridAndUndo();
            }
        }else{
            hideAllUIElements();
            holesBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAddButtonClicked(View view, MovableType type) {

        int resourceId = getResources().getIdentifier("@" + view.getTag(), "drawable", this.getPackageName());
        touchView.executeAddCommand(this, resourceId, "", type);

        ColorCommand.clearGrayColor(this);
        hideAllUIElements();
        showGridAndUndo();
        showDeleteAndRotate();
        vButton.setVisibility(View.VISIBLE);

    }
    public void onAddButtonClicked(View view) {

        int resourceId = getResources().getIdentifier("@" + view.getTag(), "drawable", this.getPackageName());
        if(view.getTag(view.getId()).equals("punch")){
            touchView.executeAddCommand(this, resourceId, "", MovableType.P_HOLE);
        }else{
            touchView.executeAddCommand(this, resourceId, "", MovableType.T_HOLE);
        }

        ColorCommand.clearGrayColor(this);
        hideAllUIElements();
        showGridAndUndo();
        showDeleteAndRotate();
        vButton.setVisibility(View.VISIBLE);

    }

    public void onDeleteBtnClicked(View v) {
        if(!TouchView.shapes.isEmpty() ){
            DeleteCommand deleteCommand = new DeleteCommand(Movable.current_movable, this);
            deleteCommand.execute();
            if(deleteCommand.isExecute()){
                TouchView.commandStack.push(deleteCommand);
                touchView.invalidate();
            }
            ColorCommand.clearGrayColor(this);
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
        if(Movable.current_movable != null){
            touchView.executeAngleCommand(Movable.current_movable, newAngle);
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

                        x = motionEvent.getX()/3;
                        delta = (x - Movable.current_movable.getAngle());

                        if(Movable.current_movable != null){
                            angleCommand = new AngleCommand(Movable.current_movable);
                        }else{
                            angleCommand = new AngleCommand(null);
                        }

                        break;
                    case MotionEvent.ACTION_MOVE:

                        x = motionEvent.getX()/3;
                        float newAngle = (x - delta) ;

                        angleCommand.setNewAngle(newAngle);
                        angleCommand.execute();
                        //activate moveCommand for case x  y  exceed from borders of view after rotation
                        MoveCommand moveCommand = new MoveCommand(Movable.current_movable, touchView.getWidth(), touchView.getHeight());
                        moveCommand.setNewX(Movable.current_movable.getPosX());
                        moveCommand.setNewY(Movable.current_movable.getPosY());
                        moveCommand.execute();

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

    public void initBottomBar(){
        switch (category){
            case "sign":case "magnet":case "doorSign":case "bookmark":case "pictureFrame":
                bottomBar = (RelativeLayout) findViewById(R.id.bottom_bar1);
                break;
            case "coffeeStencil":case "thinThing":case "coaster":
                bottomBar = (RelativeLayout) findViewById(R.id.bottom_bar2);
                break;
            case "petTag":case "luggageTag":
                bottomBar = (RelativeLayout) findViewById(R.id.bottom_bar3);
                break;
            case "businessCardStand":case "gyring":case "opener":
                bottomBar = (RelativeLayout) findViewById(R.id.bottom_bar4);
                break;
            case "nameNecklace":
                bottomBar = (RelativeLayout) findViewById(R.id.bottom_bar5);
                break;
            case "earrings":case "pendant":case "keychain":
                bottomBar = (RelativeLayout) findViewById(R.id.bottom_bar6);
                break;
            default:
                bottomBar = (RelativeLayout) findViewById(R.id.bottom_bar1);
                break;
        }
        bottomBar.setVisibility(View.VISIBLE);

    }

    public void setFontsByCategory(){
        switch (category){
            case "coaster":case "coffeeStencil":case "bookmark":
                font1.setTypeface(WC_Wunderbach);
                font2.setTypeface(loaded);
                font3.setVisibility(View.INVISIBLE);
                font4.setVisibility(View.INVISIBLE);
                font5.setVisibility(View.INVISIBLE);
                font6.setVisibility(View.INVISIBLE);
                font7.setVisibility(View.INVISIBLE);
                font8.setVisibility(View.INVISIBLE);
                font9.setVisibility(View.INVISIBLE);
                font10.setVisibility(View.INVISIBLE);
                font11.setVisibility(View.INVISIBLE);
                break;
            case "pendant":case "earrings":case "nameNecklace":case "doorSign":
                font1.setTypeface(pacifico);
                font2.setTypeface(grandHotel);
                font3.setVisibility(View.INVISIBLE);
                font4.setVisibility(View.INVISIBLE);
                font5.setVisibility(View.INVISIBLE);
                font6.setVisibility(View.INVISIBLE);
                font7.setVisibility(View.INVISIBLE);
                font8.setVisibility(View.INVISIBLE);
                font9.setVisibility(View.INVISIBLE);
                font10.setVisibility(View.INVISIBLE);
                font11.setVisibility(View.INVISIBLE);
                break;
            case "businessCardStand":case "luggageTag":case "petTag":case "opener":
                font1.setTypeface(boogaloo);
                font2.setTypeface(komika);
                font2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                font3.setTypeface(montserrat);
                font4.setTypeface(orbitron);
                font5.setTypeface(oswald);
                font6.setTypeface(sourceSans1);
                font7.setTypeface(sourceSans2);
                font8.setTypeface(troika);
                font9.setTypeface(nautilus);
                font10.setVisibility(View.INVISIBLE);
                font11.setVisibility(View.INVISIBLE);
                break;
            case "thinThing":case "magnet":case "keychain":case "pictureFrame":
                font1.setTypeface(boogaloo);
                font2.setTypeface(komika);
                font2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                font3.setTypeface(montserrat);
                font4.setTypeface(orbitron);
                font5.setTypeface(oswald);
                font6.setTypeface(sourceSans1);
                font7.setTypeface(sourceSans2);
                font8.setTypeface(troika);
                font9.setTypeface(nautilus);
                font10.setTypeface(pacifico);
                font11.setTypeface(grandHotel);
                break;
            case "gyring":
                font1.setTypeface(vampiroOne);
                font1.setVisibility(View.INVISIBLE);
                font2.setVisibility(View.INVISIBLE);
                font3.setVisibility(View.INVISIBLE);
                font4.setVisibility(View.INVISIBLE);
                font5.setVisibility(View.INVISIBLE);
                font6.setVisibility(View.INVISIBLE);
                font7.setVisibility(View.INVISIBLE);
                font8.setVisibility(View.INVISIBLE);
                font9.setVisibility(View.INVISIBLE);
                font10.setVisibility(View.INVISIBLE);
                font11.setVisibility(View.INVISIBLE);
                break;
            default:

                break;
        }

        currentFont = font1.getTypeface();
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
        File file1 = null, file2 = null, file3 = null, file4 = null;
        String fileName1 = "", fileName2 = "", fileName3 = "", fileName4 = "";

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

/////////////// directory
            File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//////////// file with directory and path
            file1 = getImageFile(dir, "mainImage");
            file2 = getImageFile(dir, "tShape");
            file3 = getImageFile(dir, "pShape");
            file4 = getImageFile(dir, "bShape");


        try {

            Uri uri= Uri.fromFile(file1);
            Uri tUri = Uri.fromFile(file2);
            Uri pUri = Uri.fromFile(file3);
            Uri bUri = Uri.fromFile(file4);

            fileName1 = PathUtil.getPath(getApplicationContext(), uri);
            fileName2 = PathUtil.getPath(getApplicationContext(), tUri);
            fileName3 = PathUtil.getPath(getApplicationContext(), pUri);
            fileName4 = PathUtil.getPath(getApplicationContext(), bUri);

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
        String emailSubject = "My "+ category;

        String colorStr = "#"+Integer.toHexString(currentColor);

        String font = getCurrentFontString();
        String emailBody = "design size: " + finalSize +", color: " + colorStr + " font: " + font;
        new SendMailTask(DesignActivity.this).execute(fromEmail,
                fromPassword, toEmailList, emailSubject, "", fileName1, fileName2, fileName3,fileName4, emailBody);

    }

    public String getCurrentFontString(){
        Typeface[] typefaces = {boogaloo, komika, montserrat, nautilus, orbitron, oswald, sourceSans1, sourceSans2, troika, pacifico, grandHotel, vampiroOne, WC_Wunderbach, loaded};
        String font = Helper.fontNames[0];
        for(int i = 0; i < typefaces.length; i++){
            if(currentFont.equals(typefaces[i])){
                font = Helper.fontNames[i];
            }
        }

        return font;
    }

    public File getImageFile(File dir, String name){
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        File imageFile = null;

        String mPath = name+now+".jpg";
        Bitmap mainBitmap = null;
        designContainer.setBackgroundColor(Color.WHITE);

        switch (name){
            case "mainImage":

                View v1 = getWindow().getDecorView().findViewById(R.id.design_container);
                v1.setDrawingCacheEnabled(true);
                mainBitmap = Bitmap.createBitmap(v1.getDrawingCache());
                v1.setDrawingCacheEnabled(false);

                break;
            case "tShape":

                if(mainImage.getDrawable()!=null){
                    mainImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.transparent), PorterDuff.Mode.SRC_IN);
                    colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.transparent), PorterDuff.Mode.SRC_IN);
                }
                if(!TouchView.shapes.isEmpty()){
                    for (Movable movable : TouchView.shapes) {
                        movable.setColor(this, Color.BLACK);
                    }
                    touchView.drawToppings();
                }

                View v2 = getWindow().getDecorView().findViewById(R.id.design_container);
                v2.setDrawingCacheEnabled(true);
                mainBitmap = Bitmap.createBitmap(v2.getDrawingCache());
                v2.setDrawingCacheEnabled(false);
                touchView.removeToppings();

                break;
            case "pShape":
                if(colorImage.getDrawable() != null){
                    colorImage.getDrawable().mutate().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                }
                if (!TouchView.shapes.isEmpty()) {
                    for (Movable movable : TouchView.shapes) {
                        movable.setColor(this, Color.BLACK);
                    }
                    DesignActivity.touchView.invalidate();
                }

                View v3 = getWindow().getDecorView().findViewById(R.id.design_container);
                v3.setDrawingCacheEnabled(true);
                mainBitmap = Bitmap.createBitmap(v3.getDrawingCache());
                v3.setDrawingCacheEnabled(false);
                break;
            case "bShape":
                if(colorImage.getDrawable() != null){
                    colorImage.getDrawable().mutate().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                }

                designContainer.removeView(touchView);
                View v4 = getWindow().getDecorView().findViewById(R.id.design_container);
                v4.setDrawingCacheEnabled(true);
                mainBitmap = Bitmap.createBitmap(v4.getDrawingCache());
                v4.setDrawingCacheEnabled(false);
                designContainer.addView(touchView);
                break;
            default:
                break;
        }

        designContainer.setBackgroundColor(Color.TRANSPARENT);

        if (!TouchView.shapes.isEmpty()) {
            for (Movable movable : TouchView.shapes) {
                movable.setColor(this, currentColor);
            }
            DesignActivity.touchView.invalidate();
        }
        setUpBaseShape(resourceId);
        if(colorImage.getDrawable() != null) {
            colorImage.getDrawable().mutate().setColorFilter(baseCurrentColor, PorterDuff.Mode.SRC_IN);
        }

//////////// file with directory and path
        imageFile = new File(dir, mPath);

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(imageFile);

        int quality = 100;
        mainBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        outputStream.flush();
        outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return imageFile;
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
        }else if(editText.getVisibility()==View.VISIBLE) {
            textContainer.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.INVISIBLE);
            vText.setVisibility(View.INVISIBLE);
            cleanBarButtons();
        }else if(resizeBar.getVisibility()==View.VISIBLE){
            resizeBar.setVisibility(View.INVISIBLE);
            bottomBar.setVisibility(View.VISIBLE);
            designContainer.setScaleX(1);
            designContainer.setScaleY(1);
            title.setText("Create your design");
            if(!TouchView.shapes.isEmpty()  || currentColor != 0){
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
        Movable.current_movable = null;
        currentColor = 0;
    }

    public void init(){

        heightScreen = getResources().getDisplayMetrics().heightPixels;
        widthScreen = getResources().getDisplayMetrics().widthPixels;

        designContainer = (RelativeLayout)findViewById(R.id.design_container);

        mainImage = (ImageView)findViewById(R.id.main_imageView);
        colorImage = (ImageView)findViewById(R.id.color_imageView);

        if(category.equals("coffeeStencil")) {
            mainImage.setScaleX(2.5f);
            mainImage.setScaleY(2.5f);
            mainImage.setY(getResources().getDimension(R.dimen.canvas_coffe_marginTop));
            colorImage.setScaleX(2.5f);
            colorImage.setScaleY(2.5f);
            colorImage.setY(getResources().getDimension(R.dimen.canvas_coffe_marginTop));
        }else if(category.equals("bookmark")){
            mainImage.setScaleX(1.62f);
            mainImage.setScaleY(1.62f);
            mainImage.setY(getResources().getDimension(R.dimen.canvas_bookmark_marginTop));
            colorImage.setScaleX(1.62f);
            colorImage.setScaleY(1.62f);
            colorImage.setY(getResources().getDimension(R.dimen.canvas_bookmark_marginTop));
        }else if(category.equals("opener")){
            mainImage.setScaleX(1.7f);
            mainImage.setScaleY(1.7f);
            colorImage.setScaleX(1.7f);
            colorImage.setScaleY(1.7f);
        }else{
            mainImage.setScaleX(1.62f);
            mainImage.setScaleY(1.62f);
            colorImage.setScaleX(1.62f);
            colorImage.setScaleY(1.62f);
        }

        initBottomBar();

        colorBar = (RelativeLayout)findViewById(R.id.color_bar);
        textContainer = (RelativeLayout)findViewById(R.id.text_container);
        holesBar = (RelativeLayout)findViewById(R.id.holes_bar);

        editText = (EditText)findViewById(R.id.edit_text);
        editText.setText("");
        editText.setTextSize(30);

        fontsBar = (LinearLayout)findViewById(R.id.fonts_bar);

        color = (ImageButton)findViewById(R.id.color);
        topping = (ImageButton)findViewById(R.id.topping);
        punch = (ImageButton)findViewById(R.id.punch);
        text = (ImageButton)findViewById(R.id.text);
        holes = (ImageButton)findViewById(R.id.hole);


        vButton = (Button)findViewById(R.id.v);

        grid = (Button)findViewById(R.id.gridBtn);
        undo = (Button)findViewById(R.id.undo);
        delete = (Button)findViewById(R.id.delete);
        rotate = (Button)findViewById(R.id.rotate);

        rotateRuler = (ImageView)findViewById(R.id.rotate_ruler);
        rotateCircle = (ImageView)findViewById(R.id.rotate_circle);
        rotateLine = (ImageView)findViewById(R.id.line);
        rotationBar = (RelativeLayout)findViewById(R.id.rotation_kit);

        gridScreen = (RelativeLayout) findViewById(R.id.grid_screen);

        toppingTabs = (NestedScrollView)findViewById(R.id.topping_scrollView);
        punchTabs = (NestedScrollView)findViewById(R.id.scrollView_punch);

        initDrawerAndNavigationView();
        toppingViewPager = (ViewPager) findViewById(R.id.topping_viewpager);
        punchViewPager = (ViewPager) findViewById(R.id.punch_viewpager);

        toppingTabLayout = (TabLayout) findViewById(R.id.topping_tabs);
        punchTabLayout = (TabLayout) findViewById(R.id.punch_tabs);

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
        font8 = (Button)findViewById(R.id.font_8);
        font9 = (Button)findViewById(R.id.font_9);
        font10 = (Button)findViewById(R.id.font_10);
        font11 = (Button)findViewById(R.id.font_11);


        editTextBody = (Button)findViewById(R.id.edit_text_body);

        boogaloo = Typeface.createFromAsset(getAssets(), "Boogaloo-Regular.otf");
        komika = Typeface.createFromAsset(getAssets(), "KomikaTitle-Wide.ttf");
        montserrat = Typeface.createFromAsset(getAssets(), "Montserrat-Medium.otf");
        nautilus = Typeface.createFromAsset(getAssets(), "Nautilus.otf");
        orbitron = Typeface.createFromAsset(getAssets(), "orbitron-bold.otf");
        oswald = Typeface.createFromAsset(getAssets(), "Oswald-Bold.ttf");
        sourceSans1 = Typeface.createFromAsset(getAssets(), "SourceSansPro-Semibold.otf");
        sourceSans2 = Typeface.createFromAsset(getAssets(), "SourceSansPro-SemiboldIt.otf");
        troika = Typeface.createFromAsset(getAssets(), "troika.otf");
        pacifico = Typeface.createFromAsset(getAssets(), "Pacifico.ttf");
        grandHotel = Typeface.createFromAsset(getAssets(), "GrandHotel-Regular.otf");
        vampiroOne = Typeface.createFromAsset(getAssets(), "VampiroOne-Regular.ttf");
        WC_Wunderbach = Typeface.createFromAsset(getAssets(), "WC_Wunderbach.otf");
        loaded = Typeface.createFromAsset(getAssets(), "loaded.ttf");

        setFontsByCategory();

        vText = (ImageButton)findViewById(R.id.v_text);
        vText.setVisibility(View.INVISIBLE);

        next = (ImageButton)findViewById(R.id.next);
        resizeBar = (RelativeLayout)findViewById(R.id.resize_container);
        resizeRuler = (ImageView)findViewById(R.id.resize_ruler);

        for(int i = 0; i < 8; i++){
            Helper.holeBtns[i] = (ImageButton)findViewById(Helper.holeBtnsId[i]);
            Helper.holeBtns[i].setTag(Helper.holeBtnsId[i], "punch");
        }
        for(int i = 8; i < Helper.holeBtnsId.length; i++){
            Helper.holeBtns[i] = (ImageButton)findViewById(Helper.holeBtnsId[i]);
            Helper.holeBtns[i].setTag(Helper.holeBtnsId[i], "topping");
        }

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