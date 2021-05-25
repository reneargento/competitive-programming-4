package chapter2.section3.d.hash.table.set;

import java.awt.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/05/21.
 */
public class GreetingCard {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int lines = FastReader.nextInt();
        Set<Point> points = new HashSet<>();

        for (int i = 0; i < lines; i++) {
            Point point = new Point(FastReader.nextInt(), FastReader.nextInt());
            points.add(point);
        }
        int connectedSegments = countAllSegments(points);

        outputWriter.printLine(connectedSegments);
        outputWriter.flush();
    }

    private static int countAllSegments(Set<Point> points) {
        int connectedSegments = 0;

        for (Point point : points) {
            connectedSegments += countSegments(points, point);
        }
        return connectedSegments / 2;
    }

    private static int countSegments(Set<Point> points, Point point) {
        int segments = 0;

        Point point1 = new Point(point.x + 2018, point.y);
        Point point2 = new Point(point.x, point.y + 2018);
        Point point3 = new Point(point.x - 2018, point.y);
        Point point4 = new Point(point.x, point.y - 2018);
        Point point5 = new Point(point.x + 1118, point.y + 1680);
        Point point6 = new Point(point.x + 1680, point.y + 1118);
        Point point7 = new Point(point.x - 1118, point.y + 1680);
        Point point8 = new Point(point.x + 1680, point.y - 1118);
        Point point9 = new Point(point.x + 1118, point.y - 1680);
        Point point10 = new Point(point.x - 1680, point.y + 1118);
        Point point11 = new Point(point.x - 1118, point.y - 1680);
        Point point12 = new Point(point.x - 1680, point.y - 1118);

        Point[] candidates = { point1, point2, point3, point4, point5, point6, point7, point8, point9,
                point10, point11, point12 };

        for (Point candidate : candidates) {
            if (points.contains(candidate)) {
                segments++;
            }
        }
        return segments;
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
