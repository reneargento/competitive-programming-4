package chapter2.section2.c.twod.array.manipulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/02/21.
 */
public class SpiralTap {

    private static class Cell {
        long row;
        long column;
        long position;

        public Cell(long row, long column, long position) {
            this.row = row;
            this.column = column;
            this.position = position;
        }
    }

    private enum Direction {
        UP, LEFT, DOWN, RIGHT;

        Direction getNextDirection() {
            switch (this) {
                case UP: return LEFT;
                case LEFT: return DOWN;
                case DOWN: return RIGHT;
                default: return UP;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int boardSize = FastReader.nextInt();
        long spiralPosition = FastReader.nextLong();

        while (boardSize != 0 || spiralPosition != 0) {
            Cell position = findPosition(boardSize, spiralPosition);
            outputWriter.printLine("Line = " + position.row + ", column = " + position.column + ".");

            boardSize = FastReader.nextInt();
            spiralPosition = FastReader.nextLong();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static Cell findPosition(int boardSize, long spiralPosition) {
        Cell startCell = findStartCell(boardSize, spiralPosition);
        if (startCell.position == spiralPosition) {
            return startCell;
        }

        long row = startCell.row;
        long column = startCell.column;
        long position = startCell.position;

        long increment = (long) Math.sqrt(position);

        // Move up
        position++;
        row++;
        if (position == spiralPosition) {
            return new Cell(row, column, spiralPosition);
        }

        // Move left
        Direction direction = Direction.LEFT;
        if (position + increment >= spiralPosition) {
            return getTargetCell(row, column, position, spiralPosition, direction);
        }
        position += increment;
        column -= increment;

        // Other moves
        while (true) {
            increment++;

            for (int i = 0; i < 2; i++) {
                direction = direction.getNextDirection();
                if (position + increment >= spiralPosition) {
                    return getTargetCell(row, column, position, spiralPosition, direction);
                }
                position += increment;

                switch (direction) {
                    case LEFT: column -= increment; break;
                    case DOWN: row -= increment; break;
                    case RIGHT: column += increment; break;
                    case UP: row += increment;
                }
            }
        }
    }

    private static Cell findStartCell(int boardSize, long spiralPosition) {
        long middle = (boardSize / 2) + 1;

        long sqrt = (long) Math.sqrt(spiralPosition);
        if (sqrt % 2 == 0) {
            sqrt--;
        }

        long increment = sqrt / 2;
        long position = sqrt * sqrt;

        long row = middle + increment;
        long column = middle + increment;
        return new Cell(row, column, position);
    }

    private static Cell getTargetCell(long row, long column, long position, long targetPosition, Direction direction) {
        while (position != targetPosition) {
            position++;

            switch (direction) {
                case UP: row++; break;
                case DOWN: row--; break;
                case LEFT: column--; break;
                default: column++;
            }
        }
        return new Cell(row, column, targetPosition);
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
