package chapter5.section3.k.divisibility.test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/10/25.
 */
public class TheMagical3 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int number = FastReader.nextInt();
        while (number != 0) {
            long smallestBaseWith3 = computeSmallestBaseWith3(number);
            if (smallestBaseWith3 == -1) {
                outputWriter.printLine("No such base");
            } else {
                outputWriter.printLine(smallestBaseWith3);
            }
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeSmallestBaseWith3(int number) {
        if (number < 3) {
            return -1;
        }
        if (number == 3) {
            return 4;
        }
        List<Long> factors = getFactors(number - 3);
        Collections.sort(factors);
        for (long factor : factors) {
            if (number % factor == 3) {
                return factor;
            }
        }
        return -1;
    }

    private static List<Long> getFactors(long number) {
        List<Long> factors = new ArrayList<>();
        int upperLimit = (int) Math.sqrt(number);

        for (int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                factors.add((long) i);

                if (i != number / i) {
                    factors.add(number / i);
                }
            }
        }
        return factors;
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
