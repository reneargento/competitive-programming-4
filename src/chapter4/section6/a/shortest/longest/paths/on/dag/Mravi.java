package chapter4.section6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/04/24.
 */
@SuppressWarnings("unchecked")
public class Mravi {

    private static class Edge {
        int nodeID1;
        int nodeID2;
        int flow;
        boolean hasSuperpower;

        public Edge(int nodeID1, int nodeID2, int flow, boolean hasSuperpower) {
            this.nodeID1 = nodeID1;
            this.nodeID2 = nodeID2;
            this.flow = flow;
            this.hasSuperpower = hasSuperpower;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodes = FastReader.nextInt();
        double[] liquidRequired = new double[nodes];

        List<Edge>[] adjacencyList = new List[nodes];
        List<Edge>[] reverseAdjacencyList = new List[nodes];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
            reverseAdjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < nodes - 1; i++) {
            int nodeID1 = FastReader.nextInt() - 1;
            int nodeID2 = FastReader.nextInt() - 1;
            int flow = FastReader.nextInt();
            boolean hasSuperpower = FastReader.nextInt() == 1;

            Edge edge = new Edge(nodeID1, nodeID2, flow, hasSuperpower);
            Edge reverseEdge = new Edge(nodeID2, nodeID1, flow, hasSuperpower);
            adjacencyList[nodeID1].add(edge);
            reverseAdjacencyList[nodeID2].add(reverseEdge);
        }

        for (int i = 0; i < nodes; i++) {
            liquidRequired[i] = FastReader.nextInt();
        }

        double minimumRequiredLiquid = computeMinimumRequiredLiquid(adjacencyList, reverseAdjacencyList, liquidRequired);
        outputWriter.printLine(minimumRequiredLiquid);
        outputWriter.flush();
    }

    private static double computeMinimumRequiredLiquid(List<Edge>[] adjacencyList, List<Edge>[] reverseAdjacencyList,
                                                       double[] liquidRequired) {
        int[] topologicalOrder = khanTopologicalSort(adjacencyList);
        for (int i = topologicalOrder.length - 1; i >= 0; i--) {
            int nodeID = topologicalOrder[i];
            for (Edge edge : reverseAdjacencyList[nodeID]) {
                int neighbor = edge.nodeID2;
                double liquid;
                if (edge.hasSuperpower) {
                    liquid = Math.sqrt(liquidRequired[nodeID]);
                } else {
                    liquid = liquidRequired[nodeID];
                }

                double requiredLiquid = 100.0 * liquid / edge.flow;
                liquidRequired[neighbor] = Math.max(liquidRequired[neighbor], requiredLiquid);
            }
        }
        return liquidRequired[0];
    }

    private static int[] khanTopologicalSort(List<Edge>[] adjacencyList) {
        int[] inDegrees = new int[adjacencyList.length];

        for (int i = 0; i < adjacencyList.length; i++) {
            for (Edge edge : adjacencyList[i]) {
                int neighbor = edge.nodeID2;
                inDegrees[neighbor]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            if (inDegrees[vertexID] == 0) {
                queue.add(vertexID);
            }
        }

        int[] topologicalOrder = new int[adjacencyList.length];
        int topologicalOrderIndex = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            topologicalOrder[topologicalOrderIndex++] = currentVertex;

            for (Edge edge : adjacencyList[currentVertex]) {
                int neighbor = edge.nodeID2;
                inDegrees[neighbor]--;

                if (inDegrees[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }
        return topologicalOrder;
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
