package chapter5.section8.section4;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/04/26.
 */
public class BigMod {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            if (line.isEmpty()) {
                line = FastReader.getLine();
            }
            long base = Long.parseLong(line);
            long power = FastReader.nextLong();
            long mod = FastReader.nextLong();

            long result = fastExponentiation(base, power, mod);
            outputWriter.printLine(result);
            line = FastReader.getLine();
        }
        outputWriter.flush();
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
