package chapter4.session6.e.bipartite.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 29/07/24.
 */
@SuppressWarnings("unchecked")
public class AttackingRooks {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int dimension = Integer.parseInt(line);
            char[][] board = new char[dimension][dimension];

            for (int row = 0; row < board.length; row++) {
                board[row] = FastReader.getLine().toCharArray();
            }
            int maximumRooks = computeMaximumRooks(board);
            outputWriter.printLine(maximumRooks);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMaximumRooks(char[][] board) {
        List<Integer>[] adjacencyList = new List[board.length * board[0].length + 2];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        int leftPartitionMaxVertexID = createGraph(board, adjacencyList);
        return computeMaximumCardinality(adjacencyList, leftPartitionMaxVertexID);
    }

    private static int createGraph(char[][] board, List<Integer>[] adjacencyList) {
        int[][] rowIds = new int[board.length][board[0].length];
        int[][] columnIds = new int[board.length][board[0].length];

        int rowCount = 0;
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == 'X') {
                    continue;
                }
                if (column == 0 || board[row][column - 1] == 'X') {
                    rowCount++;
                }
                if (board[row][column] == '.') {
                    rowIds[row][column] = rowCount;
                }
            }
        }

        int columnCount = 0;
        for (int column = 0; column < board[0].length; column++) {
            for (int row = 0; row < board.length; row++) {
                if (board[row][column] == 'X') {
                    continue;
                }
                if (row == 0 || board[row - 1][column] == 'X') {
                    columnCount++;
                }
                if (board[row][column] == '.') {
                    columnIds[row][column] = columnCount;
                }
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == '.') {
                    int rowVertexId = rowIds[row][column];
                    int columnVertexId = rowCount + columnIds[row][column];

                    adjacencyList[rowVertexId].add(columnVertexId);
                    adjacencyList[columnVertexId].add(rowVertexId);
                }
            }
        }
        return rowCount;
    }

    private static int computeMaximumCardinality(List<Integer>[] adjacencyList, int leftPartitionMaxVertexID) {
        int maximumMatches = 0;
        int[] match = new int[adjacencyList.length];
        Arrays.fill(match, -1);

        for (int vertexID = 0; vertexID <= leftPartitionMaxVertexID; vertexID++) {
            boolean[] visited = new boolean[adjacencyList.length];
            maximumMatches += tryToMatch(adjacencyList, match, visited, vertexID);
        }
        return maximumMatches;
    }

    private static int tryToMatch(List<Integer>[] adjacencyList, int[] match, boolean[] visited, int vertexID) {
        if (visited[vertexID]) {
            return 0;
        }
        visited[vertexID] = true;

        for (int neighbor : adjacencyList[vertexID]) {
            if (match[neighbor] == -1 || tryToMatch(adjacencyList, match, visited, match[neighbor]) == 1) {
                match[neighbor] = vertexID; // flip status
                return 1;
            }
        }
        return 0;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
