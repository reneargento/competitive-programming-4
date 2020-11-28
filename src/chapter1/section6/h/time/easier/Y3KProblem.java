package chapter1.section6.h.time.easier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 16/11/20.
 */
public class Y3KProblem {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int daysToPredict = scanner.nextInt();
        int day = scanner.nextInt();
        int month = scanner.nextInt();
        int year = scanner.nextInt();

        while (day != 0) {
            LocalDate date = getLocalDate(day, month, year);
            LocalDate futureDate = date.plusDays(daysToPredict);

            System.out.printf("%d %d %d\n", futureDate.getDayOfMonth(), futureDate.getMonthValue(), futureDate.getYear());

            daysToPredict = scanner.nextInt();
            day = scanner.nextInt();
            month = scanner.nextInt();
            year = scanner.nextInt();
        }
    }

    private static LocalDate getLocalDate(int day, int month, int year) {
        String formattedDay = getFormattedValue(day);
        String formattedMonth = getFormattedValue(month);
        String date = formattedDay + "-" + formattedMonth + "-" + year;
        return LocalDate.parse(date, dateTimeFormatter);
    }

    private static String getFormattedValue(int value) {
        String formattedValue = String.valueOf(value);
        if (formattedValue.length() == 1) {
            formattedValue = "0" + formattedValue;
        }
        return formattedValue;
    }
}
