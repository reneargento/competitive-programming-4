package chapter1.section6.i.time.harder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 19/11/20.
 */
public class BestBefore {

    private static final int CENTURY = 2000;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String dateString = scanner.next();
        String[] date = dateString.split("/");

        int time1 = Integer.parseInt(date[0]);
        int time2 = Integer.parseInt(date[1]);
        int time3 = Integer.parseInt(date[2]);

        LocalDate earliestDate = getEarliestPossibleDate(time1, time2, time3);
        if (earliestDate == null) {
            System.out.println(dateString + " is illegal");
        } else {
            System.out.printf("%d-%02d-%02d\n", earliestDate.getYear(), earliestDate.getMonthValue(),
                    earliestDate.getDayOfMonth());
        }
    }

    private static LocalDate getEarliestPossibleDate(int time1, int time2, int time3) {
        int[] timeArray1 = {time1, time2, time3};
        LocalDate date1 = getLocalDateIfValidDayMonth(timeArray1);

        int[] timeArray2 = {time1, time3, time2};
        LocalDate date2 = getLocalDateIfValidDayMonth(timeArray2);

        int[] timeArray3 = {time2, time1, time3};
        LocalDate date3 = getLocalDateIfValidDayMonth(timeArray3);

        int[] timeArray4 = {time2, time3, time1};
        LocalDate date4 = getLocalDateIfValidDayMonth(timeArray4);

        int[] timeArray5 = {time3, time1, time2};
        LocalDate date5 = getLocalDateIfValidDayMonth(timeArray5);

        int[] timeArray6 = {time3, time2, time1};
        LocalDate date6 = getLocalDateIfValidDayMonth(timeArray6);

        LocalDate[] dates = new LocalDate[]{date1, date2, date3, date4, date5, date6};
        List<LocalDate> validDates = getValidDates(dates);
        return getEarliestDate(validDates);
    }

    private static LocalDate getLocalDateIfValidDayMonth(int[] timeArray) {
        if (String.valueOf(timeArray[0]).length() == 4
                || String.valueOf(timeArray[1]).length() == 4) {
            return null;
        }
        return getLocalDate(timeArray);
    }

    private static LocalDate getLocalDate(int[] timeArray) {
        String formattedDay = String.format("%02d", timeArray[0]);
        String formattedMonth = String.format("%02d", timeArray[1]);
        int year = String.valueOf(timeArray[2]).length() != 4 ? CENTURY + timeArray[2] : timeArray[2];
        String date = formattedDay + "-" + formattedMonth + "-" + year;

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
