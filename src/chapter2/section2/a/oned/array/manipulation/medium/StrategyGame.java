package chapter2.section2.a.oned.array.manipulation.medium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/01/21.
 */
public class StrategyGame {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        String input = FastReader.getLine();

        while (input != null) {
            String[] inputValue = input.split(" ");
            int players = Integer.parseInt(inputValue[0]);
            int rounds = Integer.parseInt(inputValue[1]);
            int[] points = new int[players];

            int maxPoints = -1;
            int winner = -1;

            for (int r = 0; r < rounds; r++) {
                for (int p = 0; p < players; p++) {
                    points[p] += FastReader.nextInt();

                    if (points[p] >= maxPoints) {
                        maxPoints = points[p];
                        winner = p + 1;
                    }
                }
            }

            System.out.println(winner);
            input = FastReader.getLine();
        }
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

        // Used to check EOF
        // If getLine() == null, it is a EOF
        // Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
