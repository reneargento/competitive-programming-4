package chapter1.section6.i.time.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/11/20.
 */
public class February29 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 1; t <= tests; t++) {
            String[] date1 = scanner.nextLine().split(" ");
            int startDay = Integer.parseInt(date1[1].substring(0, date1[1].length() - 1));
            String startMonth = date1[0];
            long startYear = Long.parseLong(date1[2]);

            String[] date2 = scanner.nextLine().split(" ");
            int endDay = Integer.parseInt(date2[1].substring(0, date2[1].length() - 1));
            String endMonth = date2[0];
            long endYear = Long.parseLong(date2[2]);

            if (!isFirstTwoMonths(startDay, startMonth, 29)) {
                startYear++;
            }
            if (isFirstTwoMonths(endDay, endMonth, 28)) {
                endYear--;
            }

            long leapYears = countLeapYears(startYear, endYear);
            System.out.printf("Case %d: %d\n", t, leapYears);
        }
    }

    private static long countLeapYears(long startYear, long endYear) {
        long totalMultiplesOf4 = countMultiples(startYear, endYear, 4);
        long totalMultiplesOf100 = countMultiples(startYear, endYear, 100);
        long totalMultiplesOf400 = countMultiples(startYear, endYear, 400);
        return totalMultiplesOf4 - totalMultiplesOf100 + totalMultiplesOf400;
    }

    // Based on https://math.stackexchange.com/questions/975539/how-do-you-find-the-number-of-multiples-of-a-given-range-of-numbers
    private static long countMultiples(long startYear, long endYear, int value) {
        long firstMultiple = findFirstMultipleOf(startYear, value, true, endYear);
        long lastMultiple = findFirstMultipleOf(endYear, value, false, startYear);

        if (firstMultiple == 0) {
            return 0;
        }
        return lastMultiple - firstMultiple + 1;
    }

    private static long findFirstMultipleOf(long year, int value, boolean leftToRight, long stopYear) {
        while (true) {
            if (year % value == 0) {
                return year / value;
            }

            if (year == stopYear) {
                return 0;
            }
            year = leftToRight ? year + 1 : year - 1;
        }
    }

    private static boolean isFirstTwoMonths(int day, String month, int lastFebruaryDay) {
        return month.equals("January") || (month.equals("February") && day <= lastFebruaryDay);
    }
}
