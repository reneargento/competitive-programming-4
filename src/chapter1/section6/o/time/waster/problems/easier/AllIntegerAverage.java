package chapter1.section6.o.time.waster.problems.easier;

import java.util.Scanner;

/**
 * Created by Rene Argento on 23/12/20.
 */
public class AllIntegerAverage {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int integers = scanner.nextInt();
        int caseId = 1;

        while (integers != 0) {
            System.out.printf("Case %d:\n", caseId);
            int sum = 0;
            for (int i = 0; i < integers; i++) {
                sum += scanner.nextInt();
            }
            int divisionResult = sum / integers;
            int remaining = sum % integers;

            if (remaining == 0) {
                printNumber(divisionResult);
                System.out.println();
            } else {
                boolean isNegative = remaining < 0;
                if (isNegative) {
                    remaining *= -1;
                }

                int gcd = gcd(remaining, integers);
                remaining /= gcd;
                integers /= gcd;

                int horizontalBarLength = String.valueOf(integers).length();
                int divisionResultLength = divisionResult != 0 ? String.valueOf(divisionResult).length() : 0;
                int initialPadding = horizontalBarLength + divisionResultLength;

                if (divisionResult < 0) {
                    initialPadding++;
                } else if (divisionResult == 0 && isNegative) {
                    initialPadding += 2;
                }

                System.out.printf("%" + initialPadding + "d\n", remaining);

                if (divisionResult != 0) {
                    printNumber(divisionResult);
                } else if (isNegative) {
                    System.out.print("- ");
                }
                for (int i = 0; i < horizontalBarLength; i++) {
                    System.out.print("-");
                }
                System.out.println();

                System.out.printf("%" + initialPadding + "d\n", integers);
            }

            caseId++;
            integers = scanner.nextInt();
        }
    }

    private static int gcd(int number1, int number2) {
        while (number2 > 0) {
            int temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static void printNumber(int number) {
        if (number < 0) {
            System.out.printf("- %d", -number);
        } else {
            System.out.print(number);
        }
    }
}
