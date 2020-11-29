package chapter1.section6.i.time.harder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Created by Rene Argento on 18/11/20.
 */
public class CanOfBeans {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            long century = scanner.nextLong() * 100;
            int time1 = scanner.nextInt();
            int time2 = scanner.nextInt();
            int time3 = scanner.nextInt();

            LocalDate date = getEarliestPossibleDate(time1, time2, time3, century);
            if (date == null) {
                System.out.println("-1");
            } else {
                int yearLastDigits = date.getYear() % 100;
                System.out.printf("%02d %02d %02d\n", yearLastDigits, date.getMonthValue(), date.getDayOfMonth());
            }
        }
    }

    private static LocalDate getEarliestPossibleDate(long time1, long time2, long time3, long century) {
        long[] timeArray1 = {time1, time2, time3};
        LocalDate date1 = getLocalDate(timeArray1, century);

        long[] timeArray2 = {time1, time3, time2};
        LocalDate date2 = getLocalDate(timeArray2, century);

        long[] timeArray3 = {time2, time1, time3};
        LocalDate date3 = getLocalDate(timeArray3, century);

        long[] timeArray4 = {time2, time3, time1};
        LocalDate date4 = getLocalDate(timeArray4, century);

        long[] timeArray5 = {time3, time1, time2};
        LocalDate date5 = getLocalDate(timeArray5, century);

        long[] timeArray6 = {time3, time2, time1};
        LocalDate date6 = getLocalDate(timeArray6, century);

        LocalDate[] dates = new LocalDate[]{date1, date2, date3, date4, date5, date6};
        List<LocalDate> validDates = getValidDates(dates);
        return getEarliestDate(validDates);
    }

    private static LocalDate getLocalDate(long[] timeArray, long century) {
        if (!isValidLeapYear(timeArray, century)) {
            return null;
        }

        String formattedDay = String.format("%02d", timeArray[0]);
        String formattedMonth = String.format("%02d", timeArray[1]);
        String formattedYear = String.format("%02d", timeArray[2]);
        String date = formattedDay + "-" + formattedMonth + "-" + formattedYear;

        try {
            LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
            if (localDate.getDayOfMonth() == timeArray[0] && localDate.getMonthValue() == timeArray[1]) {
                return localDate;
            }
            return null;
        } catch (DateTimeParseException exception) {
            return null;
        }
    }

    private static boolean isValidLeapYear(long[] timeArray, long century) {
        if (timeArray[0] == 29 && timeArray[1] == 2) {
            long year = century + timeArray[2];
            return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
        }
        return true;
    }

    private static List<LocalDate> getValidDates(LocalDate[] dates) {
        List<LocalDate> validDates = new ArrayList<>();
        for (LocalDate date : dates) {
            if (date != null) {
                validDates.add(date);
            }
        }
        return validDates;
    }

    private static LocalDate getEarliestDate(List<LocalDate> dates) {
        if (dates.isEmpty()) {
            return null;
        }

        LocalDate earliestDate = dates.get(0);
        for (LocalDate date : dates) {
            if (date.isBefore(earliestDate)) {
                earliestDate = date;
            }
        }
        return earliestDate;
    }
}
