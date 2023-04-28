package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/04/23.
 */
public class StickerCollectorRobot {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int instructionsLength = FastReader.nextInt();

        while (rows != 0 || columns != 0 || instructionsLength != 0) {
            char[][] arena = new char[rows][columns];
            char initialOrientation = 'X';
            int robotRow = 0;
            int robotColumn = 0;

            for (int row = 0; row < arena.length; row++) {
                arena[row] = FastReader.next().toCharArray();
                for (int column = 0; column < arena[row].length; column++) {
                    if (arena[row][column] == 'N' || arena[row][column] == 'S' || arena[row][column] == 'L'
                            || arena[row][column] == 'O') {
                        initialOrientation = arena[row][column];
                        robotRow = row;
                        robotColumn = column;
                    }
                }
            }
            String instructions = FastReader.next();
            int stickersCollected = countStickersCollected(arena, instructions, robotRow, robotColumn,
                    initialOrientation);
            outputWriter.printLine(stickersCollected);

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
            instructionsLength = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int countStickersCollected(char[][] arena, String instructions, int robotRow, int robotColumn,
                                              char orientation) {
        int stickersCollected = 0;
        if (arena[robotRow][robotColumn] == '*') {
            stickersCollected++;
            arena[robotRow][robotColumn] = '.';
        }

        for (char instruction : instructions.toCharArray()) {
            switch (instruction) {
                case 'D': orientation = getNextOrientation(orientation, true); break;
                case 'E': orientation = getNextOrientation(orientation, false); break;
                case 'F':
                    int nextRow = robotRow;
                    int nextColumn = robotColumn;
                    switch (orientation) {
                        case 'N': nextRow--; break;
                        case 'L': nextColumn++; break;
                        case 'S': nextRow++; break;
                        default: nextColumn--; break;
                    }
                    if (isValid(arena, nextRow, nextColumn) && arena[nextRow][nextColumn] != '#') {
                        robotRow = nextRow;
                        robotColumn = nextColumn;
                    }
                    if (arena[robotRow][robotColumn] == '*') {
                        stickersCollected++;
                        arena[robotRow][robotColumn] = '.';
                    }
            }
        }
        return stickersCollected;
    }

    private static char getNextOrientation(char orientation, boolean isRight) {
        char nextOrientation;
        switch (orientation) {
            case 'N': nextOrientation = isRight ? 'L' : 'O'; break;
            case 'L': nextOrientation = isRight ? 'S' : 'N'; break;
            case 'S': nextOrientation = isRight ? 'O' : 'L'; break;
            default: nextOrientation = isRight ? 'N' : 'S'; break;
        }
        return nextOrientation;
    }

    private static boolean isValid(char[][] arena, int row, int column) {
        return row >= 0 && row < arena.length && column >= 0 && column < arena[0].length;
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
