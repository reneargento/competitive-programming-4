package chapter4.session6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/07/24.
 */
@SuppressWarnings("unchecked")
public class FlightPlanningUVa {

    private static class Edge {
        int vertexId1;
        int vertexId2;

        public Edge(int vertexId1, int vertexId2) {
            this.vertexId1 = vertexId1;
            this.vertexId2 = vertexId2;
        }
    }

    private static class Result {
        int minimumFlights;
        int cityId1ToRemoveFlight;
        int cityId2ToRemoveFlight;
        int cityId1ToAddFlight;
        int cityId2ToAddFlight;

        public Result(int minimumFlights, int cityId1ToRemoveFlight, int cityId2ToRemoveFlight, int cityId1ToAddFlight,
                      int cityId2ToAddFlight) {
            this.minimumFlights = minimumFlights;
            this.cityId1ToRemoveFlight = cityId1ToRemoveFlight;
            this.cityId2ToRemoveFlight = cityId2ToRemoveFlight;
            this.cityId1ToAddFlight = cityId1ToAddFlight;
            this.cityId2ToAddFlight = cityId2ToAddFlight;
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = inputReader.nextInt();
        for (int t = 0; t < tests; t++) {
            int cities = inputReader.nextInt();
            List<Integer>[] adjacencyList = new List[cities];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < cities - 1; i++) {
                int cityId1 = inputReader.nextInt() - 1;
                int cityId2 = inputReader.nextInt() - 1;
                adjacencyList[cityId1].add(cityId2);
                adjacencyList[cityId2].add(cityId1);
            }

            Result result = updateFlights(adjacencyList);
            outputWriter.printLine(result.minimumFlights);
            outputWriter.printLine(String.format("%d %d", result.cityId1ToRemoveFlight, result.cityId2ToRemoveFlight));
            outputWriter.printLine(String.format("%d %d", result.cityId1ToAddFlight, result.cityId2ToAddFlight));
        }
        outputWriter.flush();
    }

    private static Result updateFlights(List<Integer>[] adjacencyList) {
        int bestDiameter = adjacencyList.length;
        int cityId1ToRemoveFlight = 0;
        int cityId2ToRemoveFlight = 0;
        int cityId1ToAddFlight = 0;
        int cityId2ToAddFlight = 0;
        int[] parent = new int[adjacencyList.length];
        Arrays.fill(parent, -1);

        List<Edge> edgesInDiameter = getEdgesInDiameter(adjacencyList);

        for (Edge edgeToRemove : edgesInDiameter) {
            List<Integer> centers = new ArrayList<>();
            boolean[] visited = new boolean[adjacencyList.length];
            int[] distances = new int[adjacencyList.length];

            for (int vertexId = 0; vertexId < adjacencyList.length; vertexId++) {
                if (visited[vertexId]) {
                    continue;
                }

                int furthestVertex = getFurthestVertex(adjacencyList, parent, distances, visited, edgeToRemove,
                        null, vertexId);
                int furthestVertexFromFurthest = getFurthestVertex(adjacencyList, parent, distances, visited,
                        edgeToRemove, null, furthestVertex);
                int diameter = distances[furthestVertexFromFurthest];
                int radius = diameter / 2;
                int center = furthestVertexFromFurthest;

                while (distances[center] > radius) {
                    center = parent[center];
                }
                centers.add(center);
            }

            Edge edgeToAdd = new Edge(centers.get(0), centers.get(1));
            int furthestVertex = getFurthestVertex(adjacencyList, parent, distances, visited, edgeToRemove,
                    edgeToAdd, 0);
            int furthestVertexFromFurthest = getFurthestVertex(adjacencyList, parent, distances, visited,
                    edgeToRemove, edgeToAdd, furthestVertex);

            int diameter = distances[furthestVertexFromFurthest];
            if (diameter < bestDiameter) {
                if (edgeToRemove.vertexId1 == Math.min(centers.get(0), centers.get(1)) &&
                        edgeToRemove.vertexId2 == Math.max(centers.get(0), centers.get(1))) {
                    continue;
                }
                bestDiameter = diameter;
                cityId1ToRemoveFlight = edgeToRemove.vertexId1;
                cityId2ToRemoveFlight = edgeToRemove.vertexId2;
                cityId1ToAddFlight = centers.get(0);
                cityId2ToAddFlight = centers.get(1);
            }
        }
        return new Result(bestDiameter, cityId1ToRemoveFlight + 1, cityId2ToRemoveFlight + 1,
                cityId1ToAddFlight + 1, cityId2ToAddFlight + 1);
    }

    private static List<Edge> getEdgesInDiameter(List<Integer>[] adjacencyList) {
        int[] parent = new int[adjacencyList.length];
        Arrays.fill(parent, -1);
        boolean[] visited = new boolean[adjacencyList.length];
        int[] distances = new int[adjacencyList.length];

        int furthestVertex = getFurthestVertex(adjacencyList, parent, distances, visited, null,
                null, 0);
        int furthestVertexFromFurthest = getFurthestVertex(adjacencyList, parent, distances, visited,
                null, null, furthestVertex);
        return getEdgesInDiameter(parent, furthestVertexFromFurthest);
    }

    private static List<Edge> getEdgesInDiameter(int[] parent, int vertexIdOrigin) {
        List<Edge> edgesInDiameter = new ArrayList<>();

        int vertexId = vertexIdOrigin;
        while (parent[vertexId] != -1) {
            int parentVertexId = parent[vertexId];
            edgesInDiameter.add(new Edge(vertexId, parentVertexId));
            vertexId = parentVertexId;
        }
        return edgesInDiameter;
    }

    private static int getFurthestVertex(List<Integer>[] adjacencyList, int[] parent, int[] distances,
                                         boolean[] visited, Edge removedEdge, Edge edgeToAdd, int sourceVertexId) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(sourceVertexId);
        visited[sourceVertexId] = true;

        Arrays.fill(distances, -1);
        distances[sourceVertexId] = 0;
        parent[sourceVertexId] = -1;

        int furthestVertex = sourceVertexId;

        while (!queue.isEmpty()) {
            int vertexId = queue.poll();

            for (int neighbor : adjacencyList[vertexId]) {
                processEdge(queue, parent, distances, visited, removedEdge, vertexId, neighbor);
                furthestVertex = getUpdatedFurthestVertex(distances, furthestVertex, neighbor);
            }
            if (edgeToAdd != null) {
                if (edgeToAdd.vertexId1 == vertexId) {
                    processEdge(queue, parent, distances, visited, removedEdge, vertexId, edgeToAdd.vertexId2);
                    furthestVertex = getUpdatedFurthestVertex(distances, furthestVertex, edgeToAdd.vertexId2);
                } else if (edgeToAdd.vertexId2 == vertexId) {
                    processEdge(queue, parent, distances, visited, removedEdge, vertexId, edgeToAdd.vertexId1);
                    furthestVertex = getUpdatedFurthestVertex(distances, furthestVertex, edgeToAdd.vertexId1);
                }
            }
        }
        return furthestVertex;
    }

    private static void processEdge(Queue<Integer> queue, int[] parent, int[] distances, boolean[] visited,
                                    Edge removedEdge, int vertexId, int neighbor) {
        if (distances[neighbor] == -1) {
            // Avoid using removed edge
            if (removedEdge != null
                    && (vertexId == removedEdge.vertexId1 || vertexId == removedEdge.vertexId2)
                    && (neighbor == removedEdge.vertexId1 || neighbor == removedEdge.vertexId2)) {
                return;
            }

            visited[neighbor] = true;
            distances[neighbor] = distances[vertexId] + 1;
            parent[neighbor] = vertexId;
            queue.offer(neighbor);
        }
    }

    private static int getUpdatedFurthestVertex(int[] distances, int currentFurthestVertex, int candidateVertexId) {
        if (distances[candidateVertexId] > distances[currentFurthestVertex]) {
            return candidateVertexId;
        } else {
            return currentFurthestVertex;
        }
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String next() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public String nextLine() throws IOException {
            int c = snext();
            while (isSpaceChar(c))
                c = snext();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
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
