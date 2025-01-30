package chapter5.section2.c.base.number.conversion;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/01/25.
 */
public class KibblesNBitsNBitsNBits {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String hexadecimal1 = FastReader.next();
            String operator = FastReader.next();
            String hexadecimal2 = FastReader.next();

            int decimal1 = Integer.parseInt(hexadecimal1, 16);
            int decimal2 = Integer.parseInt(hexadecimal2, 16);
            int result;
            if (operator.equals("+")) {
                result = decimal1 + decimal2;
            } else {
                result = decimal1 - decimal2;
            }
            String binary1 = Integer.toString(decimal1, 2);
            String binary2 = Integer.toString(decimal2, 2);

            outputWriter.printLine(String.format("%s %s %s = %d", addZeroPrefix(binary1), operator, addZeroPrefix(binary2), result));
        }
        outputWriter.flush();
    }

    private static String addZeroPrefix(String value) {
        StringBuilder valueWithZeroes = new StringBuilder();
        int zeroesNeeded = 13 - value.length();
        for (int i = 0; i < zeroesNeeded; i++) {
            valueWithZeroes.append("0");
        }
        valueWithZeroes.append(value);
        return valueWithZeroes.toString();
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