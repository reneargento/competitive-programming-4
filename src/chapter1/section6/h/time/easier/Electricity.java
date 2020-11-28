package chapter1.section6.h.time.easier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 16/11/20.
 */
public class Electricity {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int measures = scanner.nextInt();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (measures != 0) {
            LocalDate previousDate = null;
            int previousConsumption = -1;
            int days = 0;
            long totalConsumption = 0;

            for (int i = 0; i < measures; i++) {
                int day = scanner.nextInt();
                int month = scanner.nextInt();
                int year = scanner.nextInt();

                String formattedDay = getFormattedValue(day);
                String formattedMonth = getFormattedValue(month);

                String date = formattedDay + "-" + formattedMonth + "-" + year;
                int consumption = scanner.nextInt();

                LocalDate currentDate = LocalDate.parse(date, dateTimeFormatter);
                if (previousDate != null
                        && currentDate.equals(previousDate.plusDays(1))) {
                    days++;
                    totalConsumption += (consumption - previousConsumption);
                }
                previousConsumption = consumption;
                previousDate = currentDate;
            }
            System.out.printf("%d %d\n", days, totalConsumption);
            measures = scanner.nextInt();
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
