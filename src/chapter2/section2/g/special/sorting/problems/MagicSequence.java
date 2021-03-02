package chapter2.section2.g.special.sorting.problems;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 16/01/21.
 */
public class MagicSequence {

    private static class Sequence {
        long[] values;
        long maxValue;

        public Sequence(long[] values, long maxValue) {
            this.values = values;
            this.maxValue = maxValue;
        }
    }

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = inputReader.readInt();
        long[] sequence = new long[1000000];
        long[] aux = new long[sequence.length];

        for (int t = 0; t < tests; t++) {
            int numbers = inputReader.readInt();
            int a = inputReader.readInt();
            int b = inputReader.readInt();
            int c = inputReader.readInt();
            int x = inputReader.readInt();
            int y = inputReader.readInt();

            Sequence sequenceData = generateSequence(a, b, c, numbers, sequence);

            long[] sortedSequence = radixSort(sequenceData, aux, numbers);
            long hash = computeHash(sortedSequence, x, y, numbers);
            outputWriter.printLine(hash);
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static Sequence generateSequence(int a, int b, int c, int numbers, long[] sequence) {
        sequence[0] = a;
        long maxValue = a;

        for (int i = 1; i < numbers; i++) {
            sequence[i] = (sequence[i - 1] * b + a) % c;
            maxValue = Math.max(maxValue, sequence[i]);
        }
        return new Sequence(sequence, maxValue);
    }

    private static long[] radixSort(Sequence sequenceData, long[] aux, int length) {
        long[] sequence = sequenceData.values;
        int powerOf2 = 15;
        int radix = (2 << powerOf2); // Power of 2 nearest to 10^5
        long exponent = 1;
        boolean isSortedArraySwapped = false;

        while ((2L << (exponent - 1)) < sequenceData.maxValue) {
            int[] values = new int[radix];

            for (int i = 0; i < length; i++) {
                int dividedValue;

                if (!isSortedArraySwapped) {
                    dividedValue = (int) (sequence[i] >> (exponent - 1)); // Same as sequence[i] / 2^(exponent - 1)
                } else {
                    dividedValue = (int) (aux[i] >> (exponent - 1));
                }
                int index = dividedValue & (radix - 1); // Same as dividedValue % radix
                values[index]++;
            }

            for (int i = 1; i < values.length; i++) {
                values[i] += values[i - 1];
            }

            for (int i = length - 1; i >= 0; i--) {
                if (!isSortedArraySwapped) {
                    int dividedValue = (int) (sequence[i] >> (exponent - 1));
                    int index = dividedValue & (radix - 1);
                    aux[--values[index]] = sequence[i];
                } else {
                    int dividedValue = (int) (aux[i] >> (exponent - 1));
                    int index = dividedValue & (radix - 1);
                    sequence[--values[index]] = aux[i];
                }
            }

            isSortedArraySwapped = !isSortedArraySwapped;
            exponent += powerOf2;
        }

        if (isSortedArraySwapped) {
            return aux;
        }
        return sequence;
    }

    private static long computeHash(long[] sequence, int x, int y, int numbers) {
        long hash = 0;

        for (int i = 0; i < numbers; i++) {
            hash = (hash * x + sequence[i]) % y;
        }
        return hash;
    }

    private static class InputReader {
        private InputStream stream;
        private byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() {
            return readString();
        }

        private interface SpaceCharFilter {
            boolean isSpaceChar(int ch);
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
