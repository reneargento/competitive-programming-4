package chapter5.section3.b.probabilistic.prime.testing;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/06/25.
 */
public class PseudoprimeNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int p = FastReader.nextInt();
        int a = FastReader.nextInt();

        while (p != 0 || a != 0) {
            boolean isPseudoPrime = isPseudoPrime(p, a);
            outputWriter.printLine(isPseudoPrime ? "yes" : "no");

            p = FastReader.nextInt();
            a = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean isPseudoPrime(int p, int a) {
        BigInteger bigIntegerP = new BigInteger(String.valueOf(p));
        BigInteger bigIntegerA = new BigInteger(String.valueOf(a));
        BigInteger bigInteger2 = new BigInteger("2");

        if (bigIntegerP.isProbablePrime(10)) {
            return false;
        }
        return fastExponentiation(bigIntegerA, bigIntegerP, bigIntegerP, bigInteger2).equals(bigIntegerA);
    }

    private static BigInteger fastExponentiation(BigInteger base, BigInteger exponent, BigInteger mod,
                                                 BigInteger bigInteger2) {
        if (exponent.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }

        BigInteger baseSquared = base.multiply(base);
        BigInteger result = fastExponentiation(baseSquared.mod(mod), exponent.divide(bigInteger2), mod, bigInteger2);
        if (exponent.mod(bigInteger2).equals(BigInteger.ZERO)) {
            return result;
        } else {
            return base.multiply(result).mod(mod);
        }
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

        public void flush() {
            writer.flush();
        }
    }
}
