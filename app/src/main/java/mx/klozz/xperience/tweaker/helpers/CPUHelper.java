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

import mx.klozz.xperience.tweaker.util.Constants;
import mx.klozz.xperience.tweaker.util.Utils;

public class CPUHelper implements Constants {

    public static boolean getIntelliPlugEcoMode() {
        if (Utils.existFile(ECO_MODE)) try {
            return Helpers.LeerUnaLinea(ECO_MODE).equals("1");
        } catch (Exception ignored) {}
        return false;
    }

    public static boolean getIntelliPlug() {
        if (Utils.existFile(INTELLI_PLUG)) try {
            return Helpers.LeerUnaLinea(INTELLI_PLUG).equals("1");
        } catch (Exception ignored) {}
        return false;
    }

    public static String getCurGovernor() {
        if (Utils.existFile(CUR_GOVERNOR)) try {
            return Helpers.LeerUnaLinea(CUR_GOVERNOR);
        } catch (NumberFormatException ignored) {} catch (Exception ignored) {}
        return "";
    }

    public static String[] getAvailableGovernor() {
        if (Utils.existFile(AVAILABLE_GOVERNOR)) try {
            return Helpers.LeerUnaLinea(AVAILABLE_GOVERNOR).split(" ");
        } catch (Exception ignored) {}
        return new String[] { "" };
    }

    public static int getMinScreenOnFreq() {
        if (Utils.existFile(MIN_SCREEN_ON)) try {
            return Integer.valueOf(Helpers.LeerUnaLinea(MIN_SCREEN_ON));
        } catch (NumberFormatException ignored) {} catch (Exception ignored) {}
        return 0;
    }

    public static int getMaxScreenOffFreq() {
        if (Utils.existFile(MAX_SCREEN_OFF)) try {
            return Integer.valueOf(Helpers.LeerUnaLinea(MAX_SCREEN_OFF));
        } catch (NumberFormatException ignored) {} catch (Exception ignored) {}
        return 0;
    }

    public static int getMinFreq() {
        if (Utils.existFile(MIN_FREQ)) try {
            return Integer.valueOf(Helpers.LeerUnaLinea(MIN_FREQ));
        } catch (NumberFormatException ignored) {} catch (Exception ignored) {}
        return 0;
    }

    public static int getMaxFreq() {
        if (Utils.existFile(MAX_FREQ)) try {
            return Integer.parseInt(Helpers.LeerUnaLinea(MAX_FREQ));
        } catch (NumberFormatException ignored) {} catch (Exception ignored) {}
        return 0;
    }

    public static Integer[] getAvailableFreq() {
        if (Utils.existFile(AVAILABLE_FREQ)) try {
            String[] value = Utils.readBlock(AVAILABLE_FREQ).split("\\r?\\n");
            Integer[] freqs = new Integer[value.length];
            for (int i = 0; i < value.length; i++)
                freqs[i] = Integer.valueOf(value[i].split(" ")[0]);
            return freqs;
        } catch (Exception ignored) {}
        return new Integer[] { 0 };
    }

    public static boolean getCoreOnline(int core) {
        if (Utils.existFile(CORE_STAT.replace("present", String.valueOf(core)))) try {
            return Helpers.LeerUnaLinea(
                    CORE_STAT.replace("present", String.valueOf(core))).equals(
                    "1");

        } catch (Exception ignored) {}
        return true;
    }

    public static int getFreqScaling(int core) {
        try {
            return Utils.existFile(FREQUENCY_SCALING.replace("present",
                    String.valueOf(core))) ? Integer.valueOf(Helpers
                    .LeerUnaLinea(FREQUENCY_SCALING.replace("present",
                            String.valueOf(core)))) : 0;

        } catch (Exception ignored) {}
        return 0;
    }

    public static int getCoreCount() {
        if (Utils.existFile(CORE_VALUE)) try {
            String output = Helpers.LeerUnaLinea(CORE_VALUE);
            if (!output.equals("0")) return Integer
                    .parseInt(output.split("-")[1]) + 1;
            else return 1;

        } catch (Exception ignored) {}
        return 0;
    }
}
