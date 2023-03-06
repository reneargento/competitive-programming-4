package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/03/23.
 */
@SuppressWarnings("unchecked")
public class Dominator {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int nodes = FastReader.nextInt();
            List<Integer>[] adjacencyList = new List[nodes];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int vertex1 = 0; vertex1 < nodes; vertex1++) {
                for (int vertex2 = 0; vertex2 < nodes; vertex2++) {
                    int connected = FastReader.nextInt();
                    if (connected == 1) {
                        adjacencyList[vertex1].add(vertex2);
                    }
                }
            }
            boolean[] visited = new boolean[adjacencyList.length];

            boolean[][] dominators = computeDominators(adjacencyList, visited);
            outputWriter.printLine(String.format("Case %d:", t));
            printResults(dominators, visited, outputWriter);
        }
        outputWriter.flush();
    }

    private static boolean[][] computeDominators(List<Integer>[] adjacencyList, boolean[] visited) {
        boolean[][] dominators = new boolean[adjacencyList.length][adjacencyList.length];
        Arrays.fill(dominators[0], true);

        depthFirstSearch(adjacencyList, visited, -1, 0);

        for (int deletedNode = 1; deletedNode < adjacencyList.length; deletedNode++) {
            boolean[] visitedWithDelete = new boolean[adjacencyList.length];
            depthFirstSearch(adjacencyList, visitedWithDelete, deletedNode, 0);

            for (int node = 1; node < adjacencyList.length; node++) {
                if (visited[node] && !visitedWithDelete[node]) {
                    dominators[deletedNode][node] = true;
                }
            }
        }
        return dominators;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int deletedNode, int vertex) {
        visited[vertex] = true;

        for (int neighbor : adjacencyList[vertex]) {
            if (neighbor != deletedNode && !visited[neighbor]) {
                depthFirstSearch(adjacencyList, visited, deletedNode, neighbor);
            }
        }
    }

    private static void printResults(boolean[][] dominators, boolean[] visited, OutputWriter outputWriter) {
        printRowDivisor(dominators.length, outputWriter);
        for (int vertex1 = 0; vertex1 < dominators.length; vertex1++) {
            outputWriter.print("|");
            for (int vertex2 = 0; vertex2 < dominators[0].length; vertex2++) {
                if (visited[vertex2] && dominators[vertex1][vertex2]) {
                    outputWriter.print("Y");
                } else {
                    outputWriter.print("N");
                }
                outputWriter.print("|");
            }
            outputWriter.printLine();
            printRowDivisor(dominators.length, outputWriter);
        }
    }

    private static void printRowDivisor(int nodes, OutputWriter outputWriter) {
        outputWriter.print("+-");
        for (int i = 0; i < nodes - 1; i++) {
            outputWriter.print("--");
        }
        outputWriter.printLine("+");
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