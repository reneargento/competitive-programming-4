package chapter1.section6.i.time.harder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 22/11/20.
 */
public class DaylightSavingTime {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            char direction = scanner.next().charAt(0);
            int minutesToChange = scanner.nextInt();
            int hours = scanner.nextInt();
            int minutes = scanner.nextInt();

            LocalDateTime dateTime = getLocalDateTime(18, 4, 1989, hours, minutes, 0);

            if (direction == 'B') {
                dateTime = dateTime.minusMinutes(minutesToChange);
            } else {
                dateTime = dateTime.plusMinutes(minutesToChange);
            }

            System.out.printf("%d %d\n", dateTime.getHour(), dateTime.getMinute());
        }
    }

    private static LocalDateTime getLocalDateTime(int day, int month, int year, int hour, int minute, int second) {
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String formattedHour = String.format("%02d", hour);
        String formattedMinute = String.format("%02d", minute);
        String formattedSecond = String.format("%02d", second);

        String dateTime = String.format("%s-%s-%s %s:%s:%s", formattedDay, formattedMonth, year, formattedHour,
                formattedMinute, formattedSecond);
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }
}
