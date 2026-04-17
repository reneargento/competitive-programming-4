package chapter5.section8.section4;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/04/26.
 */
public class ColossalFibonacciNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();
        for (int t = 0; t < tests; t++) {
            BigInteger base = new BigInteger(FastReader.next());
            BigInteger exponent = new BigInteger(FastReader.next());
            BigInteger mod = new BigInteger(FastReader.next());

            if (mod.equals(BigInteger.ONE)) {
                outputWriter.printLine("0");
            } else {
                long fibonacci = computeFibonacci(base, exponent, mod);
                outputWriter.printLine(fibonacci);
            }
        }
        outputWriter.flush();
    }

    private static long computeFibonacci(BigInteger base, BigInteger exponent, BigInteger mod) {
        List<Long> pisanoPeriod = computePisanoPeriod(mod.longValue());
        int index = fastExponentiation(base, exponent, BigInteger.valueOf(pisanoPeriod.size())).intValue();
        return pisanoPeriod.get(index);
    }

    private static List<Long> computePisanoPeriod(long mod) {
        List<Long> pisanoPeriod = new ArrayList<>();
        pisanoPeriod.add(0L);
        pisanoPeriod.add(1L);
        long fibonacci1 = 0;
        long fibonacci2 = 1;

        while (true) {
            long aux = fibonacci2;
            fibonacci2 = (fibonacci1 + fibonacci2) % mod;
            fibonacci1 = aux;

            if (fibonacci1 == 0 && fibonacci2 == 1) {
                pisanoPeriod.remove(pisanoPeriod.size() - 1);
                break;
            }
            pisanoPeriod.add(fibonacci2);
        }
        return pisanoPeriod;
    }

    private static BigInteger fastExponentiation(BigInteger base, BigInteger exponent, BigInteger mod) {
        if (exponent.equals(BigInteger.ZERO)) {
            return BigInteger.ONE.mod(mod);
        }

        BigInteger bigInteger2 = BigInteger.valueOf(2);
        BigInteger baseSquared = base.multiply(base);
        BigInteger result = fastExponentiation(baseSquared.mod(mod), exponent.divide(bigInteger2), mod);
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
            while (!tokenizer.hasMoreTokens() ) {
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
