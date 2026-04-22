package chapter5.section8.section4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/04/26.
 */
public class LinearRecurrences {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int degree = FastReader.nextInt();
        long[] coefficients = new long[degree + 1];
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] = FastReader.nextInt();
        }
        long[][] values = new long[degree + 1][1];
        values[0][0] = 1;
        for (int i = 0; i < values.length - 1; i++) {
            values[values.length - 1 - i][0] = FastReader.nextInt();
        }

        int queries = FastReader.nextInt();
        for (int q = 0; q < queries; q++) {
            long index = FastReader.nextLong();
            int mod = FastReader.nextInt();
            long term = computeTerm(coefficients, values, index, mod, degree);
            outputWriter.printLine(term);
        }
        outputWriter.flush();
    }

    private static long computeTerm(long[] coefficients, long[][] values, long index, long mod, long degree) {
        if (index < values.length - 1) {
            int valueIndex = (int) (values.length - 1 - index);
            return mod(values[valueIndex][0], mod);
        }

        long[][] transitionMatrix = new long[coefficients.length][coefficients.length];
        transitionMatrix[1] = coefficients;
        for (int i = 0; i < transitionMatrix.length; i++) {
            if (i == 1) {
                continue;
            }
            int column = i == 0 ? i : i - 1;
            transitionMatrix[i][column] = 1;
        }

        long[][] matrixPower = fastMatrixExponentiation(transitionMatrix, index - degree + 1, mod);
        long[][] result = matrixMultiplication(matrixPower, values, mod);
        return result[1][0];
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
                    result[i][j] = mod((result[i][j] + matrix1[i][k] * matrix2[k][j]), mod);
                }
            }
        }
        return result;
    }

    private static long mod(long number, long mod) {
        return ((number % mod) + mod) % mod;
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
