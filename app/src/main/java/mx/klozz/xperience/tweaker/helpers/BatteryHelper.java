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

public class BatteryHelper implements Constants {

    public static int getBLX() {
        if (Utils.existFile(BLX)) try {
            return Integer.parseInt(Helpers.LeerUnaLinea(BLX));
        } catch (NumberFormatException ignored) {} catch (Exception ignored) {}
        return 0;
    }

    public static boolean getFastCharge() {
        if (Utils.existFile(FAST_CHARGE)) try {
            return Helpers.LeerUnaLinea(FAST_CHARGE).equals("1");
        } catch (Exception ignored) {}
        return false;
    }

    public static int getCurBatteryVoltage() {
        if (Utils.existFile(BATTERY_VOLTAGE)) try {
            return Integer.parseInt(Helpers.LeerUnaLinea(BATTERY_VOLTAGE));
        } catch (NumberFormatException ignored) {} catch (Exception ignored) {}
        return 0;
    }
}
