package chapter4.session6.d.tree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/07/24.
 */
@SuppressWarnings("unchecked")
public class TheMazeMakers {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final int[] neighborRows = { -1, 0, 1, 0 };
    private static final int[] neighborColumns = { 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int height = FastReader.nextInt();
        int width = FastReader.nextInt();

        while (height != 0 || width != 0) {
            char[][] maze = new char[height][width];
            for (int row = 0; row < maze.length; row++) {
                maze[row] = FastReader.getLine().toCharArray();
            }

            String result = analyzeMaze(maze);
            outputWriter.printLine(result);

            height = FastReader.nextInt();
            width = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String analyzeMaze(char[][] maze) {
        Cell[] exteriorCells = new Cell[2];
        List<Integer>[] adjacencyList = buildGraph(maze, exteriorCells);

        int exteriorCellId1 = getNodeId(maze, exteriorCells[0].row, exteriorCells[0].column);
        int exteriorCellId2 = getNodeId(maze, exteriorCells[1].row, exteriorCells[1].column);

        boolean[] visited = new boolean[adjacencyList.length];
        traverseMaze(adjacencyList, visited, exteriorCellId1);

        if (!visited[exteriorCellId2]) {
            return "NO SOLUTION";
        }
        if (isAnyCellUnreachable(visited)) {
            return "UNREACHABLE CELL";
        }
        HasCycleUndirectedGraph hasCycle = new HasCycleUndirectedGraph(adjacencyList);
        if (hasCycle.hasCycle) {
            return "MULTIPLE PATHS";
        }
        return "MAZE OK";
    }

    private static List<Integer>[] buildGraph(char[][] maze, Cell[] exteriorCells) {
        List<Integer>[] adjacencyList = new List[maze.length * maze[0].length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        int exteriorCellIndex = 0;

        for (int row = 0; row < maze.length; row++) {
            for (int column = 0; column < maze[0].length; column++) {
                int nodeId = getNodeId(maze, row, column);
                char cellValue = maze[row][column];
                boolean[] passages = getPassages(cellValue);

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row + neighborRows[i];
                    int neighborColumn = column + neighborColumns[i];

                    if (passages[i]) {
                        if (isValid(maze, neighborRow, neighborColumn)) {
                            int neighborId = getNodeId(maze, neighborRow, neighborColumn);
                            adjacencyList[nodeId].add(neighborId);
                        } else {
                            exteriorCells[exteriorCellIndex] = new Cell(row, column);
                            exteriorCellIndex++;
                        }
                    }
                }
            }
        }
        return adjacencyList;
    }

    private static void traverseMaze(List<Integer>[] adjacencyList, boolean[] visited, int nodeId) {
        visited[nodeId] = true;

        for (int neighborId : adjacencyList[nodeId]) {
            if (!visited[neighborId]) {
                traverseMaze(adjacencyList, visited, neighborId);
            }
        }
    }

    private static boolean isAnyCellUnreachable(boolean[] visited) {
        for (boolean reachable : visited) {
            if (!reachable) {
                return true;
            }
        }
        return false;
    }

    private static boolean[] getPassages(char cellValue) {
        boolean[] passages = new boolean[4];
        // Top
        passages[0] = cellValue >= '0' && cellValue <= '7';
        // Right
        passages[1] = (cellValue >= '0' && cellValue <= '3')
                       || (cellValue >= '8' && cellValue <= '9')
                       || (cellValue >= 'A' && cellValue <= 'B');
        // Bottom
        passages[2] = (cellValue >= '0' && cellValue <= '1')
                       || (cellValue >= '4' && cellValue <= '5')
                       || (cellValue >= '8' && cellValue <= '9')
                       || (cellValue >= 'C' && cellValue <= 'D');
        // Left
        passages[3] = cellValue == '0' || cellValue == '2'
                       || cellValue == '4' || cellValue == '6'
                       || cellValue == '8' || cellValue == 'A'
                       || cellValue == 'C' || cellValue == 'E';
        return passages;
    }

    private static int getNodeId(char[][] maze, int row, int column) {
        return row * maze[0].length + column;
    }

    private static boolean isValid(char[][] maze, int row, int column) {
        return row >= 0 && row < maze.length && column >= 0 && column < maze[0].length;
    }

    private static class HasCycleUndirectedGraph {
        private final boolean[] visited;
        private boolean hasCycle;

        public HasCycleUndirectedGraph(List<Integer>[] adjacencyList) {
            visited = new boolean[adjacencyList.length];

            for (int source = 0; source < adjacencyList.length; source++) {
                if (!visited[source]) {
                    dfs(adjacencyList, source, source);
                }
            }
        }

        private void dfs(List<Integer>[] adjacencyList, int vertex, int origin) {
            visited[vertex] = true;

            if (adjacencyList[vertex] != null) {
                for (int neighbor : adjacencyList[vertex]) {
                    if (!visited[neighbor]) {
                        dfs(adjacencyList, neighbor, vertex);
                    } else if (neighbor != origin) {
                        hasCycle = true;
                    }
                }
            }
        }

        public boolean hasCycle() {
            return hasCycle;
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
