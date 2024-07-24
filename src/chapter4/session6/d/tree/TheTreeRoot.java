package chapter4.session6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/07/24.
 */
@SuppressWarnings("unchecked")
public class TheTreeRoot {

    private static class Result {
        List<Integer> bestRoots;
        List<Integer> worstRoots;

        public Result(List<Integer> bestRoots, List<Integer> worstRoots) {
            this.bestRoots = bestRoots;
            this.worstRoots = worstRoots;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int nodes = Integer.parseInt(line);
            List<Integer>[] adjacencyList = new List[nodes];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int nodeId = 0; nodeId < adjacencyList.length; nodeId++) {
                int connections = FastReader.nextInt();
                for (int i = 0; i < connections; i++) {
                    int neighborId = FastReader.nextInt() - 1;
                    adjacencyList[nodeId].add(neighborId);
                }
            }

            Result result = computeRoots(adjacencyList);

            outputWriter.print("Best Roots  : ");
            printResults(outputWriter, result.bestRoots);
            outputWriter.print("Worst Roots : ");
            printResults(outputWriter, result.worstRoots);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeRoots(List<Integer>[] adjacencyList) {
        List<Integer> bestRoots = getDiameterCenters(adjacencyList);
        List<Integer> worstRoots = new ArrayList<>();

        for (int bestRoot : bestRoots) {
            int[] parent = new int[adjacencyList.length];
            int[] distances = new int[adjacencyList.length];

            List<Integer> furthestVerticesFromBestRoot = getFurthestVertices(adjacencyList, parent, distances, bestRoot);
            worstRoots.addAll(furthestVerticesFromBestRoot);
        }

        Collections.sort(bestRoots);
        Collections.sort(worstRoots);
        return new Result(bestRoots, worstRoots);
    }

    public static List<Integer> getDiameterCenters(List<Integer>[] adjacencyList) {
        List<Integer> centers = new ArrayList<>();
        int[] parent = new int[adjacencyList.length];
        int[] distances = new int[adjacencyList.length];

        int furthestVertex = getFurthestVertices(adjacencyList, parent, distances, 0).get(0);
        int furthestVertexFromFurthest = getFurthestVertices(adjacencyList, parent, distances, furthestVertex).get(0);

        int diameter = distances[furthestVertexFromFurthest];
        double radius = Math.ceil(diameter / 2.0);
        int center = furthestVertexFromFurthest;

        while (distances[center] > radius) {
            center = parent[center];
        }

        centers.add(center);
        if (diameter % 2 == 1) {
            centers.add(parent[center]);
        }
        return centers;
    }

    private static List<Integer> getFurthestVertices(List<Integer>[] adjacencyList, int[] parent, int[] distances,
                                                     int sourceVertexId) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        queue.offer(sourceVertexId);
        visited[sourceVertexId] = true;

        Arrays.fill(distances, -1);
        distances[sourceVertexId] = 0;
        parent[sourceVertexId] = -1;

        List<Integer> furthestVertices = new ArrayList<>();
        int furthestDistance = 0;

        while (!queue.isEmpty()) {
            int vertexId = queue.poll();

            for (int neighbor : adjacencyList[vertexId]) {

                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    distances[neighbor] = distances[vertexId] + 1;
                    parent[neighbor] = vertexId;
                    queue.offer(neighbor);

                    if (distances[neighbor] > furthestDistance) {
                        furthestVertices = new ArrayList<>();
                        furthestVertices.add(neighbor);
                        furthestDistance = distances[neighbor];
                    } else if (distances[neighbor] == furthestDistance) {
                        furthestVertices.add(neighbor);
                    }
                }
            }
        }
        return furthestVertices;
    }

    private static void printResults(OutputWriter outputWriter, List<Integer> roots) {
        outputWriter.print((roots.get(0) + 1));
        for (int i = 1; i < roots.size(); i++) {
            outputWriter.print(" " + (roots.get(i) + 1));
        }
        outputWriter.printLine();
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
