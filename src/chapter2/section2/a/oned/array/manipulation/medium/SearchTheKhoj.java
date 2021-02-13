package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 25/01/21.
 */
public class SearchTheKhoj {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int numbersCount = scanner.nextInt();
            String[] numbers = new String[numbersCount];

            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = scanner.next();
            }

            String recalledNumber = scanner.next();

            List<String> possibleMatches = new ArrayList<>();
            for (String number : numbers) {
                if (isPossibleMatch(number, recalledNumber)) {
                    possibleMatches.add(number);
                }
            }

            System.out.printf("Case %d:\n", t);
            for (String possibleMatch : possibleMatches) {
                System.out.println(possibleMatch);
            }
        }
    }

    private static boolean isPossibleMatch(String number1, String number2) {
        int differences = 0;
        for (int i = 0; i < number1.length(); i++) {
            if (number1.charAt(i) != number2.charAt(i)) {
                differences++;
            }
        }
        return differences <= 1;
    }
}
