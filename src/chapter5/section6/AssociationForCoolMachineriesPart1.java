package chapter5.section6;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 29/03/26.
 */
public class AssociationForCoolMachineriesPart1 {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return row == cell.row && column == cell.column;
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        int dimension = inputReader.nextInt();
        char[] program = inputReader.nextLine().toCharArray();
        char[][] grid = new char[dimension][dimension];
        Cell robotStartCell = null;
        for (int row = 0; row < grid.length; row++) {
            String values = inputReader.nextLine();
            for (int column = 0; column < grid[0].length; column++) {
                char symbol = values.charAt(column);
                if (symbol == 'R') {
                    robotStartCell = new Cell(row, column);
                }
                grid[row][column] = symbol;
            }
        }

        int result = analyseTrail(grid, program, robotStartCell);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static int analyseTrail(char[][] grid, char[] program, Cell robotCell) {
        boolean[][][] visited = new boolean[grid.length][grid[0].length][program.length];
        int[][][] stepIndex = new int[grid.length][grid[0].length][program.length];
        int currentRow = robotCell.row;
        int currentColumn = robotCell.column;
        int moveIndex = 0;
        int validMovesIndex = 0;
        List<Cell> cellList = new ArrayList<>();
        Cell currentCell = robotCell;

        while (!visited[currentRow][currentColumn][moveIndex]) {
            visited[currentRow][currentColumn][moveIndex] = true;
            stepIndex[currentRow][currentColumn][moveIndex] = validMovesIndex;
            int movesStuck = 0;

            while (true) {
                Cell nextCell = getNextCell(currentCell, program[moveIndex]);
                if (grid[nextCell.row][nextCell.column] == '#') {
                    movesStuck++;
                    moveIndex = (moveIndex + 1) % program.length;
                    if (movesStuck == program.length) {
                        return 1;
                    }
                } else {
                    currentRow = nextCell.row;
                    currentColumn = nextCell.column;
                    validMovesIndex++;
                    cellList.add(nextCell);

                    moveIndex = (moveIndex + 1) % program.length;
                    currentCell = nextCell;
                    break;
                }
            }
        }
        int cycleStartIndex = stepIndex[currentRow][currentColumn][moveIndex];
        int candidateCycleSize = validMovesIndex - cycleStartIndex;
        if (candidateCycleSize == 1) {
            return 1;
        }
        return computeCycleSize(cellList, cycleStartIndex);
    }

    private static int computeCycleSize(List<Cell> cellList, int cycleStartIndex) {
        for (int size = 1; size < cellList.size() + 1; size++) {
            boolean isCycle = true;
            for (int index = cycleStartIndex + size; index < cellList.size(); index++) {
                if (!cellList.get(index).equals(cellList.get(index - size))) {
                    isCycle = false;
                    break;
                }
            }

            if (isCycle) {
                return size;
            }
        }
        return 1;
    }

    private static Cell getNextCell(Cell cell, char move) {
        int row = cell.row;
        int column = cell.column;
        if (move == '<') {
            column--;
        } else if (move == '>') {
            column++;
        } else if (move == '^') {
            row--;
        } else {
            row++;
        }
        return new Cell(row, column);
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String nextLine() throws IOException {
            int c = snext();
            while (isSpaceChar(c))
                c = snext();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
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
