package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/09/21.
 */
public class ClosestSumsKattis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String integersLine = FastReader.getLine();
        int caseNumber = 1;

        while (integersLine != null) {
            int[] integers = new int[Integer.parseInt(integersLine)];
            for (int i = 0; i < integers.length; i++) {
                integers[i] = FastReader.nextInt();
            }

            outputWriter.printLine(String.format("Case %d:", caseNumber));

            int queries = FastReader.nextInt();
            for (int i = 0; i < queries; i++) {
                int query = FastReader.nextInt();
                long closestSum = getClosestSum(integers, query);
                outputWriter.printLine(String.format("Closest sum to %d is %d.", query, closestSum));
            }
            integersLine = FastReader.getLine();
            caseNumber++;
        }
        outputWriter.flush();
    }

    private static long getClosestSum(int[] integers, int query) {
        long closestSum = Integer.MAX_VALUE;
        long closestDistance = Integer.MAX_VALUE;

        for (int i = 0; i < integers.length; i++) {
            for (int j = 0; j < integers.length; j++) {
                if (i == j) {
                    continue;
                }
                long sum = integers[i] + integers[j];
                long distance = Math.abs(query - sum);

                if (closestDistance == Integer.MAX_VALUE || distance < closestDistance) {
                    closestDistance = distance;
                    closestSum = sum;
                }
            }
        }
        return closestSum;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
