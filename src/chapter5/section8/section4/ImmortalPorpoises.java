package chapter5.section8.section4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/04/26.
 */
public class ImmortalPorpoises {

    private static final int MOD = 1000000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            long years = FastReader.nextLong();
            long fibonacci = fibonacciNumber(years, MOD);
            outputWriter.printLine(dataSetNumber + " " + fibonacci);
        }
        outputWriter.flush();
    }

    private static long fibonacciNumber(long number, long mod) {
        if (number == 0 || number == 1) {
            return number;
        }

        long[][] initialMatrix = {
                { 0, 1 }
        };
        long[][] matrixToMultiply = {
                { 0, 1 },
                { 1, 1 }
        };
        long[][] exponentialMatrix = fastMatrixExponentiation(matrixToMultiply, number - 1, mod);
        long[][] result = matrixMultiplication(initialMatrix, exponentialMatrix, mod);
        return result[0][1] % mod;
    }

    private static long[][] fastMatrixExponentiation(long[][] matrix, long exponent, long mod) {
        if (exponent == 0) {
            long[][] identityMatrix = new long[matrix.length][matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                identityMatrix[i][i] = 1;
            }
            return identityMatrix;
        }
        if (exponent == 1) {
            return matrix;
        }

        long[][] matrixSquared = matrixMultiplication(matrix, matrix, mod);
        if (exponent % 2 == 0) {
            return fastMatrixExponentiation(matrixSquared, exponent / 2, mod);
        } else {
            return (matrixMultiplication(matrix,
                    fastMatrixExponentiation(matrixSquared, exponent / 2, mod), mod));
        }
    }

    private static long[][] matrixMultiplication(long[][] matrix1, long[][] matrix2, long mod) {
        long[][] result = new long[matrix1.length][matrix2[0].length];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                for (int k = 0; k < matrix1[0].length; k++) {
                    result[i][j] = (result[i][j] + matrix1[i][k] * matrix2[k][j]) % mod;
                }
            }
        }
        return result;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
