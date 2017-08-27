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
                    return new PunchFragment();
                case 1:
                    return new PunchFragment();
                case 2:
                    return new PunchFragment();
                case 3:
                    return new PunchFragment();
                case 4:
                    return new PunchFragment();
                case 5:
                    return new PunchFragment();
                case 6:
                    return new PunchFragment();
                case 7:
                    return new PunchFragment();
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
