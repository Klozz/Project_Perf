package mx.klozz.xperience.tweaker.CpuSpy;

/**
 * Created by klozz on 14/05/2015.
 */
/**
 * main application class
 */
public class CpuSpyApp {

    /**
     * the long-living object used to monitor the system frequency states
     */
    private static CpuStateMonitor _monitor = new CpuStateMonitor();

    /**
     * @return the internal CpuStateMonitor object
     */
    public static CpuStateMonitor getCpuStateMonitor() {
        return _monitor;
    }

}
