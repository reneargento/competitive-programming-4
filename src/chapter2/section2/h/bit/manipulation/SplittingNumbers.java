package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/03/21.
 */
public class SplittingNumbers {

    private static class SplitNumbers {
        int numberA;
        int numberB;

        public SplitNumbers(int numberA, int numberB) {
            this.numberA = numberA;
            this.numberB = numberB;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int number = FastReader.nextInt();

        while (number != 0) {
            SplitNumbers splitNumbers = splitNumber(number);
            System.out.printf("%d %d\n", splitNumbers.numberA, splitNumbers.numberB);
            number = FastReader.nextInt();
        }
    }

    private static SplitNumbers splitNumber(int number) {
        int numberA = 0;
        int numberB = 0;
        int bitsSetFound = 0;
        int mask = 1;

        for (int i = 0; i <= 31; i++) {
            if (isBitSet(number, mask)) {
                if (bitsSetFound % 2 == 0) {
                    numberA = setBit(numberA, mask);
                } else {
                    numberB = setBit(numberB, mask);
                }
                bitsSetFound++;
            }
            mask <<= 1;
        }
        return new SplitNumbers(numberA, numberB);
    }

    private static boolean isBitSet(int number, int mask) {
        return (mask & number) > 0;
    }

    private static int setBit(int number, int mask) {
        return mask | number;
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
