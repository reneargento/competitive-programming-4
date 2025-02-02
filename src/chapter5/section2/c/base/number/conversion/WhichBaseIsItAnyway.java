package chapter5.section2.c.base.number.conversion;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/01/25.
 */
public class WhichBaseIsItAnyway {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            String number = FastReader.next();

            int octalValue = getNumberInBase(number, 8);
            int decimalValue = getNumberInBase(number, 10);
            int hexadecimalValue = getNumberInBase(number, 16);
            outputWriter.printLine(dataSetNumber + " " + octalValue + " " + decimalValue + " " + hexadecimalValue);
        }
        outputWriter.flush();
    }

    private static int getNumberInBase(String number, int base) {
        try {
            return Integer.parseInt(number, base);
        } catch (NumberFormatException exception) {
            return 0;
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