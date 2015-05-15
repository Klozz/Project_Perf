package mx.klozz.xperience.tweaker.helpers;

/**
 * Created by klozz on 14/05/2015.
 */
import android.app.Activity;
import android.content.Context;

import mx.klozz.xperience.tweaker.util.Constants;
import mx.klozz.xperience.tweaker.util.Utils;

public class CommandControl implements Constants {

    public final String fileSplit = "xperiencecom";
    public final String idSplit = "sdfwefwefwe";

    public enum CommandType {
        GENERIC, CPU
    }

    public void commandSaver(String file, String value, int customID,
                             Context context) {
        Utils.saveString(file, value, context);

        String saved = Utils.getString(COMMAND_NAME, "nothing_found", context);
        if (customID > -1) {
            Utils.saveString(file + idSplit + customID, value, context);

            if (!saved.contains(file + idSplit + customID)) Utils.saveString(
                    COMMAND_NAME, saved.equals("nothing_found") ? file
                            + idSplit + customID : saved + fileSplit + file
                            + idSplit + customID, context);
        } else {
            Utils.saveString(file, value, context);

            String name = Utils.getString(COMMAND_NAME, "nothing_found",
                    context);
            if (!name.contains(file)) Utils.saveString(COMMAND_NAME,
                    name.equals("nothing_found") ? file : name + fileSplit
                            + file, context);
        }
    }

    public void runGeneric(String file, String value, int customID,
                           Context context) {
        RootHelper.run("echo " + value + " > " + file);

        commandSaver(file, "echo " + value + " > " + file, customID, context);
    }

    public void startModule(String module, boolean save, Context context) {
        RootHelper.run("start " + module);

        if (save) commandSaver(module, "start " + module, -1, context);
    }



}
