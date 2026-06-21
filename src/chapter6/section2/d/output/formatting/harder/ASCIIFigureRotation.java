package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/06/2026.
 */
public class ASCIIFigureRotation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int lines = FastReader.nextInt();
        int caseId = 1;

        while (lines != 0) {
            if (caseId > 1) {
                outputWriter.printLine();
            }

            char[][] image = new char[100][100];
            for (int i = 0; i < image.length; i++) {
                Arrays.fill(image[i], '*');
            }
            for (int i = 0; i < lines; i++) {
                String values = FastReader.getLine();
                for (int column = 0; column < values.length(); column++) {
                    image[i][column] = values.charAt(column);
                }
            }

            char[][] rotatedImage = rotateImage(image);
            printImage(rotatedImage, outputWriter);

            caseId++;
            lines = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static char[][] rotateImage(char[][] image) {
        char[][] rotatedImage = new char[100][100];
        for (int row = 0; row < image.length; row++) {
            for (int column = 0; column < image[row].length; column++) {
                int rotatedRow = column;
                int rotatedColumn = image[row].length - 1 - row;

                char newSymbol = image[row][column];
                if (image[row][column] == '|') {
                    newSymbol = '-';
                } else if (image[row][column] == '-') {
                    newSymbol = '|';
                }
                rotatedImage[rotatedRow][rotatedColumn] = newSymbol;
            }
        }
        return rotatedImage;
    }

    private static void printImage(char[][] rotatedImage, OutputWriter outputWriter) {
        int startColumn = 100;
        for (int row = 0; row < rotatedImage.length; row++) {
            if (isImageFinished(rotatedImage[row])) {
                break;
            }
            for (int column = 0; column < rotatedImage[row].length; column++) {
                if (rotatedImage[row][column] == '*') {
                    if (isSpace(rotatedImage[row], column) && column >= startColumn) {
                        outputWriter.print(' ');
                    }
                } else if (rotatedImage[row][column] == ' ') {
                    if (isSpace(rotatedImage[row], column)) {
                        outputWriter.print(' ');
                    }
                } else {
                    if (column < startColumn) {
                        startColumn = column;
                    }
                    outputWriter.print(rotatedImage[row][column]);
                }
            }
            outputWriter.printLine();
        }
    }

    private static boolean isSpace(char[] row, int index) {
        for (int i = index + 1; i < row.length; i++) {
            if (row[i] != '*' && row[i] != ' ') {
                return true;
            }
        }
        return false;
    }

    private static boolean isImageFinished(char[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] != '*') {
                return false;
            }
        }
        return true;
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