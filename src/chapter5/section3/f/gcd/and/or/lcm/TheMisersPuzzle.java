package chapter5.section3.f.gcd.and.or.lcm;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/08/25.
 */
public class TheMisersPuzzle {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] values = new int[FastReader.nextInt()];
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.nextInt();
            }

            long minimumGold = computeMinimumGold(values);
            outputWriter.printLine(String.format("Case %d: %d", t, minimumGold));
        }
        outputWriter.flush();
    }

    private static long computeMinimumGold(int[] values) {
        long lcm = 1;
        for (int value : values) {
            lcm = lcm(lcm, value);
        }
        return lcm * 35;
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    public static long lcm(long number1, long number2) {
        return number1 * (number2 / gcd(number1, number2));
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
