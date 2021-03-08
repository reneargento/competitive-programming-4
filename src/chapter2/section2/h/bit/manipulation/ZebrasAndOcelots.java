package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/03/21.
 */
public class ZebrasAndOcelots {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int bits = FastReader.nextInt();
        long bellRings = 0;
        long[] powersOf2 = computePowersOf2();

        for (int i = bits - 1; i >= 0; i--) {
            String animal = FastReader.next();
            if (animal.equals("O")) {
                bellRings += powersOf2[i];
            }
        }
        System.out.println(bellRings);
    }

    private static long[] computePowersOf2() {
        long[] powersOf2 = new long[60];
        powersOf2[0] = 1;

        for (int i = 1; i < powersOf2.length; i++) {
            powersOf2[i] = powersOf2[i - 1] * 2;
        }
        return powersOf2;
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
