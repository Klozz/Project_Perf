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

public class AudioHelper implements Constants {

    public static final String[] FAUX_SOUND = { FAUX_SOUND_CONTROL,
            FAUX_HEADPHONE_GAIN, FAUX_HANDSET_MIC_GAIN, FAUX_CAM_MIC_GAIN,
            FAUX_SPEAKER_GAIN, FAUX_HEADPHONE_PA_GAIN };

    public static int getFauxSoundControlValues(int count) {
        try {
            if (FAUX_SOUND[count].equals(FAUX_HEADPHONE_GAIN)) return Integer
                    .parseInt(Helpers.LeerUnaLinea(FAUX_HEADPHONE_GAIN).split(" ")[0]) - 40;
            else if (FAUX_SOUND[count].equals(FAUX_HEADPHONE_PA_GAIN)) return Integer
                    .parseInt(Helpers.LeerUnaLinea(FAUX_HEADPHONE_PA_GAIN).split(" ")[0]) - 12;
            else return Integer.parseInt(Helpers.LeerUnaLinea(FAUX_SOUND[count])) - 40;
        } catch (NumberFormatException ignored) {} catch (Exception ignored) {}
        return 0;
    }
}
