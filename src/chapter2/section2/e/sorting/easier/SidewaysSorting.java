package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/02/21.
 */
public class SidewaysSorting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int textNumber = 1;

        while (rows != 0 || columns != 0) {
            if (textNumber > 1) {
                System.out.println();
            }
            String[] words = new String[columns];
            Arrays.fill(words, "");

            for (int row = 0; row < rows; row++) {
                String text = FastReader.next();
                for (int column = 0; column < columns; column++) {
                    words[column] += text.charAt(column);
                }
            }

            Arrays.sort(words, new Comparator<String>() {
                @Override
                public int compare(String string1, String string2) {
                    return string1.toLowerCase().compareTo(string2.toLowerCase());
                }
            });

            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    System.out.print(words[column].charAt(row));
                }
                System.out.println();
            }

            textNumber++;
            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
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
}
