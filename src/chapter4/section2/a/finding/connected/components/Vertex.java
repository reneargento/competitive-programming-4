package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/02/23.
 */
@SuppressWarnings("unchecked")
public class Vertex {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int vertices = FastReader.nextInt();

        while (vertices != 0) {
            List<Integer>[] adjacencyList = new List[vertices];
            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                adjacencyList[vertexID] = new ArrayList<>();
            }

            String line = FastReader.getLine();
            while (!line.equals("0")) {
                String[] data = line.split(" ");
                int origin = Integer.parseInt(data[0]) - 1;
                for (int i = 1; i < data.length - 1; i++) {
                    int destination = Integer.parseInt(data[i]) - 1;
                    adjacencyList[origin].add(destination);
                }
                line = FastReader.getLine();
            }

            String[] queries = FastReader.getLine().split(" ");
            for (int i = 1; i < queries.length; i++) {
                int startVertexID = Integer.parseInt(queries[i]) - 1;
                List<Integer> unreachableVertices = computeUnreachableVertices(adjacencyList, startVertexID);
                outputWriter.print(unreachableVertices.size());
                for (int unreachableVertex : unreachableVertices) {
                    outputWriter.print(" " + unreachableVertex);
                }
                outputWriter.printLine();
            }
            vertices = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeUnreachableVertices(List<Integer>[] adjacencyList, int startVertexID) {
        List<Integer> unreachableVertices = new ArrayList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        depthFirstSearch(adjacencyList, visited, startVertexID);

        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            if (!visited[vertexID]) {
                unreachableVertices.add(vertexID + 1);
            }
        }
        return unreachableVertices;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int vertexID) {
        for (int neighborVertexID : adjacencyList[vertexID]) {
            if (!visited[neighborVertexID]) {
                visited[neighborVertexID] = true;
                depthFirstSearch(adjacencyList, visited, neighborVertexID);
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