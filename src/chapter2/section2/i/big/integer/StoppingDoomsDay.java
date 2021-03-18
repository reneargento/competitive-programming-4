package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/03/21.
 */
// Based on https://github.com/Hikari9/UVa/blob/master/12143%20-%20Stopping%20Doom's%20Day.c
// and https://codeforces.com/blog/entry/21421
public class StoppingDoomsDay {

    private final static int MOD = 10007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        BigInteger number = new BigInteger(FastReader.next());
        int[] factor = { 1652, -3927, 5995, -781, 7068 };

        while (number.compareTo(BigInteger.ZERO) != 0) {
            int sum = 0;
            int numberValue = number.mod(BigInteger.valueOf(MOD)).intValue();

            numberValue %= MOD;
            numberValue = (numberValue * numberValue) % MOD;
            int numberToPower = numberValue;

            for (int i = 0; i < 5; i++) {
                sum = (factor[i] * numberToPower + sum) % MOD;
                numberToPower = (numberToPower * numberValue) % MOD;
            }
            System.out.println(sum);

            number = new BigInteger(FastReader.next());
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
    }
}
