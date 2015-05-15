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

/**
 * Created by klozz on 14/05/2015.
 */
import android.util.Log;

import com.stericson.roottools.CommandCapture;
import com.stericson.roottools.RootDeniedException;
import com.stericson.roottools.RootTools;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import mx.klozz.xperience.tweaker.util.Constants;

public class RootHelper {

    public static void run(String command) {
        try {
            RootTools.getShell().add(new CommandCapture(0, command))
                    .commandCompleted(0, 0);
        } catch (IOException ignored) {} catch (TimeoutException ignored) {} catch (RootDeniedException ignored) {}
    }

}