package chapter5.section6;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/03/26.
 */
public class PowersEtAl {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        BigInteger m = new BigInteger(FastReader.next());
        BigInteger n = new BigInteger(FastReader.next());
        BigInteger bigInteger10 = BigInteger.valueOf(10);

        while (m.compareTo(BigInteger.ZERO) != 0 || n.compareTo(BigInteger.ZERO) != 0) {
            BigInteger mPowerN = m.modPow(n, bigInteger10);
            String mPowerNString = mPowerN.toString();
            char lastDigit = mPowerNString.charAt(mPowerNString.length() - 1);
            outputWriter.printLine(lastDigit);

            m = new BigInteger(FastReader.next());
            n = new BigInteger(FastReader.next());
        }
        outputWriter.flush();
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
