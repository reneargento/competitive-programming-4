package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/05/23.
 */
public class Jetpack {

    private static List<Integer> columnsToRise;

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        char[][] field = new char[10][inputReader.readInt()];

        for (int row = 0; row < 10; row++) {
            String columns = inputReader.readLine();
            field[row] = columns.toCharArray();
        }

        columnsToRise = new ArrayList<>();
        depthFirstSearch(field, field.length - 1, 0);
        outputWriter.printLine(columnsToRise.size());
        for (int i = columnsToRise.size() - 1; i >= 0; i--) {
            outputWriter.printLine(String.format("%d %d", columnsToRise.get(i), 1));
        }
        outputWriter.flush();
    }

    private static boolean depthFirstSearch(char[][] field, int row, int column) {
        if (field[row][column] == 'X') {
            return false;
        }
        if (column == field[0].length - 1) {
            return true;
        }
        field[row][column] = 'X';

        int rowBelow = Math.min(row + 1, 9);
        int rowAbove = Math.max(row - 1, 0);
        if (depthFirstSearch(field, rowBelow, column + 1)) {
            return true;
        }
        if (depthFirstSearch(field, rowAbove, column + 1)) {
            columnsToRise.add(column);
            return true;
        }
        return false;
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        // Returns null on EOF
        public String readLine() {
            int c = read();
            if (c == -1) {
                return null;
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == -1;
        }

        public String next() {
            return readString();
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
