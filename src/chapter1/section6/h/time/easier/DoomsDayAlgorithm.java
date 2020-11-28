package chapter1.section6.h.time.easier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 17/11/20.
 */
public class DoomsDayAlgorithm {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            int month = scanner.nextInt();
            int day = scanner.nextInt();
            String dateString = getFormattedValue(day) + "-" + getFormattedValue(month) + "-2011";
            LocalDate date = LocalDate.parse(dateString, dateTimeFormatter);
            String dayOfWeekString = String.valueOf(date.getDayOfWeek());
            String dayOfWeek = dayOfWeekString.charAt(0) + dayOfWeekString.substring(1).toLowerCase();
            System.out.println(dayOfWeek);
        }
    }

    private static String getFormattedValue(int value) {
        String formattedValue = String.valueOf(value);
        if (formattedValue.length() == 1) {
            formattedValue = "0" + formattedValue;
        }
        return formattedValue;
    }
}
