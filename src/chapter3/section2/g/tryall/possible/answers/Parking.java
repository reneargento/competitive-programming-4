package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/12/21.
 */
public class Parking {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] stores = new int[FastReader.nextInt()];

            for (int i = 0; i < stores.length; i++) {
                stores[i] = FastReader.nextInt();
            }
            Arrays.sort(stores);

            int minimalDistance = computeMinimalDistance(stores);
            outputWriter.printLine(minimalDistance);
        }
        outputWriter.flush();
    }

    private static int computeMinimalDistance(int[] stores) {
        int minimalDistance = Integer.MAX_VALUE;

        for (int position = stores[0]; position <= stores[stores.length - 1]; position++) {
            int distance = computeDistance(stores, position);
            minimalDistance = Math.min(minimalDistance, distance);
        }
        return minimalDistance;
    }

    private static int computeDistance(int[] stores, int position) {
        int distance = 0;
        int previousPosition = position;

        for (int store : stores) {
            distance += Math.abs(previousPosition - store);
            previousPosition = store;
        }

        return distance * 2;
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
