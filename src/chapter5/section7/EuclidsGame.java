package chapter5.section7;

import java.io.*;

/**
 * Created by Rene Argento on 01/04/26.
 */
public class EuclidsGame {

    public static void main(String[] args) throws IOException {
        FastReaderInteger fastReaderInteger = new FastReaderInteger();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int number1 = fastReaderInteger.nextInt();
        int number2 = fastReaderInteger.nextInt();
        while (number1 != 0 || number2 != 0) {
            String winner = computeWinner(0, number1, number2);
            outputWriter.printLine(winner + " wins");

            number1 = fastReaderInteger.nextInt();
            number2 = fastReaderInteger.nextInt();
        }
        outputWriter.flush();
    }

    private static String computeWinner(int playerId, int number1, int number2) {
        int minNumber = Math.min(number1, number2);
        int maxNumber = Math.max(number1, number2);

        // Check if winning move exists
        if (maxNumber % minNumber == 0) {
            return playerId == 0 ? "Stan" : "Ollie";
        }

        if (maxNumber / minNumber == 1) {
            String winner = computeWinner(playerId, minNumber, maxNumber - minNumber);
            if (winner.equals("Stan")) {
                return "Ollie";
            } else {
                return "Stan";
            }
        }
        return "Stan";
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
