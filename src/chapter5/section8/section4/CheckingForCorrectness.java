package chapter5.section8.section4;

import java.io.*;

/**
 * Created by Rene Argento on 16/04/26.
 */
public class CheckingForCorrectness {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            long number1 = Long.parseLong(data[0]);
            String operator = data[1];
            long number2 = Long.parseLong(data[2]);

            long result = processExpression(number1, number2, operator);
            outputWriter.printLine(result);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long processExpression(long number1, long number2, String operator) {
        int mod = 10000;

        if (operator.equals("+")) {
            return (number1 + number2) % mod;
        } else if (operator.equals("*")) {
            return ((number1 % mod) * (number2 % mod)) % mod;
        }
        return fastExponentiation(number1 % mod, number2, mod);
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

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
