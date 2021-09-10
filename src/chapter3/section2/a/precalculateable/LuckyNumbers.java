package chapter3.section2.a.precalculateable;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/09/21.
 */
public class LuckyNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int digits = FastReader.nextInt();
        long luckyNumbersSize = computeLuckyNumbers(digits);
        outputWriter.printLine(luckyNumbersSize);

        outputWriter.flush();
    }

    private static long computeLuckyNumbers(int digits) {
        List<BigInteger> candidates = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            candidates.add(BigInteger.valueOf(i));
        }

        for (int size = 2; size <= digits; size++) {
            candidates = computeLuckyNumbersOfSize(size, candidates);
        }
        return candidates.size();
    }

    private static List<BigInteger> computeLuckyNumbersOfSize(int size, List<BigInteger> candidates) {
        List<BigInteger> luckyNumbers = new ArrayList<>();
        BigInteger bigInteger10 = BigInteger.valueOf(10);
        BigInteger bigIntegerSize = BigInteger.valueOf(size);

        for (BigInteger candidate : candidates) {
            for (int digit = 0; digit <= 9; digit++) {
                BigInteger nextNumber = candidate.multiply(bigInteger10).add(BigInteger.valueOf(digit));

                if (nextNumber.mod(bigIntegerSize).equals(BigInteger.ZERO)) {
                    luckyNumbers.add(nextNumber);
                }
            }
        }
        return luckyNumbers;
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

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
