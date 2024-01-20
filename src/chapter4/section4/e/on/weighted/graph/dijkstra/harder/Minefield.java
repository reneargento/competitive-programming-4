package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/01/24.
 */
@SuppressWarnings("unchecked")
public class Minefield {

    private static class Coordinate {
        double x;
        double y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final double MAX_POINT_DISTANCE = 1.5;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("*")) {
            String[] data = line.split(" ");
            int targetX = Integer.parseInt(data[0]);
            int targetY = Integer.parseInt(data[1]);
            Coordinate target = new Coordinate(targetX, targetY);

            Coordinate[] points = new Coordinate[FastReader.nextInt() + 2];
            points[0] = new Coordinate(0, 0);
            points[1] = target;
            for (int pointID = 2; pointID < points.length; pointID++) {
                points[pointID] = new Coordinate(FastReader.nextDouble(), FastReader.nextDouble());
            }
            double maxDistance = FastReader.nextDouble();

            boolean safePathExists = safePathExists(points, maxDistance);
            outputWriter.printLine(safePathExists ? "I am lucky!" : "Boom!");
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean safePathExists(Coordinate[] points, double maxDistance) {
        List<Edge>[] adjacencyList = createGraph(points);
        Dijkstra dijkstra = new Dijkstra(adjacencyList, 0, 1, maxDistance);
        return dijkstra.hasPathTo(1);
    }

    private static List<Edge>[] createGraph(Coordinate[] points) {
        List<Edge>[] adjacencyList = new List[points.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int pointID1 = 0; pointID1 < points.length - 1; pointID1++) {
            for (int pointID2 = pointID1 + 1; pointID2 < points.length; pointID2++) {
                double distance = distanceBetweenPoints(points[pointID1], points[pointID2]);
                if (distance <= MAX_POINT_DISTANCE) {
                    adjacencyList[pointID1].add(new Edge(pointID2, distance));
                    adjacencyList[pointID2].add(new Edge(pointID1, distance));
                }
            }
        }
        return adjacencyList;
    }

    private static double distanceBetweenPoints(Coordinate coordinate1, Coordinate coordinate2) {
        return Math.sqrt(Math.pow(coordinate1.x - coordinate2.x, 2) + Math.pow(coordinate1.y - coordinate2.y, 2));
    }

    private static class Edge {
        private final int vertex2;
        private final double distance;

        public Edge(int vertex2, double distance) {
            this.vertex2 = vertex2;
            this.distance = distance;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            double distance;

            public Vertex(int id, double distance) {
                this.id = id;
                this.distance = distance;
            }

            @Override
            public int compareTo(Vertex other) {
                return Double.compare(distance, other.distance);
            }
        }

        private final double[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final double MAX_VALUE = Double.POSITIVE_INFINITY;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int target, double maxDistance) {
            distTo = new double[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = 0;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == target) {
                    break;
                }
                relax(adjacencyList, vertex, maxDistance);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex, double maxDistance) {
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;
                double candidateDistance = distTo[vertex.id] + edge.distance;

                if (distTo[neighbor] > candidateDistance && candidateDistance <= maxDistance) {
                    distTo[neighbor] = candidateDistance;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] != MAX_VALUE;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static String getLine() throws IOException {
            return reader.readLine();
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
