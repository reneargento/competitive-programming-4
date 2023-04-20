package chapter4.section2.f.finding.articulation.points.bridges;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 16/04/23.
 */
@SuppressWarnings("unchecked")
public class CriticalLinks {

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

        String line = FastReader.getLine();

        while (line != null) {
            int servers = Integer.parseInt(line);
            List<Integer>[] adjacencyList = new List[servers];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(" ");
                int serverID = Integer.parseInt(data[0]);
                for (int i = 2; i < data.length; i++) {
                    int connectedServerID = Integer.parseInt(data[i]);
                    adjacencyList[serverID].add(connectedServerID);
                }
                line = FastReader.getLine();
            }

            List<Edge> bridges = Bridges.findBridges(adjacencyList);
            Collections.sort(bridges);

            outputWriter.printLine(String.format("%d critical links", bridges.size()));
            for (Edge bridge : bridges) {
                outputWriter.printLine(bridge.vertex1 + " - " + bridge.vertex2);
            }
            outputWriter.printLine();
            line = FastReader.getLine();
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
                        int minimumVertex = Math.min(currentVertex, neighbor);
                        int maximumVertex = Math.max(currentVertex, neighbor);
                        bridges.add(new Edge(minimumVertex, maximumVertex));
                    }
                } else if (neighbor != sourceVertex) {
                    low[currentVertex] = Math.min(low[currentVertex], time[neighbor]);
                }
            }
        }
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
