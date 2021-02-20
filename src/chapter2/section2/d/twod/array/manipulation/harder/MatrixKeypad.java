package chapter2.section2.d.twod.array.manipulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/02/21.
 */
public class MatrixKeypad {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            int[][] keypad = new int[rows][columns];

            for (int row = 0; row < keypad.length; row++) {
                String rowValue = FastReader.next();
                for (int column = 0; column < keypad[0].length; column++) {
                    keypad[row][column] = Character.getNumericValue(rowValue.charAt(column));
                }
            }

            char[][] keypadData = interpretKeypad(keypad);
            if (keypadData == null) {
                outputWriter.printLine("impossible");
            } else {
                printKeypad(keypadData, outputWriter);
            }
            outputWriter.printLine("----------");
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static char[][] interpretKeypad(int[][] keypad) {
        char[][] keypadData = new char[keypad.length][keypad[0].length];
        int[] buttonsPressedOnRows = new int[keypad.length];
        int[] buttonsPressedOnColumns = new int[keypad[0].length];

        for (int row = 0; row < keypad.length; row++) {
            for (int column = 0; column < keypad[0].length; column++) {
                if (keypad[row][column] == 1) {
                    buttonsPressedOnRows[row]++;
                    buttonsPressedOnColumns[column]++;
                }
            }
        }

        for (int row = 0; row < keypad.length; row++) {
            for (int column = 0; column < keypad[0].length; column++) {
                if (keypad[row][column] == 0) {
                    if (buttonsPressedOnRows[row] == 0 || buttonsPressedOnColumns[column] == 0) {
                        keypadData[row][column] = 'N';
                    } else {
                        return null;
                    }
                } else {
                    if (buttonsPressedOnRows[row] == 1 || buttonsPressedOnColumns[column] == 1) {
                        keypadData[row][column] = 'P';
                    } else {
                        keypadData[row][column] = 'I';
                    }
                }
            }
        }
        return keypadData;
    }

    private static void printKeypad(char[][] keypadData, OutputWriter outputWriter) {
        for (int row = 0; row < keypadData.length; row++) {
            for (int column = 0; column < keypadData[0].length; column++) {
                outputWriter.print(keypadData[row][column]);
            }
            outputWriter.printLine();
        }
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
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
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
