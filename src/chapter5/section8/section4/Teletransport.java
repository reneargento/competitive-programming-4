package chapter5.section8.section4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/04/26.
 */
public class Teletransport {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int spaceships = Integer.parseInt(data[0]);
            int trips = Integer.parseInt(data[1]);
            int start = FastReader.nextInt() - 1;
            int destination = FastReader.nextInt() - 1;

            int[][] paths = new int[spaceships][spaceships];
            for (int spaceship = 0; spaceship < spaceships; spaceship++) {
                for (int button = 0; button < 4; button++) {
                    int target = FastReader.nextInt() - 1;
                    paths[spaceship][target]++;
                }
            }

            int sequences = countSequences(paths, trips, start, destination);
            outputWriter.printLine(sequences);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countSequences(int[][] paths, int trips, int start, int destination) {
        int[][] matrix = fastMatrixExponentiation(paths, trips, 10000);
        return matrix[start][destination];
    }

    private static int[][] fastMatrixExponentiation(int[][] matrix, int exponent, int mod) {
        if (exponent == 0) {
            int[][] identityMatrix = new int[matrix.length][matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                identityMatrix[i][i] = 1;
            }
            return identityMatrix;
        }
        if (exponent == 1) {
            return matrix;
        }

        int[][] matrixSquared = matrixMultiplication(matrix, matrix, mod);
        if (exponent % 2 == 0) {
            return fastMatrixExponentiation(matrixSquared, exponent / 2, mod);
        } else {
            return (matrixMultiplication(matrix,
                    fastMatrixExponentiation(matrixSquared, exponent / 2, mod), mod));
        }
    }

    private static int[][] matrixMultiplication(int[][] matrix1, int[][] matrix2, int mod) {
        int[][] result = new int[matrix1.length][matrix2[0].length];

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
