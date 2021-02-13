package chapter2.section2.a.oned.array.manipulation.medium;

import java.util.Scanner;

/**
 * Created by Rene Argento on 24/01/21.
 */
public class NoProblem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int initialProblems = scanner.nextInt();
        int caseNumber = 1;

        while (initialProblems >= 0) {
            int problems = initialProblems;
            int[] problemsCreated = new int[12];
            for (int i = 0; i < problemsCreated.length; i++) {
                problemsCreated[i] = scanner.nextInt();
            }

            int[] problemsRequired = new int[12];
            for (int i = 0; i < problemsRequired.length; i++) {
                problemsRequired[i] = scanner.nextInt();
            }

            System.out.printf("Case %d:\n", caseNumber++);
            for (int i = 0 ; i < problemsRequired.length; i++) {
                if (problems < problemsRequired[i]) {
                    System.out.println("No problem. :(");
                } else {
                    System.out.println("No problem! :D");
                    problems -= problemsRequired[i];
                }
                problems += problemsCreated[i];
            }
            initialProblems = scanner.nextInt();
        }
    }
}
