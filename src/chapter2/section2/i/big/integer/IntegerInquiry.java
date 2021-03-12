package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/03/21.
 */
public class IntegerInquiry {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String number = FastReader.next();
        BigInteger sum = BigInteger.ZERO;

        while (!number.equals("0")) {
            sum = sum.add(new BigInteger(number));
            number = FastReader.next();
        }
        System.out.println(sum);
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
