package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/05/24.
 */
public class NumberingThePaths {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int roadIntersections = FastReader.nextInt();
            int roads = FastReader.nextInt();
            long[] pathCount = new long[roadIntersections];
            boolean[][] adjacencyMatrix = new boolean[roadIntersections][roadIntersections];

            for (int i = 0; i < roads; i++) {
                String road = FastReader.next();
                int nodeID1 = getVertexID(road.charAt(0));
                int nodeID2 = getVertexID(road.charAt(1));
                adjacencyMatrix[nodeID1][nodeID2] = true;
            }

            countPaths(pathCount, adjacencyMatrix, 0);

            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                String path = FastReader.next();
                outputWriter.print(String.format("%s: ", path));

                int pathLength = path.length();
                int startVertexID = getVertexID(path.charAt(0));
                computePathNumber(path, adjacencyMatrix, pathCount, 0, startVertexID, 0,
                        pathLength, outputWriter);
            }
        }
        outputWriter.flush();
    }

    private static long countPaths(long[] pathCount, boolean[][] adjacencyMatrix, int vertexID) {
        if (pathCount[vertexID] != 0) {
            return pathCount[vertexID];
        }

        boolean updatedCount = false;
        for (int neighborID = 0; neighborID < pathCount.length; neighborID++) {
            if (adjacencyMatrix[vertexID][neighborID]) {
                pathCount[vertexID] += countPaths(pathCount, adjacencyMatrix, neighborID);
                updatedCount = true;
            }
        }

        if (!updatedCount) {
            pathCount[vertexID] = 1;
        }
        return pathCount[vertexID];
    }

    private static boolean computePathNumber(String path, boolean[][] adjacencyMatrix, long[] pathCount,
                                             long numberOfPaths, int vertexID, int verticesVisited, int pathLength,
                                             OutputWriter outputWriter) {
        if (verticesVisited == pathLength - 1) {
            outputWriter.printLine((numberOfPaths + 1));
            return true;
        }

        int nextVertexID = getVertexID(path.charAt(verticesVisited + 1));

        for (int neighborID = 0; neighborID < adjacencyMatrix.length; neighborID++) {
            if (!adjacencyMatrix[vertexID][neighborID]) {
                continue;
            }

            if (neighborID == nextVertexID) {
                if (computePathNumber(path, adjacencyMatrix, pathCount, numberOfPaths, nextVertexID,
                        verticesVisited + 1, pathLength, outputWriter)) {
                    return true;
                }
            }
            numberOfPaths += pathCount[neighborID];
        }
        return true;
    }

    private static int getVertexID(char letter) {
        return letter - 'A';
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
