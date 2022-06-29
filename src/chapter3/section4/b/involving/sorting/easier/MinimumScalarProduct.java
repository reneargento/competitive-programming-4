package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/06/22.
 */
public class MinimumScalarProduct {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int coordinates = FastReader.nextInt();
            long[] vector1 = readVector(coordinates);
            long[] vector2 = readVector(coordinates);
            long minimumScalarProduct = computeMSP(vector1, vector2);
            outputWriter.printLine(String.format("Case #%d: %d", t, minimumScalarProduct));
        }
        outputWriter.flush();
    }

    private static long[] readVector(int coordinates) throws IOException {
        long[] vector = new long[coordinates];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = FastReader.nextInt();
        }
        return vector;
    }

    private static long computeMSP(long[] vector1, long[] vector2) {
        Arrays.sort(vector1);
        Arrays.sort(vector2);
        long minimumScalarProduct = 0;

        for (int i = 0; i < vector1.length; i++) {
            long product = vector1[i] * vector2[vector2.length - 1 - i];
            minimumScalarProduct += product;
        }
        return minimumScalarProduct;
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