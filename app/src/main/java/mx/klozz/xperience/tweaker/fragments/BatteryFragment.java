/*
 * Copyright (C) 2014-2015 Carlos Jesus <TeamMEX@XDA-Developers>
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

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import mx.klozz.xperience.tweaker.MainActivity;
import mx.klozz.xperience.tweaker.R;
import mx.klozz.xperience.tweaker.helpers.BatteryHelper;
import mx.klozz.xperience.tweaker.helpers.LayoutHelper;
import mx.klozz.xperience.tweaker.util.Constants;
import mx.klozz.xperience.tweaker.util.klzz;
import mx.klozz.xperience.tweaker.util.InformationDialog;
import mx.klozz.xperience.tweaker.util.Utils;

public class BatteryFragment extends Fragment implements Constants,
        OnClickListener, OnCheckedChangeListener, OnSeekBarChangeListener {

    private static Context context;

    public static LinearLayout layout = null;

    private static Handler hand = new Handler();
    private static TextView mBatteryVoltageTitle;
    private static TextView mBatteryVoltageText;

    private static CheckBox mFastChargeBox;

    private static TextView mBLXTitle;
    private static TextView mBLXText;
    private static Button mBLXMinus;
    private static SeekBar mBLXBar;
    private static Button mBLXPlus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.generic, container, false);
        layout = (LinearLayout) rootView.findViewById(R.id.layout);

        setLayout();

        mBatteryVoltageTitle.setOnClickListener(this);
        mFastChargeBox.setOnCheckedChangeListener(this);
        mBLXMinus.setOnClickListener(this);
        mBLXBar.setOnSeekBarChangeListener(this);
        mBLXPlus.setOnClickListener(this);

        return rootView;
    }

    private void setLayout() {
        mBatteryVoltageTitle = new TextView(context);
        mBatteryVoltageTitle.setPadding(0, MainActivity.mHeight / 25, 0, 15);
        if (Utils.existFile(BATTERY_VOLTAGE)) layout.addView(mBatteryVoltageTitle);

        mBatteryVoltageText = new TextView(context);
        if (Utils.existFile(BATTERY_VOLTAGE)) layout.addView(mBatteryVoltageText);

        LinearLayout mFastChargeLayout = new LinearLayout(context);
        mFastChargeLayout.setGravity(Gravity.CENTER);
        mFastChargeLayout.setPadding(0, (int) (MainActivity.mHeight / 21.6), 0,
                0);
        if (Utils.existFile(FAST_CHARGE)) layout.addView(mFastChargeLayout);

        mFastChargeBox = new CheckBox(context);
        mFastChargeLayout.addView(mFastChargeBox);

        mBLXTitle = new TextView(context);
        LayoutHelper.setTextTitle(mBLXTitle, getString(R.string.blx), context);
        mBLXTitle.setPadding(0, (int) (MainActivity.mHeight / 21.6), 0, 0);
        if (Utils.existFile(BLX)) layout.addView(mBLXTitle);

        mBLXText = new TextView(context);
        LayoutHelper.setSeekBarText(mBLXText,
                String.valueOf(BatteryHelper.getBLX()));
        if (Utils.existFile(BLX)) layout.addView(mBLXText);

        LinearLayout mBLXLayout = new LinearLayout(context);
        mBLXLayout.setGravity(Gravity.CENTER);
        if (Utils.existFile(BLX)) layout.addView(mBLXLayout);

        mBLXMinus = (Button) LayoutHelper.createSeekBar(getActivity())[0];
        mBLXLayout.addView(mBLXMinus);

        mBLXBar = (SeekBar) LayoutHelper.createSeekBar(getActivity())[1];
        mBLXLayout.addView(mBLXBar);

        mBLXPlus = (Button) LayoutHelper.createSeekBar(getActivity())[2];
        mBLXLayout.addView(mBLXPlus);

        setValues();
    }

    public static void setValues() {
        LayoutHelper.setTextTitle(mBatteryVoltageTitle,
                context.getString(R.string.curbatteryvoltage), context);

        LayoutHelper.setSubTitle(mBatteryVoltageText,
                String.valueOf(BatteryHelper.getCurBatteryVoltage() / 1000)
                        + context.getString(R.string.mv));

        LayoutHelper.setCheckBox(mFastChargeBox, BatteryHelper.getFastCharge(),
                context.getString(R.string.fastcharge), context);

        LayoutHelper.setNormalSeekBar(mBLXBar, 100, BatteryHelper.getBLX(),
                context);
    }

    @Override
    public void onResume() {
        hand.postDelayed(run, 0);
        super.onResume();
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            mBatteryVoltageText.setText(String.valueOf(BatteryHelper
                    .getCurBatteryVoltage() / 1000)
                    + context.getString(R.string.mv));
            hand.postDelayed(run, 1000);
        }
    };

    @Override
    public void onDestroy() {
        hand.removeCallbacks(run);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mBatteryVoltageTitle)) try {
            startActivity(new Intent(Intent.ACTION_POWER_USAGE_SUMMARY));
        } catch (Exception ignored) {}
        if (v.equals(mBLXTitle)) InformationDialog.showInfo(mBLXTitle.getText()
                .toString(), getString(R.string.blx_summary), context);
        if (v.equals(mBLXMinus)) {
            mBLXBar.setProgress(mBLXBar.getProgress() - 1);
            saveBLX();
        }
        if (v.equals(mBLXPlus)) {
            mBLXBar.setProgress(mBLXBar.getProgress() + 1);
            saveBLX();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        MainActivity.BatteryChange = true;
        MainActivity.showButtons(true);
        if (buttonView.equals(mFastChargeBox)) klzz.runBatteryGeneric(
                isChecked ? "1" : "0", FAST_CHARGE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
        MainActivity.BatteryChange = true;
        MainActivity.showButtons(true);

        if (seekBar.equals(mBLXBar)) {
            mBLXText.setText(String.valueOf(progress));
            saveBLX();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    private static void saveBLX() {
        klzz.runBatteryGeneric(mBLXText.getText().toString(), BLX);
    }
}
