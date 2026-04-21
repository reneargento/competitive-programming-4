package chapter5.section8.section4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/04/26.
 */
public class Recurrences {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int order = FastReader.nextInt();
        int n = FastReader.nextInt();
        int mod = FastReader.nextInt();

        while (order != 0 || n != 0 || mod != 0) {
            long[][] coefficients = new long[order][order];
            for (int i = 0; i < order; i++) {
                coefficients[0][i] = FastReader.nextInt();
            }
            long[] values = new long[order];
            for (int i = 0; i < order; i++) {
                values[i] = FastReader.nextInt();
            }

            long fnMod = computeFnMod(n, mod, coefficients, values);
            outputWriter.printLine(fnMod);

            order = FastReader.nextInt();
            n = FastReader.nextInt();
            mod = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeFnMod(int n, int mod, long[][] coefficients, long[] values) {
        if (n < values.length) {
            return values[n];
        }

        long[] valuesReverse = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            valuesReverse[i] = values[values.length - 1 - i];
        }

        for (int i = 1; i < coefficients.length; i++) {
            coefficients[i][i - 1] = 1;
        }

        long[][] matrixPower = fastMatrixExponentiation(coefficients, n - values.length, mod);
        long[] result = matrixVectorMultiplication(matrixPower, valuesReverse, mod);
        return result[0];
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

    private static long[] matrixVectorMultiplication(long[][] matrix, long[] vector, long mod) {
        long[] result = new long[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[0].length; k++) {
                result[i] = (result[i] + matrix[i][k] * vector[k]) % mod;
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
