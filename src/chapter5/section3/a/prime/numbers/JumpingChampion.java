package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/05/25.
 */
public class JumpingChampion {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        Integer[] primeNumbers = eratosthenesSieve();

        for (int t = 0; t < tests; t++) {
            int lowerLimit = FastReader.nextInt();
            int upperLimit = FastReader.nextInt();

            int jumpingChampion = computeJumpingChampion(primeNumbers, lowerLimit, upperLimit);
            if (jumpingChampion == -1) {
                outputWriter.printLine("No jumping champion");
            } else {
                outputWriter.printLine("The jumping champion is " + jumpingChampion);
            }
        }
        outputWriter.flush();
    }

    private static int computeJumpingChampion(Integer[] primeNumbers, int lowerLimit, int upperLimit) {
        int startIndex = binarySearch(primeNumbers, lowerLimit, true);
        int endIndex = binarySearch(primeNumbers, upperLimit, false);

        int jumpingChampion = -1;
        int highestFrequency = 0;

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int i = startIndex + 1; i <= endIndex; i++) {
            int jump = primeNumbers[i] - primeNumbers[i - 1];

            int frequency = frequencyMap.getOrDefault(jump, 0) + 1;
            frequencyMap.put(jump, frequency);

            if (frequency > highestFrequency) {
                highestFrequency = frequency;
                jumpingChampion = jump;
            } else if (frequency == highestFrequency) {
                jumpingChampion = -1;
            }
        }
        return jumpingChampion;
    }

    private static Integer[] eratosthenesSieve() {
        long maxNumber = 1000001;
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[(int) maxNumber];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < maxNumber; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                primeNumbers.add((int) i);
            }
        }
        return primeNumbers.toArray(new Integer[0]);
    }

    private static int binarySearch(Integer[] values, int target, boolean isLowerBound) {
        int low = 0;
        int high = values.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (isLowerBound) {
                if (values[middle] < target) {
                    low = middle + 1;
                } else {
                    result = middle;
                    high = middle - 1;
                }
            } else {
                if (values[middle] > target) {
                    high = middle - 1;
                } else {
                    result = middle;
                    low = middle + 1;
                }
            }
        }
        return result;
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
