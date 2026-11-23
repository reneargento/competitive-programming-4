package chapter7.section2.a.points;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/11/2026.
 */
public class Symmetry {

    private static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Point[] points = new Point[FastReader.nextInt()];
            for (int i = 0; i < points.length; i++) {
                points[i] = new Point(FastReader.nextInt(), FastReader.nextInt());
            }

            String result = isSymmetric(points);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String isSymmetric(Point[] points) {
        double centerX = findCenterX(points);
        boolean[] matched = new boolean[points.length];
        int matches = 0;

        for (int i = 0; i < points.length; i++) {
            if (matched[i]) {
                continue;
            }
            Point point = points[i];
            if (point.x == centerX) {
                matches++;
                continue;
            }

            double distanceX = point.x - centerX;
            double targetDistanceX = -distanceX;

            for (int j = 0; j < points.length; j++) {
                if (i == j || matched[j]) {
                    continue;
                }
                Point pointCandidate = points[j];
                double pointCandidateDistance = pointCandidate.x - centerX;
                if (point.y == pointCandidate.y
                        && pointCandidateDistance == targetDistanceX) {
                    matched[i] = true;
                    matched[j] = true;
                    matches += 2;
                    break;
                }
            }
        }
        return matches == points.length ? "YES" : "NO";
    }

    private static double findCenterX(Point[] points) {
        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        for (Point point : points) {
            minX = Math.min(minX, point.x);
            maxX = Math.max(maxX, point.x);
        }
        return (minX + maxX) / 2;
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