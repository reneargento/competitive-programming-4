package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/03/22.
 */
public class WhereIsTheMarble {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numberOfMarbles = FastReader.nextInt();
        int queries = FastReader.nextInt();
        int caseId = 1;

        while (numberOfMarbles != 0 || queries != 0) {
            int[] marbles = new int[numberOfMarbles];
            for (int i = 0; i < marbles.length; i++) {
                marbles[i] = FastReader.nextInt();
            }
            Arrays.sort(marbles);

            outputWriter.printLine(String.format("CASE# %d:", caseId));
            for (int q = 0; q < queries; q++) {
                int query = FastReader.nextInt();
                int position = findMarble(marbles, query, 0, marbles.length - 1);

                if (position != -1) {
                    outputWriter.printLine(String.format("%d found at %d", query, position + 1));
                } else {
                    outputWriter.printLine(String.format("%d not found", query));
                }
            }
            caseId++;
            numberOfMarbles = FastReader.nextInt();
            queries = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int findMarble(int[] marbles, int query, int low, int high) {
        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (marbles[middle] > query) {
                high = middle - 1;
            } else if (marbles[middle] < query) {
                low = middle + 1;
            } else {
                int result = middle;
                int candidate = findMarble(marbles, query, low, middle - 1);
                if (candidate != -1) {
                    result = candidate;
                }
                return result;
            }
        }
        return -1;
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
