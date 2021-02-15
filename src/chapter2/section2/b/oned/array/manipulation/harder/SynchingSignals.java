package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 28/01/21.
 */
public class SynchingSignals {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int setId = 1;

        while (scanner.hasNext()) {
            String[] signalsData = scanner.nextLine().split(" ");
            int[] cycles = new int[signalsData.length];
            for (int i = 0; i < cycles.length; i++) {
                cycles[i] = Integer.parseInt(signalsData[i]);
            }

            int secondsRequired = computeTimeOfNextSynchronization(cycles);
            int minutes = secondsRequired / 60;
            int seconds = secondsRequired % 60;

            System.out.printf("Set %d ", setId);
            if (minutes > 60 || (minutes == 60 && seconds > 0)) {
                System.out.println("is unable to synch after one hour.");
            } else {
                System.out.printf("synchs again at %d minute(s) and %d second(s) after all turning green.\n",
                        minutes, seconds);
            }
            setId++;
        }
    }

    private static int computeTimeOfNextSynchronization(int[] cycles) {
        boolean[] isGreen = new boolean[cycles.length];
        Arrays.fill(isGreen, true);
        int[] signalTimes = new int[cycles.length];

        for (int i = 0; i < cycles.length; i++) {
            signalTimes[i] = cycles[i];
        }

        int startTime = getStartTime(signalTimes);
        int maxTime = 60 * 60;
        for (int s = startTime; s <= maxTime; s++) {
            for (int i = 0; i < signalTimes.length; i++) {
                while (signalTimes[i] < s) {
                    signalTimes[i] += cycles[i];
                    isGreen[i] = !isGreen[i];
                }
            }

            if (areAllSignalsGreen(isGreen, s, signalTimes)) {
                return s;
            }
        }
        return maxTime + 1;
    }

    private static int getStartTime(int[] signalTimes) {
        int startTime = Integer.MAX_VALUE;
        for (int i = 0; i < signalTimes.length; i++) {
            startTime = Math.min(startTime, signalTimes[i]);
        }
        return startTime;
    }

    private static boolean areAllSignalsGreen(boolean[] isGreen, int currentSeconds, int[] signalTimes) {
        for (int i = 0; i < isGreen.length; i++) {
            if ((!isGreen[i] && signalTimes[i] != currentSeconds)
                    || (isGreen[i] && signalTimes[i] - 5 <= currentSeconds)) {
                return false;
            }
        }
        return true;
    }
}
