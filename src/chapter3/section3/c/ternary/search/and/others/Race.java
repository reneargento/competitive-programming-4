package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/04/22.
 */
// Based on https://assets.hkoi.org/training2021/adc.pdf
// Problem located at https://ioinformatics.org/files/ioi2011problem2.pdf
public class Race {

    private static class Edge {
        int neighbor;
        int length;

        public Edge(int neighbor, int length) {
            this.neighbor = neighbor;
            this.length = length;
        }
    }

    private static class LengthAndNumberOfEdges {
        int length;
        int numberOfEdges;

        public LengthAndNumberOfEdges(int length, int numberOfEdges) {
            this.length = length;
            this.numberOfEdges = numberOfEdges;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();
        int lengthRequired = FastReader.nextInt();
        int[][] highways = new int[cities - 1][2];
        int[] highwayLengths = new int[highways.length];

        for (int i = 0; i < highways.length; i++) {
            highways[i][0] = FastReader.nextInt();
            highways[i][1] = FastReader.nextInt();
            highwayLengths[i] = FastReader.nextInt();
        }

        int minimumNumberOfHighways = bestPath(cities, lengthRequired, highways, highwayLengths);
        if (minimumNumberOfHighways == Integer.MAX_VALUE) {
            outputWriter.printLine(-1);
        } else {
            outputWriter.printLine(minimumNumberOfHighways);
        }
        outputWriter.flush();
    }

    private static int bestPath(int cities, int lengthRequired, int[][] highways, int[] highwayLengths) {
        List<Edge>[] adjacencyList = buildAdjacencyLists(highways, highwayLengths);
        return centroidDecomposition(adjacencyList, lengthRequired);
    }

    private static int centroidDecomposition(List<Edge>[] adjacencyList, int lengthRequired) {
        boolean[] deleted = new boolean[adjacencyList.length];
        int[] subtreeSizes = new int[adjacencyList.length];
        return process(adjacencyList, deleted, 0, lengthRequired, subtreeSizes);
    }

    private static int process(List<Edge>[] adjacencyList, boolean[] deleted, int vertex, int lengthRequired,
                               int[] subtreeSizes) {
        int bestPathNumberOfEdges = Integer.MAX_VALUE;
        int centroid = getCentroid(adjacencyList, deleted, vertex, subtreeSizes);
        deleted[centroid] = true;

        for (Edge edge : adjacencyList[centroid]) {
            int neighbor = edge.neighbor;
            if (!deleted[neighbor]) {
                int childPathNumberOfEdges = process(adjacencyList, deleted, neighbor, lengthRequired,
                        subtreeSizes);
                bestPathNumberOfEdges = Math.min(bestPathNumberOfEdges, childPathNumberOfEdges);
            }
        }

        Map<Integer, Integer> lengthToEdgesMap = new HashMap<>();
        List<LengthAndNumberOfEdges> entriesToAdd = new ArrayList<>();
        int pathNumberOfEdges = dfsToGetPath(adjacencyList, deleted, lengthRequired, lengthToEdgesMap,
                entriesToAdd, centroid, -1, 0, 0);

        deleted[centroid] = false;
        return Math.min(bestPathNumberOfEdges, pathNumberOfEdges);
    }

    private static int getCentroid(List<Edge>[] adjacencyList, boolean[] deleted, int root, int[] subtreeSizes) {
        computeSubtreeSizes(subtreeSizes, adjacencyList, deleted, root, -1);
        int totalSize = subtreeSizes[root];
        return dfsToGetCentroid(subtreeSizes, adjacencyList, deleted, totalSize, root, -1);
    }

    private static void computeSubtreeSizes(int[] subtreeSizes, List<Edge>[] adjacencyList, boolean[] deleted,
                                            int vertex, int parent) {
        subtreeSizes[vertex] = 1;
        for (Edge edge : adjacencyList[vertex]) {
            int neighbor = edge.neighbor;
            if (neighbor != parent && !deleted[neighbor]) {
                computeSubtreeSizes(subtreeSizes, adjacencyList, deleted, neighbor, vertex);
                subtreeSizes[vertex] += subtreeSizes[neighbor];
            }
        }
    }

    private static int dfsToGetCentroid(int[] subtreeSizes, List<Edge>[] adjacencyList, boolean[] deleted,
                                        int totalSize, int vertex, int parent) {
        for (Edge edge : adjacencyList[vertex]) {
            int neighbor = edge.neighbor;
            if (neighbor != parent && !deleted[neighbor] && subtreeSizes[neighbor] > totalSize / 2) {
                return dfsToGetCentroid(subtreeSizes, adjacencyList, deleted, totalSize, neighbor, vertex);
            }
        }
        return vertex;
    }

    private static int dfsToGetPath(List<Edge>[] adjacencyList, boolean[] deleted, int lengthRequired,
                                    Map<Integer, Integer> lengthToEdgesMap, List<LengthAndNumberOfEdges> entriesToAdd,
                                    int vertex, int parent, int currentLength, int currentNumberOfEdges) {
        int bestPathNumberOfEdges = Integer.MAX_VALUE;

        for (Edge edge : adjacencyList[vertex]) {
            int neighbor = edge.neighbor;
            if (neighbor != parent && !deleted[neighbor]) {
                int newLength = currentLength + edge.length;
                int newNumberOfEdges = currentNumberOfEdges + 1;
                entriesToAdd.add(new LengthAndNumberOfEdges(newLength, newNumberOfEdges));

                int lengthRemaining = lengthRequired - newLength;
                if (lengthRemaining == 0) {
                    bestPathNumberOfEdges = Math.min(bestPathNumberOfEdges, newNumberOfEdges);
                } else if (lengthRemaining > 0) {
                    if (lengthToEdgesMap.containsKey(lengthRemaining)) {
                        int totalNumberOfEdges = newNumberOfEdges + lengthToEdgesMap.get(lengthRemaining);
                        bestPathNumberOfEdges = Math.min(bestPathNumberOfEdges, totalNumberOfEdges);
                    }
                    int pathNumberOfEdges = dfsToGetPath(adjacencyList, deleted, lengthRequired,
                            lengthToEdgesMap, entriesToAdd, neighbor, vertex, newLength, newNumberOfEdges);
                    bestPathNumberOfEdges = Math.min(bestPathNumberOfEdges, pathNumberOfEdges);
                }
            }

            if (parent == -1) {
                for (LengthAndNumberOfEdges lengthAndNumberOfEdges : entriesToAdd) {
                    int length = lengthAndNumberOfEdges.length;
                    int numberOfEdges = lengthAndNumberOfEdges.numberOfEdges;
                    if (!lengthToEdgesMap.containsKey(length)
                            || lengthToEdgesMap.get(length) > numberOfEdges) {
                        lengthToEdgesMap.put(length, numberOfEdges);
                    }
                }
                entriesToAdd.clear();
            }
        }
        return bestPathNumberOfEdges;
    }

    @SuppressWarnings("unchecked")
    private static List<Edge>[] buildAdjacencyLists(int[][] highways, int[] highwayLengths) {
        List<Edge>[] adjacencyList = new List[highways.length + 1];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < highways.length; i++) {
            int city1 = highways[i][0];
            int city2 = highways[i][1];
            adjacencyList[city1].add(new Edge(city2, highwayLengths[i]));
            adjacencyList[city2].add(new Edge(city1, highwayLengths[i]));
        }
        return adjacencyList;
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
