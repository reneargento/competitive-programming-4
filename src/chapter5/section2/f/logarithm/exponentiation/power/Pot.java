package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/04/25.
 */
public class Pot {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int addends = FastReader.nextInt();
        long x = 0;

        for (int i = 0; i < addends; i++) {
            String numberWithoutFormat = FastReader.getLine();
            int lastDigitIndex = numberWithoutFormat.length() - 1;
            int number = Integer.parseInt(numberWithoutFormat.substring(0, lastDigitIndex));
            int exponent = Character.getNumericValue(numberWithoutFormat.charAt(lastDigitIndex));

            x += (long) Math.pow(number, exponent);
        }
        outputWriter.printLine(x);
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

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
