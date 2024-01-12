package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/01/24.
 */
public class MinimumTransportCost {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();
        boolean printSeparatorLine = false;

        for (int t = 0; t < tests; t++) {
            String line = FastReader.getLine();
            String[] data = line.split(" ");

            int vertices = data.length;
            int[][] costs = new int[vertices][vertices];
            int[] cityTax = new int[vertices];

            for (int vertexID = 0; vertexID < data.length; vertexID++) {
                int cost = Integer.parseInt(data[vertexID]);
                costs[0][vertexID] = cost;
            }

            for (int vertexID1 = 1; vertexID1 < vertices; vertexID1++) {
                for (int vertexID2 = 0; vertexID2 < vertices; vertexID2++) {
                    costs[vertexID1][vertexID2] = FastReader.nextInt();
                }
            }

            for (int i = 0; i < cityTax.length; i++) {
                cityTax[i] = FastReader.nextInt();
            }

            line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                String[] lineData = line.split(" ");
                int sourceID = Integer.parseInt(lineData[0]) - 1;
                int destinationID = Integer.parseInt(lineData[1]) - 1;

                int[] totalCosts = new int[vertices];
                int[] parents = new int[vertices];
                computeTotalCosts(costs, cityTax, totalCosts, parents, sourceID);

                if (printSeparatorLine) {
                    outputWriter.printLine();
                } else {
                    printSeparatorLine = true;
                }
                if (sourceID == destinationID) {
                    totalCosts[destinationID] = cityTax[sourceID];
                }

                String path = computePath(sourceID, destinationID, parents);
                long cost = totalCosts[destinationID] - cityTax[destinationID];

                outputWriter.printLine(String.format("From %d to %d :", sourceID + 1, destinationID + 1));
                outputWriter.printLine(String.format("Path: %s", path));
                outputWriter.printLine(String.format("Total cost : %d", cost));

                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static void computeTotalCosts(int[][] costs, int[] cityTax, int[] totalCosts, int[] parents,
                                          int sourceID) {
        int vertices = cityTax.length;
        Arrays.fill(totalCosts, 10000000);
        totalCosts[sourceID] = 0;

        Queue<Integer> queue = new LinkedList<>();
        boolean[] isInQueue = new boolean[vertices];

        queue.offer(sourceID);
        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            isInQueue[currentVertex] = false;

            for (int vertexID = 0; vertexID < vertices; vertexID++) {
                if (costs[currentVertex][vertexID] == -1) {
                    continue;
                }
                if (totalCosts[vertexID] > totalCosts[currentVertex] + cityTax[vertexID] + costs[currentVertex][vertexID]) {
                    totalCosts[vertexID] = totalCosts[currentVertex] + cityTax[vertexID] + costs[currentVertex][vertexID];
                    parents[vertexID] = currentVertex;

                    if (!isInQueue[vertexID]) {
                        isInQueue[vertexID] = true;
                        queue.offer(vertexID);
                    }
                }
            }
        }
    }

    private static String computePath(int sourceID, int destinationID, int[] parents) {
        if (sourceID == destinationID) {
            return String.valueOf(sourceID + 1);
        }

        List<Integer> verticesInPath = new ArrayList<>();
        int vertex = destinationID;
        while (vertex != sourceID) {
            verticesInPath.add(vertex);
            vertex = parents[vertex];
        }

        StringBuilder path = new StringBuilder();
        path.append(sourceID + 1);
        for (int i = verticesInPath.size() - 1; i >= 0; i--) {
            int vertexID = verticesInPath.get(i) + 1;
            path.append("-->").append(vertexID);
        }
        return path.toString();
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
