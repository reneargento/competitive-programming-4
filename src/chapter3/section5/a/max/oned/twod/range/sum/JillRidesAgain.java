package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/09/22.
 */
public class JillRidesAgain {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int routes = FastReader.nextInt();

        for (int r = 1; r <= routes; r++) {
            int roads = FastReader.nextInt() - 1;
            long nicestSum = 0;
            long currentSum = 0;
            int nicestPartStart = 1;
            int nicestPartEnd = 1;
            int nicestPartLongestLength = 0;
            int currentStart = 1;

            for (int i = 1; i <= roads; i++) {
                currentSum += FastReader.nextInt();
                if (currentSum > nicestSum
                        || (currentSum == nicestSum && i - currentStart + 1 > nicestPartLongestLength)) {
                    nicestSum = currentSum;
                    nicestPartStart = currentStart;
                    nicestPartEnd = i;
                    nicestPartLongestLength = nicestPartEnd - nicestPartStart + 1;
                }

                if (currentSum < 0) {
                    currentSum = 0;
                    currentStart = i + 1;
                }
            }

            if (nicestSum <= 0) {
                outputWriter.printLine(String.format("Route %d has no nice parts", r));
            } else {
                outputWriter.printLine(String.format("The nicest part of route %d is between stops %d and %d", r,
                        nicestPartStart, nicestPartEnd + 1));
            }
        }
        outputWriter.flush();
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