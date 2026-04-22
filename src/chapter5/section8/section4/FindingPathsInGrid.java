package chapter5.section8.section4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/04/26.
 */
public class FindingPathsInGrid {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        long[][] matrix = new long[35][35];
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[0].length; column++) {
                if (isValidMove(row, column)) {
                    matrix[row][column] = 1;
                }
            }
        }

        for (int t = 0; t < tests; t++) {
            int targetRow = FastReader.nextInt();
            int[] startRow = new int[7];
            for (int i = 0; i < 7; i++) {
                int value = FastReader.nextInt();
                if (value > 0) {
                    startRow[i] = 1;
                }
            }

            long waysToReach = computeWaysToReach(matrix, targetRow, startRow);
            outputWriter.printLine(waysToReach);
        }
        outputWriter.flush();
    }

    private static long computeWaysToReach(long[][] matrix, long targetRow, int[] startRow) {
        int mod = 1000000007;
        long[][] matrixPower = fastMatrixExponentiation(matrix, targetRow - 1, mod);

        int permutationIndex = getPermutationIndex(startRow);
        long waysToReach = 0;
        for (int i = 0; i < matrixPower.length; i++) {
            waysToReach += matrixPower[permutationIndex][i];
            waysToReach %= mod;
        }
        return waysToReach;
    }

    private static boolean isValidMove(int row, int column) {
        int[] permutation1 = getPermutation(row);
        int[] permutation2 = getPermutation(column);

        for (int i = 0; i < permutation1.length; i++) {
            if (permutation1[i] == 0) {
                continue;
            }
            if (i > 0 && permutation2[i - 1] == 1) {
                permutation2[i - 1] = 0;
                continue;
            }
            if (i < permutation2.length - 1 && permutation2[i + 1] == 1) {
                permutation2[i + 1] = 0;
                continue;
            }
            return false;
        }
        return true;
    }

    private static int[] getPermutation(int index) {
        int[] permutation = { 0, 0, 0, 1, 1, 1, 1 };

        for (int i = 0; i < index; i++) {
            permutation = nextPermutation(permutation);
        }
        return permutation;
    }

    private static int getPermutationIndex(int[] row) {
        int[] permutation = { 0, 0, 0, 1, 1, 1, 1 };
        int permutationIndex = 0;

        while (true) {
            boolean foundPermutation = true;
            for (int i = 0; i < permutation.length; i++) {
                if (permutation[i] != row[i]) {
                    foundPermutation = false;
                    break;
                }
            }

            if (foundPermutation) {
                break;
            }

            permutation = nextPermutation(permutation);
            permutationIndex++;
        }
        return permutationIndex;
    }

    private static int[] nextPermutation(int[] permutation) {
        // 1. Find the largest k, such that permutation[k] < permutation[k + 1]
        int first = getFirstIndexToSwap(permutation);
        if (first == -1) {
            return null; // no greater permutation
        }

        // 2. Find the last index toSwap, that permutation[k] < permutation[toSwap]
        int toSwap = permutation.length - 1;
        while (permutation[first] >= permutation[toSwap]) {
            toSwap--;
        }

        // 3. Swap elements with indexes first and toSwap
        swap(permutation, first++, toSwap);

        // 4. Reverse sequence from k + 1 to n (inclusive)
        toSwap = permutation.length - 1;
        while (first < toSwap) {
            swap(permutation, first++, toSwap--);
        }
        return permutation;
    }

    // Finds the largest k, that permutation[k] < permutation[k + 1]
    // If no such k exists (there is not greater permutation), return -1
    private static int getFirstIndexToSwap(int[] permutation) {
        for (int i = permutation.length - 2; i >= 0; --i) {
            if (permutation[i] < permutation[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    private static void swap(int[] permutation, int index1, int index2) {
        int temp = permutation[index1];
        permutation[index1] = permutation[index2];
        permutation[index2] = temp;
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
