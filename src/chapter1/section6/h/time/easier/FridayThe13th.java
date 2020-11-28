package chapter1.section6.h.time.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/11/20.
 */
public class FridayThe13th {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int totalDays = scanner.nextInt(); // unused
            int totalMonths = scanner.nextInt();
            int[] months = new int[totalMonths];

            for (int i = 0; i < totalMonths; i++) {
                months[i] = scanner.nextInt();
            }

            int fridayThe13ths = 0;
            int currentDay = 0;

            for (int m = 0; m < months.length; m++) {
                for (int d = 1; d <= months[m]; d++) {
                    if (currentDay != 0
                            && currentDay % 5 == 0
                            && d == 13) {
                        fridayThe13ths++;
                    }
                    currentDay++;
                    currentDay %= 7;
                }
            }
            System.out.println(fridayThe13ths);
        }
    }
}
