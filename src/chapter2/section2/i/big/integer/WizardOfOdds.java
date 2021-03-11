package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/03/21.
 */
public class WizardOfOdds {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        BigDecimal number = new BigDecimal(FastReader.next());
        BigDecimal guesses = new BigDecimal(FastReader.next());

        BigDecimal log = BigDecimal.valueOf(log2(number));
        if (guesses.compareTo(log) >= 0) {
            System.out.println("Your wish is granted!");
        } else {
            System.out.println("You will become a flying monkey!");
        }
    }

    private static long log2(BigDecimal number) {
        long log = 0;

        while (number.compareTo(BigDecimal.ONE) > 0) {
            log++;
            number = number.divide(BigDecimal.valueOf(2), RoundingMode.CEILING);
        }
        return log;
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
