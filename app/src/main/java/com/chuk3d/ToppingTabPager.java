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

public class ToppingTabPager extends TabPager {
    Context context;

    public ToppingTabPager(ViewPager viewPager, TabLayout tabLayout, Context context, FragmentManager fm) {
        super(viewPager, tabLayout, context, fm);
    }

    public void setupViewPager(ViewPager viewPager) {

        ToppingTabPager.Adapter adapter = new ToppingTabPager.Adapter(fm, context);
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
                    return new GToppingFragment();
                case 1:
                    return new OtherToppingFragment();
                case 2:
                    return new PlantToppingFragment();
                case 3:
                    return new GToppingFragment();
                case 4:
                    return new GToppingFragment();
                case 5:
                    return new GToppingFragment();
                case 6:
                    return new GToppingFragment();
                case 7:
                    return new GToppingFragment();
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
