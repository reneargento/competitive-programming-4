package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 17/02/25.
 */
public class PermutedArithmeticSequence {

    public static void main(String[] args) throws IOException {
        FastReaderInteger fastReaderInteger = new FastReaderInteger();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = fastReaderInteger.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] sequence = new int[fastReaderInteger.nextInt()];
            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = fastReaderInteger.nextInt();
            }

            String sequenceType = analyzeSequence(sequence);
            outputWriter.printLine(sequenceType);
        }
        outputWriter.flush();
    }

    private static String analyzeSequence(int[] sequence) {
        if (isArithmetic(sequence)) {
            return "arithmetic";
        }

        Arrays.sort(sequence);
        if (isArithmetic(sequence)) {
            return "permuted arithmetic";
        }
        return "non-arithmetic";
    }

    private static boolean isArithmetic(int[] sequence) {
        int difference = sequence[1] - sequence[0];

        for (int i = 2; i < sequence.length; i++) {
            if (sequence[i] - sequence[i - 1] != difference) {
                return false;
            }
        }
        return true;
    }

    private static class FastReaderInteger {
        private static final InputStream in = System.in;
        private static final int bufferSize = 30000;
        private static final byte[] buffer = new byte[bufferSize];
        private static int position = 0;
        private static int byteCount = bufferSize;
        private static byte character;

        FastReaderInteger() throws IOException {
            fill();
        }

        private void fill() throws IOException {
            byteCount = in.read(buffer, 0, bufferSize);
        }

        private int nextInt() throws IOException {
            while (character < '-') {
                character = readByte();
            }
            boolean isNegative = (character == '-');
            if (isNegative) {
                character = readByte();
            }
            int value = character - '0';
            while ((character = readByte()) >= '0' && character <= '9') {
                value = value * 10 + character - '0';
            }
            return isNegative ? -value : value;
        }

        private byte readByte() throws IOException {
            if (position == byteCount) {
                fill();
                position = 0;
            }
            return buffer[position++];
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
