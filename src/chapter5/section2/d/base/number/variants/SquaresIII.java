package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/02/25.
 */
public class SquaresIII {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long number = FastReader.nextLong();

        while (number != 0) {
            int base = findBase(number);
            outputWriter.printLine(base);
            number = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    private static int findBase(long number) {
        for (int base = 2; base <= 99; base++) {
            try {
                long convertedNumber = convertNumber(number, base);
                long sqrtConvertedNumber = (long) Math.sqrt(convertedNumber);

                if (sqrtConvertedNumber * sqrtConvertedNumber == convertedNumber) {
                    return base;
                }
            } catch (NumberFormatException exception) {
                // Ignore invalid bases
            }
        }
        return 0;
    }

    private static long convertNumber(long number, int radix) {
        long convertedNumber = 0;
        long multiplier = 1;

        while (number > 0) {
            long digit = number % 10;
            if (digit >= radix) {
                throw new NumberFormatException();
            }
            convertedNumber += digit * multiplier;
            multiplier *= radix;
            number /= 10;
        }
        return convertedNumber;
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
