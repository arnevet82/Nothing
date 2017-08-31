package com.chuk3d;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by Admin on 22/08/2017.
 */

public class PunchTabPager extends TabPager {
    Context context;

    public PunchTabPager(ViewPager viewPager, TabLayout tabLayout, Context context, FragmentManager fm) {
        super(viewPager, tabLayout, context, fm);
    }

    public void setupViewPager(ViewPager viewPager) {

        PunchTabPager.Adapter adapter = new PunchTabPager.Adapter(fm, context);
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
                    return new GPunchFragment();
                case 1:
                    return new OtherPunchFragment();
                case 2:
                    return new GPunchFragment();
                case 3:
                    return new GPunchFragment();
                case 4:
                    return new GPunchFragment();
                case 5:
                    return new GPunchFragment();
                case 6:
                    return new GPunchFragment();
                case 7:
                    return new GPunchFragment();
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
