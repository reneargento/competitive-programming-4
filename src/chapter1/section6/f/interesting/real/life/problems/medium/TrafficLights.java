package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 03/11/20.
 */
public class TrafficLights {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> times;
        int number = scanner.nextInt();

        while (number != 0) {
            int minTime = Integer.MAX_VALUE;
            times = new ArrayList<>();

            while (number != 0) {
                times.add(number);
                minTime = Math.min(minTime, number);
                number = scanner.nextInt();
            }

            if (!times.isEmpty()) {
                int[] currentTimes = new int[times.size()];
                int targetTime = -1;

                for (int s = minTime; s <= 18000; s++) {
                    boolean valid = true;

                    for (int i = 0; i < times.size(); i++) {
                        while (currentTimes[i] + times.get(i) - 5 <= s) {
                            currentTimes[i] += 2 * times.get(i);
                        }

                        if (!(currentTimes[i] <= s && s < currentTimes[i] + times.get(i) - 5)) {
                            s = currentTimes[i] - 1;
                            valid = false;
                            break;
                        }
                    }

                    if (valid) {
                        targetTime = s;
                        break;
                    }
                }

                if (targetTime == -1) {
                    System.out.println("Signals fail to synchronise in 5 hours");
                } else {
                    System.out.printf("%02d:%02d:%02d\n", targetTime/3600, targetTime%3600/60, targetTime%60);
                }
            }
            number = scanner.nextInt();
        }
    }
}
