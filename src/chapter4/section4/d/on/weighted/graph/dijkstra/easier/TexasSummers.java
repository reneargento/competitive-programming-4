package chapter4.section4.d.on.weighted.graph.dijkstra.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/01/24.
 */
@SuppressWarnings("unchecked")
public class TexasSummers {

    public static void main(String[] args) throws IOException {
        FastReaderInteger fastReader = new FastReaderInteger();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Location[] locations = new Location[fastReader.nextInt() + 2];
        for (int i = 0; i < locations.length; i++) {
            locations[i] = new Location(fastReader.nextInt(), fastReader.nextInt());
        }

        List<Integer> path = computePath(locations);
        if (path.isEmpty()) {
            outputWriter.printLine("-");
        } else {
            for (int shadySpotID : path) {
                outputWriter.printLine(shadySpotID);
            }
        }
        outputWriter.flush();
    }

    private static List<Integer> computePath(Location[] locations) {
        List<Edge>[] adjacencyList = computeGraph(locations);
        int dormitoryID = locations.length - 2;
        int classroomID = locations.length - 1;

        Dijkstra dijkstra = new Dijkstra(adjacencyList, dormitoryID);
        return dijkstra.pathTo(classroomID, dormitoryID);
    }

    private static List<Edge>[] computeGraph(Location[] locations) {
        List<Edge>[] adjacencyList = new List[locations.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int locationID1 = 0; locationID1 < locations.length; locationID1++) {
            for (int locationID2 = locationID1 + 1; locationID2 < locations.length; locationID2++) {
                double distance = distanceBetweenPointsNoSqrt(locations[locationID1], locations[locationID2]);
                adjacencyList[locationID1].add(new Edge(locationID1, locationID2, distance));
                adjacencyList[locationID2].add(new Edge(locationID2, locationID1, distance));
            }
        }
        return adjacencyList;
    }

    public static double distanceBetweenPointsNoSqrt(Location point1, Location point2) {
        return (point1.x - point2.x)  * (point1.x - point2.x)
                + (point1.y - point2.y) * (point1.y - point2.y);
    }

    private static class Location {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
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

        private final Edge[] edgeTo;

        public Dijkstra(List<Edge>[] adjacencyList, int source) {
            edgeTo = new Edge[adjacencyList.length];
            double[] distTo = new double[adjacencyList.length];
            Arrays.fill(distTo, Double.MAX_VALUE);
            boolean[] visited = new boolean[adjacencyList.length];

            distTo[source] = 0;

            while (true) {
                int nextVertexID = -1;
                double nextShortestDistance = Double.MAX_VALUE;

                for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                    if (!visited[vertexID] && distTo[vertexID] < nextShortestDistance) {
                        nextShortestDistance = distTo[vertexID];
                        nextVertexID = vertexID;
                    }
                }

                if (nextVertexID == -1) {
                    break;
                }

                // Relax edges
                for (Edge edge : adjacencyList[nextVertexID]) {
                    double totalDistance = distTo[nextVertexID] + edge.distance;
                    if (distTo[edge.vertex2] > totalDistance) {
                        distTo[edge.vertex2] = totalDistance;
                        edgeTo[edge.vertex2] = edge;
                    }
                }
                visited[nextVertexID] = true;
            }
        }

        public List<Integer> pathTo(int vertex, int dormitoryID) {
            List<Integer> path = new ArrayList<>();
            for (Edge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.vertex1]) {
                if (edge.vertex1 != dormitoryID) {
                    path.add(edge.vertex1);
                }
            }
            Collections.reverse(path);
            return path;
        }
    }

    private static class FastReaderInteger {
        private static final InputStream in = System.in;
        private static final int bufferSize = 30000;
        private static final byte[] buffer = new byte[bufferSize];
        private static int position = 0;
        private static int byteCount = bufferSize;
        private static byte character;

        FastReaderInteger() throws IOException {
            fill();
        }

        private void fill() throws IOException {
            byteCount = in.read(buffer, 0, bufferSize);
        }

        private int nextInt() throws IOException {
            while (character < '-') {
                character = readByte();
            }
            boolean isNegative = (character == '-');
            if (isNegative) {
                character = readByte();
            }
            int value = character - '0';
            while ((character = readByte()) >= '0' && character <= '9') {
                value = value * 10 + character - '0';
            }
            return isNegative ? -value : value;
        }

        private byte readByte() throws IOException {
            if (position == byteCount) {
                fill();
                position = 0;
            }
            return buffer[position++];
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
