package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/07/22.
 */
public class Skocimis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] kangarooPositions = new int[3];
        for (int i = 0; i < kangarooPositions.length; i++) {
            kangarooPositions[i] = FastReader.nextInt();
        }

        long maximumMoves = computeMaximumMoves(kangarooPositions);
        outputWriter.printLine(maximumMoves);
        outputWriter.flush();
    }

    private static long computeMaximumMoves(int[] kangarooPositions) {
        int distance1 = kangarooPositions[1] - kangarooPositions[0];
        int distance2 = kangarooPositions[2] - kangarooPositions[1];
        int maxDistance = Math.max(distance1, distance2);
        return maxDistance - 1;
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
