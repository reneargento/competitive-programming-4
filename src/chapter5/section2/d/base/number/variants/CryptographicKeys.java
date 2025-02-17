package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/02/25.
 */
public class CryptographicKeys {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long integer = FastReader.nextLong();
        long baseWithMostTrailingZeroes = getBaseWithMostTrailingZeroes(integer);
        outputWriter.printLine(baseWithMostTrailingZeroes);

        outputWriter.flush();
    }

    private static long getBaseWithMostTrailingZeroes(long integer) {
        long baseWithMostTrailingZeroes = integer;
        int mostTrailingZeroes = 0;

        for (int base = 2; base <= 10000; base++) {
            try {
                String convertedNumber = convertNumber(integer, base);
                int trailingZeroes = countTrailingZeroes(convertedNumber);

                if (trailingZeroes > mostTrailingZeroes) {
                    mostTrailingZeroes = trailingZeroes;
                    baseWithMostTrailingZeroes = base;
                }
            } catch (NumberFormatException exception) {
                // Ignore errors
            }
        }
        return baseWithMostTrailingZeroes;
    }

    private static String convertNumber(long integer, int base) {
        StringBuilder convertedNumber = new StringBuilder();

        while (integer > 0) {
            long value = integer % base;
            convertedNumber.append(value);
            integer = integer / base;
        }
        return convertedNumber.toString();
    }

    private static int countTrailingZeroes(String number) {
        int trailingZeroes = 0;

        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == '0') {
                trailingZeroes++;
            } else {
                break;
            }
        }
        return trailingZeroes;
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
