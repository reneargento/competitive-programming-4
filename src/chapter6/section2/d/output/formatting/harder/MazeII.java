package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/06/2026.
 */
public class MazeII {

    private static class MazeCell {
        boolean[] walls = new boolean[2];

        public MazeCell() {
            Arrays.fill(walls, true);
        }
    }

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return row == cell.row && column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    private static final int DOWN = 0;
    private static final int LEFT = 1;
    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        FastReader.getLine();
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split("\\s+");
            int rows = Integer.parseInt(data[0]);
            int columns = Integer.parseInt(data[1]);
            int row = rows - FastReader.nextInt();
            int column = FastReader.nextInt() - 1;
            Set<Cell> visitedCells = new HashSet<>();
            List<Cell> cells = new ArrayList<>();
            Cell startCell = new Cell(row, column);
            cells.add(startCell);
            visitedCells.add(startCell);

            MazeCell[][] maze = new MazeCell[rows][columns];
            for (int r = 0; r < maze.length; r++) {
                for (int c = 0; c < maze[r].length; c++) {
                    maze[r][c] = new MazeCell();
                }
            }

            line = FastReader.getLine();
            while (line != null && Character.isLetter(line.charAt(0))) {
                removeNoNeighborCells(maze, visitedCells, cells);

                cells = processCommand(maze, cells, visitedCells, line);
                line = FastReader.getLine();
                if (cells.isEmpty()) {
                    break;
                }
            }
            printMaze(maze, outputWriter);
        }
        outputWriter.flush();
    }

    private static void removeNoNeighborCells(MazeCell[][] maze, Set<Cell> visitedCells, List<Cell> cells) {
        List<Integer> cellsIndexesToDelete = new ArrayList<>();

        for (int i = cells.size() - 1; i >= 0; i--) {
            Cell cell = cells.get(i);

            boolean hasNeighbor = false;
            for (int n = 0; n < neighborRows.length; n++) {
                int neighborRow = cell.row + neighborRows[n];
                int neighborColumn = cell.column + neighborColumns[n];

                if (isValid(maze, neighborRow, neighborColumn)) {
                    Cell neighborCell = new Cell(neighborRow, neighborColumn);
                    if (!visitedCells.contains(neighborCell)) {
                        hasNeighbor = true;
                        break;
                    }
                }
            }

            if (!hasNeighbor) {
                cellsIndexesToDelete.add(i);
            } else {
                break;
            }
        }

        cellsIndexesToDelete.sort(Collections.reverseOrder());
        for (int index : cellsIndexesToDelete) {
            cells.remove(index);
        }
    }

    private static List<Cell> processCommand(MazeCell[][] maze, List<Cell> cells, Set<Cell> visitedCells,
                                             String command) {
        if (command.charAt(0) == 'F') {
            String[] data = command.split("\\s+");
            int flipIndex = Integer.parseInt(data[1]);
            return flipCells(cells, flipIndex);
        }

        Cell cell = cells.get(cells.size() - 1);
        switch (command) {
            case "U": {
                maze[cell.row - 1][cell.column].walls[DOWN] = false;
                Cell upCell = new Cell(cell.row - 1, cell.column);
                cells.add(upCell);
                visitedCells.add(upCell);
            }
            break;
            case "D": {
                maze[cell.row][cell.column].walls[DOWN] = false;
                Cell downCell = new Cell(cell.row + 1, cell.column);
                cells.add(downCell);
                visitedCells.add(downCell);
            }
            break;
            case "L": {
                maze[cell.row][cell.column].walls[LEFT] = false;
                Cell leftCell = new Cell(cell.row, cell.column - 1);
                cells.add(leftCell);
                visitedCells.add(leftCell);
            }
            break;
            case "R": {
                maze[cell.row][cell.column + 1].walls[LEFT] = false;
                Cell rightCell = new Cell(cell.row, cell.column + 1);
                cells.add(rightCell);
                visitedCells.add(rightCell);
            }
        }
        return cells;
    }

    private static void printMaze(MazeCell[][] maze, OutputWriter outputWriter) {
        if (maze.length == 0) {
            return;
        }
        for (int column = 0; column < maze[0].length; column++) {
            outputWriter.print(" _");
        }
        outputWriter.printLine();

        for (int row = 0; row < maze.length; row++) {
            for (int column = 0; column < maze[row].length; column++) {
                MazeCell mazeCell = maze[row][column];
                if (mazeCell.walls[LEFT]) {
                    outputWriter.print("|");
                } else {
                    outputWriter.print(" ");
                }

                if (mazeCell.walls[DOWN]) {
                    outputWriter.print("_");
                } else {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine("|");
        }
        outputWriter.printLine();
    }

    private static List<Cell> flipCells(List<Cell> cells, int flipIndex) {
        List<Cell> flippedCells = new ArrayList<>();
        for (int i = 0; i < flipIndex - 1; i++) {
            flippedCells.add(cells.get(i));
        }

        for (int i = cells.size() - 1; i >= flipIndex - 1; i--) {
            flippedCells.add(cells.get(i));
        }
        return flippedCells;
    }

    private static boolean isValid(MazeCell[][] maze, int row, int column) {
        return row >= 0 && row < maze.length && column >= 0 && column < maze[0].length;
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