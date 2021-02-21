package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 15/02/21.
 */
public class ColinAndRyan {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int cookies = FastReader.nextInt();
            int cookiesLeft = FastReader.nextInt();

            System.out.printf("Case #%d:", t);

            if (cookies == cookiesLeft) {
                System.out.println(" 0");
                continue;
            }

            int number = cookies - cookiesLeft;
            List<Integer> dividers = getFactors(number, cookiesLeft);
            Collections.sort(dividers);

            for(int divider : dividers) {
                System.out.print(" " + divider);
            }
            System.out.println();
        }
    }

    private static List<Integer> getFactors(int number, int minFactor) {
        List<Integer> factors = new ArrayList<>();
        int sqrtNumber = (int) Math.sqrt(number);

        for (int i = 1; i <= sqrtNumber; i++) {
            if (number % i == 0) {
                if (i > minFactor) {
                    factors.add(i);
                }

                if (i != (number / i)
                        && (number / i > minFactor)) {
                    factors.add(number / i);
                }
            }
        }
        return factors;
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
