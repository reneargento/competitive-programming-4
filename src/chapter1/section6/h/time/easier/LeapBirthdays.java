package chapter1.section6.h.time.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 18/11/20.
 */
public class LeapBirthdays {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int day = scanner.nextInt();
            int month = scanner.nextInt();
            int year = scanner.nextInt();
            int futureYear = scanner.nextInt();

            int birthdays;
            if (day == 29 && month == 2) {
                birthdays = computeLeapYears(year + 1, futureYear);
            } else {
                birthdays = futureYear - year;
            }
            System.out.printf("Case %d: %d\n", t, birthdays);
        }
    }

    private static int computeLeapYears(int startYear, int endYear) {
        int leapYears = 0;

        for (int year = startYear; year <= endYear; year++) {
            if (year % 400 == 0 ||
                    (year % 100 != 0 && year % 4 == 0)) {
                leapYears++;
            }
        }
        return leapYears;
    }
}
