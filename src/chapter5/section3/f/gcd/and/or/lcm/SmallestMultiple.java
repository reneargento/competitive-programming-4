package chapter5.section3.f.gcd.and.or.lcm;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/08/25.
 */
public class SmallestMultiple {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");

            BigInteger lcm = BigInteger.ONE;
            for (String numberString : data) {
                BigInteger number = new BigInteger(numberString);
                lcm = lcm(lcm, number);
            }
            outputWriter.printLine(lcm);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigInteger gcd(BigInteger number1, BigInteger number2) {
        while (number2.compareTo(BigInteger.ZERO) > 0) {
            BigInteger temp = number2;
            number2 = number1.mod(number2);
            number1 = temp;
        }
        return number1;
    }

    private static BigInteger lcm(BigInteger number1, BigInteger number2) {
        return number1.multiply(number2.divide(gcd(number1, number2)));
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
