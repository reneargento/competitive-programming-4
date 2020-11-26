package chapter1.section6.g.interesting.real.life.problems.harder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 10/11/20.
 */
public class RecognizingGoodISBNs {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String candidateISBN = scanner.nextLine().trim();
            List<Integer> digits = getDigits(candidateISBN);
            System.out.print(candidateISBN + " is ");
            if (digits != null && isCorrect(digits)) {
                System.out.println("correct.");
            } else {
                System.out.println("incorrect.");
            }
        }
    }

    private static List<Integer> getDigits(String candidateISBN) {
        List<Integer> digits = new ArrayList<>();
        candidateISBN = candidateISBN.replaceAll("-", "");

        for (int i = 0; i < candidateISBN.length(); i++) {
            char character = candidateISBN.charAt(i);

            if (Character.isDigit(character)) {
                digits.add(Character.getNumericValue(character));
            } else if (character == 'X') {
                if (i == candidateISBN.length() - 1) {
                    digits.add(10);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        if (digits.size() == 10) {
            return digits;
        } else {
            return null;
        }
    }

    private static boolean isCorrect(List<Integer> digits) {
        int s1 = 0;
        int s2 = 0;

        for (int digit : digits) {
            s1 += digit;
            s2 += s1;
        }
        return s2 % 11 == 0;
    }
}
