package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/03/22.
 */
public class SuspensionBridges {

    private static final double EPSILON = .000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int distance = FastReader.nextInt();
        int sag = FastReader.nextInt();

        double a = computeA(distance, sag);
        double length = 2 * a * Math.sinh(distance / (2.0 * a));
        outputWriter.printLine(length);
        outputWriter.flush();
    }

    // 𝑎 + 𝑠 = 𝑎 ⋅ cosh(𝑑 / 2𝑎)
    // 𝑠 = 𝑎 ⋅ cosh(𝑑 / 2𝑎) - 𝑎
    private static double computeA(int distance, int sag) {
        double low = 0;
        double high = 1000000000;

        while (low <= high) {
            double middleA = low + (high - low) / 2;
            double sagCandidate = (middleA * Math.cosh(distance / (2.0 * middleA))) - middleA;
            double difference = sag - sagCandidate;

            if (difference >= 0 && difference < EPSILON) {
                return middleA;
            } else if (difference < 0) {
                low = middleA;
            } else {
                high = middleA;
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
