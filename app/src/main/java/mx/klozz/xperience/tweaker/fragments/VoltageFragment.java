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

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import mx.klozz.xperience.tweaker.MainActivity;
import mx.klozz.xperience.tweaker.R;
import mx.klozz.xperience.tweaker.helpers.LayoutHelper;
import mx.klozz.xperience.tweaker.helpers.VoltageHelper;
import mx.klozz.xperience.tweaker.util.klzz;
import mx.klozz.xperience.tweaker.util.InformationDialog;
import mx.klozz.xperience.tweaker.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class VoltageFragment extends Fragment implements OnClickListener,
        OnSeekBarChangeListener {

    private static Context context;

    public static LinearLayout layout = null;

    private static Integer[] mVoltagesMV;

    private static TextView mVoltageText;

    private static SeekBar[] mVoltageBars;
    private static TextView[] mVoltageTexts;
    private static Button[] mVoltMinusbuttons;
    private static Button[] mVoltPlusButtons;

    private static Integer[] mFauxVoltagesMV;

    private static TextView mFauxVoltageText;

    private static SeekBar[] mFauxVoltageBars;
    private static TextView[] mFauxVoltageTexts;
    private static Button[] mFauxVoltMinusbuttons;
    private static Button[] mFauxVoltPlusButtons;

    private static List<String> mVoltageList = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.generic, container, false);
        layout = (LinearLayout) rootView.findViewById(R.id.layout);

        setLayout();

        mVoltageText.setOnClickListener(this);
        for (int i = 0; i < mVoltagesMV.length; i++) {
            mVoltMinusbuttons[i].setOnClickListener(this);
            mVoltageBars[i].setOnSeekBarChangeListener(this);
            mVoltPlusButtons[i].setOnClickListener(this);
        }

        mFauxVoltageText.setOnClickListener(this);
        for (int i = 0; i < mFauxVoltagesMV.length; i++) {
            mFauxVoltMinusbuttons[i].setOnClickListener(this);
            mFauxVoltageBars[i].setOnSeekBarChangeListener(this);
            mFauxVoltPlusButtons[i].setOnClickListener(this);
        }

        return rootView;
    }

    private void setLayout() {

        // Generic Voltage Control
        mVoltagesMV = VoltageHelper.getVoltages();

        mVoltageText = new TextView(context);
        LayoutHelper.setTextTitle(mVoltageText,
                getString(R.string.voltagecontrol), context);
        mVoltageText
                .setPadding(0, Math.round(MainActivity.mHeight / 25), 0, 15);
        if (Utils.existFile(VoltageHelper.UV_MV_PATH)) layout
                .addView(mVoltageText);

        mVoltageBars = new SeekBar[mVoltagesMV.length];
        mVoltageTexts = new TextView[mVoltagesMV.length];
        mVoltMinusbuttons = new Button[mVoltagesMV.length];
        mVoltPlusButtons = new Button[mVoltagesMV.length];

        for (int i = 0; i < mVoltagesMV.length; i++) {
            LinearLayout mVoltageLayout = new LinearLayout(context);
            mVoltageLayout.setOrientation(LinearLayout.VERTICAL);
            if (Utils.existFile(VoltageHelper.UV_MV_PATH)) layout
                    .addView(mVoltageLayout);

            TextView mVoltageFreq = new TextView(context);
            LayoutHelper.setSubTitle(mVoltageFreq,
                    String.valueOf(VoltageHelper.getFreqVoltages()[i])
                            + getString(R.string.mhz));
            mVoltageLayout.addView(mVoltageFreq);

            TextView mVoltageText = new TextView(context);
            LayoutHelper.setSeekBarText(mVoltageText,
                    String.valueOf(mVoltagesMV[i]) + getString(R.string.mv));
            mVoltageTexts[i] = mVoltageText;
            mVoltageLayout.addView(mVoltageText);

            LinearLayout mVoltageBarLayout = new LinearLayout(context);
            mVoltageBarLayout.setGravity(Gravity.CENTER);
            mVoltageLayout.addView(mVoltageBarLayout);

            Button mVoltMinusbutton = (Button) LayoutHelper
                    .createSeekBar(getActivity())[0];
            mVoltMinusbuttons[i] = mVoltMinusbutton;
            mVoltageBarLayout.addView(mVoltMinusbutton);

            SeekBar mVoltageBar = (SeekBar) LayoutHelper
                    .createSeekBar(getActivity())[1];
            mVoltageBars[i] = mVoltageBar;
            mVoltageBarLayout.addView(mVoltageBar);

            Button mVoltPlusButton = (Button) LayoutHelper
                    .createSeekBar(getActivity())[2];
            mVoltPlusButtons[i] = mVoltPlusButton;
            mVoltageBarLayout.addView(mVoltPlusButton);
        }

        // Faux Voltage Control
        mFauxVoltagesMV = VoltageHelper.getFauxVoltages();

        mFauxVoltageText = new TextView(context);
        LayoutHelper.setTextTitle(mFauxVoltageText,
                getString(R.string.fauxvoltagecontrol), context);
        mFauxVoltageText.setPadding(0, Math.round(MainActivity.mHeight / 25),
                0, 15);
        if (Utils.existFile(VoltageHelper.FAUX_VOLTAGE)) layout
                .addView(mFauxVoltageText);

        mFauxVoltageBars = new SeekBar[mFauxVoltagesMV.length];
        mFauxVoltageTexts = new TextView[mFauxVoltagesMV.length];
        mFauxVoltMinusbuttons = new Button[mFauxVoltagesMV.length];
        mFauxVoltPlusButtons = new Button[mFauxVoltagesMV.length];

        for (int i = 0; i < mFauxVoltagesMV.length; i++) {

            LinearLayout mFauxVoltageLayout = new LinearLayout(context);
            mFauxVoltageLayout.setOrientation(LinearLayout.VERTICAL);
            if (Utils.existFile(VoltageHelper.FAUX_VOLTAGE)) layout
                    .addView(mFauxVoltageLayout);

            TextView mFauxVoltageFreq = new TextView(context);
            LayoutHelper.setSubTitle(mFauxVoltageFreq,
                    String.valueOf(VoltageHelper.getFauxFreqVoltages()[i])
                            + getString(R.string.mhz));
            mFauxVoltageLayout.addView(mFauxVoltageFreq);

            TextView mFauxVoltageText = new TextView(context);
            LayoutHelper
                    .setSeekBarText(mFauxVoltageText,
                            String.valueOf(mFauxVoltagesMV[i])
                                    + getString(R.string.mv));
            mFauxVoltageTexts[i] = mFauxVoltageText;
            mFauxVoltageLayout.addView(mFauxVoltageText);

            LinearLayout mFauxVoltageBarLayout = new LinearLayout(context);
            mFauxVoltageLayout.addView(mFauxVoltageBarLayout);

            Button mFauxVoltMinusbutton = (Button) LayoutHelper
                    .createSeekBar(getActivity())[0];
            mFauxVoltMinusbuttons[i] = mFauxVoltMinusbutton;
            mFauxVoltageBarLayout.addView(mFauxVoltMinusbutton);

            SeekBar mFauxVoltageBar = (SeekBar) LayoutHelper
                    .createSeekBar(getActivity())[1];
            mFauxVoltageBars[i] = mFauxVoltageBar;
            mFauxVoltageBarLayout.addView(mFauxVoltageBar);

            Button mFauxVoltPlusButton = (Button) LayoutHelper
                    .createSeekBar(getActivity())[2];
            mFauxVoltPlusButtons[i] = mFauxVoltPlusButton;
            mFauxVoltageBarLayout.addView(mFauxVoltPlusButton);
        }

        setValues();
    }

    public static void setValues() {

        mVoltagesMV = VoltageHelper.getVoltages();
        mFauxVoltagesMV = VoltageHelper.getFauxVoltages();

        for (int i = 0; i < mVoltagesMV.length; i++)
            LayoutHelper.setNormalSeekBar(mVoltageBars[i], 180,
                    (mVoltagesMV[i] - 600) / 5, context);

        for (int i = 0; i < mFauxVoltagesMV.length; i++)
            LayoutHelper.setNormalSeekBar(mFauxVoltageBars[i], 180,
                    (mFauxVoltagesMV[i] - 600) / 5, context);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(mVoltageText) || v.equals(mFauxVoltageText)) InformationDialog
                .showInfo(v.equals(mVoltageText) ? mVoltageText.getText()
                        .toString() : mFauxVoltageText.getText().toString(),
                        getString(R.string.voltagecontrol_summary), context);
        for (int i = 0; i < mVoltagesMV.length; i++) {
            if (v.equals(mVoltMinusbuttons[i])) {
                mVoltageBars[i].setProgress(mVoltageBars[i].getProgress() - 1);
                saveVoltages();
            }
            if (v.equals(mVoltPlusButtons[i])) {
                mVoltageBars[i].setProgress(mVoltageBars[i].getProgress() + 1);
                saveVoltages();
            }
        }
        for (int i = 0; i < mFauxVoltagesMV.length; i++) {
            if (v.equals(mFauxVoltMinusbuttons[i])) {
                mFauxVoltageBars[i].setProgress(mFauxVoltageBars[i]
                        .getProgress() - 1);
                saveFauxVoltage(String.valueOf(VoltageHelper
                        .getFauxFreqVoltages()[i])
                        + "000 "
                        + mFauxVoltageTexts[i].getText().toString()
                                .replace(getString(R.string.mv), "000"));
            }
            if (v.equals(mFauxVoltPlusButtons[i])) {
                mFauxVoltageBars[i].setProgress(mFauxVoltageBars[i]
                        .getProgress() + 1);
                saveFauxVoltage(String.valueOf(VoltageHelper
                        .getFauxFreqVoltages()[i])
                        + "000 "
                        + mFauxVoltageTexts[i].getText().toString()
                                .replace(getString(R.string.mv), "000"));
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
        MainActivity.showButtons(true);
        MainActivity.VoltageChange = true;

        mVoltageList.clear();
        for (int i = 0; i < mVoltagesMV.length; i++) {
            if (seekBar.equals(mVoltageBars[i])) mVoltageTexts[i]
                    .setText(String.valueOf(progress * 5 + 600)
                            + context.getString(R.string.mv));

            mVoltageList.add(mVoltageTexts[i].getText().toString()
                    .replace(getString(R.string.mv), ""));
        }

        for (int i = 0; i < mFauxVoltagesMV.length; i++)
            if (seekBar.equals(mFauxVoltageBars[i])) mFauxVoltageTexts[i]
                    .setText(String.valueOf(progress * 5 + 600)
                            + getString(R.string.mv));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        for (int i = 0; i < mFauxVoltagesMV.length; i++)
            if (seekBar.equals(mFauxVoltageBars[i])) saveFauxVoltage(String
                    .valueOf(VoltageHelper.getFauxFreqVoltages()[i])
                    + "000 "
                    + mFauxVoltageTexts[i].getText().toString()
                            .replace(getString(R.string.mv), "000"));

        if (Utils.existFile(VoltageHelper.UV_MV_PATH)) saveVoltages();
    }

    private static void saveVoltages() {
        klzz.runVoltageGeneric(Utils.listSplitline(mVoltageList),
                VoltageHelper.UV_MV_PATH);
    }

    private static void saveFauxVoltage(String value) {
        klzz.runVoltageGeneric(value, VoltageHelper.FAUX_VOLTAGE);
    }

}
