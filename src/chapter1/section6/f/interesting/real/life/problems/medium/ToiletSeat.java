package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/11/20.
 */
public class ToiletSeat {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sequence = scanner.next();
        int countAlwaysUp = 0;
        int countAlwaysDown = 0;
        int countPreference = 0;

        char currentAdjustment = sequence.charAt(0);

        for (int i = 1; i < sequence.length(); i++) {
            if ((sequence.charAt(i) == 'U' && currentAdjustment == 'D')
                    || (sequence.charAt(i) == 'D' && currentAdjustment == 'D')) {
                countAlwaysUp++;
                currentAdjustment = 'U';
            } else if (sequence.charAt(i) == 'D' && currentAdjustment == 'U') {
                countAlwaysUp += 2;
            }
        }

        currentAdjustment = sequence.charAt(0);

        for (int i = 1; i < sequence.length(); i++) {
            if ((sequence.charAt(i) == 'U' && currentAdjustment == 'U')
                    || (sequence.charAt(i) == 'D' && currentAdjustment == 'U')) {
                countAlwaysDown++;
                currentAdjustment = 'D';
            } else if (sequence.charAt(i) == 'U' && currentAdjustment == 'D') {
                countAlwaysDown += 2;
            }
        }

        currentAdjustment = sequence.charAt(0);

        for (int i = 1; i < sequence.length(); i++) {
            if (sequence.charAt(i) != currentAdjustment) {
                countPreference++;
                currentAdjustment = sequence.charAt(i);
            }
        }

        System.out.println(countAlwaysUp);
        System.out.println(countAlwaysDown);
        System.out.println(countPreference);
    }
}
