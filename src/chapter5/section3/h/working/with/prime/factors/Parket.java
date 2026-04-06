package chapter5.section3.h.working.with.prime.factors;

import java.io.*;

/**
 * Created by Rene Argento on 05/10/25.
 */
public class Parket {

    private static class Result {
        long dimension1;
        long dimension2;

        public Result(long dimension1, long dimension2) {
            this.dimension1 = dimension1;
            this.dimension2 = dimension2;
        }
    }

    private static final int MAX_DIMENSION = 5000;

    public static void main(String[] args) throws IOException {
        FastReaderInteger fastReaderInteger = new FastReaderInteger();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int redBlocks = fastReaderInteger.nextInt();
        int brownBlocks = fastReaderInteger.nextInt();

        Result result = computeRoomDimensions(redBlocks, brownBlocks);
        outputWriter.printLine(result.dimension1 + " " + result.dimension2);
        outputWriter.flush();
    }

    private static Result computeRoomDimensions(int redBlocks, int brownBlocks) {
        for (int dimension1 = 1; dimension1 <= MAX_DIMENSION; dimension1++) {
            for (int dimension2 = 1; dimension2 <= MAX_DIMENSION; dimension2++) {
                int redBlocksCandidate = (dimension1 * 2) + (dimension2 * 2) - 4;
                int brownBlocksCandidate = (dimension1 - 2) * (dimension2 - 2);

                if (redBlocksCandidate == redBlocks && brownBlocksCandidate == brownBlocks) {
                    int maxDimension = Math.max(dimension1, dimension2);
                    int minDimension = Math.min(dimension1, dimension2);
                    return new Result(maxDimension, minDimension);
                }
            }
        }
        return new Result(1, 1);
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
