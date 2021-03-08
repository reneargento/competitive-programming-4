package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/03/21.
 */
public class HamiltonianHypercube {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int bitsLength = FastReader.nextInt();
        String binaryString1 = FastReader.next();
        String binaryString2 = FastReader.next();

        long number1 = Long.parseLong(binaryString1, 2);
        long number2 = Long.parseLong(binaryString2, 2);

        long position1 = getGreyCodePosition(number1);
        long position2 = getGreyCodePosition(number2);
        long wordsBetween = position2 - position1 - 1;

        System.out.println(wordsBetween);
    }

    // Based on https://stackoverflow.com/questions/9617782/whats-the-reverse-function-of-x-xor-x-2
    private static long getGreyCodePosition(long greyCode) {
        long position = 0;
        while (greyCode > 0) {
            position ^= greyCode;
            greyCode >>= 1;
        }
        return position;
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
