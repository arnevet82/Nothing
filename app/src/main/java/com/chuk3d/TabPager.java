package com.chuk3d;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Admin on 22/08/2017.
 */

public abstract class TabPager {
    ViewPager viewPager;
    TabLayout tabLayout;
    Context context;
    FragmentManager fm;


    public TabPager(ViewPager viewPager, TabLayout tabLayout, Context context, FragmentManager fm){
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        this.context = context;
        this.fm = fm;
    }

    public void setupViewPager(ViewPager viewPager) {

    }



}
