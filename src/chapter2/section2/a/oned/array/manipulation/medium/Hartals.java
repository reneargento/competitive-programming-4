package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 23/01/21.
 */
public class Hartals {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int days = scanner.nextInt();
            int politicalParties = scanner.nextInt();
            int[] hartals = new int[politicalParties];
            int[] strikes = new int[politicalParties];

            for (int i = 0; i < hartals.length; i++) {
                hartals[i] = scanner.nextInt();
            }

            int lostWorkingDays = 0;

            for (int day = 1; day <= days; day++) {
                if (day % 7 == 6
                        || day % 7 == 0) {
                    continue;
                }

                boolean hasStrike = false;

                for (int i = 0; i < strikes.length; i++) {
                    while (strikes[i] < day) {
                        strikes[i] += hartals[i];
                    }

                    if (strikes[i] == day) {
                        hasStrike = true;
                    }
                }

                if (hasStrike) {
                    lostWorkingDays++;
                }
            }
            System.out.println(lostWorkingDays);
        }
    }
}
