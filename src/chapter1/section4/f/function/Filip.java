package chapter1.section4.f.function;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class Filip {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String number1 = scanner.next();
        String number2 = scanner.next();

        StringBuilder reverseNumber1 = new StringBuilder(number1).reverse();
        StringBuilder reverseNumber2 = new StringBuilder(number2).reverse();

        int number1Reversed = Integer.parseInt(reverseNumber1.toString());
        int number2Reversed = Integer.parseInt(reverseNumber2.toString());

        if (number1Reversed > number2Reversed) {
            System.out.println(number1Reversed);
        } else {
            System.out.println(number2Reversed);
        }
    }

}
