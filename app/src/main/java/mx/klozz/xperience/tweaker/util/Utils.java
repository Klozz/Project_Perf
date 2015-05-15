/*
 * Copyright (C) 2014 Carlos Jesús <TeamMEX@XDA-Developers>
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

package mx.klozz.xperience.tweaker.util;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileReader;
        import java.io.IOException;
        import java.util.List;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

        import android.content.Context;
        import android.util.Log;
        import android.widget.Toast;

import mx.klozz.xperience.tweaker.helpers.Helpers;

public class Utils implements Constants {

    public static void toast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean existFile(String path) {
        return new File(path).exists();
    }

    public static String setAllLetterUpperCase(String text) {
        StringBuilder res = new StringBuilder();

        String[] strArr = text.split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(" ");
        }
        return res.toString().trim();
    }

    public static int getInteger(String name, int defaults, Context context) {
        return context.getSharedPreferences(PREF_NAME, 0)
                .getInt(name, defaults);
    }

    public static void saveInteger(String name, int value, Context context) {
        context.getSharedPreferences(PREF_NAME, 0).edit().putInt(name, value)
                .commit();
    }

    public static String getString(String name, String defaults, Context context) {
        return context.getSharedPreferences(PREF_NAME, 0).getString(name,
                defaults);
    }

    public static void saveString(String name, String value, Context context) {
        context.getSharedPreferences(PREF_NAME, 0).edit().putString(name, value)
                .commit();
    }

    public static boolean getBoolean(String name, boolean defaults,
                                     Context context) {
        return context.getSharedPreferences(PREF_NAME, 0).getBoolean(name,
                defaults);
    }

    public static void saveBoolean(String name, boolean value, Context context) {
        context.getSharedPreferences(PREF_NAME, 0).edit()
                .putBoolean(name, value).commit();
    }

    public static String readBlock(String filename) throws IOException {
        BufferedReader buffreader = new BufferedReader(
                new FileReader(filename), 256);
        String line;
        StringBuilder text = new StringBuilder();
        while ((line = buffreader.readLine()) != null) {
            text.append(line);
            text.append("\n");
        }
        buffreader.close();
        return text.toString();
    }

    public static String readFile(String filepath) {
        try {
            BufferedReader buffreader = new BufferedReader(new FileReader(
                    filepath), 256);
            String line;
            StringBuilder text = new StringBuilder();
            while ((line = buffreader.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
            buffreader.close();
            return text.toString().trim();
        } catch (FileNotFoundException e) {
            Log.e(TAG, filepath + "does not exist");
        } catch (IOException e) {
            Log.e(TAG, "I/O read error: " + filepath);
        }
        return null;
    }

    public static String getFormattedKernelVersion() {
        try {
            return formatKernelVersion(Helpers.LeerUnaLinea("/proc/version"));
        } catch (Exception e) {
            return "Unavailable";
        }
    }

    private static String formatKernelVersion(String rawKernelVersion) {
        final String PROC_VERSION_REGEX = "Linux version (\\S+) "
                + "\\((\\S+?)\\) " + "(?:\\(gcc.+? \\)) " + "(#\\d+) "
                + "(?:.*?)?" + "((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)";

        Matcher m = Pattern.compile(PROC_VERSION_REGEX).matcher(
                rawKernelVersion);

        return !m.matches() || m.groupCount() < 4 ? "Unavailable" : m.group(1)
                + "\n" + m.group(2) + " " + m.group(3) + "\n" + m.group(4);
    }

    public static String replaceLastChar(String values, int i) {
        int slength = values.length();
        if (slength < i) return "0";
        return values.substring(0, slength - i) + "";
    }

    public static String listSplitline(List<String> value) {
        StringBuilder mValue = new StringBuilder();
        for (String s : value) {
            mValue.append(s);
            mValue.append("\t");
        }
        return mValue.toString();
    }
}
