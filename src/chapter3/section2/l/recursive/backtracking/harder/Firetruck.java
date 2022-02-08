package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/02/22.
 */
@SuppressWarnings("unchecked")
public class Firetruck {

    private static class Counter {
        int counter;
    }

    private static final int INFINITE = 100000;
    private static int pathSize;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int caseId = 1;

        String line = FastReader.getLine();
        while (line != null) {
            int targetVertex = Integer.parseInt(line);
            List<Integer>[] adjacencyList = new List[21];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            int[][] distances = new int[21][21];
            for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
                for (int vertex2 = 0; vertex2 < distances[0].length; vertex2++) {
                    if (vertex1 != vertex2) {
                        distances[vertex1][vertex2] = INFINITE;
                    }
                }
            }

            int vertex1 = FastReader.nextInt();
            int vertex2 = FastReader.nextInt();
            while (vertex1 != 0 || vertex2 != 0) {
                adjacencyList[vertex1].add(vertex2);
                adjacencyList[vertex2].add(vertex1);
                distances[vertex1][vertex2] = 1;
                distances[vertex2][vertex1] = 1;

                vertex1 = FastReader.nextInt();
                vertex2 = FastReader.nextInt();
            }

            for (List<Integer> neighbors : adjacencyList) {
                Collections.sort(neighbors);
            }

            FloydWarshall floydWarshall = new FloydWarshall(distances);

            int[] startPath = new int[21];
            startPath[0] = 1;
            Counter counter = new Counter();
            pathSize = 1;

            outputWriter.printLine(String.format("CASE %d:", caseId));
            computePathsToFire(adjacencyList, targetVertex, outputWriter, floydWarshall, counter,
                    1, 2, startPath);

            outputWriter.printLine(String.format("There are %d routes from the firestation to streetcorner %d.",
                    counter.counter, targetVertex));
            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void computePathsToFire(List<Integer>[] adjacencyList, int targetVertex,
                                           OutputWriter outputWriter, FloydWarshall floydWarshall,
                                           Counter counter, int currentVertex, int mask,
                                           int[] path) {
        if (currentVertex == targetVertex) {
            outputWriter.print(path[0]);

            for (int i = 1; i < pathSize; i++) {
                outputWriter.print(String.format(" %d", path[i]));
            }
            outputWriter.printLine();
            counter.counter++;
            return;
        }

        for (int neighbor : adjacencyList[currentVertex]) {
            if ((mask & (1 << neighbor)) == 0
                    && floydWarshall.hasPath(neighbor, targetVertex)) {
                path[pathSize] = neighbor;
                pathSize++;
                int newMask = mask | (1 << neighbor);
                computePathsToFire(adjacencyList, targetVertex, outputWriter, floydWarshall, counter,
                        neighbor, newMask, path);
                pathSize--;
            }
        }
    }

    private static class FloydWarshall {
        private static int[][] distances;

        public FloydWarshall(int[][] graph) {
            int vertices = graph.length;
            distances = new int[vertices][vertices];

            for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
                for (int vertex2 = 0; vertex2 < distances.length; vertex2++) {
                    if (vertex1 != vertex2) {
                        distances[vertex1][vertex2] = INFINITE;
                    }
                }
            }

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    int distance = graph[vertex1][vertex2];
                    distances[vertex1][vertex2] = distance;
                }
            }

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                        if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                        }
                    }
                }
            }
        }

        public boolean hasPath(int source, int target) {
            return distances[source][target] != INFINITE;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
