package chapter5.section3.f.gcd.and.or.lcm;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/08/25.
 */
public class MaximumGCD {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] data = FastReader.getLine().split(" ");
            int[] values = new int[data.length];
            for (int i = 0; i < data.length; i++) {
                values[i] = Integer.parseInt(data[i]);
            }

            long maximumGCD = computeMaximumGCD(values);
            outputWriter.printLine(maximumGCD);
        }
        outputWriter.flush();
    }

    private static long computeMaximumGCD(int[] values) {
        long maximumGCD = 0;
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                long gcd = gcd(values[i], values[j]);
                maximumGCD = Math.max(maximumGCD, gcd);
            }
        }
        return maximumGCD;
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
