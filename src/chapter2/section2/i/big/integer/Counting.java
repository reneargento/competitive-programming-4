package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 14/03/21.
 */
public class Counting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String numberLine = FastReader.getLine();
        int[] values = { 1, 2, 3, 1 };
        BigInteger[] dp = new BigInteger[1001];
        dp[0] = BigInteger.ONE;

        while (numberLine != null) {
            int number = Integer.parseInt(numberLine);

            BigInteger possibilities = possibilities(values, dp, number);
            System.out.println(possibilities);
            numberLine = FastReader.getLine();
        }
    }

    private static BigInteger possibilities(int[] values, BigInteger[] dp, int currentValue) {
        if (dp[currentValue] != null) {
            return dp[currentValue];
        }
        BigInteger totalPossibilities = BigInteger.ZERO;

        for (int value : values) {
            if (currentValue - value >= 0) {
                BigInteger addedPossibilities = possibilities(values, dp, currentValue - value);
                totalPossibilities = totalPossibilities.add(addedPossibilities);
            }
        }

        dp[currentValue] = totalPossibilities;
        return dp[currentValue];
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
