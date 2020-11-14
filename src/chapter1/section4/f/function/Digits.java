package chapter1.section4.f.function;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Digits {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String number = scanner.next();

        while (!number.equals("END")) {
            long digitsSizePrevious;
            int index = 1;

            if (number.length() > 10) {
                digitsSizePrevious = number.length();
                index = 2;
            } else {
                digitsSizePrevious = Long.parseLong(number);
            }

            while (true) {
                number = String.valueOf(digitsSizePrevious);
                int digitSize = number.length();

                if (digitSize == digitsSizePrevious) {
                    System.out.println(index);
                    break;
                }
                digitsSizePrevious = digitSize;
                index++;
            }
            number = scanner.next();
        }
    }

}
