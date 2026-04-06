package chapter5.section3.g.factorial;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 02/09/25.
 */
// Based on the formula explained on https://www.youtube.com/watch?v=w8JYhY-G8wU
public class LowOrderZeros {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        long[] factorials = computeFactorials();

        int n = inputReader.nextInt();
        while (n != 0) {
            long lowestOrderNonZero = computeLowestOrderNonZero(factorials, n);
            outputWriter.printLine(lowestOrderNonZero);
            n = inputReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeLowestOrderNonZero(long[] factorials, int n) {
        int a = n / 5;
        int b = n % 5;

        long factorialALastDigit;
        long factorialBLastDigit;

        if (a >= factorials.length) {
            factorialALastDigit = computeLowestOrderNonZero(factorials, a);
        } else {
            factorialALastDigit = getLastNonZeroDigit(factorials[a]);
        }

        if (b >= factorials.length) {
            factorialBLastDigit = computeLowestOrderNonZero(factorials, b);
        } else {
            factorialBLastDigit = getLastNonZeroDigit(factorials[b]);
        }

        long value = getPower2LastDigit(a) * factorialALastDigit * factorialBLastDigit;
        return getLastNonZeroDigit(value);
    }

    private static int getLastNonZeroDigit(long value) {
        String valueString = Long.toString(value);
        for (int i = valueString.length() - 1; i >= 0; i--) {
            if (valueString.charAt(i) != '0') {
                return Character.getNumericValue(valueString.charAt(i));
            }
        }
        return 0;
    }

    private static int getPower2LastDigit(int power2) {
        if (power2 == 0) {
            return 1;
        }

        int mod = power2 % 4;
        switch (mod) {
            case 1: return 2;
            case 2: return 4;
            case 3: return 8;
            default: return 6;
        }
    }

    private static long[] computeFactorials() {
        long[] factorials = new long[20];
        factorials[0] = 1;
        for (int i = 1; i < factorials.length; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        return factorials;
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        private InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int snext() throws IOException {
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

        private int nextInt() throws IOException {
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

        private boolean isSpaceChar(int c) {
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
