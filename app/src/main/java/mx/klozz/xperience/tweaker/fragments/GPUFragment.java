/*
 * XPerience Kernel Tweaker - An Android CPU Control application
 * Copyright (C) 2011-2015 Carlos "Klozz" Jesus <TeamMEX@XDA-Developers>
 *
 *          Copyright h0rn3t and AOKP Team
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

package mx.klozz.xperience.tweaker.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.File;
import java.util.List;

import mx.klozz.xperience.tweaker.R;
import mx.klozz.xperience.tweaker.activities.ParamActivity;
import mx.klozz.xperience.tweaker.activities.Settings;
import mx.klozz.xperience.tweaker.helpers.Helpers;
import mx.klozz.xperience.tweaker.util.CMDProcessor;
import mx.klozz.xperience.tweaker.util.Constants;
import mx.klozz.xperience.tweaker.util.GPUClass;

public class GPUFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Constants {

    SharedPreferences mPreferences;

    private ListPreference lgpufmax,lgpugov;
    private Preference mGpuGovset;
    private Context context;
    private GPUClass gpu;
    private String ps = "";
    private String ps_cpuquiet = "";
    private String app = "";
    private String ps_mc_ps = "";
    private String intelliprof = "";
    private final int vstep = 12500;
    private final int vmin = 0;
    private final int nvsteps = 25;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        setHasOptionsMenu(true);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mPreferences.registerOnSharedPreferenceChangeListener(this);
        addPreferencesFromResource(R.layout.gpu_advanced);

        lgpufmax = (ListPreference) findPreference("pref_gpu_fmax");
        mGpuGovset = findPreference("pref_gpugov_set");
        lgpugov = (ListPreference) findPreference("pref_gpugov");

        app = getString(R.string.app_name);

        gpu = new GPUClass();
        if (gpu.gpuclk_path() == null) {
            PreferenceCategory hideCat = (PreferenceCategory) findPreference("gpu_max_clk");
            getPreferenceScreen().removePreference(hideCat);
        } else {
            lgpufmax.setEntries(gpu.gpuclk_names());
            lgpufmax.setEntryValues(gpu.gpuclk_values());
            final String s = Helpers.LeerUnaLinea(gpu.gpuclk_path());
            lgpufmax.setValue(s);
            lgpufmax.setSummary(ps + lgpufmax.getEntry());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tablist:
                Helpers.getTabList(getString(R.string.menu_tab), (ViewPager) getView().getParent(), getActivity());
                break;
            case R.id.app_settings:
                Intent intent = new Intent(getActivity(), Settings.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mGpuGovset) {
            Intent i = new Intent(getActivity(), ParamActivity.class);
            i.putExtra("path", gpu.gpugovset_path());
            i.putExtra("sob", GPU_PARAM_SOB);
            i.putExtra("pref", "gpuparam");
            startActivity(i);
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_gpu_fmax")) {
            setlistPref(lgpufmax, gpu.gpuclk_path(), ps + lgpufmax.getEntry());

            Intent intent = new Intent(INTENT_PP);
            intent.putExtra("from", app);
            context.sendBroadcast(intent);
        }
    }

    private void setlistPref(ListPreference l, String p, String s) {
        final String v = l.getValue();
        if (!v.equals(Helpers.LeerUnaLinea(p))) {
            new CMDProcessor().su.runWaitFor("busybox echo " + v + " > " + p);
        }
        l.setSummary(s);
    }

    private static int getNearestStepIndex(final int value, final int min, final int step, final int total) {
        int index = 0;
        for (int k = 0; k < total; k++) {
            if (value > (k * step + min)) index++;
            else break;
        }
        return index;
    }

    public void openDialog(String title, final int min, final int max, final Preference pref, final String path, final String key) {
        Resources res = context.getResources();
        String cancel = res.getString(R.string.cancel);
        String ok = res.getString(R.string.ok);
        final EditText settingText;
        LayoutInflater factory = LayoutInflater.from(context);
        final View alphaDialog = factory.inflate(R.layout.seekbar_dialog, null);

        final SeekBar seekbar = (SeekBar) alphaDialog.findViewById(R.id.seek_bar);
        int currentProgress = Integer.parseInt(Helpers.LeerUnaLinea(path));
        seekbar.setMax(max - min);
        if (currentProgress > max) currentProgress = max - min;
        else if (currentProgress < min) currentProgress = 0;
        else currentProgress = currentProgress - min;

        seekbar.setProgress(currentProgress);

        settingText = (EditText) alphaDialog.findViewById(R.id.setting_text);
        settingText.setText(Integer.toString(currentProgress + min));

        settingText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    int val = Integer.parseInt(settingText.getText().toString()) - min;
                    seekbar.setProgress(val);
                    return true;
                }
                return false;
            }
        });

        settingText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int val = Integer.parseInt(s.toString());
                    if (val > max) {
                        s.replace(0, s.length(), Integer.toString(max));
                        val = max;
                    }
                    seekbar.setProgress(val - min);
                } catch (NumberFormatException ex) {
                }
            }
        });

        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
                final int mSeekbarProgress = seekbar.getProgress();
                if (fromUser) {
                    settingText.setText(Integer.toString(mSeekbarProgress + min));
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekbar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekbar) {
            }
        };
        seekbar.setOnSeekBarChangeListener(seekBarChangeListener);

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(alphaDialog)
                .setNegativeButton(cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // nothing
                            }
                        })
                .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int val = min;
                        if (!settingText.getText().toString().equals(""))
                            val = Integer.parseInt(settingText.getText().toString());
                        if (val < min) val = min;
                        seekbar.setProgress(val - min);
                        int newProgress = seekbar.getProgress() + min;
                        new CMDProcessor().su.runWaitFor("busybox echo " + Integer.toString(newProgress) + " > " + path);
                        final String v = Helpers.LeerUnaLinea(path);
                        mPreferences.edit().putString(key, v).commit();
                        pref.setSummary(v);

                    }
                }).create().show();
    }
}//Finish
