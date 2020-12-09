package chapter1.section6.m.input.parsing.iterative;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 09/12/20.
 */
public class MoliuFractions {

    private static class Fraction {
        int numerator;
        int denominator;

        public Fraction(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        Fraction multiply(Fraction otherFraction) {
            return new Fraction(numerator * otherFraction.numerator, denominator * otherFraction.denominator);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            String problem = scanner.nextLine();
            String result = getResult(problem);
            System.out.println(result);
        }
    }

    private static String getResult(String problem) {
        List<Fraction> fractions = new ArrayList<>();

        for (int i = 0; i < problem.length(); i++) {
            char symbol = problem.charAt(i);
            List<Integer> numbers = new ArrayList<>();

            if (Character.isDigit(symbol)) {
                StringBuilder currentNumber = new StringBuilder();
                currentNumber.append(symbol);

                for (int j = i + 1; j < problem.length(); j++) {
                    i++;
                    char nextSymbol = problem.charAt(j);

                    if (nextSymbol == ' ') {
                        numbers.add(Integer.parseInt(currentNumber.toString()));
                        currentNumber = new StringBuilder();
                        break;
                    } else if (nextSymbol == '/' || nextSymbol == '-') {
                        numbers.add(Integer.parseInt(currentNumber.toString()));
                        currentNumber = new StringBuilder();
                    } else {
                        currentNumber.append(nextSymbol);
                    }
                }

                if (currentNumber.length() > 0) {
                    numbers.add(Integer.parseInt(currentNumber.toString()));
                }
                fractions.add(getFraction(numbers));
            }
        }
        return multiplyFractions(fractions);
    }

    private static Fraction getFraction(List<Integer> numbers) {
        if (numbers.size() == 1) {
            return new Fraction(numbers.get(0), 1);
        }
        if (numbers.size() == 2) {
            return new Fraction(numbers.get(0), numbers.get(1));
        }
        int denominator = numbers.get(2);
        int numerator = numbers.get(0) * denominator + numbers.get(1);
        return new Fraction(numerator, denominator);
    }

    private static String multiplyFractions(List<Fraction> fractions) {
        Fraction result = fractions.get(0);

        for (int i = 1; i < fractions.size(); i++) {
            result = result.multiply(fractions.get(i));
            result = reduceFraction(result);
        }

        int finalNumerator;
        String finalResult = "";

        if (result.numerator > result.denominator) {
            int mixedFractionStart = result.numerator / result.denominator;
            finalNumerator = result.numerator % result.denominator;
            finalResult += String.valueOf(mixedFractionStart);

            if (finalNumerator == 0) {
                return finalResult;
            } else {
                finalResult += "-";
            }
        } else {
            finalNumerator = result.numerator;
        }

        finalResult += finalNumerator;

        if (result.denominator != 1) {
            finalResult += "/" + result.denominator;
        }
        return finalResult;
    }

    private static Fraction reduceFraction(Fraction fraction) {
        int gcd = gcd(fraction.numerator, fraction.denominator);
        return new Fraction(fraction.numerator / gcd, fraction.denominator / gcd);
    }

    private static int gcd(int number1, int number2) {
        if (number2 == 0) {
            return number1;
        }
        return gcd(number2, number1 % number2);
    }
}
