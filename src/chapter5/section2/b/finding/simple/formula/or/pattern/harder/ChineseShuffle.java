package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/24.
 */
public class ChineseShuffle {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long number = FastReader.nextLong();
        while (number != -1) {
            boolean isJimmyNumber = isJimmyNumber(number);
            outputWriter.print(number + " is ");
            if (isJimmyNumber) {
                outputWriter.printLine("a Jimmy-number");
            } else {
                outputWriter.printLine("not a Jimmy-number");
            }
            number = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    private static boolean isJimmyNumber(long number) {
        return fastExponentiation(number - 1, number) == 1;
    }

    private static long fastExponentiation(long base, long mod) {
        if (base == 0) {
            return 1;
        }
        if (base == 1) {
            return 2;
        }
        long result = fastExponentiation(base / 2, mod);
        long resultSquared = result * result;
        resultSquared %= mod;

        if (base % 2 == 1) {
            return (resultSquared * 2) % mod;
        } else {
            return resultSquared % mod;
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
