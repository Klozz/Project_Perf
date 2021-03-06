package mx.klozz.xperience.tweaker.CpuSpy;

/**
 * Created by klozz on 14/05/2015.
 */
// imports

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.SystemClock;

import mx.klozz.xperience.tweaker.util.Constants;
import mx.klozz.xperience.tweaker.util.Utils;

/**
 * CpuStateMonitor is a class responsible for querying the system and getting
 * the time-in-state information, as well as allowing the user to set/reset
 * offsets to "restart" the state timers
 */
public class CpuStateMonitor implements Constants {

    private List<CpuState> _states = new ArrayList<CpuState>();
    private Map<Integer, Long> _offsets = new HashMap<Integer, Long>();

    /**
     * exception class
     */

    public class CpuStateMonitorException extends Exception {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public CpuStateMonitorException(String s) {
            super(s);
        }
    }

    /**
     * simple struct for states/time
     */
    public class CpuState implements Comparable<CpuState> {
        /**
         * init with freq and duration
         */
        public CpuState(int a, long b) {
            freq = a;
            duration = b;
        }

        public int freq = 0;
        public long duration = 0;

        /**
         * for sorting, compare the freqs
         */
        public int compareTo(CpuState state) {
            Integer a = freq;
            Integer b = state.freq;
            return a.compareTo(b);
        }
    }

    /**
     * @return List of CpuState with the offsets applied
     */
    public List<CpuState> getStates() {
        List<CpuState> states = new ArrayList<CpuState>();

        /*
         * check for an existing offset, and if it's not too big, subtract it
         * from the duration, otherwise just add it to the return List
         */
        for (CpuState state : _states) {
            long duration = state.duration;
            if (_offsets.containsKey(state.freq)) {
                long offset = _offsets.get(state.freq);
                if (offset <= duration) duration -= offset;
                else {
                    /*
                     * offset > duration implies our offsets are now invalid, so
                     * clear and recall this function
                     */
                    _offsets.clear();
                    return getStates();
                }
            }

            states.add(new CpuState(state.freq, duration));
        }

        return states;
    }

    /**
     * @return Sum of all state durations including deep sleep, accounting for
     *         offsets
     */
    public long getTotalStateTime() {
        long sum = 0;
        long offset = 0;

        for (CpuState state : _states)
            sum += state.duration;

        for (Map.Entry<Integer, Long> entry : _offsets.entrySet())
            offset += entry.getValue();

        return sum - offset;
    }

    /**
     * @return a list of all the CPU frequency states, which contains both a
     *         frequency and a duration (time spent in that state
     */
    public List<CpuState> updateStates() throws CpuStateMonitorException {
        /*
         * attempt to create a buffered reader to the time in state file and
         * read in the states to the class
         */
        try {
            InputStream is = new FileInputStream(AVAILABLE_FREQ);
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            _states.clear();
            readInStates(br);
            is.close();
        } catch (IOException e) {
            throw new CpuStateMonitorException(
                    "Problem opening time-in-states file");
        }

        /*
         * deep sleep time determined by difference between elapsed (total) boot
         * time and the system uptime (awake)
         */
        long sleepTime = (SystemClock.elapsedRealtime() - SystemClock
                .uptimeMillis()) / 10;
        _states.add(new CpuState(0, sleepTime));

        Collections.sort(_states, Collections.reverseOrder());

        return _states;
    }

    /**
     * read from a provided BufferedReader the state lines into the States
     * member field
     */
    private void readInStates(BufferedReader br)
            throws CpuStateMonitorException {
        try {
            String line;
            while ((line = br.readLine()) != null) {
                // split open line and convert to Integers
                String[] nums = line.split(" ");
                _states.add(new CpuState(Integer.parseInt(nums[0]), Long
                        .parseLong(nums[1])));
            }
        } catch (IOException e) {
            throw new CpuStateMonitorException(
                    "Problem processing time-in-states file");
        }
    }
}
