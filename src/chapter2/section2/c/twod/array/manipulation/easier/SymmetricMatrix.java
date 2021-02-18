package chapter2.section2.c.twod.array.manipulation.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/02/21.
 */
public class SymmetricMatrix {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String[] input = FastReader.getLine().split(" ");
            int dimension = Integer.parseInt(input[2]);
            long[][] matrix = new long[dimension][dimension];

            for (int row = 0; row < matrix.length; row++) {
                for (int column = 0; column < matrix[0].length; column++) {
                    matrix[row][column] = FastReader.nextLong();
                }
            }

            boolean isSymmetric = isSymmetric(matrix);
            System.out.printf("Test #%d: ", t);
            System.out.println(isSymmetric ? "Symmetric." : "Non-symmetric.");
        }
    }

    private static boolean isSymmetric(long[][] matrix) {
        int center = matrix.length / 2;

        for (int row = 0; row <= center; row++) {
            int oppositeRow = matrix.length - 1 - row;

            for (int column = 0; column < matrix[row].length; column++) {
                int oppositeColumn = matrix[row].length - 1 - column;

                if (matrix[row][column] < 0 || matrix[row][column] != matrix[oppositeRow][oppositeColumn]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
