package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/09/25.
 */
public class Iks {

    private static class Result {
        int maxScore;
        int minimumOperations;

        public Result(int maxScore, int minimumOperations) {
            this.maxScore = maxScore;
            this.minimumOperations = minimumOperations;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] numbers = new int[FastReader.nextInt()];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = FastReader.nextInt();
        }

        Result result = computeMaxScore(numbers);
        outputWriter.printLine(result.maxScore + " " + result.minimumOperations);
        outputWriter.flush();
    }

    private static Result computeMaxScore(int[] numbers) {
        Map<Integer, Integer> primesFrequency = new HashMap<>();
        Map<Integer, Integer>[] primeFactorsPerNumber = computePrimeFactorsPerNumber(numbers, primesFrequency);

        double maxScore = 1;
        int minimumOperations = 0;

        for (int prime : primesFrequency.keySet()) {
            int targetFrequency = primesFrequency.get(prime) / numbers.length;
            maxScore *= Math.pow(prime, targetFrequency);

            for (int i = 0; i < numbers.length; i++) {
                int frequency = 0;
                if (primeFactorsPerNumber[i].containsKey(prime)) {
                    frequency = primeFactorsPerNumber[i].get(prime);
                }
                int operationsRequired = targetFrequency - frequency;
                if (operationsRequired > 0) {
                    minimumOperations += operationsRequired;
                }
            }
        }
        return new Result((int) maxScore, minimumOperations);
    }

    @SuppressWarnings("unchecked")
    private static Map<Integer, Integer>[] computePrimeFactorsPerNumber(int[] numbers,
                                                                            Map<Integer, Integer> primesFrequency) {
        Map<Integer, Integer>[] primeFactorsPerNumber = new Map[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            int number = numbers[i];
            primeFactorsPerNumber[i] = primeFactors(number, primesFrequency);
        }
        return primeFactorsPerNumber;
    }

    private static Map<Integer, Integer> primeFactors(int number, Map<Integer, Integer> primesFrequency) {
        Map<Integer, Integer> primeFactors = new HashMap<>();

        for (int i = 2; i * i <= number; i++) {
            int frequency = 0;

            while (number % i == 0) {
                frequency++;
                number /= i;
            }
            if (frequency > 0) {
                updateMapFrequency(primeFactors, i, frequency);
                updateMapFrequency(primesFrequency, i, frequency);
            }
        }

        if (number > 1) {
            primeFactors.put(number, 1);
            updateMapFrequency(primeFactors, number, 1);
            updateMapFrequency(primesFrequency, number, 1);
        }
        return primeFactors;
    }

    private static void updateMapFrequency(Map<Integer, Integer> map, int key, int frequency) {
        if (!map.containsKey(key)) {
            map.put(key, 0);
        }
        int newFrequency = map.get(key) + frequency;
        map.put(key, newFrequency);
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
