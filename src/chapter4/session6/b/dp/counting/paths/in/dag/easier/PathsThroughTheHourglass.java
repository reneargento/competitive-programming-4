package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/05/24.
 */
public class PathsThroughTheHourglass {

    private static class Vertex {
        int id;
        int value;
        int leftChildID;
        int rightChildID;

        public Vertex(int id) {
            this.id = id;
            leftChildID = NO_CHILD;
            rightChildID = NO_CHILD;
        }
    }

    private static class PathContainer {
        StringBuilder path;
        boolean complete;

        public PathContainer() {
            path = new StringBuilder();
        }
    }

    private static class Result {
        long numberOfPaths;
        int lowestPathStartPoint;
        String lowestPath;

        public Result(long numberOfPaths, int lowestPathStartPoint, String lowestPath) {
            this.numberOfPaths = numberOfPaths;
            this.lowestPathStartPoint = lowestPathStartPoint;
            this.lowestPath = lowestPath;
        }
    }

    private static final int NO_CHILD = -1;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int firstRowCells = FastReader.nextInt();
        int targetSum = FastReader.nextInt();

        while (firstRowCells != 0 || targetSum != 0) {
            Vertex[] adjacencyList = new Vertex[firstRowCells * firstRowCells * 2];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new Vertex(i);
            }

            List<Integer> previousRow = null;
            int rowsNumber = 2 * firstRowCells - 1;
            int valuesToRead = firstRowCells;
            int vertexID = 0;

            for (int row = 0; row < rowsNumber; row++) {
                List<Integer> currentRow = new ArrayList<>();

                for (int i = 0; i < valuesToRead; i++) {
                    adjacencyList[vertexID].value = FastReader.nextInt();

                    if (row > 0) {
                        int leftParentID;
                        int rightParentID;

                        if (row < firstRowCells) {
                            leftParentID = previousRow.get(i);
                            rightParentID = previousRow.get(i + 1);
                        } else {
                            if (i == 0) {
                                leftParentID = -1;
                                rightParentID = previousRow.get(0);
                            } else if (i == valuesToRead - 1) {
                                leftParentID = previousRow.get(previousRow.size() - 1);
                                rightParentID = -1;
                            } else {
                                leftParentID = previousRow.get(i - 1);
                                rightParentID = previousRow.get(i);
                            }
                        }

                        if (leftParentID != -1) {
                            adjacencyList[leftParentID].rightChildID = vertexID;
                        }
                        if (rightParentID != -1) {
                            adjacencyList[rightParentID].leftChildID = vertexID;
                        }
                    }

                    currentRow.add(vertexID);
                    vertexID++;
                }
                previousRow = currentRow;
                if (row < firstRowCells - 1) {
                    valuesToRead--;
                } else {
                    valuesToRead++;
                }
            }

            Result result = countPaths(adjacencyList, firstRowCells, targetSum);
            outputWriter.printLine(result.numberOfPaths);
            if (result.numberOfPaths == 0) {
                outputWriter.printLine();
            } else {
                outputWriter.print(result.lowestPathStartPoint + " ");
                outputWriter.printLine(result.lowestPath);
            }

            firstRowCells = FastReader.nextInt();
            targetSum = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result countPaths(Vertex[] adjacencyList, int firstRowCells, int targetSum) {
        long totalPaths = 0;
        int lowestPathStartPoint = -1;
        String path = null;

        // dp[current id][current sum]
        long[][] dp = new long[adjacencyList.length][targetSum + 1];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }

        for (int vertexID = 0; vertexID < firstRowCells; vertexID++) {
            PathContainer pathContainer = new PathContainer();
            long paths = countPaths(adjacencyList, dp, targetSum, pathContainer, vertexID, 0);
            if (paths > 0) {
                if (lowestPathStartPoint == -1) {
                    lowestPathStartPoint = vertexID;
                    path = pathContainer.path.toString();
                }
                totalPaths += paths;
            }
        }
        return new Result(totalPaths, lowestPathStartPoint, path);
    }

    private static long countPaths(Vertex[] adjacencyList, long[][] dp, int targetSum, PathContainer pathContainer,
                                   int vertexID, int sum) {
        if (sum > targetSum || vertexID == NO_CHILD) {
            return 0;
        }
        if (dp[vertexID][sum] != -1) {
            return dp[vertexID][sum];
        }

        long pathsNumber = 0;
        Vertex vertex = adjacencyList[vertexID];

        int newSum = sum + vertex.value;
        if (newSum == targetSum
                && vertex.leftChildID == NO_CHILD
                && vertex.rightChildID == NO_CHILD) {
            pathContainer.complete = true;
            return 1;
        }

        if (!pathContainer.complete) {
            pathContainer.path.append("L");
        }
        pathsNumber += countPaths(adjacencyList, dp, targetSum, pathContainer, vertex.leftChildID, newSum);
        if (!pathContainer.complete) {
            pathContainer.path.deleteCharAt(pathContainer.path.length() - 1);
        }

        if (!pathContainer.complete) {
            pathContainer.path.append("R");
        }
        pathsNumber += countPaths(adjacencyList, dp, targetSum, pathContainer, vertex.rightChildID, newSum);
        if (!pathContainer.complete) {
            pathContainer.path.deleteCharAt(pathContainer.path.length() - 1);
        }

        dp[vertexID][sum] = pathsNumber;
        return dp[vertexID][sum];
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
