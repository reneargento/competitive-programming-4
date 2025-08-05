package chapter5.section3.e.modified.sieve;

import java.io.*;

/**
 * Created by Rene Argento on 29/07/25.
 */
public class NonPrimeFactors {

    public static void main(String[] args) throws IOException {
        FastReaderInteger fastReader = new FastReaderInteger();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int queries = fastReader.nextInt();
        int maxNumber = 3000001;
        int[] uniquePFs = eratosthenesSieveCountUniquePFs(maxNumber);
        int[] factorsCount = eratosthenesSieveCountFactors(maxNumber);

        for (int q = 0; q < queries; q++) {
            int query = fastReader.nextInt();
            int nonPrimeFactors = factorsCount[query] - uniquePFs[query];
            outputWriter.printLine(nonPrimeFactors);
        }
        outputWriter.flush();
    }

    private static int[] eratosthenesSieveCountUniquePFs(int maxNumber) {
        int[] uniquePFs = new int[maxNumber + 1];

        for (int i = 2; i < uniquePFs.length; i++) {
            if (uniquePFs[i] == 0) {
                for (int j = i; j < uniquePFs.length; j += i) {
                    uniquePFs[j]++;
                }
            }
        }
        return uniquePFs;
    }

    private static int[] eratosthenesSieveCountFactors(int maxNumber) {
        int[] factorsCount = new int[maxNumber];
        for (int i = 1; i < factorsCount.length; i++) {
            for (int j = i; j < factorsCount.length; j += i){
                factorsCount[j] += 1;
            }
        }
        return factorsCount;
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
