package chapter4.section5.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/03/24.
 */
@SuppressWarnings("unchecked")
public class AlwaysLate {

    private static class Edge {
        private final int vertex2;
        private final int weight;

        public Edge(int vertex2, int weight) {
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int setID = 1;

        while (line != null) {
            String[] data = line.split(" ");
            int junctions = Integer.parseInt(data[0]);
            int roads = Integer.parseInt(data[1]);

            List<Edge>[] adjacencyList = new List[junctions];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            int[][] distances = new int[junctions][junctions];
            for (int junctionID = 0; junctionID < distances.length; junctionID++) {
                Arrays.fill(distances[junctionID], INFINITE);
                distances[junctionID][junctionID] = 0;
            }

            for (int i = 0; i < roads; i++) {
                int junctionID1 = FastReader.nextInt();
                int junctionID2 = FastReader.nextInt();
                int length = FastReader.nextInt();
                distances[junctionID1][junctionID2] = length;
                distances[junctionID2][junctionID1] = length;
                adjacencyList[junctionID1].add(new Edge(junctionID2, length));
                adjacencyList[junctionID2].add(new Edge(junctionID1, length));
            }

            int[][] secondShortestPathLengths = computeSecondShortestPathLengths(distances, adjacencyList);

            outputWriter.printLine(String.format("Set #%d", setID));
            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int junctionID1 = FastReader.nextInt();
                int junctionID2 = FastReader.nextInt();

                int secondShortestPathLength = secondShortestPathLengths[junctionID1][junctionID2];
                if (secondShortestPathLength == INFINITE) {
                    outputWriter.printLine("?");
                } else {
                    outputWriter.printLine(secondShortestPathLength);
                }
            }

            setID++;
            line = FastReader.getLine();
            if (line != null) {
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static int[][] computeSecondShortestPathLengths(int[][] distances, List<Edge>[] adjacencyList) {
        int[][] secondShortestPathLengths = new int[distances.length][distances.length];
        for (int[] values : secondShortestPathLengths) {
            Arrays.fill(values, INFINITE);
        }
        floydWarshall(distances);

        for (int junctionID1 = 0; junctionID1 < distances.length; junctionID1++) {
            for (int junctionID2 = 0; junctionID2 < distances.length; junctionID2++) {
                if (distances[junctionID1][junctionID2] != INFINITE) {
                    dfs(distances, adjacencyList, secondShortestPathLengths, junctionID1, junctionID1, 0);
                }
            }
        }
        return secondShortestPathLengths;
    }

    private static void dfs(int[][] distances, List<Edge>[] adjacencyList, int[][] secondShortestPathLengths,
                            int junctionID, int neighborJunctionID, int currentLength) {
        if (currentLength > secondShortestPathLengths[junctionID][neighborJunctionID]) {
            return;
        }
        if (currentLength > distances[junctionID][neighborJunctionID]) {
            secondShortestPathLengths[junctionID][neighborJunctionID] = currentLength;
        }

        for (Edge edge : adjacencyList[neighborJunctionID]) {
            dfs(distances, adjacencyList, secondShortestPathLengths, junctionID, edge.vertex2,
                    currentLength + edge.weight);
        }
    }

    private static void floydWarshall(int[][] distances) {
        int vertices = distances.length;

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
