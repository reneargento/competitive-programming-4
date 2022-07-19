package chapter3.section4.d.involving.priority.queue;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 17/07/22.
 */
public class PolylineSimplification {

    private static final double EPSILON = 0.1;

    private static class Point {
        int id;
        int x;
        int y;

        public Point(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

    private static class Triangle implements Comparable<Triangle> {
        Point point1;
        Point point2;
        Point point3;
        double area;

        public Triangle(Point point1, Point point2, Point point3) {
            this.point1 = point1;
            this.point2 = point2;
            this.point3 = point3;
            area = Math.abs((point1.x * (point2.y - point3.y) + point2.x * (point3.y - point1.y)
                    + point3.x * (point1.y - point2.y)) / 2.0);
        }

        @Override
        public int compareTo(Triangle other) {
            if (Math.abs(area - other.area) > EPSILON) {
                return Double.compare(area, other.area);
            }
            return Integer.compare(point2.id, other.point2.id);
        }
    }

    private static class Neighbors {
        Integer previous;
        Integer next;

        public Neighbors(Integer previous, Integer next) {
            this.previous = previous;
            this.next = next;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Point[] points = new Point[FastReader.nextInt() + 1];
        int targetSegments = FastReader.nextInt();
        Map<Integer, Neighbors> neighborsMap = new HashMap<>();

        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(i, FastReader.nextInt(), FastReader.nextInt());

            Integer previous = null;
            Integer next = null;
            if (i > 0) {
                previous = i - 1;
            }
            if (i < points.length - 1) {
                next = i + 1;
            }
            neighborsMap.put(i, new Neighbors(previous, next));
        }
        simplifyPolyline(points, targetSegments, neighborsMap, outputWriter);
        outputWriter.flush();
    }

    private static void simplifyPolyline(Point[] points, int targetSegments, Map<Integer, Neighbors> neighborsMap,
                                         OutputWriter outputWriter) {
        Set<Integer> deletedPoints = new HashSet<>();
        PriorityQueue<Triangle> trianglesPQ = new PriorityQueue<>();
        for (int i = 1; i < points.length - 1; i++) {
            trianglesPQ.add(new Triangle(points[i - 1], points[i], points[i + 1]));
        }
        int segmentsToDelete = (points.length - 1) - targetSegments;

        while (deletedPoints.size() < segmentsToDelete) {
            Triangle triangle = trianglesPQ.poll();

            if (deletedPoints.contains(triangle.point1.id)
                    || deletedPoints.contains(triangle.point2.id)
                    || deletedPoints.contains(triangle.point3.id)) {
                continue;
            }
            int id = triangle.point2.id;
            outputWriter.printLine(id);
            updateTriangles(trianglesPQ, neighborsMap, points, id);
            deletedPoints.add(id);
        }
    }

    private static void updateTriangles(PriorityQueue<Triangle> trianglesPQ, Map<Integer, Neighbors> neighborsMap,
                                        Point[] points, int id) {
        Neighbors neighbors = neighborsMap.get(id);
        Integer previousId = neighbors.previous;
        if (previousId != null) {
            Neighbors previousNeighbors = neighborsMap.get(previousId);
            previousNeighbors.next = neighbors.next;
            if (previousNeighbors.previous != null && neighbors.next != null) {
                trianglesPQ.offer(new Triangle(points[previousNeighbors.previous], points[previousId],
                        points[neighbors.next]));
            }
        }

        Integer nextId = neighbors.next;
        if (nextId != null) {
            Neighbors nextNeighbors = neighborsMap.get(nextId);
            nextNeighbors.previous = neighbors.previous;
            if (nextNeighbors.next != null && neighbors.previous != null) {
                trianglesPQ.offer(new Triangle(points[neighbors.previous], points[nextId],
                        points[nextNeighbors.next]));
            }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
