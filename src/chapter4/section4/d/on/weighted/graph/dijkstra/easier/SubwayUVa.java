package chapter4.section4.d.on.weighted.graph.dijkstra.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/01/24.
 */
@SuppressWarnings("unchecked")
public class SubwayUVa {

    private static final double KM10_TO_M_DIVIDER = 166.66;
    private static final double KM40_TO_M_DIVIDER = 666.66;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();
        for (int t = 0; t < tests; t++) {
            List<Edge>[] adjacencyList = new List[202];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int homeID = 0;
            int schoolID = 1;

            Map<Coordinate, Integer> coordinateToID = new HashMap<>();
            Coordinate home = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            Coordinate school = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            getCoordinateID(coordinateToID, home);
            getCoordinateID(coordinateToID, school);

            List<Coordinate> walkingCoordinates = new ArrayList<>();
            walkingCoordinates.add(home);
            walkingCoordinates.add(school);

            String line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(" ");
                addEdges(adjacencyList, data, coordinateToID, walkingCoordinates);
                line = FastReader.getLine();
            }
            addWalkingEdges(adjacencyList, coordinateToID, walkingCoordinates);

            Dijkstra dijkstra = new Dijkstra(adjacencyList, homeID);
            long roundedMinutes = Math.round(dijkstra.distTo[schoolID]);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(roundedMinutes);
        }
        outputWriter.flush();
    }

    private static void addEdges(List<Edge>[] adjacencyList, String[] metroLine,
                                 Map<Coordinate, Integer> coordinateToID, List<Coordinate> walkingCoordinates) {
        int xStart = Integer.parseInt(metroLine[0]);
        int yStart = Integer.parseInt(metroLine[1]);
        Coordinate previousCoordinate = new Coordinate(xStart, yStart);
        walkingCoordinates.add(previousCoordinate);

        for (int i = 2; i < metroLine.length - 2; i += 2) {
            int x = Integer.parseInt(metroLine[i]);
            int y = Integer.parseInt(metroLine[i + 1]);
            Coordinate coordinate = new Coordinate(x, y);
            addEdge(adjacencyList, coordinateToID, previousCoordinate, coordinate, KM40_TO_M_DIVIDER);
            walkingCoordinates.add(coordinate);
            previousCoordinate = coordinate;
        }
    }

    private static void addWalkingEdges(List<Edge>[] adjacencyList, Map<Coordinate, Integer> coordinateToID,
                                        List<Coordinate> walkingCoordinates) {
        for (int i = 0; i < walkingCoordinates.size(); i++) {
            Coordinate coordinate1 = walkingCoordinates.get(i);

            for (int j = i + 1; j < walkingCoordinates.size(); j++) {
                Coordinate coordinate2 = walkingCoordinates.get(j);
                addEdge(adjacencyList, coordinateToID, coordinate1, coordinate2, KM10_TO_M_DIVIDER);
            }
        }
    }

    private static void addEdge(List<Edge>[] adjacencyList, Map<Coordinate, Integer> coordinateToID,
                                Coordinate coordinate1, Coordinate coordinate2, double divider) {
        int coordinate1ID = getCoordinateID(coordinateToID, coordinate1);
        int coordinate2ID = getCoordinateID(coordinateToID, coordinate2);

        double distance = distanceBetweenPoints(coordinate1, coordinate2);
        double minutes = distance / divider;
        adjacencyList[coordinate1ID].add(new Edge(coordinate2ID, minutes));
        adjacencyList[coordinate2ID].add(new Edge(coordinate1ID, minutes));
    }

    private static int getCoordinateID(Map<Coordinate, Integer> coordinateToID, Coordinate coordinate) {
        if (!coordinateToID.containsKey(coordinate)) {
            coordinateToID.put(coordinate, coordinateToID.size());
        }
        return coordinateToID.get(coordinate);
    }

    private static double distanceBetweenPoints(Coordinate coordinate1, Coordinate coordinate2) {
        return Math.sqrt(Math.pow(coordinate1.x - coordinate2.x, 2) + Math.pow(coordinate1.y - coordinate2.y, 2));
    }

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
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

        public Dijkstra(List<Edge>[] adjacencyList, int source) {
            distTo = new double[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, Double.POSITIVE_INFINITY);
            distTo[source] = 0;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                relax(adjacencyList, priorityQueue.poll());
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                }
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
