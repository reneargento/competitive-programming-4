package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 10/11/20.
 */
public class GondwanalandTelecom {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char chargingStep = scanner.next().charAt(0);

        while (chargingStep != '#') {
            String phoneNumber = scanner.next();
            int startHour = scanner.nextInt();
            int startMinute = scanner.nextInt();
            int endHour = scanner.nextInt();
            int endMinute = scanner.nextInt();

            int timeSpentOnCategoryA = getTimeSpentOnCategory(startHour, startMinute, endHour, endMinute, 8, 17);
            int timeSpentOnCategoryB = getTimeSpentOnCategory(startHour, startMinute, endHour, endMinute, 18, 21);
            int timeSpentOnCategoryC = getTimeSpentOnCategory(startHour, startMinute, endHour, endMinute, 22, 23);
            timeSpentOnCategoryC += getTimeSpentOnCategory(startHour, startMinute, endHour, endMinute, 0, 7);

            double totalCost = timeSpentOnCategoryA * getRate(chargingStep, 1)
                    + timeSpentOnCategoryB * getRate(chargingStep, 2)
                    + timeSpentOnCategoryC * getRate(chargingStep, 3);
            System.out.printf("%10s%6d%6d%6d%3c%8.2f\n", phoneNumber, timeSpentOnCategoryA, timeSpentOnCategoryB, timeSpentOnCategoryC,
                    chargingStep, totalCost);

            chargingStep = scanner.next().charAt(0);
        }
    }

    private static int getTimeSpentOnCategory(int startHour, int startMinute, int endHour, int endMinute,
                                               int startHourReference, int endHourReference) {
        if ((isEarlier(startHour, startMinute, startHourReference, 0)
                && isEarlier(endHourReference, 59, endHour, endMinute))
        || (isEarlier(endHour, endMinute, startHour, startMinute)
                && ((isEarlier(startHour, startMinute, startHourReference, 0)
                && isEarlier(endHour, endMinute, startHourReference, 0))
                || (isEarlier(endHourReference, 59, startHour, startMinute)
                && isEarlier(endHourReference, 59, endHour, endMinute))))
        || (startHour == endHour && startMinute == endMinute)
        ) {
            return (endHourReference - startHourReference + 1) * 60;
        }

        int timeSpent = 0;

        if (startHourReference <= startHour && startHour <= endHourReference) {
            if (startHourReference <= endHour && endHour <= endHourReference) {
                if (isEarlier(endHour, endMinute, startHour, startMinute)) {
                    timeSpent = getMinutesDifferenceWithoutOverlap(startHour, startMinute, endHourReference + 1, 0);
                    timeSpent += getMinutesDifferenceWithoutOverlap(startHourReference, 0, endHour, endMinute);
                } else {
                    timeSpent = getMinutesDifferenceWithoutOverlap(startHour, startMinute, endHour, endMinute);
                }
            } else {
                timeSpent = getMinutesDifferenceWithoutOverlap(startHour, startMinute, endHourReference + 1, 0);
            }
        } else if (startHourReference <= endHour && endHour <= endHourReference) {
            timeSpent = getMinutesDifferenceWithoutOverlap(startHourReference, 0, endHour, endMinute);
        }
        return timeSpent;
    }

    private static int getMinutesDifferenceWithoutOverlap(int startHour, int startMinute, int endHour, int endMinute) {
        int minutes;
        if (startHour == endHour) {
            minutes = endMinute - startMinute;
        } else {
            minutes = (60 - startMinute) + endMinute + 60 * (endHour - startHour - 1);
        }
        return minutes;
    }

    private static boolean isEarlier(int hour1, int minute1, int hour2, int minute2) {
        return hour1 < hour2 || (hour1 == hour2 && minute1 < minute2);
    }

    private static double getRate(char chargingStep, int category) {
        switch (chargingStep) {
            case 'A':
                if (category == 1) {
                    return 0.1;
                } else if (category == 2) {
                    return 0.06;
                } else {
                    return 0.02;
                }
            case 'B':
                if (category == 1) {
                    return 0.25;
                } else if (category == 2) {
                    return 0.15;
                } else {
                    return 0.05;
                }
            case 'C':
                if (category == 1) {
                    return 0.53;
                } else if (category == 2) {
                    return 0.33;
                } else {
                    return 0.13;
                }
            case 'D':
                if (category == 1) {
                    return 0.87;
                } else if (category == 2) {
                    return 0.47;
                } else {
                    return 0.17;
                }
            default:
                if (category == 1) {
                    return 1.44;
                } else if (category == 2) {
                    return 0.8;
                } else {
                    return 0.3;
                }
        }
    }
}
