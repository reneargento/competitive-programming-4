package chapter4.section2.section4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/02/23.
 */
@SuppressWarnings("unchecked")
public class Exercise2DFS {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            int maxVertexID = getVertexID(FastReader.getLine().charAt(0));
            List<Integer>[] adjacencyList = new List[maxVertexID + 1];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            String edge = FastReader.getLine();
            while (edge != null && !edge.isEmpty()) {
                int vertexID1 = getVertexID(edge.charAt(0));
                int vertexID2 = getVertexID(edge.charAt(1));
                adjacencyList[vertexID1].add(vertexID2);
                adjacencyList[vertexID2].add(vertexID1);
                edge = FastReader.getLine();
            }
            int connectedComponents = countConnectedComponents(adjacencyList);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(connectedComponents);
        }
        outputWriter.flush();
    }

    private static int getVertexID(char vertexName) {
        return vertexName - 'A';
    }

    private static int countConnectedComponents(List<Integer>[] adjacencyList) {
        int connectedComponents = 0;
        boolean[] visited = new boolean[adjacencyList.length];
        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            if (visited[vertexID]) {
                continue;
            }
            visited[vertexID] = true;
            dfs(adjacencyList, visited, vertexID);
            connectedComponents++;
        }
        return connectedComponents;
    }

    private static void dfs(List<Integer>[] adjacencyList, boolean[] visited, int vertexID) {
        for (int neighbor : adjacencyList[vertexID]) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                dfs(adjacencyList, visited, neighbor);
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
