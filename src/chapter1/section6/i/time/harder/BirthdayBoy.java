package chapter1.section6.i.time.harder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 19/11/20.
 */
public class BirthdayBoy {

    private static class Day implements Comparable<Day> {
        int day;
        int month;

        public Day(int day, int month) {
            this.day = day;
            this.month = month;
        }

        @Override
        public int compareTo(Day other) {
            if (month != other.month) {
                return month - other.month;
            }
            return day - other.day;
        }
    }

    private static final int NON_LEAP_YEAR = 1988;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        LocalDate tomorrow = LocalDate.parse("28-10-" + NON_LEAP_YEAR, dateTimeFormatter);
        List<Day> dateList = new ArrayList<>();

        for (int t = 0; t < tests; t++) {
            scanner.next(); // Ignore name
            String[] monthDay = scanner.next().split("-");
            int month = Integer.parseInt(monthDay[0]);
            int day = Integer.parseInt(monthDay[1]);
            dateList.add(new Day(day, month));
        }

        Collections.sort(dateList);
        LocalDate targetDate = null;

        if (dateList.size() == 1) {
            LocalDate date = getLocalDate(dateList.get(0).day, dateList.get(0).month);
            targetDate = date.minusDays(1);
        } else {
            LocalDate previousDate = null;
            long longestDays = 0;
            Day firstDay = dateList.get(0);
            dateList.add(new Day(firstDay.day, firstDay.month));

            for (Day date : dateList) {
                LocalDate birthday = getLocalDate(date.day, date.month);

                if (previousDate != null) {
                    if (birthday.isBefore(previousDate)) {
                        birthday = birthday.plusYears(1);
                    }

                    long daysBetween = ChronoUnit.DAYS.between(previousDate, birthday.plusDays(1));

                    if (daysBetween > longestDays) {
                        longestDays = daysBetween;
                        targetDate = birthday.minusDays(1);
                    } else if (daysBetween == longestDays) {
                        LocalDate birthdayToCompare = birthday;
                        if (birthday.isBefore(tomorrow)) {
                            birthdayToCompare = birthdayToCompare.plusYears(1);
                        }
                        LocalDate targetDateToCompare = targetDate;
                        if (targetDate.isBefore(tomorrow)) {
                            targetDateToCompare = targetDateToCompare.plusYears(1);
                        }

                        long distanceFromTomorrowToCurrentDate = ChronoUnit.DAYS.between(tomorrow.plusDays(1), birthdayToCompare.plusDays(1));
                        long distanceFromTomorrowToTargetDate = ChronoUnit.DAYS.between(tomorrow.plusDays(1), targetDateToCompare.plusDays(1));
                        if (distanceFromTomorrowToCurrentDate < distanceFromTomorrowToTargetDate) {
                            targetDate = birthday.minusDays(1);
                        }
                    }
                }
                previousDate = birthday;
            }
        }
        System.out.printf("%02d-%02d\n", targetDate.getMonthValue(), targetDate.getDayOfMonth());
    }

    private static LocalDate getLocalDate(int day, int month) {
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String date = formattedDay + "-" + formattedMonth + "-" + NON_LEAP_YEAR;
        return LocalDate.parse(date, dateTimeFormatter);
    }
}
