package chapter5.section8.section4;

import java.io.*;

/**
 * Created by Rene Argento on 13/04/26.
 */
public class ContemplationAlgebra {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            if (data.length == 2) {
                break;
            }
            int p = Integer.parseInt(data[0]);
            int q = Integer.parseInt(data[1]);
            int n = Integer.parseInt(data[2]);

            long powerSum = computePowerSum(p, q, n);
            outputWriter.printLine(powerSum);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computePowerSum(int p, int q, int n) {
        long[][] matrix = {
                { p, -q },
                { 1, 0 }
        };
        long[] firstValues = { p, 2 };
        long[][] matrixPower = fastMatrixExponentiation(matrix, n);
        long[] result = matrixVectorMultiplication(matrixPower, firstValues);
        return result[1];
    }

    private static long[][] fastMatrixExponentiation(long[][] matrix, int exponent) {
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

        long[][] matrixSquared = matrixMultiplication(matrix, matrix);
        if (exponent % 2 == 0) {
            return fastMatrixExponentiation(matrixSquared, exponent / 2);
        } else {
            return (matrixMultiplication(matrix,
                    fastMatrixExponentiation(matrixSquared, exponent / 2)));
        }
    }

    private static long[][] matrixMultiplication(long[][] matrix1, long[][] matrix2) {
        long[][] result = new long[matrix1.length][matrix2[0].length];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                for (int k = 0; k < matrix1[0].length; k++) {
                    result[i][j] = result[i][j] + matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    private static long[] matrixVectorMultiplication(long[][] matrix, long[] vector) {
        long[] result = new long[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[0].length; k++) {
                result[i] += matrix[i][k] * vector[k];
            }
        }
        return result;
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

        public void flush() {
            writer.flush();
        }
    }
}
