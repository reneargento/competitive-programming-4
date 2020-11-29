package chapter1.section6.i.time.harder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Rene Argento on 19/11/20.
 */
public class DoubleTime {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("#")) {
            String[] data = line.split(" ");
            String weekDay = data[0];
            String day = data[1];
            String month = data[2];
            String year = data[3];

            Set<Integer> extraLeapYears = getExtraLeapYears();

            boolean isOldStyle = false;
            LocalDate date = getLocalDate(day, month, year);
            int dayOfWeekValue = getDayOfWeekValue(weekDay);

            if (date.getDayOfWeek().getValue() == dayOfWeekValue) {
                date = date.minusDays(10);
                int extraDays = getExtraDays(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), extraLeapYears);
                date = date.minusDays(extraDays);
                isOldStyle = true;
            } else {
                date = date.plusDays(10);
                int extraDays = getExtraDays(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), extraLeapYears);
                date = date.plusDays(extraDays);
            }

            String formattedDay = String.valueOf(date.getDayOfMonth());
            if (isOldStyle) {
                formattedDay += "*";
            }
            String monthString = String.valueOf(date.getMonth());
            String formattedMonth = monthString.charAt(0) + monthString.substring(1).toLowerCase();

            System.out.printf("%s %s %s %d\n", weekDay, formattedDay, formattedMonth, date.getYear());
            line = scanner.nextLine();
        }
    }

    private static LocalDate getLocalDate(String day, String month, String year) {
        String formattedDay = day.length() == 1 ? "0" + day : day;
        String date = formattedDay + "-" + month + "-" + year;
        return LocalDate.parse(date, dateTimeFormatter);
    }

    private static Set<Integer> getExtraLeapYears() {
        Set<Integer> extraLeapYears = new HashSet<>();
        for (int year = 1600; year <= 2200; year += 100) {
            if (year % 400 != 0) {
                extraLeapYears.add(year);
            }
        }
        return extraLeapYears;
    }

    private static int getExtraDays(int year, int month, int day, Set<Integer> extraLeapYears) {
        int extraDays = 0;

        for (int leapYear : extraLeapYears) {
            if (leapYear < year) {
                extraDays++;
            }
        }

        if (extraLeapYears.contains(year) && (month > 2 || (month == 2 && day == 29))) {
            extraDays++;
        }
        return extraDays;
    }

    private static int getDayOfWeekValue(String day) {
        switch (day) {
            case "Monday": return 1;
            case "Tuesday": return 2;
            case "Wednesday": return 3;
            case "Thursday": return 4;
            case "Friday": return 5;
            case "Saturday": return 6;
            default: return 7;
        }
    }
}
