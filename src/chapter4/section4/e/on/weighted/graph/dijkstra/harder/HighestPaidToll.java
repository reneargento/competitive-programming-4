package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/01/24.
 */
@SuppressWarnings("unchecked")
public class HighestPaidToll {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            List<Edge>[] adjacencyList = new List[FastReader.nextInt()];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int roads = FastReader.nextInt();
            int sourceNode = FastReader.nextInt() - 1;
            int targetNode = FastReader.nextInt() - 1;
            int maxTaka = FastReader.nextInt();

            for (int m = 0; m < roads; m++) {
                int nodeID1 = FastReader.nextInt() - 1;
                int nodeID2 = FastReader.nextInt() - 1;
                int toll = FastReader.nextInt();
                adjacencyList[nodeID1].add(new Edge(nodeID2, toll, toll, 0));
            }

            int maxToll = computeMaxToll(adjacencyList, sourceNode, targetNode, maxTaka);
            outputWriter.printLine(maxToll);
        }
        outputWriter.flush();
    }

    private static int computeMaxToll(List<Edge>[] adjacencyList, int sourceNode, int targetNode, int maxTaka) {
        int maxToll = -1;
        Queue<Edge> queue = new LinkedList<>();
        int[] maxTollPerNode = new int[adjacencyList.length];

        queue.offer(new Edge(sourceNode, 0, 0,0));

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            if (edge.vertex == targetNode) {
                maxToll = Math.max(maxToll, edge.currentMaxToll);
                continue;
            }

            for (Edge neighbor : adjacencyList[edge.vertex]) {
                long nextTaka = edge.takaSpent + neighbor.toll;
                int currentMaxToll = Math.max(edge.currentMaxToll, neighbor.toll);

                if (nextTaka <= maxTaka && maxTollPerNode[neighbor.vertex] < currentMaxToll) {
                    maxTollPerNode[neighbor.vertex] = currentMaxToll;
                    Edge nextEdge = new Edge(neighbor.vertex, neighbor.toll, currentMaxToll, nextTaka);
                    queue.offer(nextEdge);
                }
            }
        }
        return maxToll;
    }

    private static class Edge {
        private final int vertex;
        private final int toll;
        private final int currentMaxToll;
        private final long takaSpent;

        public Edge(int vertex, int toll, int currentMaxToll, long takaSpent) {
            this.vertex = vertex;
            this.toll = toll;
            this.currentMaxToll = currentMaxToll;
            this.takaSpent = takaSpent;
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
