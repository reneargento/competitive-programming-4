package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 26/06/2026.
 */
public class PathTracing {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String move = FastReader.getLine();
        int minRow = 502;
        int maxRow = 502;
        int minColumn = 502;
        int maxColumn = 502;
        char[][] map = new char[1005][1005];
        for (int row = 0; row < map.length; row++) {
            Arrays.fill(map[row], ' ');
        }
        int row = 502;
        int column = 502;
        map[row][column] = 'S';

        while (move != null) {
            if (move.equals("up")) {
                row--;
                minRow = Math.min(minRow, row);
            } else if (move.equals("down")) {
                row++;
                maxRow = Math.max(maxRow, row);
            } else if (move.equals("left")) {
                column--;
                minColumn = Math.min(minColumn, column);
            } else {
                column++;
                maxColumn = Math.max(maxColumn, column);
            }
            if (map[row][column] != 'S') {
                map[row][column] = '*';
            }
            move = FastReader.getLine();
        }
        map[row][column] = 'E';
        printMap(map, minRow, maxRow, minColumn, maxColumn, outputWriter);
        outputWriter.flush();
    }

    private static void printMap(char[][] map, int minRow, int maxRow, int minColumn, int maxColumn,
                                 OutputWriter outputWriter) {
        for (int row = minRow - 1; row <= maxRow + 1; row++) {
            map[row][minColumn - 1] = '#';
            map[row][maxColumn + 1] = '#';
        }
        for (int column = minColumn - 1; column <= maxColumn + 1; column++) {
            map[minRow - 1][column] = '#';
            map[maxRow + 1][column] = '#';
        }

        for (int row = minRow - 1; row <= maxRow + 1; row++) {
            for (int column = minColumn - 1; column <= maxColumn + 1; column++) {
                outputWriter.print(map[row][column]);
            }
            outputWriter.printLine();
        }
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