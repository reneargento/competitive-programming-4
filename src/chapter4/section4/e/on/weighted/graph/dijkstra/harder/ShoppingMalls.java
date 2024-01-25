package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/01/24.
 */
@SuppressWarnings("unchecked")
public class ShoppingMalls {

    private static class Place {
        int floor;
        int x;
        int y;

        public Place(int floor, int x, int y) {
            this.floor = floor;
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Place[] places = new Place[FastReader.nextInt()];
        int connections = FastReader.nextInt();

        for (int p = 0; p < places.length; p++) {
            places[p] = new Place(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
        }

        List<Edge>[] adjacencyList = new List[places.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int m = 0; m < connections; m++) {
            int placeID1 = FastReader.nextInt();
            int placeID2 = FastReader.nextInt();
            String movementType = FastReader.next();

            Place place1 = places[placeID1];
            Place place2 = places[placeID2];
            double distance1;
            double distance2;
            double euclideanDistance = distanceBetweenPoints(place1, place2);

            if (movementType.equals("walking") || movementType.equals("stairs")) {
                distance1 = euclideanDistance;
                distance2 = distance1;
            } else if (movementType.equals("lift")) {
                distance1 = 1;
                distance2 = distance1;
            } else {
                distance1 = 1;
                distance2 = euclideanDistance * 3;
            }
            adjacencyList[placeID1].add(new Edge(placeID1, placeID2, distance1));
            adjacencyList[placeID2].add(new Edge(placeID2, placeID1, distance2));
        }

        int queries = FastReader.nextInt();
        for (int q = 0; q < queries; q++) {
            int placeID1 = FastReader.nextInt();
            int placeID2 = FastReader.nextInt();

            Dijkstra dijkstra = new Dijkstra(adjacencyList, placeID1, placeID2);
            List<Integer> path = dijkstra.pathTo(placeID2);
            outputWriter.print(path.get(0));
            for (int i = 1; i < path.size(); i++) {
                outputWriter.print(" " + path.get(i));
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static double distanceBetweenPoints(Place place1, Place place2) {
        return Math.sqrt(Math.pow(place1.x - place2.x, 2) + Math.pow(place1.y - place2.y, 2)
                + Math.pow((place1.floor - place2.floor) * 5, 2));
    }

    private static class Edge {
        private final int vertex1;
        private final int vertex2;
        private final double distance;

        public Edge(int vertex1, int vertex2, double distance) {
            this.vertex1 = vertex1;
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

        private final Edge[] edgeTo;
        private final double[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final double MAX_VALUE = Double.POSITIVE_INFINITY;

        public Dijkstra(List<Edge>[] adjacencyList, int source, int target) {
            edgeTo = new Edge[adjacencyList.length];
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
                relax(adjacencyList, vertex);
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
                    edgeTo[neighbor] = edge;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] != MAX_VALUE;
        }

        public List<Integer> pathTo(int vertex) {
            if (!hasPathTo(vertex)) {
                return null;
            }

            List<Integer> path = new ArrayList<>();
            for (Edge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.vertex1]) {
                path.add(edge.vertex2);

                if (edgeTo[edge.vertex1] == null) {
                    path.add(edge.vertex1);
                }
            }
            Collections.reverse(path);
            return path;
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
