package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by Rene Argento on 21/11/25.
 */
public class FibinaryNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        BigInteger[] fibonacci = computeFibonacci();
        int testCase = 1;

        String line = FastReader.getLine();
        while (line != null) {
            if (line.isEmpty()) {
                line = FastReader.getLine();
            }
            BigInteger value = getValueFromFibinary(fibonacci, line);
            line = FastReader.getLine();
            value = value.add(getValueFromFibinary(fibonacci, line));

            String fibinary = getFibinaryFromValue(fibonacci, value);
            if (testCase > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(fibinary);

            testCase++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigInteger getValueFromFibinary(BigInteger[] fibonacci, String fibinary) {
        BigInteger value = BigInteger.ZERO;
        int fibinacciIndex = 0;

        for (int i = fibinary.length() - 1; i >= 0; i--) {
            if (fibinary.charAt(i) == '1') {
                value = value.add(fibonacci[fibinacciIndex]);
            }
            fibinacciIndex++;
        }
        return value;
    }

    private static String getFibinaryFromValue(BigInteger[] fibonacci, BigInteger value) {
        StringBuilder fibinary = new StringBuilder();
        boolean fibinaryStarted = false;

        for (int i = fibonacci.length - 1; i >= 0; i--) {
            if (value.compareTo(fibonacci[i]) >= 0) {
                value = value.subtract(fibonacci[i]);
                fibinary.append("1");
                fibinaryStarted = true;
            } else if (fibinaryStarted) {
                fibinary.append("0");
            }
        }

        if (fibinary.length() == 0) {
            return "0";
        }
        return fibinary.toString();
    }

    private static BigInteger[] computeFibonacci() {
        BigInteger[] fibonacci = new BigInteger[101];
        fibonacci[0] = BigInteger.ONE;
        fibonacci[1] = new BigInteger("2");

        for (int i = 2; i < fibonacci.length; i++) {
            fibonacci[i] = fibonacci[i - 1].add(fibonacci[i - 2]);
        }
        return fibonacci;
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
