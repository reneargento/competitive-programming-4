package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/05/25.
 */
public class AnagrammaticPrimes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Integer[] primeNumbers = eratosthenesSieve();

        int number = FastReader.nextInt();
        while (number != 0) {
            int nextAnagrammaticPrime = getNextAnagrammaticPrime(primeNumbers, number);
            outputWriter.printLine(nextAnagrammaticPrime);
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int getNextAnagrammaticPrime(Integer[] primeNumbers, int number) {
        int startIndex = binarySearch(primeNumbers, number);
        String numberString = String.valueOf(number);
        int maxValue = (int) (Math.pow(10, numberString.length())) - 1;

        for (int i = startIndex; i < primeNumbers.length && primeNumbers[i] <= maxValue; i++) {
            if (isAnagrammaticPrime(primeNumbers[i])) {
                return primeNumbers[i];
            }
        }
        return 0;
    }

    private static boolean isAnagrammaticPrime(int number) {
        if (!isPrime[number] && !isValid(number)) {
            return false;
        }
        String numberString = String.valueOf(number);
        char[] digitValues = numberString.toCharArray();
        int[] digits = new int[numberString.length()];
        for (int i = 0; i < digits.length; i++) {
            digits[i] = Character.getNumericValue(digitValues[i]);
        }
        Arrays.sort(digits);

        while (digits != null) {
            int numberValue = getNumberFromDigits(digits);
            if (!isPrime[numberValue]) {
                return false;
            }
            digits = nextPermutation(digits);
        }
        return true;
    }

    private static int getNumberFromDigits(int[] digits) {
        int number = 0;
        int multiplier = 1;

        for (int i = digits.length - 1; i >= 0; i--) {
            number += digits[i] * multiplier;
            multiplier *= 10;
        }
        return number;
    }

    private static int[] nextPermutation(int[] permutation) {
        int first = getFirstIndexToSwap(permutation);
        if (first == -1) {
            return null; // no greater permutation
        }

        int toSwap = permutation.length - 1;
        while (permutation[first] >= permutation[toSwap]) {
            toSwap--;
        }

        swap(permutation, first++, toSwap);

        toSwap = permutation.length - 1;
        while (first < toSwap) {
            swap(permutation, first++, toSwap--);
        }
        return permutation;
    }

    private static int getFirstIndexToSwap(int[] permutation ) {
        for (int i = permutation.length - 2; i >= 0; --i) {
            if (permutation[i] < permutation[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    private static void swap(int[] permutation, int index1, int index2) {
        int temp = permutation[index1];
        permutation[index1] = permutation[index2];
        permutation[index2] = temp;
    }

    private static boolean isValid(int number) {
        if (number > 1000) {
            return false;
        }
        while (number > 0) {
            int digit = number % 10;
            number /= 10;
            if (digit == 0
                    || digit == 2
                    || digit == 4
                    || digit == 5
                    || digit == 6
                    || digit == 8) {
                return false;
            }
        }
        return true;
    }

    private static int binarySearch(Integer[] values, int target) {
        int low = 0;
        int high = values.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (values[middle] <= target) {
                low = middle + 1;
            } else {
                result = middle;
                high = middle - 1;
            }
        }
        return result;
    }

    private static boolean[] isPrime;

    private static Integer[] eratosthenesSieve() {
        int maxNumber = 10000000;
        List<Integer> primeNumbers = new ArrayList<>();
        isPrime = new boolean[maxNumber + 1];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i <= maxNumber; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                primeNumbers.add((int) i);
            }
        }
        return primeNumbers.toArray(new Integer[0]);
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
