package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/02/21.
 */
public class ChildrensGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int numbers = FastReader.nextInt();

        while (numbers != 0) {
            String[] values = new String[numbers];
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.next();
            }

            sortValues(values);

            for (String value : values) {
                System.out.print(value);
            }
            System.out.println();

            numbers = FastReader.nextInt();
        }
    }

    private static void sortValues(String[] values) {
        Arrays.sort(values, new Comparator<String>() {
            @Override
            public int compare(String string1, String string2) {
                String string1First = string1 + string2;
                String string2First = string2 + string1;

                if (string1First.compareTo(string2First) > 0) {
                    return -1;
                } else if (string2First.compareTo(string1First) > 0) {
                    return 1;
                }
                return 0;
            }
        });
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
