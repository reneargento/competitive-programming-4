package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/01/22.
 */
public class LipschitzConstant {

    private static class Point implements Comparable<Point> {
        int x;
        double fx;

        public Point(int x, double fx) {
            this.x = x;
            this.fx = fx;
        }

        @Override
        public int compareTo(Point other) {
            return Integer.compare(x, other.x);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Point[] points = new Point[FastReader.nextInt()];

        for (int i = 0; i < points.length; i++) {
            int x = FastReader.nextInt();
            double fx = FastReader.nextDouble();
            points[i] = new Point(x, fx);
        }
        double lipschitzConstant = computeLipschitzConstant(points);
        outputWriter.printLine(lipschitzConstant);
        outputWriter.flush();
    }

    private static double computeLipschitzConstant(Point[] points) {
        double lipschitzConstant = Double.MIN_VALUE;
        Arrays.sort(points);

        for (int i = 0; i < points.length - 1; i++) {
            Point point1 = points[i];
            Point point2 = points[i + 1];

            double value = Math.abs(point1.fx - point2.fx) / Math.abs(point1.x - point2.x);
            lipschitzConstant = Math.max(lipschitzConstant, value);
        }
        return lipschitzConstant;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
