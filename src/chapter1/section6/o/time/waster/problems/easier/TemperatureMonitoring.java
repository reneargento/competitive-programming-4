package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/12/20.
 */
public class TemperatureMonitoring {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int measurementInterval = scanner.nextInt();
            int startupDelay = scanner.nextInt();
            int[] thresholds = new int[4];
            for (int i = 0; i < thresholds.length; i++) {
                thresholds[i] = scanner.nextInt();
            }
            int alarmsConfigurations = scanner.nextInt();
            boolean[] isOn = getAlarmData(alarmsConfigurations, 0, 4);
            boolean[] isHighTrigger = getAlarmData(alarmsConfigurations, 4, 8);

            int timeTemperatures = scanner.nextInt();
            boolean[] triggered = getTriggeredAlarms(measurementInterval, startupDelay, thresholds, isOn, isHighTrigger,
                    timeTemperatures, scanner);

            System.out.printf("Case %d: ", t);
            printResults(triggered);
        }
    }

    private static boolean[] getTriggeredAlarms(int measurementInterval, int startupDelay, int[] thresholds,
                                                boolean[] isOn, boolean[] isHighTrigger, int timeTemperatures,
                                                Scanner scanner) {
        boolean[] triggered = new boolean[4];
        int time = 0;

        for (int i = 0; i < timeTemperatures; i++) {
            int nextTime = time + scanner.nextInt();
            int temperature = scanner.nextInt();

            int measurementTime = nextTime / measurementInterval * measurementInterval;

            if (time <= measurementTime && measurementTime <= nextTime && measurementTime >= startupDelay) {
                for (int j = 0; j < 4; j++) {
                    if (isOn[j]) {
                        if ((isHighTrigger[j] && temperature > thresholds[j])
                                || (!isHighTrigger[j] && temperature < thresholds[j])) {
                            triggered[j] = true;
                        }
                    }
                }
            }
            time = nextTime;
        }
        return triggered;
    }

    private static boolean[] getAlarmData(int alarmsConfigurations, int startBit, int endBit) {
        boolean[] isSet = new boolean[4];
        int index = 0;
        for (int i = startBit; i < endBit; i++) {
            isSet[index++] = isSetBit(alarmsConfigurations, i);
        }
        return isSet;
    }

    private static boolean isSetBit(int number, int bit) {
        return ((number >> bit) & 1) > 0;
    }

    private static void printResults(boolean[] triggered) {
        for (int i = 0; i < 4; i++) {
            if (triggered[i]) {
                System.out.print("yes");
            } else {
                System.out.print("no");
            }

            if (i != 3) {
                System.out.print(" ");
            } else {
                System.out.println();
            }
        }
    }
}
