package chapter2.section2.j.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/03/21.
 */
public class SymmetricOrder {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int wordsNumber = FastReader.nextInt();
        int set = 1;

        while (wordsNumber != 0) {
            String[] wordsSorted = new String[wordsNumber];
            for (int i = 0; i < wordsNumber; i++) {
                String word = FastReader.next();

                if (i % 2 == 0) {
                    int startIndex = (i / 2);
                    wordsSorted[startIndex] = word;
                } else {
                    int endIndex = wordsNumber - 1 - (i / 2);
                    wordsSorted[endIndex] = word;
                }
            }

            System.out.printf("SET %d\n", set);
            for (String word : wordsSorted) {
                System.out.println(word);
            }

            wordsNumber = FastReader.nextInt();
            set++;
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
    }
}
