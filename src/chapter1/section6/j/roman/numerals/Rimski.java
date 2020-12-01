package chapter1.section6.j.roman.numerals;

import java.util.Scanner;

/**
 * Created by Rene Argento on 24/11/20.
 */
public class Rimski {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String romanNumerals = scanner.next();
        int length = romanNumerals.length();

        if (length >= 2) {
            if (romanNumerals.substring(0, 2).equals("LX")
                    && (length == 2 || romanNumerals.charAt(2) != 'X' || (length >= 4 && romanNumerals.charAt(3) == 'I'))) {
                romanNumerals = "XL" + romanNumerals.substring(2);
            }

            if (romanNumerals.substring(length - 2).equals("XI")) {
                romanNumerals = romanNumerals.substring(0, length - 2) + "IX";
            } else if (romanNumerals.substring(length - 2).equals("VI")) {
                romanNumerals = romanNumerals.substring(0, length - 2) + "IV";
            }
        }
        System.out.println(romanNumerals);
    }
}
