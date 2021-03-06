package chapter2.section2.h.bit.manipulation;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/03/21.
 */
public class Deathstar {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int matrixSize = FastReader.nextInt();
        int[][] matrix = new int[matrixSize][matrixSize];

        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[0].length; column++) {
                matrix[row][column] = FastReader.nextInt();
            }
        }

        int[] code = getCode(matrix);
        printCode(code);
    }

    private static int[] getCode(int[][] matrix) {
        int[] code = new int[matrix.length];

        for (int row = 0; row < matrix.length; row++) {
            for (int column = row + 1; column < matrix[0].length; column++) {
                setBitsBasedOnNumber(code, row, column, matrix[row][column]);
            }
        }
        return code;
    }

    private static void setBitsBasedOnNumber(int[] code, int index1, int index2, int referenceNumber) {
        int mask = 1;

        while (mask <= referenceNumber) {
            if ((mask & referenceNumber) > 0) {
                code[index1] |= mask;
                code[index2] |= mask;
            }
            mask <<= 1;
        }
    }

    private static void printCode(int[] code) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        for (int i = 0; i < code.length; i++) {
            outputWriter.print(code[i]);
            if (i < code.length - 1) {
                outputWriter.print(" ");
            }
        }
        outputWriter.printLine();
        outputWriter.flush();
        outputWriter.close();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
