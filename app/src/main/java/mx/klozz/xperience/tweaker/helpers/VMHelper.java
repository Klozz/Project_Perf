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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mx.klozz.xperience.tweaker.util.Constants;
import mx.klozz.xperience.tweaker.util.Utils;

public class VMHelper implements Constants {

    private static final String[] supportedvm = { "dirty_ratio",
            "dirty_background_ratio", "dirty_expire_centisecs",
            "dirty_writeback_centisecs", "min_free_kbytes", "overcommit_ratio",
            "swappiness", "vfs_cache_pressure" };

    public static List<String> getVMValues() {
        List<String> dummy = new ArrayList<String>();
        if (Utils.existFile(VM_PATH)) try {
            for (String file : getVMPaths())
                dummy.add(Helpers.LeerUnaLinea(file));
        } catch (Exception ignored) {

        }
        else dummy.add("0");
        return dummy;
    }

    public static List<String> getVMFiles() {
        List<String> dummy = new ArrayList<String>();
        if (Utils.existFile(VM_PATH)) {
            if (Utils.existFile(VM_PATH)) for (String file : supportedvm)
                if (Utils.existFile(VM_PATH + "/" + file)) dummy.add(file);
        } else dummy.add("0");
        return dummy;
    }

    public static List<String> getVMPaths() {
        List<String> dummy = new ArrayList<String>();
        if (Utils.existFile(VM_PATH)) {
            if (Utils.existFile(VM_PATH)) for (String file : supportedvm)
                if (Utils.existFile(VM_PATH + "/" + file)) dummy.add(VM_PATH + "/"
                        + file);
        } else dummy.add("0");
        return dummy;
    }
}
