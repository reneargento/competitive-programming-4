package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/01/26.
 */
public class HaplessHedonism {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        long[] possibleTriangles = computePossibleTriangles();

        for (int t = 0; t < tests; t++) {
            int sticks = FastReader.nextInt();
            outputWriter.printLine(possibleTriangles[sticks]);
        }
        outputWriter.flush();
    }

    private static long[] computePossibleTriangles() {
        long[] possibleTriangles = new long[1000001];
        possibleTriangles[4] = 1;

        int increment = 2;
        long totalIncrement = 2;
        int incrementsUsed = 0;

        for (int triangles = 5; triangles < possibleTriangles.length; triangles++) {
            possibleTriangles[triangles] = possibleTriangles[triangles - 1] + totalIncrement;
            totalIncrement += increment;
            incrementsUsed++;

            if (incrementsUsed % 2 == 0) {
                incrementsUsed = 0;
                increment++;
            }
        }
        return possibleTriangles;
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
