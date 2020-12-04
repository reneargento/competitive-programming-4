package chapter1.section6.m.input.parsing.iterative;

import java.util.Scanner;

/**
 * Created by Rene Argento on 03/12/20.
 */
public class ADPProblem {

    private static class PartialResult {
        int x;
        int sum;

        public PartialResult(int x, int sum) {
            this.x = x;
            this.sum = sum;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 0; t < tests; t++) {
            String equation = scanner.next();
            System.out.println(solve(equation));
        }
    }

    private static String solve(String equation) {
        int equalIndex = equation.indexOf("=");
        PartialResult partialResult1 = solve(equation, 0, equalIndex, 1, -1);
        PartialResult partialResult2 = solve(equation, equalIndex + 1, equation.length(), -1, 1);

        double x = partialResult1.x + partialResult2.x;
        int sum = partialResult1.sum + partialResult2.sum;

        if (x == 0) {
            if (sum == 0) {
                return "IDENTITY";
            } else {
                return "IMPOSSIBLE";
            }
        }

        int solution = (int) Math.floor(sum / x);
        return String.valueOf(solution);
    }

    private static PartialResult solve(String equation, int startIndex, int endIndex, int xMultiplier, int valueMultiplier) {
        int x = 0;
        int sum = 0;

        StringBuilder number = new StringBuilder();
        int isNegativeMultiplier = 1;

        for (int i = startIndex; i < endIndex; i++) {
            char symbol = equation.charAt(i);
            if (symbol == 'x') {
                int xValue = 1;
                if (number.length() > 0) {
                    xValue = Integer.parseInt(number.toString());
                }
                x += (xValue * isNegativeMultiplier * xMultiplier);
                number = new StringBuilder();
            } else if (symbol == '-' || symbol == '+' || i == endIndex - 1) {
                if (i == endIndex - 1) {
                    number.append(symbol);
                }

                if (number.length() > 0) {
                    int value = Integer.parseInt(number.toString());
                    value *= isNegativeMultiplier * valueMultiplier;
                    sum += value;
                    number = new StringBuilder();
                }

                if (symbol == '-') {
                    isNegativeMultiplier = -1;
                } else {
                    isNegativeMultiplier = 1;
                }
            } else {
                number.append(symbol);
            }
        }
        return new PartialResult(x, sum);
    }
}
