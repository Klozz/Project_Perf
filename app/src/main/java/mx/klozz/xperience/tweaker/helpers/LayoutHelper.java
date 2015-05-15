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

package mx.klozz.xperience.tweaker.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import mx.klozz.xperience.tweaker.R;
import mx.klozz.xperience.tweaker.MainActivity;

public class LayoutHelper {

    public static View[] createEditText(Context context) {
        LayoutParams lp = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

        Button minus = new Button(context);
        minus.setText(context.getString(R.string.minus));

        EditText text = new EditText(context);
        text.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED
                | InputType.TYPE_CLASS_NUMBER);
        text.setLayoutParams(lp);

        Button plus = new Button(context);
        plus.setText(context.getString(R.string.plus));

        return new View[] { minus, text, plus };
    }

    public static View[] createSeekBar(Context context) {
        LayoutParams lp = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

        Button minus = new Button(context);
        minus.setText(context.getString(R.string.minus));

        SeekBar bar = new SeekBar(context);
        bar.setLayoutParams(lp);

        Button plus = new Button(context);
        plus.setText(context.getString(R.string.plus));

        return new View[] { minus, bar, plus };
    }

    public static void setEditText(EditText i, String text) {
        i.setGravity(Gravity.CENTER);
        i.setText(text);
    }

    public static void setSubTitle(TextView i, String text) {
        i.setGravity(Gravity.CENTER);
        i.setTextSize(18);
        i.setText(text);
    }

    public static void setCheckBox(CheckBox i, boolean checked, String text,
            Context context) {
        i.setText(text);
        i.setChecked(checked);
        i.setButtonDrawable(context.getResources().getDrawable(R.drawable.checkbox));
    }

    public static void setSpinner(Spinner i, ArrayAdapter<String> adapter,
            int selection) {
        i.setAdapter(adapter);
        i.setSelection(selection);
        i.setPadding(MainActivity.mWidth / 13, 0, MainActivity.mWidth / 13, 0);
    }

    public static void setSeekBarText(TextView i, String text) {
        i.setGravity(Gravity.CENTER);
        i.setText(text);
        i.setPadding(0, 15, 0, 0);
    }

    public static void setNormalSeekBar(SeekBar i, int max, int progress,
            Context context) {
        i.setMax(max);
        i.setProgress(progress);
        i.setProgressDrawable(context.getResources().getDrawable(
                R.drawable.seekbar_progress_bg));
        i.setThumb(context.getResources().getDrawable(
               R.drawable.seekbar_control));

    }

    public static void setSeekBar(SeekBar i, int max, int progress,
            Context context) {
        i.setMax(max);
        i.setProgress(progress);
        i.setPadding(MainActivity.mWidth / 13, 0, MainActivity.mWidth / 13, 0);
        i.setProgressDrawable(context.getResources().getDrawable(
                R.drawable.seekbar_progress_bg));
        i.setThumb(context.getResources().getDrawable(
                R.drawable.seekbar_control));
    }

    public static void setTextTitle(TextView i, String text, Context context) {
        i.setTextSize(16);
        i.setGravity(Gravity.CENTER);
        i.setTextColor(context.getResources().getColor(android.R.color.white));
        i.setTypeface(null, Typeface.BOLD);
        i.setText(text);
    }

    public static void setCurFreqText(TextView i) {
        i.setGravity(Gravity.CENTER);
        i.setTextSize(18);
    }
}
