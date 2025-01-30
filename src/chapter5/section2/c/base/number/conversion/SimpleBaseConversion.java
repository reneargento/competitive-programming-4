package chapter5.section2.c.base.number.conversion;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/01/25.
 */
public class SimpleBaseConversion {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String number = FastReader.next();
        while (number.charAt(0) != '-') {
            String convertedNumber = getConvertedNumber(number);
            outputWriter.printLine(convertedNumber);
            number = FastReader.next();
        }
        outputWriter.flush();
    }

    private static String getConvertedNumber(String number) {
        if (number.startsWith("0x")) {
            String numberWithoutPrefix = number.substring(2);
            int base10Number = Integer.parseInt(numberWithoutPrefix, 16);
            return String.valueOf(base10Number);
        } else {
            int base10Number = Integer.parseInt(number);
            String base16Number = Integer.toString(base10Number, 16).toUpperCase();
            return "0x" + base16Number;
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