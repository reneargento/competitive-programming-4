package chapter5.section3.section5;

import java.util.Arrays;

/**
 * Created by Rene Argento on 11/05/25.
 */
// The modified sieve code can be used for the other functions with the help of an extra auxiliary array and
// a loop to do the number divisions, but that leads to an increase in the sieve complexity.
public class Exercise1 {

    private static final int MAX_N = 10000000;

    private static int[] numPFArr() {
        int[] primeNumbersCount = new int[MAX_N + 10];
        int[] currentNumber = new int[MAX_N + 10];
        for (int i = 0; i < currentNumber.length; i++) {
            currentNumber[i] = i;
        }

        for (int i = 2; i <= MAX_N; i++) {
            if (primeNumbersCount[i] == 0) {
                for (int j = i; j <= MAX_N; j += i) {
                    while (currentNumber[j] % i == 0) {
                        primeNumbersCount[j]++;
                        currentNumber[j] /= i;
                    }
                }
            }
        }
        return primeNumbersCount;
    }

    private static int[] sumPFArr() {
        int[] primeNumbersSum = new int[MAX_N + 10];
        int[] currentNumber = new int[MAX_N + 10];
        for (int i = 0; i < currentNumber.length; i++) {
            currentNumber[i] = i;
        }

        for (int i = 2; i <= MAX_N; i++) {
            if (primeNumbersSum[i] == 0) {
                for (int j = i; j <= MAX_N; j += i) {
                    while (currentNumber[j] % i == 0) {
                        primeNumbersSum[j] += i;
                        currentNumber[j] /= i;
                    }
                }
            }
        }
        return primeNumbersSum;
    }

    private static int[] numDivArr() {
        int[] divNumbersCount = new int[MAX_N + 10];
        Arrays.fill(divNumbersCount, 1);
        int[] currentNumber = new int[MAX_N + 10];
        for (int i = 0; i < currentNumber.length; i++) {
            currentNumber[i] = i;
        }

        for (int i = 2; i <= MAX_N; i++) {
            if (divNumbersCount[i] == 1) {
                for (int j = i; j <= MAX_N; j += i) {
                    int power = 0;
                    while (currentNumber[j] % i == 0) {
                        currentNumber[j] /= i;
                        power++;
                    }
                    divNumbersCount[j] *= power + 1;
                }
            }
        }
        return divNumbersCount;
    }

    private static int[] sumDivArr() {
        int[] divNumbersSum = new int[MAX_N + 10];
        Arrays.fill(divNumbersSum, 1);
        int[] currentNumber = new int[MAX_N + 10];
        for (int i = 0; i < currentNumber.length; i++) {
            currentNumber[i] = i;
        }

        for (int i = 2; i <= MAX_N; i++) {
            if (divNumbersSum[i] == 1) {
                for (int j = i; j <= MAX_N; j += i) {
                    long multiplier = i;
                    long total = 1;
                    while (currentNumber[j] % i == 0) {
                        currentNumber[j] /= i;
                        total += multiplier;
                        multiplier *= i;
                    }
                    divNumbersSum[j] *= total;
                }
            }
        }
        return divNumbersSum;
    }

    public static void main(String[] args) {
        int[] primeNumbersCount = numPFArr();
        System.out.println("numPFArr(60) = " + primeNumbersCount[60]);
        System.out.println("Expected: 4");

        int[] primeNumbersSum = sumPFArr();
        System.out.println("\nsumPFArr(60) = " + primeNumbersSum[60]);
        System.out.println("Expected: 12");

        int[] divNumbersCount = numDivArr();
        System.out.println("\nnumDivArr(60) = " + divNumbersCount[60]);
        System.out.println("Expected: 12");

        int[] divNumbersSum = sumDivArr();
        System.out.println("\nsumDivArr(60) = " + divNumbersSum[60]);
        System.out.println("Expected: 168");
    }
}
