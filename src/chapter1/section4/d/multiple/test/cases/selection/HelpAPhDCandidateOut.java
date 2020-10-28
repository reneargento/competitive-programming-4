package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class HelpAPhDCandidateOut {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int i = 0; i < tests; i++) {
            String question = scanner.next();
            if (question.equals("P=NP")) {
                System.out.println("skipped");
            } else {
                String[] numbers = question.split("\\+");
                int number1 = Integer.parseInt(numbers[0]);
                int number2 = Integer.parseInt(numbers[1]);
                System.out.println((number1 + number2));
            }
        }
    }

}
