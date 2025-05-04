package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/05/25.
 */
public class MatrixInverse {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseId = 1;
        String line = FastReader.getLine();
        while (line != null) {
            int[][] matrix = new int[2][2];
            String[] data = line.split(" ");
            matrix[0][0] = Integer.parseInt(data[0]);
            matrix[0][1] = Integer.parseInt(data[1]);
            matrix[1][0] = FastReader.nextInt();
            matrix[1][1] = FastReader.nextInt();

            int[][] inverse = computeInverse(matrix);
            outputWriter.printLine(String.format("Case %d:", caseId));
            outputWriter.printLine(inverse[0][0] + " " + inverse[0][1]);
            outputWriter.printLine(inverse[1][0] + " " + inverse[1][1]);

            caseId++;
            FastReader.getLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[][] computeInverse(int[][] matrix) {
        int[][] inverse = new int[matrix.length][matrix[0].length];
        inverse[0][1] = -matrix[0][1];
        inverse[1][0] = -matrix[1][0];
        inverse[0][0] = matrix[1][1];
        inverse[1][1] = matrix[0][0];

        int determinant = (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
        for (int row = 0; row < inverse.length; row++) {
            for (int column = 0; column < inverse[0].length; column++) {
                inverse[row][column] /= determinant;
            }
        }
        return inverse;
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
