package chapter3.section4.f.non.classical.harder;

import java.io.*;

/**
 * Created by Rene Argento on 06/08/22.
 */
public class BitMask {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            long number = Long.parseLong(data[0]);
            long lowerBound = Long.parseLong(data[1]);
            long upperBound = Long.parseLong(data[2]);

            long mask = computeMask(number, lowerBound, upperBound);
            outputWriter.printLine(mask);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeMask(long number, long lowerBound, long upperBound) {
        long mask = 0;
        long movingBitMask = (1L << 31);
        int currentIndex = 31;

        while (movingBitMask > 0) {
            boolean isBitInNumberZero = ((number & movingBitMask) == 0);
            long newMask = (mask | movingBitMask);

            if (isBitInNumberZero) {
                if (newMask >= lowerBound && newMask <= upperBound) {
                    mask = newMask;
                }
            }

            if (mask < lowerBound) {
                long maxValueWithRemainingBits = (long) Math.pow(2, currentIndex) - 1;
                if (mask + maxValueWithRemainingBits < lowerBound) {
                    mask = newMask;
                }
            }
            movingBitMask >>= 1;
            currentIndex--;
        }
        return mask;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
