package chapter7.section2.a.points;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/11/2026.
 */
public class CenterOfSymmetry {

    private static class Point implements Comparable<Point> {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return Double.compare(x, point.x) == 0 && Double.compare(y, point.y) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public int compareTo(Point other) {
            double manhattanDistance1 = x + y;
            double manhattanDistance2 = other.x + other.y;
            return Double.compare(manhattanDistance1, manhattanDistance2);
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
            String result = hasCenterOfSymmetry(points);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String hasCenterOfSymmetry(Point[] points) {
        Point centerPoint = getCenterPoint(points);
        Set<Point> pointsSet = new HashSet<>(Arrays.asList(points));
        Set<Point> reversePoints = new HashSet<>();

        for (Point point : pointsSet) {
            double distanceX = centerPoint.x - point.x;
            double distanceY = centerPoint.y - point.y;
            double reverseX = centerPoint.x + distanceX;
            double reverseY = centerPoint.y + distanceY;
            Point reversePoint = new Point(reverseX, reverseY);
            reversePoints.add(reversePoint);
        }

        for (Point reversePoint : reversePoints) {
            if (!pointsSet.contains(reversePoint)) {
                return "no";
            }
        }
        return "yes";
    }

    private static Point getCenterPoint(Point[] points) {
        Arrays.sort(points);
        Point firstPoint = points[0];
        Point lastPoint = points[points.length - 1];

        double centerX = (firstPoint.x + lastPoint.x) / 2;
        double centerY = (firstPoint.y + lastPoint.y) / 2;
        return new Point(centerX, centerY);
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