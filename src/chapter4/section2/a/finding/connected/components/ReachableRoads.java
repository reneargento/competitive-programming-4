package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/03/23.
 */
@SuppressWarnings("unchecked")
public class ReachableRoads {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();

        for (int c = 0; c < cities; c++) {
            int maxEndpoint = FastReader.nextInt();
            List<Integer>[] adjacencyList = new List[maxEndpoint];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            int roads = FastReader.nextInt();
            for (int i = 0; i < roads; i++) {
                int endpoint1 = FastReader.nextInt();
                int endpoint2 = FastReader.nextInt();
                adjacencyList[endpoint1].add(endpoint2);
                adjacencyList[endpoint2].add(endpoint1);
            }
            int minimumRoadsNeeded = computeMinimumRoadsNeeded(adjacencyList);
            outputWriter.printLine(minimumRoadsNeeded);
        }
        outputWriter.flush();
    }

    private static int computeMinimumRoadsNeeded(List<Integer>[] adjacencyList) {
        boolean[] visited = new boolean[adjacencyList.length];
        int connectedComponents = 0;

        for (int endpoint = 0; endpoint < adjacencyList.length; endpoint++) {
            if (!visited[endpoint]) {
                connectedComponents++;
                depthFirstSearch(adjacencyList, visited, endpoint);
            }
        }
        return connectedComponents - 1;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int vertex) {
        visited[vertex] = true;
        for (int neighbor : adjacencyList[vertex]) {
            if (!visited[neighbor]) {
                depthFirstSearch(adjacencyList, visited, neighbor);
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
