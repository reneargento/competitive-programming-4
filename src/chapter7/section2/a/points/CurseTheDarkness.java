package chapter7.section2.a.points;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/11/2026.
 */
public class CurseTheDarkness {

    private static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final double EPSILON = 1E-9;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Point bookCoordinates = new Point(FastReader.nextDouble(), FastReader.nextDouble());
            Point[] candleCoordinates = new Point[FastReader.nextInt()];
            for (int i = 0; i < candleCoordinates.length; i++) {
                candleCoordinates[i] = new Point(FastReader.nextDouble(), FastReader.nextDouble());
            }

            String result = checkCandles(bookCoordinates, candleCoordinates);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String checkCandles(Point bookCoordinates, Point[] candleCoordinates) {
        for (Point candleCoordinate : candleCoordinates) {
            if ((distanceBetweenPoints(bookCoordinates, candleCoordinate) - 8) < EPSILON) {
                return "light a candle";
            }
        }
        return "curse the darkness";
    }

    private static double distanceBetweenPoints(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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