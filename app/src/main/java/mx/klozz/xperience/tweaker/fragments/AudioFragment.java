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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import mx.klozz.xperience.tweaker.MainActivity;
import mx.klozz.xperience.tweaker.R;
import mx.klozz.xperience.tweaker.helpers.AudioHelper;
import mx.klozz.xperience.tweaker.helpers.LayoutHelper;
import mx.klozz.xperience.tweaker.util.Constants;
import mx.klozz.xperience.tweaker.util.klzz;
import mx.klozz.xperience.tweaker.util.Utils;

public class AudioFragment extends Fragment implements Constants,
        OnClickListener, OnSeekBarChangeListener {

    private static Context context;

    public static LinearLayout layout = null;

    private static TextView[] mFauxSoundTexts;
    private static Button[] mFauxSoundMinuses;
    private static SeekBar[] mFauxSoundBars;
    private static Button[] mFauxSoundPluses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.generic, container, false);
        layout = (LinearLayout) rootView.findViewById(R.id.layout);

        setLayout();

        if (Utils.existFile(FAUX_SOUND_CONTROL)) for (int i = 1; i < AudioHelper.FAUX_SOUND.length; i++) {
            mFauxSoundMinuses[i].setOnClickListener(this);
            mFauxSoundBars[i].setOnSeekBarChangeListener(this);
            mFauxSoundPluses[i].setOnClickListener(this);
        }

        return rootView;
    }

    private void setLayout() {
        TextView mFauxSoundTitle = new TextView(getActivity());
        LayoutHelper.setTextTitle(mFauxSoundTitle,
                getString(R.string.fauxsoundcontrol), getActivity());
        mFauxSoundTitle.setPadding(0, Math.round(MainActivity.mHeight / 25), 0,
                15);
        if (Utils.existFile(FAUX_SOUND_CONTROL)) layout.addView(mFauxSoundTitle);

        if (Utils.existFile(FAUX_SOUND_CONTROL)) {

            mFauxSoundTexts = new TextView[AudioHelper.FAUX_SOUND.length];
            mFauxSoundMinuses = new Button[AudioHelper.FAUX_SOUND.length];
            mFauxSoundBars = new SeekBar[AudioHelper.FAUX_SOUND.length];
            mFauxSoundPluses = new Button[AudioHelper.FAUX_SOUND.length];

            for (int i = 1; i < AudioHelper.FAUX_SOUND.length; i++) {

                TextView mFauxTitle = new TextView(context);
                if (AudioHelper.FAUX_SOUND[i].equals(FAUX_HEADPHONE_GAIN)) LayoutHelper
                        .setSubTitle(mFauxTitle,
                                getString(R.string.headphonegain));
                else if (AudioHelper.FAUX_SOUND[i]
                        .equals(FAUX_HANDSET_MIC_GAIN)) LayoutHelper
                        .setSubTitle(mFauxTitle,
                                getString(R.string.handsetmicgain));
                else if (AudioHelper.FAUX_SOUND[i].equals(FAUX_CAM_MIC_GAIN)) LayoutHelper
                        .setSubTitle(mFauxTitle, getString(R.string.cammicgain));
                else if (AudioHelper.FAUX_SOUND[i].equals(FAUX_SPEAKER_GAIN)) LayoutHelper
                        .setSubTitle(mFauxTitle,
                                getString(R.string.speakergain));
                else if (AudioHelper.FAUX_SOUND[i]
                        .equals(FAUX_HEADPHONE_PA_GAIN)) LayoutHelper
                        .setSubTitle(mFauxTitle,
                                getString(R.string.headphonepagain));
                layout.addView(mFauxTitle);

                TextView mFauxSoundText = new TextView(context);
                LayoutHelper.setSeekBarText(
                        mFauxSoundText,
                        String.valueOf(AudioHelper.getFauxSoundControlValues(i)
                                + context.getString(R.string.db)));
                mFauxSoundTexts[i] = mFauxSoundText;
                layout.addView(mFauxSoundText);

                LinearLayout mFauxSoundLayout = new LinearLayout(context);
                mFauxSoundLayout.setGravity(Gravity.CENTER);
                layout.addView(mFauxSoundLayout);

                Button mFauxSoundMinus = (Button) LayoutHelper
                        .createSeekBar(getActivity())[0];
                mFauxSoundMinuses[i] = mFauxSoundMinus;
                mFauxSoundLayout.addView(mFauxSoundMinus);

                SeekBar mFauxSoundBar = (SeekBar) LayoutHelper
                        .createSeekBar(getActivity())[1];
                mFauxSoundBars[i] = mFauxSoundBar;
                mFauxSoundLayout.addView(mFauxSoundBar);

                Button mFauxSoundPlus = (Button) LayoutHelper
                        .createSeekBar(getActivity())[2];
                mFauxSoundPluses[i] = mFauxSoundPlus;
                mFauxSoundLayout.addView(mFauxSoundPlus);

            }

            setValues();
        }

    }

    public static void setValues() {
        for (int i = 1; i < AudioHelper.FAUX_SOUND.length; i++)
            LayoutHelper
                    .setNormalSeekBar(
                            mFauxSoundBars[i],
                            AudioHelper.FAUX_SOUND[i]
                                    .equals(FAUX_HEADPHONE_PA_GAIN) ? 17 : 30,
                            AudioHelper.FAUX_SOUND[i]
                                    .equals(FAUX_HEADPHONE_PA_GAIN) ? AudioHelper
                                    .getFauxSoundControlValues(i) + 12
                                    : AudioHelper.getFauxSoundControlValues(i) + 20,
                            context);
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < AudioHelper.FAUX_SOUND.length; i++)
            if (v.equals(mFauxSoundMinuses[i]) || v.equals(mFauxSoundPluses[i])) {
                mFauxSoundBars[i]
                        .setProgress(v.equals(mFauxSoundMinuses[i]) ? mFauxSoundBars[i]
                                .getProgress() - 1 : mFauxSoundBars[i]
                                .getProgress() + 1);
                saveFauxValue(i);
            }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
        MainActivity.AudioChange = true;
        MainActivity.showButtons(true);

        for (int i = 0; i < AudioHelper.FAUX_SOUND.length; i++)
            if (seekBar.equals(mFauxSoundBars[i])) mFauxSoundTexts[i]
                    .setText(String.valueOf(AudioHelper.FAUX_SOUND[i]
                            .equals(FAUX_HEADPHONE_PA_GAIN) ? progress - 12
                            : progress - 20)
                            + context.getString(R.string.db));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        for (int i = 0; i < AudioHelper.FAUX_SOUND.length; i++)
            if (seekBar.equals(mFauxSoundBars[i])) saveFauxValue(i);
    }

    private static void saveFauxValue(int i) {
        klzz.runFauxSound(
                mFauxSoundTexts[i].getText().toString()
                        .replace(context.getString(R.string.db), ""),
                AudioHelper.FAUX_SOUND[i]);
    }

}
