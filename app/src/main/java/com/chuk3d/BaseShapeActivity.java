package com.chuk3d;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.concurrent.RunnableFuture;

public class BaseShapeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GeometricShapeFragment.FragmentItemClickCallback,
        OtherShapeFragment.FragmentItemClickCallback{

    TextView title;
    ImageButton next;
    TabLayout tabLayout;
    ImageView mainImage;
    ImageView colorImage;
    public BaseShapeTabPager baseShapeTabPager;
    ImageView rotateSlide, rotateRuler, rotateLine;
    RelativeLayout rotationBar;
    Button degrees0, degrees90, degrees180, degrees270, degrees360;
    NestedScrollView scrollView;

    public int resourceId = 0;
    public float imageRotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        init();
        setRotationRuler();
        onNextButtonClicked();

    }

    @Override
    protected void onResume() {

        resourceId = getResources().getIdentifier("@drawable/g_base_shape_1", "drawable", this.getPackageName());
        Drawable drawable = ContextCompat.getDrawable(this, resourceId);
        Drawable colorDrawable = ContextCompat.getDrawable(this, resourceId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        colorDrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mainImage.setImageDrawable(drawable);
        colorImage.setImageDrawable(colorDrawable);
        colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);
        mainImage.setRotation(0);
        colorImage.setRotation(0);

        super.onResume();
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
                            imageRotation = degrees[i];
                        }
                    }
                }
            });
        }

        rotateRuler.setOnTouchListener(new View.OnTouchListener() {
            float x;
            float delta;
            float deltaC;
            float slideLimit;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int action = motionEvent.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        x = motionEvent.getX()/3;
                        delta = (x - mainImage.getRotation());
                        deltaC = motionEvent.getX()- rotateSlide.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = motionEvent.getX()/3;
                        mainImage.setRotation((x - delta));
                        colorImage.setRotation((x - delta));
                        imageRotation = (x - delta);

                        slideLimit = motionEvent.getX()- deltaC;
                        if(slideLimit < (rotateLine.getRight()*0.55f) && slideLimit > 40){
                            rotateSlide.setX(slideLimit);
                        }
                        break;
                    case MotionEvent.ACTION_UP:

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
                intent.putExtra(DesignActivity.RESOURCE_ID_KEY, resourceId);
                intent.putExtra(DesignActivity.MAIN_IMAGE_ROTATION, imageRotation);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onBaseButtonClicked(View view) {

        resourceId = getResources().getIdentifier("@" + view.getTag(), "drawable", this.getPackageName());
        Drawable drawable = ContextCompat.getDrawable(this, resourceId);
        Drawable colorDrawable = ContextCompat.getDrawable(this, resourceId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        colorDrawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mainImage.setImageDrawable(drawable);
        colorImage.setImageDrawable(colorDrawable);
        colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.baseShapeFirstColor),PorterDuff.Mode.SRC_IN);
        mainImage.setRotation(0);
        colorImage.setRotation(0);
        imageRotation = 0;
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
        mainImage.setScaleX(1.1f);
        mainImage.setScaleY(1.1f);
        colorImage = (ImageView)findViewById(R.id.color_imageView);
        colorImage.setImageDrawable(getResources().getDrawable(R.drawable.g_base_shape_1));
        colorImage.getDrawable().mutate().setColorFilter(getResources().getColor(R.color.transBaseShapeFirstColor),PorterDuff.Mode.SRC_IN);
        colorImage.setScaleX(1.1f);
        colorImage.setScaleY(1.1f);

        next = (ImageButton) findViewById(R.id.next);

        initDrawerAndNavigationView();

        rotateRuler = (ImageView)findViewById(R.id.rotate_ruler);
        rotateSlide = (ImageView)findViewById(R.id.rotate_slide);
        rotateLine = (ImageView)findViewById(R.id.line);
        rotationBar = (RelativeLayout)findViewById(R.id.rotation_kit);


        scrollView = (NestedScrollView)findViewById(R.id.scrollView);

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


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                scrollView.scrollTo(0,0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                scrollView.scrollTo(0,0);
            }
        });

    }


}
