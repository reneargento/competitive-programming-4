package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/03/23.
 */
@SuppressWarnings("unchecked")
public class PageHopping {

    private static final int INFINITE = 1000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int pageNumber1 = FastReader.nextInt();
        int pageNumber2 = FastReader.nextInt();
        int caseId = 1;

        while (pageNumber1 != 0 || pageNumber2 != 0) {
            List<Integer>[] adjacencyList = new List[101];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            Set<Integer> vertexIds = new HashSet<>();
            vertexIds.add(pageNumber1);
            vertexIds.add(pageNumber2);

            adjacencyList[pageNumber1].add(pageNumber2);
            pageNumber1 = FastReader.nextInt();
            pageNumber2 = FastReader.nextInt();

            while (pageNumber1 != 0 || pageNumber2 != 0) {
                vertexIds.add(pageNumber1);
                vertexIds.add(pageNumber2);

                adjacencyList[pageNumber1].add(pageNumber2);
                pageNumber1 = FastReader.nextInt();
                pageNumber2 = FastReader.nextInt();
            }
            double averageShortestPath = computeAverageShortestPath(adjacencyList, vertexIds.size());
            outputWriter.printLine(String.format("Case %d: average length between pages = %.3f clicks", caseId, averageShortestPath));

            caseId++;
            pageNumber1 = FastReader.nextInt();
            pageNumber2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeAverageShortestPath(List<Integer>[] adjacencyList, int numberOfVertices) {
        FloydWarshall floydWarshall = new FloydWarshall();
        int[][] distances = floydWarshall.computeDistances(adjacencyList);
        double averageShortestPath = 0;

        for (int vertex1 = 0; vertex1 < adjacencyList.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < adjacencyList.length; vertex2++) {
                if (distances[vertex1][vertex2] == INFINITE) {
                    continue;
                }

                averageShortestPath += distances[vertex1][vertex2];
            }
        }
        averageShortestPath /= (numberOfVertices * (numberOfVertices - 1));
        return averageShortestPath;
    }

    private static class FloydWarshall {

        public int[][] computeDistances(List<Integer>[] adjacencyList) {
            int vertices = adjacencyList.length;
            int[][] distances = new int[vertices][vertices];
            for (int[] values : distances) {
                Arrays.fill(values, INFINITE);
            }

            // Initialize distances
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                distances[vertex1][vertex1] =  0;
                for (int vertex2 : adjacencyList[vertex1]) {
                    distances[vertex1][vertex2] = 1;
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
            return distances;
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
