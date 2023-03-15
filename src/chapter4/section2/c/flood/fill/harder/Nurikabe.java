package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/03/23.
 */
public class Nurikabe {

    private static class SearchResult {
        int componentSize;
        List<Integer> numbers;

        public SearchResult() {
            componentSize = 1;
            numbers = new ArrayList<>();
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] neighborRows4 = { -1, 0, 0, 1 };
        int[] neighborColumns4 = { 0, -1, 1, 0 };
        int[] neighborRows8 = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] neighborColumns8 = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int t = 0; t < tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            int numbers = FastReader.nextInt();

            int[][] grid = new int[rows][columns];
            for (int i = 0; i < numbers; i++) {
                grid[FastReader.nextInt()][FastReader.nextInt()] = FastReader.nextInt();
            }

            char[][] candidateSolution = new char[rows][columns];
            for (int row = 0; row < rows; row++) {
                candidateSolution[row] = FastReader.next().toCharArray();
            }
            boolean isSolved = isSolved(grid, candidateSolution, neighborRows4, neighborColumns4, neighborRows8,
                    neighborColumns8);
            outputWriter.printLine(isSolved ? "solved" : "not solved");
        }
        outputWriter.flush();
    }

    private static boolean isSolved(int[][] grid, char[][] candidateSolution, int[] neighborRows4,
                                    int[] neighborColumns4, int[] neighborRows8, int[] neighborColumns8) {
        if (hasShadedNumberCell(grid, candidateSolution)) {
            return false;
        }
        if (areShadedSpacesDisconnected(candidateSolution, neighborRows4, neighborColumns4)) {
            return false;
        }
        if (areNumbersIncorrect(grid, candidateSolution, neighborRows4, neighborColumns4)) {
            return false;
        }
        if (areUnshadedNotConnectedWithEdge(candidateSolution, neighborRows8, neighborColumns8)) {
            return false;
        }
        return doAll2By2SquaresHaveUnshades(candidateSolution);
    }

    private static boolean hasShadedNumberCell(int[][] grid, char[][] candidateSolution) {
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[0].length; column++) {
                if (grid[row][column] > 0 && candidateSolution[row][column] == '#') {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean areShadedSpacesDisconnected(char[][] candidateSolution, int[] neighborRows4,
                                                       int[] neighborColumns4) {
        int shadedComponents = 0;
        char[][] copySolution = copyArray(candidateSolution);

        for (int row = 0; row < candidateSolution.length; row++) {
            for (int column = 0; column < copySolution[0].length; column++) {
                if (copySolution[row][column] == '#') {
                    shadedComponents++;
                    floodFillShades(copySolution, neighborRows4, neighborColumns4, row, column);
                }
            }
        }
        return shadedComponents > 1;
    }

    private static void floodFillShades(char[][] solution, int[] neighborRows4, int[] neighborColumns4, int row,
                                        int column) {
        solution[row][column] = '.';
        for (int i = 0; i < neighborRows4.length; i++) {
            int neighborRow = row + neighborRows4[i];
            int neighborColumn = column + neighborColumns4[i];
            if (isValid(solution, neighborRow, neighborColumn) && solution[neighborRow][neighborColumn] == '#') {
                floodFillShades(solution, neighborRows4, neighborColumns4, neighborRow, neighborColumn);
            }
        }
    }

    private static boolean areNumbersIncorrect(int[][] grid, char[][] candidateSolution, int[] neighborRows4,
                                             int[] neighborColumns4) {
        char[][] copySolution = copyArray(candidateSolution);

        for (int row = 0; row < candidateSolution.length; row++) {
            for (int column = 0; column < copySolution[0].length; column++) {
                if (copySolution[row][column] == '.') {
                    SearchResult searchResult = checkNumberComponent(grid, copySolution, neighborRows4,
                            neighborColumns4, row, column);
                    if (searchResult.numbers.size() != 1
                            || searchResult.numbers.get(0) != searchResult.componentSize) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static SearchResult checkNumberComponent(int[][] grid, char[][] solution, int[] neighborRows4,
                                                     int[] neighborColumns4, int row, int column) {
        SearchResult searchResult = new SearchResult();
        solution[row][column] = '#';
        if (grid[row][column] != 0) {
            searchResult.numbers.add(grid[row][column]);
        }

        for (int i = 0; i < neighborRows4.length; i++) {
            int neighborRow = row + neighborRows4[i];
            int neighborColumn = column + neighborColumns4[i];
            if (isValid(solution, neighborRow, neighborColumn) && solution[neighborRow][neighborColumn] == '.') {
                SearchResult nextSearchResult = checkNumberComponent(grid, solution, neighborRows4, neighborColumns4,
                        neighborRow, neighborColumn);
                searchResult.componentSize += nextSearchResult.componentSize;
                searchResult.numbers.addAll(nextSearchResult.numbers);
            }
        }
        return searchResult;
    }

    private static boolean areUnshadedNotConnectedWithEdge(char[][] candidateSolution, int[] neighborRows8,
                                                           int[] neighborColumns8) {
        char[][] copySolution = copyArray(candidateSolution);

        for (int row = 0; row < candidateSolution.length; row++) {
            for (int column = 0; column < copySolution[0].length; column++) {
                if (copySolution[row][column] == '.') {
                    boolean isConnectedWithEdge = isConnectedWithEdge(copySolution, neighborRows8, neighborColumns8,
                            row, column);
                    if (!isConnectedWithEdge) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isConnectedWithEdge(char[][] solution, int[] neighborRows8, int[] neighborColumns8, int row,
                                               int column) {
        boolean isConnected = false;
        solution[row][column] = '#';

        for (int i = 0; i < neighborRows8.length; i++) {
            int neighborRow = row + neighborRows8[i];
            int neighborColumn = column + neighborColumns8[i];
            if (isValid(solution, neighborRow, neighborColumn)) {
                if (solution[neighborRow][neighborColumn] == '.') {
                    boolean nextIsConnected = isConnectedWithEdge(solution, neighborRows8, neighborColumns8,
                            neighborRow, neighborColumn);
                    isConnected |= nextIsConnected;
                }
            } else {
                isConnected = true;
            }
        }
        return isConnected;
    }

    private static boolean doAll2By2SquaresHaveUnshades(char[][] solution) {
        for (int row = 0; row < solution.length - 1; row++) {
            for (int column = 0; column < solution[0].length - 1; column++) {
                if (solution[row][column] != '.' && solution[row + 1][column] != '.'
                        && solution[row][column + 1] != '.' && solution[row + 1][column + 1] != '.') {
                    return false;
                }
            }
        }
        return true;
    }

    private static char[][] copyArray(char[][] array) {
        char[][] copyArray = new char[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, copyArray[i], 0, array[i].length);
        }
        return copyArray;
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
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
