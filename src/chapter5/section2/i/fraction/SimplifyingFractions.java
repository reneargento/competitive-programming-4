package chapter5.section2.i.fraction;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/04/25.
 */
public class SimplifyingFractions {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            BigInteger nominator = new BigInteger(FastReader.next());
            FastReader.next();
            BigInteger denominator = new BigInteger(FastReader.next());

            BigInteger gcd = gcd(nominator, denominator);
            BigInteger simplifiedNominator = nominator.divide(gcd);
            BigInteger simplifiedDenominator = denominator.divide(gcd);
            outputWriter.printLine(simplifiedNominator + " / " + simplifiedDenominator);
        }
        outputWriter.flush();
    }

    private static BigInteger gcd(BigInteger number1, BigInteger number2) {
        while (number2.compareTo(BigInteger.ZERO) > 0) {
            BigInteger temp = number2;
            number2 = number1.mod(number2);
            number1 = temp;
        }
        return number1;
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
