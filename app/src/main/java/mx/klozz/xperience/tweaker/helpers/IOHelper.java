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

public class IOHelper implements Constants {

    public static int getExternalRead() {
        if (Utils.existFile(EXTERNAL_READ)) try {
            return Integer.parseInt(Helpers.LeerUnaLinea(EXTERNAL_READ));
        } catch (Exception ignored) {}
        return 0;
    }

    public static int getInternalRead() {
        if (Utils.existFile(INTERNAL_READ)) try {
            return Integer.parseInt(Helpers.LeerUnaLinea(INTERNAL_READ));
        } catch (Exception ignored) {}
        return 0;
    }

    public static String getCurExternalScheduler() {
        if (Utils.existFile(EXTERNAL_SCHEDULER)) try {
            String[] values = Helpers.LeerUnaLinea(EXTERNAL_SCHEDULER).split(" ");
            for (String scheduler : values)
                if (scheduler.contains("[")) return scheduler.replace("[", "")
                        .replace("]", "");
        } catch (Exception ignored) {}
        return "0";
    }

    public static String[] getExternalSchedulers() {
        if (Utils.existFile(EXTERNAL_SCHEDULER)) try {
            String[] values = Helpers.LeerUnaLinea(EXTERNAL_SCHEDULER).split(" ");
            String[] schedulers = new String[values.length];
            for (int i = 0; i < values.length; i++)
                schedulers[i] = values[i].replace("[", "").replace("]", "");
            return schedulers;
        } catch (Exception ignored) {}
        return new String[] { "0" };
    }

    public static String getCurInternalScheduler() {
        if (Utils.existFile(INTERNAL_SCHEDULER)) try {
            String[] values = Helpers.LeerUnaLinea(INTERNAL_SCHEDULER).split(" ");
            for (String scheduler : values)
                if (scheduler.contains("[")) return scheduler.replace("[", "")
                        .replace("]", "");
        } catch (Exception ignored) {}
        return "0";
    }

    public static String[] getInternalSchedulers() {
        if (Utils.existFile(INTERNAL_SCHEDULER)) try {
            String[] values = Helpers.LeerUnaLinea(INTERNAL_SCHEDULER).split(" ");
            String[] schedulers = new String[values.length];
            for (int i = 0; i < values.length; i++)
                schedulers[i] = values[i].replace("[", "").replace("]", "");
            return schedulers;
        } catch (Exception ignored) {}
        return new String[] { "0" };
    }

}
