package chapter4.section6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/05/24.
 */
@SuppressWarnings("unchecked")
public class WalkingOnTheSafeSide {

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
    
        for (int t = 0; t < tests; t++) {
            int maxHeight = FastReader.nextInt();
            int maxWidth = FastReader.nextInt();
            Set<Integer> unsafeCrossings = new HashSet<>();

            for (int height = 0; height < maxHeight; height++) {
                String[] line = FastReader.getLine().split(" ");
                for (int i = 1; i < line.length; i++) {
                    int width = Integer.parseInt(line[i]) - 1;
                    int vertexID = getVertexID(height, width, maxWidth);
                    unsafeCrossings.add(vertexID);
                }
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            long numberOfPaths = countPaths(unsafeCrossings, maxHeight, maxWidth);
            outputWriter.printLine(numberOfPaths);
        }
        outputWriter.flush();
    }

    private static long countPaths(Set<Integer> unsafeCrossings, int maxHeight, int maxWidth) {
        int totalVertices = maxHeight * maxWidth;
        List<Integer>[] adjacencyList = new List[totalVertices];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int vertexID1 = 0; vertexID1 < totalVertices; vertexID1++) {
            if (unsafeCrossings.contains(vertexID1)) {
                continue;
            }
            int row = vertexID1 / maxWidth;
            int column = vertexID1 % maxWidth;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];
                if (isValid(maxHeight, maxWidth, neighborRow, neighborColumn)) {
                    int vertexID2 = getVertexID(neighborRow, neighborColumn, maxWidth);
                    if (unsafeCrossings.contains(vertexID2) || vertexID2 < vertexID1) {
                        continue;
                    }
                    adjacencyList[vertexID1].add(vertexID2);
                }
            }
        }

        long[] numberOfPaths = NumberOfPaths.countNumberOfPaths(adjacencyList);
        return numberOfPaths[totalVertices - 1];
    }

    private static int getVertexID(int height, int width, int maxWidth) {
        return height * maxWidth + width;
    }

    private static boolean isValid(int maxHeight, int maxWidth, int row, int column) {
        return row >= 0 && row < maxHeight && column >= 0 && column < maxWidth;
    }

    private static class NumberOfPaths {

        public static long[] countNumberOfPaths(List<Integer>[] adjacencyList) {
            long[] numberOfPaths = new long[adjacencyList.length];

            int[] inDegrees = computeInDegrees(adjacencyList);
            int[] topologicalOrder = khanTopologicalSort(adjacencyList, inDegrees);

            for (int vertexID : topologicalOrder) {
                if (vertexID == 0) {
                    numberOfPaths[vertexID] = 1;
                }

                for (int neighbor : adjacencyList[vertexID]) {
                    numberOfPaths[neighbor] += numberOfPaths[vertexID];
                }
            }
            return numberOfPaths;
        }

        private static int[] computeInDegrees(List<Integer>[] adjacencyList) {
            int[] inDegrees = new int[adjacencyList.length];

            for (int i = 0; i < adjacencyList.length; i++) {
                for (int neighbor : adjacencyList[i]) {
                    inDegrees[neighbor]++;
                }
            }
            return inDegrees;
        }

        private static int[] khanTopologicalSort(List<Integer>[] adjacencyList, int[] inDegrees) {
            int[] inDegreesCopy = new int[inDegrees.length];
            System.arraycopy(inDegrees, 0, inDegreesCopy, 0, inDegrees.length);

            // Create a queue and enqueue all vertices with inDegrees 0
            Queue<Integer> queue = new LinkedList<>();
            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                if (inDegreesCopy[vertexID] == 0) {
                    queue.add(vertexID);
                }
            }

            int[] topologicalOrder = new int[adjacencyList.length];
            int topologicalOrderIndex = 0;

            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();
                topologicalOrder[topologicalOrderIndex++] = currentVertex;

                // Iterate through all neighbour nodes of the dequeued vertex and decrease their in-degree by 1
                for (int neighbor : adjacencyList[currentVertex]) {
                    inDegreesCopy[neighbor]--;

                    // If in-degree becomes zero, add it to queue
                    if (inDegreesCopy[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
            return topologicalOrder;
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
            while (!tokenizer.hasMoreTokens() ) {
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
