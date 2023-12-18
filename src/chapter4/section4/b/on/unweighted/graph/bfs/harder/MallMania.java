package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 15/12/23.
 */
public class MallMania {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class State {
        Cell cell;
        int distance;

        public State(Cell cell, int distance) {
            this.cell = cell;
            this.distance = distance;
        }
    }

    private static final int MAX_DIMENSION = 2001;
    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReaderInteger fastReader = new FastReaderInteger();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int perimeterSource = fastReader.nextInt();
        int[][] visited = new int[MAX_DIMENSION][MAX_DIMENSION];
        int[][] destinations = new int[MAX_DIMENSION][MAX_DIMENSION];
        int visitID = 1;
        int destinationID = 1000001;

        while (perimeterSource != 0) {
            Cell[] sources = new Cell[perimeterSource];
            for (int i = 0; i < sources.length; i++) {
                sources[i] = new Cell(fastReader.nextInt(), fastReader.nextInt());
            }

            int perimeterDestination = fastReader.nextInt();
            for (int i = 0; i < perimeterDestination; i++) {
                int destinationRow = fastReader.nextInt();
                int destinationColumn = fastReader.nextInt();
                destinations[destinationRow][destinationColumn] = destinationID;
            }

            int minimumDistance = computeMinimumDistance(sources, destinations, visited, visitID, destinationID);
            outputWriter.printLine(minimumDistance);

            visitID++;
            destinationID++;
            perimeterSource = fastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMinimumDistance(Cell[] sources, int[][] destinations, int[][] visited,
                                              int visitID, int destinationID) {
        Queue<State> queue = new LinkedList<>();
        for (Cell source : sources) {
            queue.offer(new State(source, 0));
            visited[source.row][source.column] = visitID;
        }

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;
            if (destinations[cell.row][cell.column] == destinationID) {
                return state.distance;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(visited, neighborRow, neighborColumn)
                        && visited[neighborRow][neighborColumn] != visitID) {
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    visited[neighborRow][neighborColumn] = visitID;
                    queue.offer(new State(nextCell, state.distance + 1));
                }
            }
        }
        return -1;
    }

    private static boolean isValid(int[][] visited, int row, int column) {
        return row >= 0 && row < visited.length && column >= 0 && column < visited[0].length;
    }

    private static class FastReaderInteger {
        private static final InputStream in = System.in;
        private static final int bufferSize = 30000;
        private static final byte[] buffer = new byte[bufferSize];
        private static int position = 0;
        private static int byteCount = bufferSize;
        private static byte character;

        FastReaderInteger() throws IOException {
            fill();
        }

        private void fill() throws IOException {
            byteCount = in.read(buffer, 0, bufferSize);
        }

        private int nextInt() throws IOException {
            while (character < '-') {
                character = readByte();
            }
            boolean isNegative = (character == '-');
            if (isNegative) {
                character = readByte();
            }
            int value = character - '0';
            while ((character = readByte()) >= '0' && character <= '9') {
                value = value * 10 + character - '0';
            }
            return isNegative ? -value : value;
        }

        private byte readByte() throws IOException {
            if (position == byteCount) {
                fill();
                position = 0;
            }
            return buffer[position++];
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
