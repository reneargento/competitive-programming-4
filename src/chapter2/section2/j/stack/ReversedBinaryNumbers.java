package chapter2.section2.j.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/03/21.
 */
public class ReversedBinaryNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int number = FastReader.nextInt();
        int numberInReverseBinary = getNumberInReverseBinary(number);
        System.out.println(numberInReverseBinary);
    }

    private static int getNumberInReverseBinary(int number) {
        StringBuilder bits = new StringBuilder();
        while (number > 0) {
            if (number % 2 == 0) {
                bits.append("0");
            } else {
                bits.append("1");
            }
            number /= 2;
        }
        return Integer.parseInt(bits.toString(), 2);
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
