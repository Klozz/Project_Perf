package mx.klozz.xperience.tweaker;
/*
 * XPerience Kernel Tweaker - An Android CPU Control application
 * Copyright (C) 2011-2015 Carlos "Klozz" Jesus <TeamMEX@XDA-Developers>
 *
 *     Copyright h0rn3t and AOKP Team
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import mx.klozz.xperience.tweaker.R;
import mx.klozz.xperience.tweaker.activities.ZramActivity;
import mx.klozz.xperience.tweaker.fragments.*;
import mx.klozz.xperience.tweaker.helpers.RootHelper;
import mx.klozz.xperience.tweaker.helpers.VMHelper;
import mx.klozz.xperience.tweaker.helpers.VoltageHelper;
import mx.klozz.xperience.tweaker.util.ActivityThemeChangeInterface;
import mx.klozz.xperience.tweaker.util.BootClass;
import mx.klozz.xperience.tweaker.util.Constants;
import mx.klozz.xperience.tweaker.helpers.Helpers;
import mx.klozz.xperience.tweaker.util.Utils;
import mx.klozz.xperience.tweaker.util.klzz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
 *
 * Common notes: This app is based on Performance Control from AOKP with modifications
 * So initial credits to AOKP team
 * and Horn3t coz I taked some code from him
 *
 * Added my license to make a code licensed on GPL3 like Performance Control from AOKP
 * but Credits are added Here!
 *
 * I added some other things and maked a more clean code
 *
 * some comments are on spanish coz I used to learn about some coding and some friends learn from my code explanations.
 *
 * v3.0.1
 * Rewrited MainActivity and added some new things
 *  Like Tabs and navigation Drawer
 *
 */

public class MainActivity extends Activity implements Constants,
        ActionBar.OnNavigationListener {

    private static DrawerLayout mDrawerLayout; //Layout para la lista
    private static ListView mDrawerList; //Lista que aparecerá en el Drawer
    private static ActionBarDrawerToggle mDrawerToggle; //

    public static ActionBar actionBar = null; //Action bar

    private static int currentPage = 0; //Current Tab

    public static int mWidth = 1;
    public static int mHeight = 1;

    public static List<Fragment> mFragments = new ArrayList<Fragment>();//List of the fragments
    public static List<String> mFragmentNames = new ArrayList<String>();//List of the fragment names

    private static MenuItem applyButton; //Apply button cancel and Set on boot.
    private static MenuItem cancelButton;
    private static MenuItem setonboot;

    //Tipo boolean para saber si hay cambios o no.
    public static boolean CPUChange = false;//CPU
    public static boolean BatteryChange = false;//Battery settings
    public static boolean AudioChange = false; //Add support to FauxSound
    public static boolean VoltageChange = false;
    public static boolean IOChange = false;
    public static boolean MinFreeChange = false;
    public static boolean VMChange = false;

    public static  Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Extract info about Display
        if (mWidth == 1 || mHeight == 1) {
            Display display = getWindowManager().getDefaultDisplay();
            mWidth = display.getWidth();
            mHeight = display.getHeight();
        }

        setContentView(R.layout.activity_main);//The main layout

        //Change all options on false state.
        CPUChange = BatteryChange = AudioChange = VoltageChange = IOChange = MinFreeChange = VMChange = false;

        //Cleaning the fragments
        mFragments.clear();
        mFragmentNames.clear();

        //Adding fragments
        // add Information Fragment
        mFragments.add(new InformationFragment());
        mFragmentNames.add(getString(R.string.information));
        //Advanced
        mFragments.add(new Advanced());
        mFragmentNames.add(getString(R.string.Advanced));
        // add CPU Fragment
        mFragments.add(new CPUFragment());
        mFragmentNames.add(getString(R.string.cpu));
        // add Battery Fragment
        if (Utils.existFile(FAST_CHARGE) || Utils.existFile(BLX)) {
            mFragments.add(new BatteryFragment());
            mFragmentNames.add(getString(R.string.battery));
        }
        // add Audio Fragment
        if (Utils.existFile(FAUX_SOUND_CONTROL)) {
            mFragments.add(new AudioFragment());
            mFragmentNames.add(getString(R.string.audio));
        }
        // add Voltage Fragment
        if (Utils.existFile(VoltageHelper.UV_MV_PATH)
                || Utils.existFile(VoltageHelper.FAUX_VOLTAGE)) {
            mFragments.add(new VoltageFragment());
            mFragmentNames.add(getString(R.string.voltage));
        }
        // add IO Fragment
        mFragments.add(new IOFragment());
        mFragmentNames.add(getString(R.string.io));
        // add MinFree Fragment
        mFragments.add(new MinFreeFragment());
        mFragmentNames.add(getString(R.string.minfree));
        // add VM Fragment
        if (VMHelper.getVMValues().size() == VMHelper.getVMFiles().size()) {
            mFragments.add(new VMFragment());
            mFragmentNames.add(getString(R.string.vm));
        }
        //Disk space info
        mFragments.add(new DiskInfo());
        mFragmentNames.add(getString(R.string.diskinfo));
        //zram
        mFragments.add(new GPUFragment());
        mFragmentNames.add(getString(R.string.gpu));
        //Tools fragment
        mFragments.add(new Tools());
        mFragmentNames.add(getString(R.string.tools));
        //Disk space info
        //mFragments.add(new DiskInfo());
        //mFragmentNames.add(getString(R.string.diskinfo));
        //
    //Finissh adding fragments

        //Kernel information
        Utils.saveString("kernelversion", Utils.getFormattedKernelVersion(),
                getApplicationContext());


        context = getApplicationContext();
        /*
        * Drawers
         */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.fragment_list, mFragmentNames));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        //ActionBar
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        actionBar.setListNavigationCallbacks(
                new ArrayAdapter<String>(actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, mFragmentNames), this);

        if (savedInstanceState == null) {
            setPerm();

            Fragment fragment = new ViewPagerFragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
            selectItem(0);
        }

    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            actionBar.setSelectedNavigationItem(position);
        }
    }

    private void selectItem(int position) {
        if (currentPage != position && ViewPagerFragment.mViewPager != null) ViewPagerFragment.mViewPager
                .setCurrentItem(position);

        currentPage = position;

        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),
                rnd.nextInt(256));

        if (ViewPagerFragment.mPagerTabStrip != null) ViewPagerFragment.mPagerTabStrip
                .setBackgroundColor(color);
        mDrawerList.setBackgroundColor(color);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        applyButton = menu.findItem(R.id.action_apply);
        cancelButton = menu.findItem(R.id.action_cancel);
        setonboot = menu.findItem(R.id.action_setonboot).setChecked(
                Utils.getBoolean("setonboot", false, getApplicationContext()));

        showButtons(CPUChange || BatteryChange || AudioChange || VoltageChange
                || IOChange || MinFreeChange || VMChange);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_apply:
                if (CPUChange) klzz.setCPU(getApplicationContext());
                if (BatteryChange) klzz.setBattery(getApplicationContext());
                if (AudioChange) klzz.setAudio(getApplicationContext());
                if (VoltageChange) klzz.setVoltage(getApplicationContext());
                if (IOChange) klzz.setIO(getApplicationContext());
                if (MinFreeChange) klzz.setMinFree(getApplicationContext());
                if (VMChange) klzz.setVM(getApplicationContext());

                klzz.reset();
                Utils.toast(getString(R.string.applysuccessfully),
                        getApplicationContext());
                break;
            case R.id.action_cancel:
                klzz.reset();
                break;
            case R.id.action_setonboot:
                Utils.saveBoolean("setonboot", !setonboot.isChecked(),
                        getApplicationContext());
                setonboot.setChecked(!setonboot.isChecked());
                break;
        }
        return mDrawerToggle.onOptionsItemSelected(item);
    }

    private void setPerm() {
        String[] files = { MAX_FREQ, MIN_FREQ, MAX_SCREEN_OFF, MIN_SCREEN_ON,
                 };
        for (String file : files)
            RootHelper.run("chmod 777 " + file);
    }

    public static void showButtons(boolean show) {
        applyButton.setVisible(show);
        cancelButton.setVisible(show);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        selectItem(itemPosition);
        return false;
    }
}

