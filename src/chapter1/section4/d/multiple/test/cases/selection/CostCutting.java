package chapter1.section4.d.multiple.test.cases.selection;

import java.util.Scanner;

/**
 * Created by Rene Argento on 04/09/20.
 */
public class CostCutting {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int test = scanner.nextInt();

        for (int i = 1; i <= test; i++) {
            int salary1 = scanner.nextInt();
            int salary2 = scanner.nextInt();
            int salary3 = scanner.nextInt();

            int salaryThatSurvives = salary1;
            if ((salary1 <= salary2 && salary2 <= salary3)
                    || (salary3 <= salary2 && salary2 <= salary1)) {
                salaryThatSurvives = salary2;
            } else if ((salary1 <= salary3 && salary3 <= salary2)
                    || (salary2 <= salary3 && salary3 <= salary1)) {
                salaryThatSurvives = salary3;
            }

            System.out.println("Case " + i + ": " + salaryThatSurvives);
        }
    }

}
