package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/11/21.
 */
public class Blocks {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int blocks = FastReader.nextInt();
            List<Long> factors = getFactors(blocks);

            long minimalPaper = getMinimalPaper(blocks, factors);
            outputWriter.printLine(minimalPaper);
        }
        outputWriter.flush();
    }

    private static List<Long> getFactors(long number) {
        List<Long> factors = new ArrayList<>();
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

    private static long getMinimalPaper(int blocks, List<Long> factors) {
        long minimalPaper = Integer.MAX_VALUE;

        for (long factor1 : factors) {
            for (long factor2 : factors) {
                for (long factor3 : factors) {
                    if (factor1 * factor2 * factor3 == blocks) {
                        long paper = (factor1 * factor2) * 2 + (factor2 * factor3) * 2
                                + (factor3 * factor1) * 2;
                        minimalPaper = Math.min(minimalPaper, paper);
                    }
                }
            }
        }
        return minimalPaper;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
