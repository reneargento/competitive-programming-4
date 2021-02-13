package chapter2.section2.a.oned.array.manipulation.medium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 15/01/21.
 */
public class Baloni {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int balloonsNumber = FastReader.nextInt();
        int[] count = new int[balloonsNumber + 1];

        for (int i = 0; i < balloonsNumber; i++) {
            int balloon = FastReader.nextInt();

            if (count[balloon] != 0) {
                count[balloon]--;
            }
            count[balloon - 1]++;
        }

        int shoots = 0;
        for (int arrows : count) {
            shoots += arrows;
        }
        System.out.println(shoots);
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
