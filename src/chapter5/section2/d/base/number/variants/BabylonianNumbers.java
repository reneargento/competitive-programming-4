package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/02/25.
 */
public class BabylonianNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String sexagesimalNumber = FastReader.next();
            long decimalNumber = computeDecimalNumber(sexagesimalNumber);
            outputWriter.printLine(decimalNumber);
        }
        outputWriter.flush();
    }

    private static long computeDecimalNumber(String sexagesimalNumber) {
        long decimalNumber = 0;
        StringBuilder currentNumber = new StringBuilder();
        sexagesimalNumber = "," + sexagesimalNumber;
        long multiplier = 1;

        for (int i = sexagesimalNumber.length() - 1; i >= 0; i--) {
            char symbol = sexagesimalNumber.charAt(i);

            if (symbol == ',') {
                if (currentNumber.length() > 0) {
                    currentNumber.reverse();
                    int number = Integer.parseInt(currentNumber.toString());
                    decimalNumber += number * multiplier;
                    currentNumber = new StringBuilder();
                }
                multiplier *= 60;
            } else {
                currentNumber.append(symbol);
            }
        }
        return decimalNumber;
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
