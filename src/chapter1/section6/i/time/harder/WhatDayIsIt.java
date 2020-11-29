package chapter1.section6.i.time.harder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Created by Rene Argento on 21/11/20.
 * @noinspection deprecation
 */
public class WhatDayIsIt {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int month = scanner.nextInt();
        int day = scanner.nextInt();
        int year = scanner.nextInt();

        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
        String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        while (month != 0 || day != 0 || year != 0) {
            GregorianCalendar gregorianCalendar = getCalendar(day, month, year);

            if (gregorianCalendar != null && !isNonExistentDate(day, month, year)) {
                String monthName = monthNames[month - 1];
                int dayOfWeekIndex = gregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK);

                System.out.printf("%s %d, %d is a %s\n", monthName, day, year, weekDays[dayOfWeekIndex - 1]);
            } else {
                System.out.printf("%d/%d/%d is an invalid date.\n", month, day, year);
            }
            month = scanner.nextInt();
            day = scanner.nextInt();
            year = scanner.nextInt();
        }
    }

    private static GregorianCalendar getCalendar(int day, int month, int year) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setLenient(false);
        // September 14 1752
        gregorianCalendar.setGregorianChange(new Date(-148, 8, 14));
        gregorianCalendar.set(year, month - 1, day);

        // Workaround to actually validate date (so February 30 will not be rounded to March 2)
        // https://stackoverflow.com/questions/226910/how-to-sanity-check-a-date-in-java
        try {
            new SimpleDateFormat("dd/MM/yyyy").format(gregorianCalendar.getTime());
            return gregorianCalendar;
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().contains("exist")) {
                gregorianCalendar.setLenient(true);
                return gregorianCalendar;
            }
            return null;
        }
    }

    private static boolean isNonExistentDate(int day, int month, int year) {
        return year == 1752 && month == 9 && (3 <= day && day <= 13);
    }
}
