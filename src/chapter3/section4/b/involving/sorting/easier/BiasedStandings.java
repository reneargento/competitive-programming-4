package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/07/22.
 */
public class BiasedStandings {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] preferredPlaces = new int[FastReader.nextInt()];

            for (int i = 0; i < preferredPlaces.length; i++) {
                FastReader.next();
                preferredPlaces[i] = FastReader.nextInt();
            }
            long bestBadness = computeBestBadness(preferredPlaces);
            outputWriter.printLine(bestBadness);
        }
        outputWriter.flush();
    }

    private static long computeBestBadness(int[] preferredPlaces) {
        Arrays.sort(preferredPlaces);
        long bestBadness = 0;
        for (int i = 0; i < preferredPlaces.length; i++) {
            int finalPosition = i +  1;
            bestBadness += Math.abs(finalPosition - preferredPlaces[i]);
        }
        return bestBadness;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
