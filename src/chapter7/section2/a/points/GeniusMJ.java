package chapter7.section2.a.points;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/11/2026.
 */
public class GeniusMJ {

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int pointsNumber = FastReader.nextInt();
            Point[] pointsSet1 = readPoints(pointsNumber);
            Point[] pointsSet2 = readPoints(pointsNumber);

            String result = checkMatch(pointsSet1, pointsSet2);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static Point[] readPoints(int pointsNumber) throws IOException {
        Point[] points = new Point[pointsNumber];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(FastReader.nextInt(), FastReader.nextInt());
        }
        return points;
    }

    private static String checkMatch(Point[] pointsSet1, Point[] pointsSet2) {
        Point[] translatedPointsSet1 = translatePointsAroundCentroid(pointsSet1);
        Point[] translatedPointsSet2 = translatePointsAroundCentroid(pointsSet2);

        if (checkMatchAfterRotation(translatedPointsSet1, translatedPointsSet2)) {
            return "MATCHED";
        }
        for (int degrees = 90; degrees <= 270; degrees += 90) {
            Point[] rotatedPoints = new Point[translatedPointsSet1.length];
            for (int i = 0; i < translatedPointsSet1.length; i++) {
                Point rotatedPoint = rotatePoint(translatedPointsSet1[i], degrees);
                rotatedPoints[i] = rotatedPoint;
            }
            if (checkMatchAfterRotation(rotatedPoints, translatedPointsSet2)) {
                return "MATCHED";
            }
        }
        return "NOT MATCHED";
    }

    private static boolean checkMatchAfterRotation(Point[] translatedPointsSet1, Point[] translatedPointsSet2) {
        Set<Point> pointsSet = new HashSet<>(Arrays.asList(translatedPointsSet1));

        for (Point point : translatedPointsSet2) {
            if (!pointsSet.contains(point)) {
                return false;
            }
            pointsSet.remove(point);
        }
        return pointsSet.isEmpty();
    }

    private static Point[] translatePointsAroundCentroid(Point[] points) {
        long sumXCoordinates = sumXCoordinates(points);
        long sumYCoordinates = sumYCoordinates(points);

        Point[] translatedPoints = new Point[points.length];
        for (int i = 0; i < translatedPoints.length; i++) {
            int translatedX = (int) ((points.length * points[i].x) - sumXCoordinates);
            int translatedY = (int) ((points.length * points[i].y) - sumYCoordinates);
            translatedPoints[i] = new Point(translatedX, translatedY);
        }
        return translatedPoints;
    }

    private static long sumXCoordinates(Point[] points) {
        long sum = 0;
        for (Point point : points) {
            sum += point.x;
        }
        return sum;
    }

    private static long sumYCoordinates(Point[] points) {
        long sum = 0;
        for (Point point : points) {
            sum += point.y;
        }
        return sum;
    }

    private static Point rotatePoint(Point point, int degrees) {
        if (degrees == 90) {
            return new Point(-point.y, point.x);
        } else if (degrees == 180) {
            return new Point(-point.x, -point.y);
        } else {
            return new Point(point.y, -point.x);
        }
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