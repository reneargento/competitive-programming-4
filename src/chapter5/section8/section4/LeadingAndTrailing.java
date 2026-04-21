package chapter5.section8.section4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/04/26.
 */
public class LeadingAndTrailing {

    private static class Result {
        String leading;
        String trailing;

        public Result(String leading, String trailing) {
            this.leading = leading;
            this.trailing = trailing;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            long n = FastReader.nextLong();
            int k = FastReader.nextInt();

            Result result = computeDigits(n, k);
            outputWriter.printLine(result.leading + "..." + result.trailing);
        }
        outputWriter.flush();
    }

    private static Result computeDigits(long n, int k) {
        long leading = getLeadingDigits(n, k);
        long trailing = fastExponentiation(n, k, 1000);
        return new Result(getFormattedValue(leading), getFormattedValue(trailing));
    }

    private static long getLeadingDigits(long value, long exponent) {
        double x = exponent * Math.log10(value);
        double fractionalPart = x - Math.floor(x);
        return (long) Math.pow(10, fractionalPart + 2);
    }

    private static long fastExponentiation(long base, long exponent, long mod) {
        if (exponent == 0) {
            return 1;
        }

        long result = fastExponentiation(base, exponent / 2, mod);
        result = (result * result) % mod;

        if (exponent % 2 == 1) {
            result = (result * base) % mod;
        }
        return result;
    }

    private static String getFormattedValue(long value) {
        StringBuilder valueString = new StringBuilder(String.valueOf(value));
        int zeroesToAppend = 3 - valueString.length();
        if (zeroesToAppend > 0) {
            for (int i = 0; i < zeroesToAppend; i++) {
                valueString.insert(0, "0");
            }
        }
        return valueString.toString();
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
