package chapter5.section3.f.gcd.and.or.lcm;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/08/25.
 */
public class Pi {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dataSetSize = FastReader.nextInt();

        while (dataSetSize != 0) {
            int[] numbers = new int[dataSetSize];
            for (int i = 0; i < dataSetSize; i++) {
                numbers[i] = FastReader.nextInt();
            }

            double estimate = computeEstimate(numbers);
            if (estimate == -1) {
                outputWriter.printLine("No estimate for this data set.");
            } else {
                outputWriter.printLine(String.format("%.6f", estimate));
            }
            dataSetSize = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeEstimate(int[] numbers) {
        double noCommonFactorPairs = 0;
        int pairs = (numbers.length * (numbers.length - 1)) / 2;

        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (gcd(numbers[i], numbers[j]) == 1) {
                    noCommonFactorPairs++;
                }
            }
        }

        if (noCommonFactorPairs == 0) {
            return -1;
        }
        double piSquared = (6 * pairs) / noCommonFactorPairs;
        return Math.sqrt(piSquared);
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
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
