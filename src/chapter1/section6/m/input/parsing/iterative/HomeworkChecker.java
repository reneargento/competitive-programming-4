package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 02/12/20.
 */
public class HomeworkChecker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int correctAnswers = 0;

        while (scanner.hasNext()) {
            String equation = scanner.next();

            if (equation.contains("?")) {
                continue;
            }

            String[] mainSections = equation.split("=");
            int result = Integer.parseInt(mainSections[1]);

            boolean isAddition = equation.contains("+");
            if (isAddition) {
                String[] values = mainSections[0].split("\\+");
                int value1 = Integer.parseInt(values[0]);
                int value2 = Integer.parseInt(values[1]);
                if (value1 + value2 == result) {
                    correctAnswers++;
                }
            } else {
                String[] values = mainSections[0].split("-");
                int value1 = Integer.parseInt(values[0]);
                int value2 = Integer.parseInt(values[1]);
                if (value1 - value2 == result) {
                    correctAnswers++;
                }
            }
        }
        System.out.println(correctAnswers);
    }
}
