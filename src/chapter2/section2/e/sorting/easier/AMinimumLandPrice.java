package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/02/21.
 */
public class AMinimumLandPrice {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            List<Long> prices = new ArrayList<>();
            long price = FastReader.nextInt();
            while (price != 0) {
                prices.add(price);
                price = FastReader.nextInt();
            }

            long totalPrice = 0;
            prices.sort(Collections.reverseOrder());

            int exponent = 1;
            for (long currentPrice : prices) {
                totalPrice += 2 * Math.pow(currentPrice, exponent);
                exponent++;
            }

            if (totalPrice <= 5000000) {
                System.out.println(totalPrice);
            } else {
                System.out.println("Too expensive");
            }
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
