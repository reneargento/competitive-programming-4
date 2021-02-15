package chapter2.section2.b.oned.array.manipulation.harder;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/01/21.
 */
public class DivideBy100 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String number = scanner.next();
        String specialNumber = scanner.next();
        StringBuilder dividedNumber = new StringBuilder();

        if (specialNumber.length() > number.length()) {
            dividedNumber.append("0.");

            int numberOfZeroesAfterDecimal = specialNumber.length() - number.length() - 1;
            for (int i = 0; i < numberOfZeroesAfterDecimal; i++) {
                dividedNumber.append("0");
            }

            dividedNumber.append(number);
        } else {
            int decimalIndex = number.length() - (specialNumber.length() - 1);
            dividedNumber.append(number.substring(0, decimalIndex)).append(".").append(number.substring(decimalIndex));
        }
        System.out.println(removeTrailingZeroes(dividedNumber.toString()));
    }

    private static String removeTrailingZeroes(String number) {
        int endIndex = 1;
        for (int i = number.length() - 1; i >= 0; i--) {
            if (number.charAt(i) != '0') {
                endIndex = number.charAt(i) != '.' ? i + 1 : i;
                break;
            }
        }
        return number.substring(0, endIndex);
    }
}
