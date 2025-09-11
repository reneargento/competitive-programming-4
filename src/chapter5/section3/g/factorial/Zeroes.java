package chapter5.section3.g.factorial;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/08/25.
 */
public class Zeroes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long low = FastReader.nextLong();
        long high = FastReader.nextLong();

        while (low != 0 || high != 0) {
            long differentZeroes = computeDifferentZeroes(low, high);
            outputWriter.printLine(differentZeroes);

            low = FastReader.nextLong();
            high = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    private static long computeDifferentZeroes(long low, long high) {
        long differentZeroes = 0;
        high++;
        boolean hasExtraLow = false;
        boolean hasExtraHigh = false;

        while (low % 5 != 0) {
            hasExtraLow = true;
            low++;
        }
        while (high % 5 != 0) {
            hasExtraHigh = true;
            high--;
        }

        if (hasExtraLow) {
            differentZeroes++;
        }
        if (hasExtraHigh) {
            differentZeroes++;
        }
        differentZeroes += (high - low) / 5;
        return differentZeroes;
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
