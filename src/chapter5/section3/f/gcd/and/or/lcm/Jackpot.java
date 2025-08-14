package chapter5.section3.f.gcd.and.or.lcm;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/08/25.
 */
public class Jackpot {

    private static final int MAX_PERIODICITY = 1000000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int machines = FastReader.nextInt();

        for (int m = 0; m < machines; m++) {
            int wheels = FastReader.nextInt();
            long lcm = 1;

            for (int w = 0; w < wheels; w++) {
                int periodicity = FastReader.nextInt();
                lcm = lcm(lcm, periodicity);
            }

            if (lcm > MAX_PERIODICITY) {
                outputWriter.printLine("More than a billion.");
            } else {
                outputWriter.printLine(lcm);
            }
        }
        outputWriter.flush();
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static long lcm(long number1, long number2) {
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
