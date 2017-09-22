package com.chuk3d;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Admin on 19/09/2017.
 */

public class CategoriesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static int resourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        initDrawerAndNavigationView();

    }

    public void initDrawerAndNavigationView(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onCategoryBtnClicked(View view){
        resourceId = getResources().getIdentifier("@" + view.getTag(), "drawable", this.getPackageName());
        Intent intent = new Intent(getApplication(), DesignActivity.class);
        intent.putExtra(DesignActivity.RESOURCE_ID_KEY, resourceId);
        startActivity(intent);

    }

    public void onBaseCategoryClicked(View view){
        Intent intent = new Intent(getApplication(), BaseShapeActivity.class);
        startActivity(intent);
    }

    public void onFreeCategoryClicked(View view){
        resourceId = 555;
        Intent intent = new Intent(getApplication(), DesignActivity.class);
        intent.putExtra(DesignActivity.RESOURCE_ID_KEY, resourceId);
        startActivity(intent);

    }

}
