package chapter1.section4.f.function;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class SummingDigits {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long number = scanner.nextLong();

        while (number != 0) {
            String numberString = String.valueOf(number);

            while (numberString.length() > 1) {
                number = 0;

                for (int i = 0; i < numberString.length(); i++) {
                    number += Integer.parseInt(numberString.charAt(i) + "");
                }

                numberString = String.valueOf(number);
            }
            System.out.println(numberString);

            number = scanner.nextLong();
        }
    }

}
