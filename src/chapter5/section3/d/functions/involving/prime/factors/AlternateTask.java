package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/07/25.
 */
public class AlternateTask {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] factorsSum = getFactorsSum();
        int caseId = 1;

        int number = FastReader.nextInt();
        while (number != 0) {
            outputWriter.printLine(String.format("Case %d: %d", caseId, factorsSum[number]));
            caseId++;
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[] getFactorsSum() {
        int[] factorsSum = new int[1001];
        Arrays.fill(factorsSum, -1);

        for (int number = 1; number < factorsSum.length; number++) {
            int factorSum = getFactorSum(number);
            if (factorSum < factorsSum.length) {
                factorsSum[factorSum] = number;
            }
        }
        return factorsSum;
    }

    private static int getFactorSum(int number) {
        int factorSum = 0;
        int upperLimit = (int) Math.sqrt(number);

        for (int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                factorSum += i;

                if (i != number / i) {
                    factorSum += number / i;
                }
            }
        }
        return factorSum;
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
