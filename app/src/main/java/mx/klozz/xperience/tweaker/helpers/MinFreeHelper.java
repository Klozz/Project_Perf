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

public class MinFreeHelper implements Constants {

    public static Integer[] getMinFreeValues() {
        if (Utils.existFile(MINFREE_PATH)) try {
            String[] dummy = Helpers.LeerUnaLinea(MINFREE_PATH).split(",");
            Integer[] values = new Integer[dummy.length];
            for (int i = 0; i < dummy.length; i++)
                values[i] = Integer.parseInt(dummy[i]);
            return values;
        } catch (Exception ignored) {}
        return new Integer[] { 0 };
    }
}
