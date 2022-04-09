package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 08/04/22.
 */
public class BitMaps {

    private static class BitmapIndex {
        int value;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (!line.equals("#")) {
            List<String> words = getWords(line);
            String type = words.get(0);
            int rows = Integer.parseInt(words.get(1));
            int columns = Integer.parseInt(words.get(2));

            StringBuilder bitmap = new StringBuilder();
            line = FastReader.getLine();
            while (!line.equals("#") && !line.startsWith("B") && !line.startsWith("D ")) {
                bitmap.append(line);
                line = FastReader.getLine();
            }

            String newType = type.equals("B") ? "D" : "B";
            String bitmapInDifferentFormat = getOtherBitmapFormat(bitmap.toString(), type, rows, columns);
            outputWriter.printLine(String.format("%s%4d%4d", newType, rows, columns));
            for (int i = 0; i < bitmapInDifferentFormat.length(); i++) {
                if (i != 0 && i % 50 == 0) {
                    outputWriter.printLine();
                }
                outputWriter.print(bitmapInDifferentFormat.charAt(i));
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static String getOtherBitmapFormat(String bitmap, String type, int rows, int columns) {
        StringBuilder result = new StringBuilder();
        if (type.equals("B")) {
            getFormatDFromB(bitmap, rows, columns, result);
        } else {
            getFormatBFromD(bitmap, rows, columns, result);
        }
        return result.toString();
    }

    private static void getFormatDFromB(String bitmap, int rows, int columns, StringBuilder result) {
        int[][] bitmapArray = getBitMapArray(bitmap, rows, columns);
        getFormatDFromB(bitmapArray, result, 0, bitmapArray.length - 1, 0, bitmapArray[0].length - 1);
    }

    private static void getFormatDFromB(int[][] bitmapArray, StringBuilder result, int startRow, int endRow,
                                        int startColumn, int endColumn) {
        boolean has0 = false;
        boolean has1 = false;

        for (int row = startRow; row <= endRow; row++) {
            for (int column = startColumn; column <= endColumn; column++) {
                if (bitmapArray[row][column] == 0) {
                    has0 = true;
                } else {
                    has1 = true;
                }
            }
        }

        if (!has0) {
            result.append("1");
            return;
        } else if (!has1) {
            result.append("0");
            return;
        }
        result.append("D");
        splitMap(bitmapArray, startRow, endRow, startColumn, endColumn, result, null, null, true);
    }

    private static void splitMap(int[][] bitmapArray, int startRow, int endRow, int startColumn, int endColumn,
                                StringBuilder result, String bitmap, BitmapIndex bitmapIndex, boolean getDFormat) {
        int rows = endRow - startRow + 1;
        int columns = endColumn - startColumn + 1;

        int bottomRows = rows / 2;
        int topRows = rows - bottomRows;
        int rightColumns = columns / 2;
        int leftColumns = columns - rightColumns;

        int startColumnPlusLeftColumn = startColumn + leftColumns;
        if (topRows != 0) {
            int newEndRow = startRow + topRows - 1;
            if (getDFormat) {
                getFormatDFromB(bitmapArray, result, startRow, newEndRow, startColumn, startColumnPlusLeftColumn - 1);
            } else {
                getFormatBFromD(bitmap, bitmapIndex, bitmapArray, startRow, newEndRow, startColumn, startColumnPlusLeftColumn - 1);
            }

            if (rightColumns != 0) {
                if (getDFormat) {
                    getFormatDFromB(bitmapArray, result, startRow, newEndRow, startColumnPlusLeftColumn, startColumnPlusLeftColumn + rightColumns - 1);
                } else {
                    getFormatBFromD(bitmap, bitmapIndex, bitmapArray, startRow, newEndRow, startColumnPlusLeftColumn, startColumnPlusLeftColumn + rightColumns - 1);
                }
            }
        }
        if (bottomRows != 0) {
            int newStartRow = startRow + topRows;
            int newEndRow = newStartRow + bottomRows - 1;
            if (getDFormat) {
                getFormatDFromB(bitmapArray, result, newStartRow, newEndRow, startColumn, startColumnPlusLeftColumn - 1);
            } else {
                getFormatBFromD(bitmap, bitmapIndex, bitmapArray, newStartRow, newEndRow, startColumn, startColumnPlusLeftColumn - 1);
            }

            if (rightColumns != 0) {
                if (getDFormat) {
                    getFormatDFromB(bitmapArray, result, newStartRow, newEndRow, startColumnPlusLeftColumn, startColumnPlusLeftColumn + rightColumns - 1);
                } else {
                    getFormatBFromD(bitmap, bitmapIndex, bitmapArray, newStartRow, newEndRow, startColumnPlusLeftColumn, startColumnPlusLeftColumn + rightColumns - 1);
                }
            }
        }
    }

    private static int[][] getBitMapArray(String bitmap, int rows, int columns) {
        int[][] bitmapArray = new int[rows][columns];
        int bitmapArrayIndex = 0;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                char character = bitmap.charAt(bitmapArrayIndex++);
                bitmapArray[row][column] = Character.getNumericValue(character);
            }
        }
        return bitmapArray;
    }

    private static void getFormatBFromD(String bitmap, int rows, int columns, StringBuilder result) {
        int[][] bitmapArray = new int[rows][columns];
        getFormatBFromD(bitmap, new BitmapIndex(), bitmapArray, 0, bitmapArray.length - 1, 0, bitmapArray[0].length - 1);
        getResult(bitmapArray, result);
    }

    private static void getFormatBFromD(String bitmap, BitmapIndex bitmapIndex, int[][] bitmapArray,
                                        int startRow, int endRow, int startColumn, int endColumn) {
        char character = bitmap.charAt(bitmapIndex.value);
        bitmapIndex.value++;
        if (character == '0') {
            fillBitmap(bitmapArray, startRow, endRow, startColumn, endColumn, 0);
        } else if (character == '1') {
            fillBitmap(bitmapArray, startRow, endRow, startColumn, endColumn, 1);
        } else {
            splitMap(bitmapArray, startRow, endRow, startColumn, endColumn, null, bitmap, bitmapIndex, false);
        }
    }

    private static void fillBitmap(int[][] bitmapArray, int startRow, int endRow, int startColumn, int endColumn,
                                   int value) {
        for (int row = startRow; row <= endRow; row++) {
            for (int column = startColumn; column <= endColumn; column++) {
                bitmapArray[row][column] = value;
            }
        }
    }

    private static void getResult(int[][] bitmapArray, StringBuilder result) {
        for (int[] row : bitmapArray) {
            for (int column = 0; column < bitmapArray[0].length; column++) {
                result.append(row[column]);
            }
        }
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
