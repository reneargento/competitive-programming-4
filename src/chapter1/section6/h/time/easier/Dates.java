package chapter1.section6.h.time.easier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 17/11/20.
 */
public class Dates {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            LocalDate date = LocalDate.parse(scanner.next(), dateTimeFormatter);
            int days = scanner.nextInt();
            LocalDate futureDate = date.plusDays(days);
            System.out.printf("Case %d: %s\n", t, futureDate.format(dateTimeFormatter));
        }
    }
}
