package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/03/23.
 */
public class VinDiagrams {

    private static class Result {
        int areaA;
        int areaB;
        int areaIntersection;

        public Result(int areaA, int areaB, int areaIntersection) {
            this.areaA = areaA;
            this.areaB = areaB;
            this.areaIntersection = areaIntersection;
        }
    }

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static final int[] neighborRows4 = { -1, 0, 0, 1 };
    private static final int[] neighborColumns4 = { 0, -1, 1, 0 };
    private static final char EMPTY = '.';
    private static final char BORDER = 'X';
    private static final char A_SECTION = 'A';
    private static final char B_SECTION = 'B';
    private static final char A_SECTION_VISITED = 'a';
    private static final char B_SECTION_VISITED = 'b';
    private static final char INTERSECTION = 'I';
    private static final char VISITED = 'V';

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        char[][] diagram = new char[rows][columns];
        for (int row = 0; row < diagram.length; row++) {
            diagram[row] = FastReader.next().toCharArray();
        }

        Result areas = computeAreas(diagram);
        outputWriter.printLine(String.format("%d %d %d", areas.areaA, areas.areaB, areas.areaIntersection));
        outputWriter.flush();
    }

    private static Result computeAreas(char[][] diagram) {
        computeIntersections(diagram);

        // Mark borders
        for (int row = 0; row < diagram.length; row++) {
            for (int column = 0; column < diagram[0].length; column++) {
                if (diagram[row][column] == A_SECTION || diagram[row][column] == B_SECTION) {
                    char visitMark = (diagram[row][column] == A_SECTION) ? A_SECTION_VISITED : B_SECTION_VISITED;
                    floodFillToMarkBorders(diagram, visitMark, row, column);
                }
            }
        }

        // Mark outside areas
        char[][] copyDiagram = copyDiagram(diagram);
        markOutsideAreas(diagram, A_SECTION_VISITED);
        markOutsideAreas(copyDiagram, B_SECTION_VISITED);

        int areaSectionA = 0;
        int areaSectionB = 0;
        int areaIntersection = 0;

        for (int row = 0; row < diagram.length; row++) {
            for (int column = 0; column < diagram[0].length; column++) {
                if (diagram[row][column] == EMPTY && copyDiagram[row][column] == EMPTY) {
                    areaIntersection++;
                } else if (diagram[row][column] == EMPTY) {
                    areaSectionA++;
                } else if (copyDiagram[row][column] == EMPTY) {
                    areaSectionB++;
                }
            }
        }
        return new Result(areaSectionA, areaSectionB,areaIntersection);
    }

    private static void computeIntersections(char[][] diagram) {
        for (int row = 0; row < diagram.length; row++) {
            for (int column = 0; column < diagram[0].length; column++) {
                if (diagram[row][column] != BORDER) {
                    continue;
                }
                int nonEmptyNeighbors = 0;

                for (int i = 0; i < neighborRows4.length; i++) {
                    int neighborRow = row + neighborRows4[i];
                    int neighborColumn = column + neighborColumns4[i];

                    if (isValid(diagram, neighborRow, neighborColumn)
                            && diagram[neighborRow][neighborColumn] == BORDER) {
                        nonEmptyNeighbors++;
                    }
                }

                if (nonEmptyNeighbors == 4) {
                    diagram[row][column] = INTERSECTION;
                }
            }
        }
    }

    private static void floodFillToMarkBorders(char[][] diagram, char visitMark, int row, int column) {
        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(row, column));
        diagram[row][column] = visitMark;

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            for (int i = 0; i < neighborRows4.length; i++) {
                int neighborRow = cell.row + neighborRows4[i];
                int neighborColumn = cell.column + neighborColumns4[i];

                if (isValid(diagram, neighborRow, neighborColumn)) {
                    if (diagram[neighborRow][neighborColumn] == INTERSECTION) {
                        neighborRow += neighborRows4[i];
                        neighborColumn += neighborColumns4[i];
                    }

                    if (diagram[neighborRow][neighborColumn] == BORDER) {
                        diagram[neighborRow][neighborColumn] = visitMark;
                        queue.offer(new Cell(neighborRow, neighborColumn));
                    }
                }
            }
        }
    }

    private static char[][] copyDiagram(char[][] diagram) {
        char[][] copyDiagram = new char[diagram.length][diagram[0].length];
        for (int row = 0; row < diagram.length; row++) {
            System.arraycopy(diagram[row], 0, copyDiagram[row], 0, diagram[row].length);
        }
        return copyDiagram;
    }

    private static void markOutsideAreas(char[][] diagram, char section) {
        for (int row = 0; row < diagram.length; row++) {
            floodFillOutsideArea(diagram, section, row, 0);
            floodFillOutsideArea(diagram, section, row, diagram[0].length - 1);
        }
        for (int column = 0; column < diagram[0].length; column++) {
            floodFillOutsideArea(diagram, section, 0, column);
            floodFillOutsideArea(diagram, section, diagram.length - 1, column);
        }
    }

    private static void floodFillOutsideArea(char[][] diagram, char section, int row, int column) {
        if (diagram[row][column] != EMPTY) {
            return;
        }
        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(row, column));
        diagram[row][column] = VISITED;

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            for (int i = 0; i < neighborRows4.length; i++) {
                int neighborRow = cell.row + neighborRows4[i];
                int neighborColumn = cell.column + neighborColumns4[i];

                if (isValid(diagram, neighborRow, neighborColumn)
                        && diagram[neighborRow][neighborColumn] != VISITED
                        && diagram[neighborRow][neighborColumn] != INTERSECTION
                        && diagram[neighborRow][neighborColumn] != section) {
                    diagram[neighborRow][neighborColumn] = VISITED;
                    queue.offer(new Cell(neighborRow, neighborColumn));
                }
            }
        }
    }

    private static boolean isValid(char[][] diagram, int row, int column) {
        return row >= 0 && row < diagram.length && column >= 0 && column < diagram[0].length;
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
