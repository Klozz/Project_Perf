package mx.klozz.xperience.tweaker.util;

/**
 * Created by klozz on 14/05/2015.
 */
import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import mx.klozz.xperience.tweaker.MainActivity;
import mx.klozz.xperience.tweaker.fragments.AudioFragment;
import mx.klozz.xperience.tweaker.fragments.BatteryFragment;
import mx.klozz.xperience.tweaker.fragments.CPUFragment;
import mx.klozz.xperience.tweaker.fragments.IOFragment;
import mx.klozz.xperience.tweaker.fragments.MinFreeFragment;
//import mx.klozz.xperience.tweaker.fragments.Tools;
import mx.klozz.xperience.tweaker.fragments.VMFragment;
import mx.klozz.xperience.tweaker.helpers.RootHelper;

public class klzz implements Constants {
    private static List<String> CPUklzz = new ArrayList<String>();
    private static List<String> Toolsklzz = new ArrayList<String>();
    private static List<String> Batteryklzz = new ArrayList<String>();
    private static List<String> Audioklzz = new ArrayList<String>();
    private static List<String> Voltageklzz = new ArrayList<String>();
    private static List<String> IOklzz = new ArrayList<String>();
    private static List<String> MinFreeklzz = new ArrayList<String>();
    private static List<String> VMklzz = new ArrayList<String>();

    //setCPU Actions
    public static void setCPU(Context context) {
        for (String cpuval : CPUklzz) {
            String file = cpuval.split("::")[0];//Split all cpu freqs
            String command = cpuval.split("::")[1];//

            RootHelper.run(command);
            Utils.saveString(file, command, context);//save the strings
        }
    }

    public static void setBattery(Context context){
        for(String batteryval : Batteryklzz){
            String file = batteryval.split("::")[0];
            String command = batteryval.split("::")[1];
            RootHelper.run(command);
            Utils.saveString(file, command, context);
        }
    }

    public static void setAudio(Context context){
        for(String audioval : Audioklzz){
            String file = audioval.split("::")[0];
            String command = audioval.split("::")[1];
            RootHelper.run(command);
            Utils.saveString(file, command, context);
        }
    }
    public static void setVoltage(Context context) {
        for (String voltagevalue : Voltageklzz) {
            String file = voltagevalue.split("::")[0];
            String command = voltagevalue.split("::")[1];

            RootHelper.run(command);
            Utils.saveString(file, command, context);
        }
    }

    public static void setIO(Context context) {
        for (String iovalue : IOklzz) {
            String file = iovalue.split("::")[0];
            String command = iovalue.split("::")[1];

            RootHelper.run(command);
            Utils.saveString(file, command, context);
        }
    }

    public static void setMinFree(Context context) {
        for (String minfreevalue : MinFreeklzz) {
            String file = minfreevalue.split("::")[0];
            String command = minfreevalue.split("::")[1];

            RootHelper.run(command);
            Utils.saveString(file, command, context);
        }
    }
    public static void setVM(Context context) {
        for (String vmvalue : VMklzz) {
            String file = vmvalue.split("::")[0];
            String command = vmvalue.split("::")[1];

            RootHelper.run(command);
            Utils.saveString(file, command, context);
        }
    }

    //RUN COMMANDS
    public static void runCPUGeneric(String value, String fie){
        if(CPUklzz.indexOf(fie) !=-1)
            CPUklzz.remove(CPUklzz.indexOf(fie));

        CPUklzz.add(fie  + "::echo "+ value + " > " + fie);
    }

    public static void runBatteryGeneric(String value, String fie){
        if(Batteryklzz.indexOf(fie) !=-1)
            Batteryklzz.remove(Batteryklzz.indexOf(fie));

        Batteryklzz.add(fie+ "::echo "+ value + " > "+ fie);
    }

    public static void runFauxSound(String value,String fie){
        if(Audioklzz.indexOf(fie) !=-1)
            Audioklzz.remove(Audioklzz.indexOf(fie));

        if (fie.equals(FAUX_HEADPHONE_GAIN)) Audioklzz.add(String
                .valueOf(fie + "::echo " + Integer.parseInt(value) + 40 + " "
                        + Integer.parseInt(value) + 40 + " > " + fie));
        else if (fie.equals(FAUX_HEADPHONE_PA_GAIN)) Audioklzz.add(String
                .valueOf(fie + "::echo " + Integer.parseInt(value) + 12 + " "
                        + Integer.parseInt(value) + 12 + " > " + fie));
        else Audioklzz.add(String.valueOf(fie + "::echo "
                    + Integer.parseInt(value) + 40 + " > " + fie));
    }

    public static void runIOGeneric(String value, String fie){
        if(IOklzz.indexOf(fie) !=-1)
            IOklzz.remove(IOklzz.indexOf(fie));

        IOklzz.add(fie+"::echo "+value+" > "+fie);
    }

    public static void runMinFreeGeneric(String value, String fie){
        if(MinFreeklzz.indexOf(fie) !=-1)
            MinFreeklzz.remove(MinFreeklzz.indexOf(fie));

        MinFreeklzz.add(fie+"::echo "+value+" > "+fie);
    }

    public static void runVMGeneric(String value, String fie){
        if(VMklzz.indexOf(fie) !=-1)
            VMklzz.remove(VMklzz.indexOf(fie));

        VMklzz.add(fie + "::echo " + value + " > " + fie);
    }

    public static void runVoltageGeneric(String value, String file) {
        if (Voltageklzz.indexOf(file) != -1) Voltageklzz
                .remove(Voltageklzz.indexOf(file));

        Voltageklzz.add(file + "::echo " + value + " > " + file);
    }
    //FINISH RUNCOMMANDS


    public static void reset(){

        Runnable R = new Runnable() {
            @Override
            public void run() {
                if(CPUFragment.layout != null && MainActivity.CPUChange)
                    CPUFragment.setValues();
                if(BatteryFragment.layout != null && MainActivity.BatteryChange)
                    BatteryFragment.setValues();
                if(AudioFragment.layout != null && MainActivity.AudioChange)
                    AudioFragment.setValues();
                if(IOFragment.layout != null && MainActivity.IOChange)
                    IOFragment.setValues();
                if (MinFreeFragment.layout != null && MainActivity.MinFreeChange)
                    MinFreeFragment.setValues();
                if (VMFragment.layout != null && MainActivity.VMChange)
                    VMFragment.setValues();
                //if(Tools.layout != null && MainActivity.ToolsChange)
                //    Tools.setValues();
                MainActivity.CPUChange = MainActivity.BatteryChange = MainActivity.AudioChange = false;
                MainActivity.VoltageChange = MainActivity.IOChange = MainActivity.MinFreeChange = false;
                MainActivity.VMChange = false;

                MainActivity.showButtons(false);

                CPUklzz.clear();
                Batteryklzz.clear();
                Audioklzz.clear();
                Voltageklzz.clear();
                IOklzz.clear();
                MinFreeklzz.clear();
                VMklzz.clear();
            }
        };
        Handler handler = new Handler();
        handler.post(R);
        handler.postDelayed(R, 500);
    }

}
