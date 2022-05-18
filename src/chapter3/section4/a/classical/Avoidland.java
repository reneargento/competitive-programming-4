package chapter3.section4.a.classical;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/05/22.
 */
public class Avoidland {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dimension = FastReader.nextInt();
        int[] rows = new int[dimension + 1];
        int[] columns = new int[dimension + 1];

        for (int i = 0; i < dimension; i++) {
            rows[FastReader.nextInt()]++;
            columns[FastReader.nextInt()]++;
        }

        long minimumMoves = computeMinimumMoves(rows);
        minimumMoves += computeMinimumMoves(columns);
        outputWriter.printLine(minimumMoves);
        outputWriter.flush();
    }

    private static long computeMinimumMoves(int[] values) {
        long minimumMoves = 0;
        List<Integer> missingValues = new ArrayList<>();
        List<Integer> duplicatedValues = new ArrayList<>();

        for (int i = 1; i < values.length; i++) {
            if (values[i] == 0) {
                missingValues.add(i);
            }

            for (int j = 0; j < values[i] - 1; j++) {
                duplicatedValues.add(i);
            }
        }

        for (int i = 0; i < missingValues.size(); i++) {
            minimumMoves += Math.abs(missingValues.get(i) - duplicatedValues.get(i));
        }
        return minimumMoves;
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
