package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/07/25.
 */
public class ReachingTheFixPoint {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] sopfs = computeSopfs();

        for (int t = 1; t <= tests; t++) {
            int value1 = FastReader.nextInt();
            int value2 = FastReader.nextInt();

            int highestSopf = getHighestSopf(sopfs, value1, value2);
            outputWriter.printLine(String.format("Case #%d:", t));
            outputWriter.printLine(highestSopf);
        }
        outputWriter.flush();
    }

    private static int getHighestSopf(int[] sopfs, int value1, int value2) {
        int highestSopf = 0;
        int start = Math.min(value1, value2);
        int end = Math.max(value1, value2);

        for (int i = start; i <= end; i++) {
            if (sopfs[i] > highestSopf) {
                highestSopf = sopfs[i];
            }
        }
        return highestSopf;
    }

    private static int[] computeSopfs() {
        int[] sopfs = new int[500001];

        for (int number = 2; number < sopfs.length; number++) {
            int numberCopy = number;
            int primeFactorsSum = 0;
            int iterations = 0;
            boolean updated = false;

            while (true) {
                if (sopfs[primeFactorsSum] != 0) {
                    sopfs[number] = iterations + sopfs[primeFactorsSum];
                    updated = true;
                    break;
                }

                iterations++;
                primeFactorsSum = computePrimeFactorsSum(numberCopy);
                if (primeFactorsSum == numberCopy) {
                    break;
                }
                numberCopy = primeFactorsSum;
            }
            if (!updated) {
                sopfs[number] = iterations;
            }
        }
        return sopfs;
    }

    private static int computePrimeFactorsSum(int number) {
        int primeFactorsSum = 0;

        for (int i = 2; i * i <= number; i++) {
            while (number % i == 0) {
                primeFactorsSum += i;
                number /= i;
            }
        }

        if (number > 1) {
            primeFactorsSum += number;
        }
        return primeFactorsSum;
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
