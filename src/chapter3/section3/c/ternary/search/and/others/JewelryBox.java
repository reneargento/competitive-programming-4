package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/04/22.
 */
public class JewelryBox {

    private static final double EPSILON = 0.00000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int x = FastReader.nextInt();
            int y = FastReader.nextInt();

            double highestVolume = computeHighestVolume(x, y);
            outputWriter.printLine(String.format("%.11f", highestVolume));
        }
        outputWriter.flush();
    }

    private static double computeHighestVolume(int x, int y) {
        double low = 0;
        double high = Math.min(x, y) / 2.0;
        double highestVolume = 0;

        while (low + EPSILON <= high) {
            double middle = (high - low) / 3;
            double middle1 = low + middle;
            double middle2 = high - middle;

            double volume1 = computeVolume(x, y, middle1);
            double volume2 = computeVolume(x, y, middle2);
            double maxVolume = Math.max(volume1, volume2);
            highestVolume = Math.max(highestVolume, maxVolume);

            if (volume1 >= volume2) {
                high = middle2;
            } else {
                low = middle1;
            }
        }
        return highestVolume;
    }

    private static double computeVolume(int x, int y, double h) {
        double a = x - 2 * h;
        double b = y - 2 * h;
        return a * b * h;
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
