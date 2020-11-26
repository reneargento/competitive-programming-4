package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 09/11/20.
 */
public class WorkoutForADumbbell {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] usage = new int[10];
        int[] rest = new int[10];

        int[] otherPeopleUsage = new int[10];
        int[] otherPeopleRest = new int[10];

        long[] otherPeopleTimes = new long[10];

        for (int i = 0; i < 10; i++) {
            usage[i] = scanner.nextInt();
            rest[i] = scanner.nextInt();
        }

        for (int i = 0; i < 10; i++) {
            otherPeopleUsage[i] = scanner.nextInt();
            otherPeopleRest[i] = scanner.nextInt();
            otherPeopleTimes[i] = scanner.nextInt();
        }

        long totalTime = 0;

        for (int s = 0; s < 3; s++) {
            for (int i = 0; i < 10; i++) {
                if (totalTime < otherPeopleTimes[i]) {
                    totalTime += usage[i];

                    if (totalTime > otherPeopleTimes[i]) {
                        otherPeopleTimes[i] = totalTime;
                    }
                } else {
                    while (otherPeopleTimes[i] <= totalTime) {
                        otherPeopleTimes[i] += otherPeopleUsage[i];

                        if (otherPeopleTimes[i] >= totalTime) {
                            totalTime = otherPeopleTimes[i];
                            totalTime += usage[i];

                            otherPeopleTimes[i] = Math.max(totalTime, otherPeopleTimes[i] + otherPeopleRest[i]);
                            break;
                        }

                        otherPeopleTimes[i] += otherPeopleRest[i];
                        if (otherPeopleTimes[i] > totalTime) {
                            totalTime += usage[i];
                            otherPeopleTimes[i] = Math.max(totalTime, otherPeopleTimes[i]);
                            break;
                        }
                    }
                }
                if (!(s == 2 && i == 9)) {
                    totalTime += rest[i];
                }
            }
        }
        System.out.println(totalTime);
    }

}
