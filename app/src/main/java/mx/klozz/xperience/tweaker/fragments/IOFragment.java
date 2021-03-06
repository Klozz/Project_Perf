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
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import mx.klozz.xperience.tweaker.MainActivity;
import mx.klozz.xperience.tweaker.R;
import mx.klozz.xperience.tweaker.helpers.IOHelper;
import mx.klozz.xperience.tweaker.helpers.LayoutHelper;
import mx.klozz.xperience.tweaker.util.Constants;
import mx.klozz.xperience.tweaker.util.klzz;
import mx.klozz.xperience.tweaker.util.InformationDialog;
import mx.klozz.xperience.tweaker.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IOFragment extends Fragment implements Constants, OnClickListener,
        OnItemSelectedListener, OnSeekBarChangeListener {

    private static Context context;

    public static LinearLayout layout = null;

    private static TextView mInternalSchedulerTitle;
    private static List<String> mAvailableInternalSchedulersList = new ArrayList<String>();
    private static Spinner mInternalSchedulerSpinner;

    private static TextView mExternalSchedulerTitle;
    private static List<String> mAvailableExternalSchedulersList = new ArrayList<String>();
    private static Spinner mExternalSchedulerSpinner;

    private static TextView mInternalReadTitle;
    private static Button mInternalReadMinus;
    private static SeekBar mInternalReadBar;
    private static Button mInternalReadPlus;
    private static TextView mInternalReadText;

    private static TextView mExternalReadTitle;
    private static Button mExternalReadMinus;
    private static SeekBar mExternalReadBar;
    private static Button mExternalReadPlus;
    private static TextView mExternalReadText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.generic, container, false);
        layout = (LinearLayout) rootView.findViewById(R.id.layout);

        setLayout();

        mInternalSchedulerTitle.setOnClickListener(this);
        mInternalSchedulerSpinner.setOnItemSelectedListener(this);
        mExternalSchedulerTitle.setOnClickListener(this);
        mExternalSchedulerSpinner.setOnItemSelectedListener(this);
        mInternalReadTitle.setOnClickListener(this);
        mInternalReadMinus.setOnClickListener(this);
        mInternalReadBar.setOnSeekBarChangeListener(this);
        mInternalReadPlus.setOnClickListener(this);
        mExternalReadTitle.setOnClickListener(this);
        mExternalReadMinus.setOnClickListener(this);
        mExternalReadBar.setOnSeekBarChangeListener(this);
        mExternalReadPlus.setOnClickListener(this);

        return rootView;
    }

    private void setLayout() {

        // Internal storage scheduler
        LinearLayout mInternalSchedulerLayout = new LinearLayout(context);
        mInternalSchedulerLayout.setPadding(0,
                Math.round(MainActivity.mHeight / 25), 0, 0);
        mInternalSchedulerLayout.setGravity(Gravity.CENTER);
        if (Utils.existFile(INTERNAL_SCHEDULER)) layout
                .addView(mInternalSchedulerLayout);

        mInternalSchedulerTitle = new TextView(context);
        LayoutHelper.setTextTitle(mInternalSchedulerTitle,
                getString(R.string.internalstoragescheduler), context);
        mInternalSchedulerLayout.addView(mInternalSchedulerTitle);

        String[] mAvailableInternalSchedulers = IOHelper
                .getInternalSchedulers();
        mAvailableInternalSchedulersList = Arrays
                .asList(mAvailableInternalSchedulers);

        mInternalSchedulerSpinner = new Spinner(context);
        mInternalSchedulerLayout.addView(mInternalSchedulerSpinner);

        // External storage scheduler
        LinearLayout mExternalSchedulerLayout = new LinearLayout(context);
        mExternalSchedulerLayout.setPadding(0,
                Math.round(MainActivity.mHeight / 25), 0, 0);
        mExternalSchedulerLayout.setGravity(Gravity.CENTER);
        if (Utils.existFile(EXTERNAL_SCHEDULER)) layout
                .addView(mExternalSchedulerLayout);

        mExternalSchedulerTitle = new TextView(context);
        LayoutHelper.setTextTitle(mExternalSchedulerTitle,
                getString(R.string.externalstoragescheduler), context);
        mExternalSchedulerLayout.addView(mExternalSchedulerTitle);

        String[] mAvailableExternalSchedulers = IOHelper
                .getExternalSchedulers();
        mAvailableExternalSchedulersList = Arrays
                .asList(mAvailableExternalSchedulers);

        mExternalSchedulerSpinner = new Spinner(context);
        mExternalSchedulerLayout.addView(mExternalSchedulerSpinner);

        // Internal storage read-ahead
        mInternalReadTitle = new TextView(context);
        LayoutHelper.setTextTitle(mInternalReadTitle,
                getString(R.string.internalstorageread), context);
        mInternalReadTitle.setPadding(0, Math.round(MainActivity.mHeight / 25),
                0, 0);
        if (Utils.existFile(INTERNAL_READ)) layout.addView(mInternalReadTitle);

        mInternalReadText = new TextView(context);
        LayoutHelper.setSeekBarText(mInternalReadText,
                String.valueOf(IOHelper.getInternalRead())
                        + getString(R.string.kb));
        if (Utils.existFile(INTERNAL_READ)) layout.addView(mInternalReadText);

        LinearLayout mInternalReadLayout = new LinearLayout(context);
        mInternalReadLayout.setGravity(Gravity.CENTER);
        if (Utils.existFile(INTERNAL_READ)) layout.addView(mInternalReadLayout);

        mInternalReadMinus = (Button) LayoutHelper.createSeekBar(getActivity())[0];
        mInternalReadLayout.addView(mInternalReadMinus);

        mInternalReadBar = (SeekBar) LayoutHelper.createSeekBar(getActivity())[1];
        mInternalReadLayout.addView(mInternalReadBar);

        mInternalReadPlus = (Button) LayoutHelper.createSeekBar(getActivity())[2];
        mInternalReadLayout.addView(mInternalReadPlus);

        // External storage read-ahead
        mExternalReadTitle = new TextView(context);
        LayoutHelper.setTextTitle(mExternalReadTitle,
                getString(R.string.externalstorageread), context);
        mExternalReadTitle.setPadding(0, Math.round(MainActivity.mHeight / 25),
                0, 0);
        if (Utils.existFile(EXTERNAL_READ)) layout.addView(mExternalReadTitle);

        mExternalReadText = new TextView(context);
        LayoutHelper.setSeekBarText(mExternalReadText,
                String.valueOf(IOHelper.getExternalRead())
                        + getString(R.string.kb));
        if (Utils.existFile(EXTERNAL_READ)) layout.addView(mExternalReadText);

        LinearLayout mExternalReadLayout = new LinearLayout(context);
        mExternalReadLayout.setGravity(Gravity.CENTER);
        if (Utils.existFile(EXTERNAL_READ)) layout.addView(mExternalReadLayout);

        mExternalReadMinus = (Button) LayoutHelper.createSeekBar(getActivity())[0];
        mExternalReadLayout.addView(mExternalReadMinus);

        mExternalReadBar = (SeekBar) LayoutHelper.createSeekBar(getActivity())[1];
        mExternalReadLayout.addView(mExternalReadBar);

        mExternalReadPlus = (Button) LayoutHelper.createSeekBar(getActivity())[2];
        mExternalReadLayout.addView(mExternalReadPlus);

        setValues();
    }

    public static void setValues() {

        ArrayAdapter<String> adapterInternalScheduler = new ArrayAdapter<String>(
                context, R.layout.spinner, mAvailableInternalSchedulersList);
        adapterInternalScheduler
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LayoutHelper.setSpinner(mInternalSchedulerSpinner,
                adapterInternalScheduler, mAvailableInternalSchedulersList
                        .indexOf(IOHelper.getCurInternalScheduler()));

        ArrayAdapter<String> adapterExternalScheduler = new ArrayAdapter<String>(
                context, R.layout.spinner, mAvailableExternalSchedulersList);
        adapterExternalScheduler
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LayoutHelper.setSpinner(mExternalSchedulerSpinner,
                adapterExternalScheduler, mAvailableExternalSchedulersList
                        .indexOf(IOHelper.getCurExternalScheduler()));

        LayoutHelper.setNormalSeekBar(mInternalReadBar, 31,
                (IOHelper.getInternalRead() - 128) / 128, context);

        LayoutHelper.setNormalSeekBar(mExternalReadBar, 31,
                (IOHelper.getExternalRead() - 128) / 128, context);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(mInternalSchedulerTitle)) InformationDialog.showInfo(
                mInternalSchedulerTitle.getText().toString(),
                getString(R.string.storagescheduler_summary), context);
        if (v.equals(mExternalSchedulerTitle)) InformationDialog.showInfo(
                mExternalSchedulerTitle.getText().toString(),
                getString(R.string.storagescheduler_summary), context);
        if (v.equals(mInternalReadTitle)) InformationDialog.showInfo(
                mInternalReadTitle.getText().toString(),
                getString(R.string.internalstorageread_summary), context);
        if (v.equals(mInternalReadMinus)) mInternalReadBar
                .setProgress(mInternalReadBar.getProgress() - 1);
        if (v.equals(mInternalReadPlus)) mInternalReadBar
                .setProgress(mInternalReadBar.getProgress() + 1);
        if (v.equals(mExternalReadTitle)) InformationDialog.showInfo(
                mExternalReadTitle.getText().toString(),
                getString(R.string.externalstorageread_summary), context);
        if (v.equals(mExternalReadMinus)) mExternalReadBar
                .setProgress(mExternalReadBar.getProgress() - 1);
        if (v.equals(mExternalReadPlus)) mExternalReadBar
                .setProgress(mExternalReadBar.getProgress() + 1);
        saveReadahead(v.equals(mExternalReadMinus)
                || v.equals(mExternalReadPlus) ? mExternalReadBar
                : mInternalReadBar);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
            long arg3) {
        if (arg0.equals(mInternalSchedulerSpinner)) {
            if (arg2 != mAvailableInternalSchedulersList.indexOf(IOHelper
                    .getCurInternalScheduler())) {
                MainActivity.showButtons(true);
                MainActivity.IOChange = true;

                klzz.runIOGeneric(
                        mAvailableInternalSchedulersList.get(arg2),
                        INTERNAL_SCHEDULER);
            }
        }

        if (arg0.equals(mExternalSchedulerSpinner)) if (arg2 != mAvailableExternalSchedulersList
                .indexOf(IOHelper.getCurExternalScheduler())) {
            MainActivity.showButtons(true);
            MainActivity.IOChange = true;

            klzz.runIOGeneric(mAvailableExternalSchedulersList.get(arg2),
                    EXTERNAL_SCHEDULER);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
        MainActivity.IOChange = true;
        MainActivity.showButtons(true);
        if (seekBar.equals(mInternalReadBar)) mInternalReadText.setText(String
                .valueOf(progress * 128 + 128) + getString(R.string.kb));
        if (seekBar.equals(mExternalReadBar)) mExternalReadText.setText(String
                .valueOf(progress * 128 + 128) + getString(R.string.kb));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        saveReadahead(seekBar);
    }

    private static void saveReadahead(SeekBar seekBar) {
        if (seekBar.equals(mInternalReadBar)) klzz.runIOGeneric(
                mInternalReadText.getText().toString()
                        .replace(context.getString(R.string.kb), ""),
                INTERNAL_READ);
        if (seekBar.equals(mExternalReadBar)) klzz.runIOGeneric(
                mExternalReadText.getText().toString()
                        .replace(context.getString(R.string.kb), ""),
                EXTERNAL_READ);
    }
}
