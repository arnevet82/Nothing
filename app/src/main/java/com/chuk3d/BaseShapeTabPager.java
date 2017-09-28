package com.chuk3d;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import static com.chuk3d.BaseShapeActivity.*;

/**
 * Created by Admin on 22/08/2017.
 */

public class BaseShapeTabPager extends TabPager {
    Context context;


    public BaseShapeTabPager(ViewPager viewPager, TabLayout tabLayout, Context context, FragmentManager fm) {
        super(viewPager, tabLayout, context, fm);
    }

    public void setupViewPager(ViewPager viewPager) {

        BaseShapeTabPager.Adapter adapter = new BaseShapeTabPager.Adapter(fm, context);
        viewPager.setAdapter(adapter);
    }


    static class Adapter extends FragmentPagerAdapter {


        private String fragments [] = {"", "", "", "", "", "", "", ""};

        public Adapter(FragmentManager fm, Context ctx) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new GeometricShapeFragment();
                case 1:
                    return new OtherShapeFragment();
                case 2:
                    return new PlantBaseShapeFragment();
                case 3:
                    return new GeometricShapeFragment();
                case 4:
                    return new GeometricShapeFragment();
                case 5:
                    return new GeometricShapeFragment();
                case 6:
                    return new GeometricShapeFragment();
                case 7:
                    return new GeometricShapeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }


    }


}
