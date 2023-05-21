package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/05/23.
 */
public class Ads {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int height = FastReader.nextInt();
        int width = FastReader.nextInt();
        char[][] webpage = new char[height][width];

        for (int row = 0; row < height; row++) {
            webpage[row] = FastReader.getLine().toCharArray();
        }

        removeAds(webpage);
        for (int row = 0; row < webpage.length; row++) {
            for (int column = 0; column < webpage[0].length; column++) {
                outputWriter.print(webpage[row][column]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static void removeAds(char[][] webpage) {
        boolean[][] visited = new boolean[webpage.length][webpage[0].length];

        for (int row = 0; row < webpage.length; row++) {
            for (int column = 0; column < webpage[0].length; column++) {
                if (webpage[row][column] == '+' && !visited[row][column]) {
                    analyzeSection(webpage, visited, row, column);
                }
            }
        }
    }

    private static boolean analyzeSection(char[][] webpage, boolean[][] visited, int row, int column) {
        boolean hasAds = false;

        if (webpage[row][column] == '+') {
            if (!visited[row][column]) {
                int endColumn = computeEndColumn(webpage, visited, row, column);
                int endRow = computeEndRow(webpage, visited, row, column);
                markRowVisited(visited, endRow, column, endColumn);
                markColumnVisited(visited, row, endRow, endColumn);
                boolean adRemoved = false;

                for (int internalRow = row + 1; internalRow <= endRow; internalRow++) {
                    for (int internalColumn = column; internalColumn <= endColumn; internalColumn++) {
                        boolean hasAdsInternal = analyzeSection(webpage, visited, internalRow, internalColumn);
                        if (hasAdsInternal) {
                            removeAd(webpage, visited, row, column, endRow, endColumn);
                            adRemoved = true;
                            break;
                        }
                    }
                    if (adRemoved) {
                        break;
                    }
                }
            }
        } else {
            visited[row][column] = true;
            if (isCharacterAd(webpage[row][column])) {
                return true;
            }

            if (isValid(webpage, row, column + 1) && !visited[row][column + 1]) {
                hasAds = analyzeSection(webpage, visited, row, column + 1);
            }
            if (isValid(webpage, row + 1, column) && !visited[row + 1][column]) {
                hasAds |= analyzeSection(webpage, visited, row + 1, column);
            }
        }
        return hasAds;
    }

    private static void removeAd(char[][] webpage, boolean[][] visited, int startRow, int startColumn, int endRow,
                                 int endColumn) {
        for (int row = startRow; row <= endRow; row++) {
            for (int column = startColumn; column <= endColumn; column++) {
                webpage[row][column] = ' ';
                visited[row][column] = true;
            }
        }
    }

    private static boolean isCharacterAd(char character) {
        return !Character.isLetterOrDigit(character)
                && character != '?'
                && character != '!'
                && character != ','
                && character != '.'
                && character != ' '
                && character != '+';
    }

    private static int computeEndColumn(char[][] webpage, boolean[][] visited, int row, int column) {
        while (column < webpage[row].length && webpage[row][column] == '+') {
            visited[row][column] = true;
            column++;
        }
        return column - 1;
    }

    private static int computeEndRow(char[][] webpage, boolean[][] visited, int row, int column) {
        while (row < webpage.length && webpage[row][column] == '+') {
            visited[row][column] = true;
            row++;
        }
        return row - 1;
    }

    private static void markRowVisited(boolean[][] visited, int row, int startColumn, int endColumn) {
        for (int column = startColumn; column <= endColumn; column++) {
            visited[row][column] = true;
        }
    }

    private static void markColumnVisited(boolean[][] visited, int startRow, int endRow, int column) {
        for (int row = startRow; row <= endRow; row++) {
            visited[row][column] = true;
        }
    }

    private static boolean isValid(char[][] webpage, int row, int column) {
        return row >= 0 && row < webpage.length && column >= 0 && column < webpage[0].length;
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
