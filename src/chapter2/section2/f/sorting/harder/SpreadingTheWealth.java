package chapter2.section2.f.sorting.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/02/21.
 */
// Based on https://www.codeleading.com/article/76163581443/
public class SpreadingTheWealth {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String peopleLine = FastReader.getLine();

        while (peopleLine != null) {
            int people = Integer.parseInt(peopleLine);
            long[] coins = new long[people];
            long sum = 0;

            for (int i = 0; i < coins.length; i++) {
                coins[i] = FastReader.nextInt();
                sum += coins[i];
            }

            long coinsExchanged = computeCoinsExchanged(coins, sum);
            System.out.println(coinsExchanged);

            peopleLine = FastReader.getLine();
        }
    }

    private static long computeCoinsExchanged(long[] coins, long sum) {
        long coinsExchanged = 0;

        long target = sum / coins.length;
        long[] exchanges = new long[coins.length];

        exchanges[0] = 0;
        for (int i = 1; i < coins.length; i++) {
            exchanges[i] = exchanges[i - 1] + coins[i - 1] - target;
        }

        Arrays.sort(exchanges);
        long median = exchanges[exchanges.length / 2];

        for (int i = 0; i < exchanges.length; i++) {
            coinsExchanged += Math.abs(median - exchanges[i]);
        }
        return coinsExchanged;
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
