package chapter2.section2.b.oned.array.manipulation.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/01/21.
 */
public class InvertedDeck {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int numbers = FastReader.nextInt();
        int[] values = new int[numbers];

        for (int i = 0; i < values.length; i++) {
            values[i] = FastReader.nextInt();
        }

        int[] indexes = getIndexes(values);

        if (indexes != null) {
            System.out.printf("%d %d\n", indexes[0], indexes[1]);
        } else {
            System.out.println("impossible");
        }
    }

    private static int[] getIndexes(int[] values) {
        boolean reversed = false;
        int startIndex = 0;
        int endIndex = 0;

        for (int i = 1; i < values.length; i++) {
            if (values[i] < values[i - 1]) {
                if (reversed) {
                    return null;
                } else {
                    i++;

                    while (i < values.length && values[i] <= values[i - 1]) {
                        i++;
                    }
                    endIndex = i - 1;
                    startIndex = getStartIndex(values, endIndex);
                    reversed = true;
                    i--;
                }
            }
        }
        if ((startIndex > 0 && values[endIndex] < values[startIndex - 1])
                || (endIndex < values.length - 1 && values[startIndex] > values[endIndex + 1])) {
            return null;
        }
        return new int[]{ startIndex + 1, endIndex + 1};
    }

    private static int getStartIndex(int[] values, int endIndex) {
        for (int i = endIndex - 1; i > 0; i--) {
            if (values[i - 1] < values[i]) {
                return i;
            }
        }
        return 0;
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
