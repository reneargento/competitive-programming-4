package chapter5.section6;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Created by Rene Argento on 24/03/26.
 */
public class CyclicNumbers {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = inputReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int numerator = inputReader.nextInt();
            int denominator = inputReader.nextInt();

            String decimalRepresentation = computeDecimalRepresentation(numerator, denominator);
            outputWriter.printLine(decimalRepresentation);
        }
        outputWriter.flush();
    }

    private static String computeDecimalRepresentation(int numerator, int denominator) {
        StringBuilder result = new StringBuilder();

        // Integer part
        result.append(numerator / denominator);
        long remainder = numerator % denominator;
        if (remainder == 0) {
            return result.append(".0").toString(); // no decimal part
        }

        result.append(".");

        Map<Long, Integer> remainderToIndexMap = new HashMap<>();
        while (remainder != 0) {
            if (remainderToIndexMap.containsKey(remainder)) {
                int startIndex = remainderToIndexMap.get(remainder);
                result.insert(startIndex, "(");
                result.append(")");
                return result.toString();
            }
            remainderToIndexMap.put(remainder, result.length());
            remainder *= 10;
            result.append(remainder / denominator);
            remainder %= denominator;
        }
        return result.toString();
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
