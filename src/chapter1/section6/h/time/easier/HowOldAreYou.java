package chapter1.section6.h.time.easier;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Created by Rene Argento on 16/11/20.
 */
public class HowOldAreYou {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            LocalDate dateEnd = LocalDate.parse(scanner.next(), dateTimeFormatter);
            LocalDate dateStart = LocalDate.parse(scanner.next(), dateTimeFormatter);

            System.out.printf("Case #%d: ", t);

            if (dateEnd.isBefore(dateStart)) {
                System.out.println("Invalid birth date");
            } else {
                Period period = Period.between(dateStart, dateEnd);
                int years = period.getYears();
                if (years > 130) {
                    System.out.println("Check birth date");
                } else {
                    System.out.println(years);
                }
            }
        }
    }
}
