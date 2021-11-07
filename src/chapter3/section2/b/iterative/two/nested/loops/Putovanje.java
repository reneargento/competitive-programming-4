package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/11/21.
 */
public class Putovanje {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] weights = new int[FastReader.nextInt()];
        int capacity = FastReader.nextInt();

        for (int i = 0; i < weights.length; i++) {
            weights[i] = FastReader.nextInt();
        }
        int maxFruits = computeMaxPossibleFruits(weights, capacity);
        outputWriter.printLine(maxFruits);

        outputWriter.flush();
    }

    private static int computeMaxPossibleFruits(int[] weights, int capacity) {
        int maxFruits = 0;

        for (int i = 0; i < weights.length; i++) {
            int currentFruits = 0;
            int currentWeight = 0;

            for (int j = i; j < weights.length; j++) {
                if (currentWeight + weights[j] > capacity) {
                    continue;
                }
                currentFruits++;
                currentWeight += weights[j];
            }

            if (currentFruits > maxFruits) {
                maxFruits = currentFruits;
            }
        }
        return maxFruits;
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
