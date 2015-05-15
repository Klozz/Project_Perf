/*
 * Copyright (C) 2014-2015 Carlos Jesús <TeamMEX@XDA-Developers>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package mx.klozz.xperience.tweaker.fragments;

/**
 * Created by klozz on 14/05/2015.
 */
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.klozz.xperience.tweaker.MainActivity;
import mx.klozz.xperience.tweaker.R;

public class ViewPagerFragment extends Fragment implements
        ViewPager.OnPageChangeListener {

    public static ViewPager mViewPager;
    public static PagerTabStrip mPagerTabStrip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container,
                false);

        assert rootView != null;
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(
                getFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(this);

        mPagerTabStrip = (PagerTabStrip) rootView//pager view
                .findViewById(R.id.pagerTabStrip);
        mPagerTabStrip.setTabIndicatorColor(getResources().getColor(
                android.R.color.white));
        mPagerTabStrip.setDrawFullUnderline(false);

        return rootView;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MainActivity.mFragments.get(position);
        }

        @Override
        public int getCount() {
            return MainActivity.mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MainActivity.mFragmentNames.get(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {}

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {}

    @Override
    public void onPageSelected(int arg0) {
        MainActivity.actionBar.setSelectedNavigationItem(arg0);
    }

}
