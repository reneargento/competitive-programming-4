package chapter1.section6.n.output.formatting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/12/20.
 */
public class NoBall {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            char[][] field = new char[5][5];
            for (int r = 0; r < 5; r++) {
                field[r] = FastReader.getLine().toCharArray();
            }

            boolean isNoBall = isNoBall(field);
            System.out.printf("Case %d: ", t);
            System.out.println(isNoBall ? "No Ball" : "Thik Ball");

            if (t != tests) {
                FastReader.getLine();
            }
        }
    }

    private static boolean isNoBall(char[][] field) {
        for (int row = 0; row < field.length; row++){
            boolean foundBar = false;

            for (int column = 0; column < field[0].length; column++) {
                char symbol = field[row][column];

                if (symbol == '|') {
                    foundBar = true;
                } else if (symbol == '<' && !foundBar) {
                    return true;
                } else if (symbol == '>' && foundBar) {
                    return true;
                }
            }
        }
        return false;
    }

    public static class FastReader {
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        // Used to check EOF
        // If getLine() == null, it is a EOF
        // Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
