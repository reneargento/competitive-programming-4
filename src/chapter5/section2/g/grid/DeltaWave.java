package chapter5.section2.g.grid;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/04/25.
 */
public class DeltaWave {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            String line = FastReader.getLine();
            List<String> words = getWords(line);

            int number1 = Integer.parseInt(words.get(0));
            int number2 = Integer.parseInt(words.get(1));

            long shortestDistance = computeShortestDistance(number1, number2);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(shortestDistance);
        }
        outputWriter.flush();
    }

    private static long computeShortestDistance(int number1, int number2) {
        if (number1 > number2) {
            int aux = number1;
            number1 = number2;
            number2 = aux;
        }

        Cell number1Cell = getCell(number1);
        Cell number2Cell = getCell(number2);

        if (number1Cell.row == number2Cell.row) {
            return Math.abs(number1Cell.column - number2Cell.column);
        }
        long shortestDistance = Long.MAX_VALUE;
        int rowDifference = number2Cell.row - number1Cell.row;

        if (number1Cell.column % 2 == 0) {
            int column = 1;

            while (column <= 2 * number1Cell.row - 1) {
                if (column <= number2Cell.column
                        && number2Cell.column <= column + 2 * rowDifference) {
                    if (number2Cell.column % 2 == 0) {
                        shortestDistance =
                                Math.min(shortestDistance, 2L * rowDifference
                                        + Math.abs(column - number1Cell.column) - 1);
                    } else {
                        shortestDistance =
                                Math.min(shortestDistance, 2L * rowDifference
                                        + Math.abs(column - number1Cell.column));
                    }
                }
                column += 2;
            }
        } else {
            int column = 1;

            while (column <= 2 * number2Cell.row - 1) {
                if (number1Cell.column <= column
                        && column <= number1Cell.column + 2 * rowDifference) {
                    if (column % 2 == 0) {
                        shortestDistance =
                                Math.min(shortestDistance, 2L * rowDifference
                                        + Math.abs(column - number2Cell.column) - 1);
                    } else {
                        shortestDistance =
                                Math.min(shortestDistance, 2L * rowDifference
                                        + Math.abs(column - number2Cell.column));
                    }
                }
                column++;
            }
        }
        return shortestDistance;
    }

    private static Cell getCell(int number) {
        double numberSqrt = Math.sqrt(number);
        int row = (int) Math.ceil(numberSqrt);
        int column = number - (row - 1) * (row - 1);
        return new Cell(row, column);
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
