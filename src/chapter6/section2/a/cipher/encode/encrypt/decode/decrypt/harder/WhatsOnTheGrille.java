package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/04/26.
 */
public class WhatsOnTheGrille {

    private static class Cell implements Comparable<Cell> {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int compareTo(Cell other) {
            if (row != other.row) {
                return Integer.compare(row, other.row);
            }
            return Integer.compare(column, other.column);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int gridSize = FastReader.nextInt();
        List<Cell> holes = new ArrayList<>();

        for (int row = 0; row < gridSize; row++) {
            String columns = FastReader.next();
            for (int column = 0; column < gridSize; column++) {
                char value = columns.charAt(column);
                if (value == '.') {
                    holes.add(new Cell(row, column));
                }
            }
        }
        String encryptedMessage = FastReader.next();

        String decryptedMessage = decryptMessage(gridSize, holes, encryptedMessage);
        if (decryptedMessage == null) {
            outputWriter.printLine("invalid grille");
        } else {
            outputWriter.printLine(decryptedMessage);
        }
        outputWriter.flush();
    }

    private static String decryptMessage(int gridSize, List<Cell> holes, String encryptedMessage) {
        int totalCells = gridSize * gridSize;
        if (holes.size() != totalCells / 4) {
            return null;
        }
        boolean[][] used = new boolean[gridSize][gridSize];
        char[][] messageGrid = buildMessageGrid(gridSize, holes, encryptedMessage, used);

        for (int row = 0; row < used.length; row++) {
            for (int column = 0; column < used[row].length; column++) {
                if (!used[row][column]) {
                    return null;
                }
            }
        }
        return getDecryptedMessageFromGrid(messageGrid);
    }

    private static char[][] buildMessageGrid(int gridSize, List<Cell> holes, String encryptedMessage, boolean[][] used) {
        char[][] messageGrid = new char[gridSize][gridSize];
        int encryptedMessageIndex = 0;

        for (int i = 0; i < 4 && encryptedMessageIndex < encryptedMessage.length(); i++) {
            List<Cell> nextHoles = new ArrayList<>();
            Collections.sort(holes);

            for (Cell hole : holes) {
                messageGrid[hole.row][hole.column] = encryptedMessage.charAt(encryptedMessageIndex);
                encryptedMessageIndex++;
                used[hole.row][hole.column] = true;
                nextHoles.add(rotateCell(messageGrid.length, hole));
            }
            holes = nextHoles;
        }
        return messageGrid;
    }

    private static String getDecryptedMessageFromGrid(char[][] messageGrid) {
        StringBuilder decryptedMessage = new StringBuilder();
        for (int row = 0; row < messageGrid.length; row++) {
            for (int column = 0; column < messageGrid[row].length; column++) {
                decryptedMessage.append(messageGrid[row][column]);
            }
        }
        return decryptedMessage.toString();
    }

    private static Cell rotateCell(int gridSize, Cell cell) {
        int newColumn = gridSize - cell.row - 1;
        int newRow = cell.column;
        return new Cell(newRow, newColumn);
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
