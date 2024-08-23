package chapter4.session6.e.bipartite.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/08/24.
 */
@SuppressWarnings("unchecked")
public class Superdoku {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int gridSize = FastReader.nextInt();
        int rowsFilled = FastReader.nextInt();

        int[][] grid = new int[gridSize][gridSize];
        Set<Integer>[] availableValuesPerColumn = new Set[gridSize];
        for (int column = 0; column < availableValuesPerColumn.length; column++) {
            availableValuesPerColumn[column] = new HashSet<>();
            for (int value = 1; value <= gridSize; value++) {
                availableValuesPerColumn[column].add(value);
            }
        }

        boolean possible = true;
        for (int row = 0; row < rowsFilled; row++) {
            Set<Integer> values = new HashSet<>();

            for (int column = 0; column < grid[0].length; column++) {
                int value = FastReader.nextInt();
                grid[row][column] = value;
                values.add(value);

                if (!availableValuesPerColumn[column].contains(value)) {
                    possible = false;
                }
                availableValuesPerColumn[column].remove(value);
            }
            if (values.size() != gridSize) {
                possible = false;
            }
        }

        if (!possible) {
            outputWriter.printLine("no");
        } else {
            for (int row = rowsFilled; row < gridSize; row++) {
                int[] newRowMatches = getRow(availableValuesPerColumn);
                for (int column = 0; column < newRowMatches.length; column++) {
                    grid[row][column] = newRowMatches[column] + 1;
                    availableValuesPerColumn[column].remove(grid[row][column]);
                }
            }

            outputWriter.printLine("yes");
            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[row].length; column++) {
                    outputWriter.print(grid[row][column]);
                    if (column != grid[row].length - 1) {
                        outputWriter.print(" ");
                    }
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static int[] getRow(Set<Integer>[] availableValuesPerColumn) {
       List<Integer>[] adjacencyList = new List[availableValuesPerColumn.length];
       for (int i = 0; i < adjacencyList.length; i++) {
           adjacencyList[i] = new ArrayList<>();
       }

        for (int column = 0; column < availableValuesPerColumn.length; column++) {
            for (int value : availableValuesPerColumn[column]) {
                adjacencyList[column].add(value - 1);
            }
        }

        int[] newRowMatches = new int[adjacencyList.length];
        int[] match = new int[adjacencyList.length];
        Arrays.fill(match, -1);
        Arrays.fill(newRowMatches, -1);

        boolean addedMatches = true;
        while (addedMatches) {
            addedMatches = false;
            boolean[] visited = new boolean[adjacencyList.length];

            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                if (newRowMatches[vertexID] == -1) {
                    int matched = tryToMatch(adjacencyList, match, newRowMatches, visited, vertexID);
                    if (matched > 0) {
                        addedMatches = true;
                    }
                }
            }
        }
        return newRowMatches;
    }

    private static int tryToMatch(List<Integer>[] adjacencyList, int[] match, int[] newRowMatches, boolean[] visited,
                                  int vertexID) {
        if (visited[vertexID]) {
            return 0;
        }
        visited[vertexID] = true;

        for (int neighbor : adjacencyList[vertexID]) {
            if (match[neighbor] == -1
                    || tryToMatch(adjacencyList, match, newRowMatches, visited, match[neighbor]) == 1) {
                match[neighbor] = vertexID; // flip status
                newRowMatches[vertexID] = neighbor;
                return 1;
            }
        }
        return 0;
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
