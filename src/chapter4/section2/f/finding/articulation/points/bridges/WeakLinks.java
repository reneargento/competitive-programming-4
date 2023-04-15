package chapter4.section2.f.finding.articulation.points.bridges;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 13/04/23.
 */
@SuppressWarnings("unchecked")
public class WeakLinks {

    private static class Edge implements Comparable<Edge> {
        int vertex1;
        int vertex2;

        Edge(int vertex1, int vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }

        @Override
        public int compareTo(Edge other) {
            if (vertex1 != other.vertex1) {
                return Integer.compare(vertex1, other.vertex1);
            }
            return Integer.compare(vertex2, other.vertex2);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int criminals = FastReader.nextInt();
        int communications = FastReader.nextInt();

        while (criminals != 0 || communications != 0) {
            List<Integer>[] adjacencyList = new List[criminals];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < communications; i++) {
                int criminal1 = FastReader.nextInt();
                int criminal2 = FastReader.nextInt();
                adjacencyList[criminal1].add(criminal2);
                adjacencyList[criminal2].add(criminal1);
            }

            List<Edge> bridges = Bridges.findBridges(adjacencyList);
            Collections.sort(bridges);

            outputWriter.print(bridges.size());
            for (Edge bridge : bridges) {
                outputWriter.print(String.format(" %d %d", bridge.vertex1, bridge.vertex2));
            }
            outputWriter.printLine();
            criminals = FastReader.nextInt();
            communications = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static class Bridges {
        private static int count;
        private static int[] time;
        private static int[] low;

        private static List<Edge> findBridges(List<Integer>[] adjacencyList) {
            low = new int[adjacencyList.length];
            time = new int[adjacencyList.length];
            List<Edge> bridges = new ArrayList<>();

            Arrays.fill(low, -1);
            Arrays.fill(time, -1);

            for (int vertex = 0; vertex < adjacencyList.length; vertex++) {
                if (time[vertex] == -1) {
                    dfs(adjacencyList, vertex, vertex, bridges);
                }
            }
            return bridges;
        }

        private static void dfs(List<Integer>[] adjacencyList, int currentVertex, int sourceVertex, List<Edge> bridges) {
            time[currentVertex] = count;
            low[currentVertex] = count;
            count++;

            for (int neighbor : adjacencyList[currentVertex]) {
                if (time[neighbor] == -1) {
                    dfs(adjacencyList, neighbor, currentVertex, bridges);

                    low[currentVertex] = Math.min(low[currentVertex], low[neighbor]);

                    if (low[neighbor] > time[currentVertex]) {
                        int lowestVertex = Math.min(currentVertex, neighbor);
                        int highestVertex = Math.max(currentVertex, neighbor);
                        bridges.add(new Edge(lowestVertex, highestVertex));
                    }
                } else if (neighbor != sourceVertex) {
                    low[currentVertex] = Math.min(low[currentVertex], time[neighbor]);
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
