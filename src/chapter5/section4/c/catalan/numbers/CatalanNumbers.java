package chapter5.section4.c.catalan.numbers;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/12/25.
 */
public class CatalanNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        BigInteger[] catalanNumbers = catalanNumbers();

        for (int t = 0; t < tests; t++) {
            int x = FastReader.nextInt();
            outputWriter.printLine(catalanNumbers[x]);
        }
        outputWriter.flush();
    }

    private static BigInteger[] catalanNumbers() {
        BigInteger[] catalanNumbers = new BigInteger[5001];
        catalanNumbers[0] = BigInteger.ONE;

        for (int i = 0; i < catalanNumbers.length - 1; i++) {
            catalanNumbers[i + 1] = catalanNumbers[i]
                    .multiply(BigInteger.valueOf(4 * i + 2))
                    .divide(BigInteger.valueOf(i + 2));
        }
        return catalanNumbers;
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
