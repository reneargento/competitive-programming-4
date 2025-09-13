package chapter5.section3.g.factorial;

import java.io.*;

/**
 * Created by Rene Argento on 11/09/25.
 */
public class JustTheFacts {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long[] factorials = computeFactorials();

        String line = FastReader.getLine();
        while (line != null) {
            int value = Integer.parseInt(line);
            int lastNonZeroDigit = computeLowestOrderNonZero(factorials, value);

            outputWriter.printLine(String.format("%5d -> %d", value, lastNonZeroDigit));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeLowestOrderNonZero(long[] factorials, int n) {
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
