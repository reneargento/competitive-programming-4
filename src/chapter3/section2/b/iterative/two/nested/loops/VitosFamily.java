package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/10/21.
 */
public class VitosFamily {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int relatives = FastReader.nextInt();
            int[] streets = new int[relatives];

            for (int i = 0; i < streets.length; i++) {
                streets[i] = FastReader.nextInt();
            }

            Arrays.sort(streets);
            int medianIndex = streets.length / 2;

            long distance = computeDistanceFromRelatives(streets[medianIndex], streets);
            outputWriter.printLine(distance);
        }
        outputWriter.flush();
    }

    private static long computeDistanceFromRelatives(int origin, int[] streets) {
        long distance = 0;

        for (int street : streets) {
            distance += Math.abs(origin - street);
        }
        return distance;
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