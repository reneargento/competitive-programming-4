package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/03/23.
 */
public class YouWantWhatFilled {

    private static class Hole implements Comparable<Hole> {
        char ID;
        int size;

        public Hole(char ID, int size) {
            this.ID = ID;
            this.size = size;
        }

        @Override
        public int compareTo(Hole other) {
            if (size != other.size) {
                return Integer.compare(other.size, size);
            }
            return Character.compare(ID, other.ID);
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int problemID = 1;

        while (rows != 0 || columns != 0) {
            char[][] island = new char[rows][columns];
            for (int row = 0; row < island.length; row++) {
                island[row] = FastReader.next().toCharArray();
            }

            List<Hole> holes = computeHoles(island);
            outputWriter.printLine(String.format("Problem %d:", problemID));
            for (Hole hole : holes) {
                outputWriter.printLine(hole.ID + " " + hole.size);
            }

            problemID++;
            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Hole> computeHoles(char[][] island) {
        List<Hole> holes = new ArrayList<>();

        for (int row = 0; row < island.length; row++) {
            for (int column = 0; column < island[0].length; column++) {
                if (island[row][column] != '.') {
                    char holeID = island[row][column];
                    int holeSize = floodFill(island, holeID, row, column);
                    holes.add(new Hole(holeID, holeSize));
                }
            }
        }
        Collections.sort(holes);
        return holes;
    }

    private static int floodFill(char[][] island, char holeID, int row, int column) {
        int size = 1;
        island[row][column] = '.';

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (isValid(island, neighborRow, neighborColumn)
                    && island[neighborRow][neighborColumn] == holeID) {
                size += floodFill(island, holeID, neighborRow, neighborColumn);
            }
        }
        return size;
    }

    private static boolean isValid(char[][] island, int row, int column) {
        return row >= 0 && row < island.length && column >= 0 && column < island[0].length;
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
