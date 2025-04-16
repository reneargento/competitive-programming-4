package chapter5.section2.g.grid;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/04/25.
 */
public class CanYouSolveIt {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int x1 = FastReader.nextInt();
            int y1 = FastReader.nextInt();
            int x2 = FastReader.nextInt();
            int y2 = FastReader.nextInt();

            long distance = computeDistance(x1, y1, x2, y2);
            outputWriter.printLine(String.format("Case %d: %d", t, distance));
        }
        outputWriter.flush();
    }

    private static long computeDistance(int x1, int y1, int x2, int y2) {
        long sum1 = x1 + y1;
        long sum2 = x2 + y2;

        long totalElementsUntilCircle1 = (sum1) * (sum1 + 1) / 2 + x1;
        long totalElementsUntilCircle2 = (sum2) * (sum2 + 1) / 2 + x2;

        return totalElementsUntilCircle2 - totalElementsUntilCircle1;
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
