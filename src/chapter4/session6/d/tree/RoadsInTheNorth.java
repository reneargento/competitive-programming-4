package chapter4.session6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/07/24.
 */
public class RoadsInTheNorth {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            Map<Integer, List<Edge>> adjacencyList = new HashMap<>();

            while (line != null && !line.isEmpty()) {
                String[] data = line.split(" ");

                int vertexId1 = Integer.parseInt(data[0]) - 1;
                int vertexId2 = Integer.parseInt(data[1]) - 1;
                int distance = Integer.parseInt(data[2]);

                if (!adjacencyList.containsKey(vertexId1)) {
                    adjacencyList.put(vertexId1, new ArrayList<>());
                }
                if (!adjacencyList.containsKey(vertexId2)) {
                    adjacencyList.put(vertexId2, new ArrayList<>());
                }
                adjacencyList.get(vertexId1).add(new Edge(vertexId2, distance));
                adjacencyList.get(vertexId2).add(new Edge(vertexId1, distance));

                line = FastReader.getLine();
            }

            int treeDiameter = computeTreeDiameter(adjacencyList);
            outputWriter.printLine(treeDiameter);

            if (line != null) {
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static class Edge {
        int nextVertexId;
        int weight;

        public Edge(int nextVertexId, int weight) {
            this.nextVertexId = nextVertexId;
            this.weight = weight;
        }
    }

    private static class VertexData {
        int vertexId;
        int distance;

        public VertexData(int vertexId, int distance) {
            this.vertexId = vertexId;
            this.distance = distance;
        }
    }

    public static int computeTreeDiameter(Map<Integer, List<Edge>> adjacencyList) {
        VertexData furthestVertex = getFurthestVertex(adjacencyList, 0);
        VertexData furthestVertexFromFurthest = getFurthestVertex(adjacencyList, furthestVertex.vertexId);
        return furthestVertexFromFurthest.distance;
    }

    private static VertexData getFurthestVertex(Map<Integer, List<Edge>> adjacencyList, int sourceVertexId) {
        Queue<VertexData> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjacencyList.size()];
        VertexData source = new VertexData(sourceVertexId, 0);
        queue.offer(source);
        visited[sourceVertexId] = true;

        VertexData furthestVertex = new VertexData(sourceVertexId, 0);

        while (!queue.isEmpty()) {
            VertexData vertexData = queue.poll();

            for (Edge edge : adjacencyList.get(vertexData.vertexId)) {
                if (!visited[edge.nextVertexId]) {
                    visited[edge.nextVertexId] = true;
                    int newDistance = vertexData.distance + edge.weight;
                    queue.offer(new VertexData(edge.nextVertexId, newDistance));

                    if (newDistance > furthestVertex.distance) {
                        furthestVertex = new VertexData(edge.nextVertexId, newDistance);
                    }
                }
            }
        }
        return furthestVertex;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
