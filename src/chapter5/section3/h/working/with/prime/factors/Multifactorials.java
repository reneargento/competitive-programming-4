package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/09/25.
 */
public class Multifactorials {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String line = FastReader.getLine();
            int exclamationMarkIndex = line.indexOf('!');
            int number = Integer.parseInt(line.substring(0, exclamationMarkIndex));
            int marks = line.length() - exclamationMarkIndex;

            long dividers = computeDividers(number, marks);
            outputWriter.print(String.format("Case %d: ", t));
            if (dividers == -1) {
                outputWriter.printLine("Infinity");
            } else {
                outputWriter.printLine(dividers);
            }
        }
        outputWriter.flush();
    }

    private static long computeDividers(int number, int marks) {
        int[] factorsCount = new int[1001];
        Arrays.fill(factorsCount, 1);

        for (int i = number; i >= 0; i -= marks) {
            int numberCopy = i;

            for (int j = 2; j <= i; j++) {
                while (numberCopy % j == 0) {
                    factorsCount[j]++;
                    numberCopy /= j;
                }
            }
        }

        long dividers = 1;
        double digits = 0;
        for (int i = 1; i <= number; i++) {
            if (factorsCount[i] > 1) {
                dividers *= factorsCount[i];
                digits += Math.log10(factorsCount[i]);
            }

            if (digits > 18) {
                return -1;
            }
        }
        return dividers;
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
