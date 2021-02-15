package chapter2.section2.b.oned.array.manipulation.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/02/21.
 */
public class VerifyThisYourMajesty {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int queens = FastReader.nextInt();
        Set<Integer> rows = new HashSet<>();
        Set<Integer> columns = new HashSet<>();
        Set<Integer> rightDiagonals = new HashSet<>();
        Set<Integer> leftDiagonals = new HashSet<>();

        boolean correct = true;

        for (int i = 0; i < queens; i++) {
            int row = FastReader.nextInt();
            int column = FastReader.nextInt();

            int rightDiagonal = row - column;
            int leftDiagonal = row + column;

            if (rows.contains(row) || columns.contains(column) || rightDiagonals.contains(rightDiagonal)
                    || leftDiagonals.contains(leftDiagonal)) {
                correct = false;
                break;
            }
            rows.add(row);
            columns.add(column);
            rightDiagonals.add(rightDiagonal);
            leftDiagonals.add(leftDiagonal);
        }
        System.out.println(correct ? "CORRECT" : "INCORRECT");
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
    }
}
