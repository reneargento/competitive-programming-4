package chapter1.section6.f.interesting.real.life.problems.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 07/11/20.
 */
public class BabyMe {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 1; t <= tests; t++) {
            double weight = 0;
            String data = scanner.nextLine();
            int firstPartEnd = getFirstIndexOfNonCharacter(data, 0);

            int first = Integer.parseInt(data.substring(0, firstPartEnd));
            weight += first * 0.5;

            int secondPartStart = getFirstIndexOfCharacter(data, firstPartEnd + 1);

            if (secondPartStart != -1) {
                int secondPartEnd = getFirstIndexOfNonCharacter(data, secondPartStart);
                int second = Integer.parseInt(data.substring(secondPartStart, secondPartEnd));
                weight += second * 0.05;
            }
            System.out.printf("Case %d: %s\n", t, getFormattedNumber(weight));
        }
    }

    private static int getFirstIndexOfNonCharacter(String data, int startIndex) {
        for (int i = startIndex; i < data.length(); i++) {
            if (!Character.isDigit(data.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    private static int getFirstIndexOfCharacter(String data, int startIndex) {
        for (int i = startIndex; i < data.length(); i++) {
            if (Character.isDigit(data.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    private static String getFormattedNumber(double number) {
        String numberString = String.valueOf(number);
        if (!numberString.contains(".")) {
            return numberString;
        }

        int lastIndex = -1;
        for (int i = numberString.length() - 1; i >= 0; i--) {
            if (numberString.charAt(i) == '.') {
                lastIndex = i - 1;
                break;
            }
            if (numberString.charAt(i) != '0') {
                lastIndex = i;
                break;
            }
        }
        return numberString.substring(0, lastIndex + 1);
    }
}
