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

import java.io.IOException;

public class VoltageHelper implements Constants {

    public static Integer[] getFauxFreqVoltages() {
        if (Utils.existFile(FAUX_VOLTAGE)) try {
            String[] value = Utils.readBlock(FAUX_VOLTAGE).replace(" ", "")
                    .split("\\r?\\n");
            Integer[] freq = new Integer[value.length];
            for (int i = 0; i < value.length; i++)
                freq[i] = Integer.parseInt(value[i].split(":")[0]) / 1000;
            return freq;
        } catch (IOException ignored) {}
        return new Integer[] { 0 };
    }

    public static Integer[] getFauxVoltages() {
        if (Utils.existFile(FAUX_VOLTAGE)) try {
            String[] value = Utils.readBlock(FAUX_VOLTAGE).replace(" ", "")
                    .split("\\r?\\n");
            Integer[] voltage = new Integer[value.length];
            for (int i = 0; i < value.length; i++)
                voltage[i] = Integer.parseInt(value[i].split(":")[1]) / 1000;
            return voltage;
        } catch (IOException ignored) {}
        return new Integer[] { 0 };
    }

    public static Integer[] getFreqVoltages() {
        if (Utils.existFile(UV_MV_PATH)) try {
            String[] value = Utils.readBlock(UV_MV_PATH).replace(" mV", "")
                    .replace("mhz:", "").split("\\r?\\n");
            Integer[] freq = new Integer[value.length];
            for (int i = 0; i < value.length; i++)
                freq[i] = Integer.parseInt(value[i].split(" ")[0]);
            return freq;
        } catch (IOException ignored) {}
        return new Integer[] { 0 };
    }

    public static Integer[] getVoltages() {
        if (Utils.existFile(UV_MV_PATH)) try {
            String[] value = Utils.readBlock(UV_MV_PATH).replace(" mV", "")
                    .replace("mhz:", "").split("\\r?\\n");
            Integer[] voltages = new Integer[value.length];
            for (int i = 0; i < value.length; i++)
                voltages[i] = Integer.parseInt(value[i].split(" ")[1]);
            return voltages;
        } catch (IOException ignored) {}
        return new Integer[] { 0 };
    }

}
