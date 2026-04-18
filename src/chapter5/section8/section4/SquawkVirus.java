package chapter5.section8.section4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/04/26.
 */
public class SquawkVirus {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int users = FastReader.nextInt();
        long[][] paths = new long[users][users];
        int links = FastReader.nextInt();
        int infectedUser = FastReader.nextInt();
        int minutes = FastReader.nextInt();

        for (int i = 0; i < links; i++) {
            int userId1 = FastReader.nextInt();
            int userId2 = FastReader.nextInt();
            paths[userId1][userId2] = 1;
            paths[userId2][userId1] = 1;
        }

        long squawks = computeSquawks(paths, infectedUser, minutes);
        outputWriter.printLine(squawks);
        outputWriter.flush();
    }

    private static long computeSquawks(long[][] paths, int infectedUser, int minutes) {
        long[][] matrixPower = fastMatrixExponentiation(paths, minutes);
        long squawks = 0;
        for (int userId = 0; userId < paths.length; userId++) {
            squawks += matrixPower[infectedUser][userId];
        }
        return squawks;
    }

    private static long[][] fastMatrixExponentiation(long[][] matrix, long exponent) {
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
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
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
