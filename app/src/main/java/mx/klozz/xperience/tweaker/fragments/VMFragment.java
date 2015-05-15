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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import mx.klozz.xperience.tweaker.MainActivity;
import mx.klozz.xperience.tweaker.R;
import mx.klozz.xperience.tweaker.helpers.LayoutHelper;
import mx.klozz.xperience.tweaker.helpers.VMHelper;
import mx.klozz.xperience.tweaker.util.klzz;
import mx.klozz.xperience.tweaker.util.InformationDialog;
import mx.klozz.xperience.tweaker.util.Utils;

import java.util.List;

public class VMFragment extends Fragment implements OnClickListener,
        TextWatcher {

    private static Context context;

    public static LinearLayout layout = null;

    private static List<String> mVMFiles;

    private static TextView mVMTitle;
    private static Button[] mMinusButtons;
    private static EditText[] mVMEdits;
    private static Button[] mPlusButtons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.generic, container, false);
        layout = (LinearLayout) rootView.findViewById(R.id.layout);

        setLayout();

        mVMTitle.setOnClickListener(this);

        for (int i = 0; i < mVMFiles.size(); i++) {
            mMinusButtons[i].setOnClickListener(this);
            mVMEdits[i].addTextChangedListener(this);
            mPlusButtons[i].setOnClickListener(this);
        }

        return rootView;
    }

    private void setLayout() {
        mVMFiles = VMHelper.getVMFiles();

        // Create VM Tuning Title
        mVMTitle = new TextView(context);
        LayoutHelper.setTextTitle(mVMTitle, getString(R.string.vmtuning),
                context);
        mVMTitle.setPadding(0, Math.round(MainActivity.mHeight / 25), 0, 15);
        if (Utils.existFile(VMHelper.VM_PATH)) layout.addView(mVMTitle);

        mMinusButtons = new Button[mVMFiles.size()];
        mVMEdits = new EditText[mVMFiles.size()];
        mPlusButtons = new Button[mVMFiles.size()];

        for (int i = 0; i < mVMFiles.size(); i++) {
            TextView mVMText = new TextView(context);
            LayoutHelper.setSubTitle(
                    mVMText,
                    Utils.setAllLetterUpperCase(mVMFiles.get(i).replace("_",
                            " ")));
            if (Utils.existFile(VMHelper.VM_PATH)) layout.addView(mVMText);

            LinearLayout mVMLayout = new LinearLayout(context);
            mVMLayout.setGravity(Gravity.CENTER);
            if (Utils.existFile(VMHelper.VM_PATH)) layout.addView(mVMLayout);

            Button mMinusButton = (Button) LayoutHelper
                    .createEditText(getActivity())[0];
            mMinusButtons[i] = mMinusButton;
            mVMLayout.addView(mMinusButton);

            EditText mVMEdit = (EditText) LayoutHelper
                    .createEditText(getActivity())[1];
            mVMEdits[i] = mVMEdit;
            mVMLayout.addView(mVMEdit);

            Button mPlusButton = (Button) LayoutHelper
                    .createEditText(getActivity())[2];
            mPlusButtons[i] = mPlusButton;
            mVMLayout.addView(mPlusButton);
        }
        setValues();
    }

    public static void setValues() {
        for (int i = 0; i < mVMFiles.size(); i++)
            LayoutHelper.setEditText(mVMEdits[i],
                    String.valueOf(VMHelper.getVMValues().get(i)));
    }

    @Override
    public void onClick(View v) {
        if (!v.equals(mVMTitle)) {
            MainActivity.VMChange = true;
            MainActivity.showButtons(true);
        }

        if (v.equals(mVMTitle)) InformationDialog.showInfo(mVMTitle.getText()
                .toString(), getString(R.string.vmtunig_summary), context);

        for (int i = 0; i < mVMFiles.size(); i++) {
            if (v.equals(mMinusButtons[i])) {
                mVMEdits[i].setText(String.valueOf(Integer.parseInt(mVMEdits[i]
                        .getText().toString()) - 1));
                klzz.runVMGeneric(mVMEdits[i].getText().toString(),
                        mVMFiles.get(i));
            }
            if (v.equals(mPlusButtons[i])) {
                mVMEdits[i].setText(String.valueOf(Integer.parseInt(mVMEdits[i]
                        .getText().toString()) + 1));
                klzz.runVMGeneric(mVMEdits[i].getText().toString(),
                        mVMFiles.get(i));
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        MainActivity.VMChange = true;
        MainActivity.showButtons(true);

        for (int i = 0; i < mVMFiles.size(); i++)
            klzz.runVMGeneric(mVMEdits[i].getText().toString(),
                    mVMFiles.get(i));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
}
