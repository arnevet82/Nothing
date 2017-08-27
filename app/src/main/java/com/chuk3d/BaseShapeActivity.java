package com.chuk3d;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaseShapeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseFragment.FragmentItemClickCallback {

    TextView title;
    ImageButton next;
    TabLayout tabLayout;
    ImageView mainImage;
    static ImageView colorImage;
    public BaseShapeTabPager baseShapeTabPager;
    ImageView rotateCircle, rotateRuler, rotateLine;
    RelativeLayout rotationBar;
    Button degrees0, degrees90, degrees180, degrees270, degrees360;

    public static int imagePosition;
    public static float imageRotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        init();
        setRotationRuler();
        onNextButtonClicked();
    }


    public void setRotationRuler() {

        Button[]buttons = {degrees0, degrees90, degrees180, degrees270, degrees360};
        for(Button button: buttons){
            button.setOnClickListener(new View.OnClickListener() {


                int[]buttonId = {R.id.degrees_zero, R.id.degrees_ninty, R.id.degrees_one_eighty, R.id.degrees_two_seventy, R.id.degrees_three_sixty};
                float[]degrees = {0, 90, 180, 270, 360};
                @Override
                public void onClick(View v) {
                    for(int i = 0; i < buttonId.length; i++){
                        if(v.getId() == buttonId[i]){
                            mainImage.setRotation(degrees[i]);
                            colorImage.setRotation(degrees[i]);
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
                        x = motionEvent.getX()/3;
                        delta = (x/3 - mainImage.getRotation());

                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = motionEvent.getX()/3;

                        mainImage.setRotation((x/3 - delta));
                        colorImage.setRotation((x/3 - delta));
                        break;
                    case MotionEvent.ACTION_UP:
                        mainImage.setRotation((x/3 - delta));
                        colorImage.setRotation((x/3 - delta));
                        imageRotation = (x/3 - delta);
                        break;
                }


                return true;
            }
        });
    }


    public void onNextButtonClicked() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), DesignActivity.class);
                intent.putExtra(DesignActivity.POSITION_KEY, imagePosition);
                intent.putExtra(DesignActivity.MAIN_IMAGE_ROTATION, imageRotation);

                startActivity(intent);


            }
        });
    }

    @Override
    public int onBaseButtonClicked(View view) {
        int[]buttonId = {R.id.base1, R.id.base2, R.id.base3, R.id.base4, R.id.base5, R.id.base6, R.id.base7, R.id.base8, R.id.base9, R.id.base10, R.id.base11, R.id.base12, R.id.base13, R.id.base14, R.id.base15, R.id.base16, R.id.base17, R.id.base18, R.id.base19, R.id.base20, R.id.base21, R.id.base22, R.id.base23, R.id.base24, R.id.base25, R.id.base26, R.id.base27, R.id.base28, R.id.base29, R.id.base30, R.id.base31, R.id.base32, R.id.base33, R.id.base34, R.id.base35, R.id.base36};
        int[]baseShapes = {R.drawable.g_base_shape_1, R.drawable.g_base_shape_2, R.drawable.g_base_shape_3,R.drawable.g_base_shape_4, R.drawable.g_base_shape_5, R.drawable.g_base_shape_6, R.drawable.g_base_shape_7, R.drawable.g_base_shape_8, R.drawable.g_base_shap_9, R.drawable.g_base_shape_10, R.drawable.g_base_shape_11, R.drawable.g_base_shape_12, R.drawable.g_base_shape_13, R.drawable.g_base_shape_14, R.drawable.g_base_shape_15, R.drawable.g_base_shape_16, R.drawable.g_base_shape_17, R.drawable.g_base_shape_18, R.drawable.g_base_shape_19, R.drawable.g_base_shape_20, R.drawable.g_base_shape_21, R.drawable.g_base_shape_22, R.drawable.g_base_shape_23, R.drawable.g_base_shape_24, R.drawable.g_base_shape_25, R.drawable.g_base_shape_26, R.drawable.g_base_shape_27, R.drawable.g_base_shape_28, R.drawable.g_base_shape_29, R.drawable.g_base_shape_30, R.drawable.g_base_shape_31, R.drawable.g_base_shape_32, R.drawable.g_base_shape_33, R.drawable.g_base_shape_34, R.drawable.g_base_shape_35, R.drawable.g_base_shape_36};
        int pos = 0;
        for(pos = 0; pos < buttonId.length; pos++){
            if(view.getId() == buttonId[pos]){
                mainImage.setImageDrawable(getResources().getDrawable(baseShapes[pos]));
                colorImage.setImageDrawable(getResources().getDrawable(baseShapes[pos]));
                colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);
                mainImage.setRotation(0);
                colorImage.setRotation(0);
                imageRotation = 0;
                rotateCircle.setX(getResources().getDimension(R.dimen.circle_margin));
                imagePosition = pos;
            }
        }
        return pos;
    }


    public void initDrawerAndNavigationView(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        title = (TextView) toolbar.findViewById(R.id.title_text);
        title.setText("Choose your base shape");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerArrowDrawable(new HamburgerDrawable(this));


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    public void init(){

        initDrawerAndNavigationView();

        mainImage = (ImageView)findViewById(R.id.main_imageView);
        mainImage.setImageDrawable(getResources().getDrawable(R.drawable.g_base_shape_1));
        mainImage.setScaleX(1.2f);
        mainImage.setScaleY(1.2f);
        colorImage = (ImageView)findViewById(R.id.color_imageView);
        colorImage.setImageDrawable(getResources().getDrawable(R.drawable.g_base_shape_1));
        colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);
        colorImage.setScaleX(1.2f);
        colorImage.setScaleY(1.2f);

        next = (ImageButton) findViewById(R.id.next);

        initDrawerAndNavigationView();

        rotateRuler = (ImageView)findViewById(R.id.rotate_ruler);
        rotateCircle = (ImageView)findViewById(R.id.rotate_circle);
        rotateLine = (ImageView)findViewById(R.id.line);
        rotationBar = (RelativeLayout)findViewById(R.id.rotation_kit);

        degrees0 = (Button)findViewById(R.id.degrees_zero);
        degrees90 = (Button)findViewById(R.id.degrees_ninty);
        degrees180 = (Button)findViewById(R.id.degrees_one_eighty);
        degrees270 = (Button)findViewById(R.id.degrees_two_seventy);
        degrees360 = (Button)findViewById(R.id.degrees_three_sixty);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        FragmentManager fm = getSupportFragmentManager();
        baseShapeTabPager = new BaseShapeTabPager(viewPager, tabLayout, this, fm);
        if(viewPager != null){
            baseShapeTabPager.setupViewPager(viewPager);
        }

        tabLayout.setupWithViewPager(viewPager);
        int[]icons = {R.drawable.category_icon_8, R.drawable.category_icon_7, R.drawable.category_icon_6, R.drawable.category_icon_5, R.drawable.category_icon_4, R.drawable.category_icon_3, R.drawable.category_icon_2, R.drawable.category_icon_1};
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }

}
