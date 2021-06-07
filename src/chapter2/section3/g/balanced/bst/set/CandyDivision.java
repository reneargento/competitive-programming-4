package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/06/21.
 */
public class CandyDivision {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long candies = FastReader.nextLong();
        TreeSet<Long> factors = getFactors(candies);
        int factorIndex = 0;

        for (Long factor : factors) {
            outputWriter.print(factor - 1);

            if (factorIndex != factors.size() - 1) {
                outputWriter.print(" ");
            }
            factorIndex++;
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static TreeSet<Long> getFactors(long number) {
        TreeSet<Long> factors = new TreeSet<>();
        int upperLimit = (int) Math.sqrt(number);

        for(int i = 1; i <= upperLimit; i++) {
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
