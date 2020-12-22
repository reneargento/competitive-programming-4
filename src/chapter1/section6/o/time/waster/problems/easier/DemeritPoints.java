package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 22/12/20.
 */
public class DemeritPoints {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
            }

            String dateOfIssue = scanner.nextLine();
            int meritPoints = 0;
            int demeritPoints = 0;

            printNoPoints(dateOfIssue);
            String event = scanner.nextLine();

            String nextYearDate = incrementYearsInDate(dateOfIssue, 1);
            String next2YearsDate = incrementYearsInDate(dateOfIssue, 2);
            while (!event.isEmpty()) {
                String[] data = event.split(" ");
                String eventDate = data[0];
                int points = Integer.parseInt(data[1]);

                while (nextYearDate.compareTo(eventDate) <= 0) {
                    if (demeritPoints > 0) {
                        if (demeritPoints == 2) {
                            demeritPoints = 0;
                        } else {
                            demeritPoints /= 2;
                        }
                        printDemeritPoints(nextYearDate, demeritPoints);
                        if (demeritPoints == 0) {
                            next2YearsDate = incrementYearsInDate(nextYearDate, 2);
                        }
                    }

                    if (next2YearsDate.compareTo(eventDate) <= 0 && meritPoints < 5 && demeritPoints == 0) {
                        meritPoints++;
                        printMeritPoints(next2YearsDate, meritPoints);
                        next2YearsDate = incrementYearsInDate(next2YearsDate, 2);
                    }
                    nextYearDate = incrementYearsInDate(nextYearDate, 1);
                }

                if (points > 2 * meritPoints) {
                    points -= (2 * meritPoints);
                    meritPoints = 0;
                } else {
                    meritPoints -= (points / 2.0);
                    points = 0;
                }
                demeritPoints += points;
                printDemeritPoints(eventDate, demeritPoints);

                nextYearDate = incrementYearsInDate(eventDate, 1);
                next2YearsDate = incrementYearsInDate(eventDate, 2);

                if (!scanner.hasNext()) {
                    break;
                }
                event = scanner.nextLine();
            }

            while (demeritPoints != 0) {
                if (demeritPoints == 2) {
                    demeritPoints = 0;
                } else {
                    demeritPoints /= 2;
                }
                printDemeritPoints(nextYearDate, demeritPoints);
                next2YearsDate = incrementYearsInDate(nextYearDate, 2);
                nextYearDate = incrementYearsInDate(nextYearDate, 1);
            }

            while (meritPoints != 5) {
                meritPoints++;
                printMeritPoints(next2YearsDate, meritPoints);
                next2YearsDate = incrementYearsInDate(next2YearsDate, 2);
            }
        }
    }

    private static void printNoPoints(String date) {
        printFormattedDate(date);
        System.out.println(" No merit or demerit points.");
    }

    private static void printDemeritPoints(String date, int points) {
        if (points == 0) {
            printNoPoints(date);
        } else {
            printFormattedDate(date);
            System.out.printf(" %d demerit point(s).\n", points);
        }
    }

    private static void printMeritPoints(String date, int points) {
        printFormattedDate(date);
        System.out.printf(" %d merit point(s).\n", points);
    }

    private static void printFormattedDate(String date) {
        System.out.print(date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6));
    }

    private static String incrementYearsInDate(String date, int increment) {
        int currentYear = Integer.parseInt(date.substring(0, 4));
        String yearValue = String.valueOf(currentYear + increment);
        while (yearValue.length() != 4) {
            yearValue = "0" + yearValue;
        }
        return yearValue + date.substring(4);
    }
}
