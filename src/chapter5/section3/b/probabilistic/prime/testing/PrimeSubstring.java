package chapter5.section3.b.probabilistic.prime.testing;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/06/25.
 */
public class PrimeSubstring {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        BigInteger maxPrime = new BigInteger("100000");
        String string = FastReader.next();
        while (!string.equals("0")) {
            String largestPrime = computeLargestPrime(string, maxPrime);
            outputWriter.printLine(largestPrime);
            string = FastReader.next();
        }
        outputWriter.flush();
    }

    private static String computeLargestPrime(String string, BigInteger maxPrime) {
        BigInteger largestPrime = new BigInteger("2");

        for (int startIndex = 0; startIndex < string.length(); startIndex++) {
            for (int endIndex = startIndex + 1; endIndex < string.length(); endIndex++) {
                String substring = string.substring(startIndex, endIndex);
                BigInteger bigInteger = new BigInteger(substring);

                if (bigInteger.isProbablePrime(10)
                        && bigInteger.compareTo(maxPrime) <= 0
                        && bigInteger.compareTo(largestPrime) > 0) {
                    largestPrime = bigInteger;
                }
            }
        }
        return largestPrime.toString();
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

        public void flush() {
            writer.flush();
        }
    }
}