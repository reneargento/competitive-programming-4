package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/03/21.
 */
public class AlgorithmOfPhil {

    private static final int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String bits = FastReader.next();
            String resultBits = getResultBits(bits);

            long result = getDecimalNumber(resultBits);
            System.out.printf("Case #%d: %d\n", t, result);
        }
    }

    private static String getResultBits(String bits) {
        StringBuilder resultBits = new StringBuilder();
        int left;
        int right;
        int middle = bits.length() / 2;

        if (bits.length() % 2 == 1) {
            char bit = bits.charAt(middle);
            resultBits.append(bit);
            left = middle - 1;
            right = middle + 1;
        } else {
            left = middle - 1;
            right = middle;
        }

        while (left >= 0 || right < bits.length()) {
            int leftSize = left + 1;
            int rightSize = bits.length() - right;

            if ((leftSize > rightSize) || (leftSize == rightSize && bits.charAt(left) == '1')) {
                resultBits.append(bits.charAt(left));
                left--;
            } else {
                resultBits.append(bits.charAt(right));
                right++;
            }
        }
        return resultBits.toString();
    }

    private static long getDecimalNumber(String bits) {
        long result = 0;
        int multiplier = 1;

        for (int i = bits.length() - 1; i >= 0; i--) {
            if (bits.charAt(i) == '1') {
                result += multiplier;
            }
            result %= MOD;
            multiplier *= 2;
            multiplier %= MOD;
        }
        return result;
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
