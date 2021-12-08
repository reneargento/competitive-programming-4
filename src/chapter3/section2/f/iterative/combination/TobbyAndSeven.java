package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/12/21.
 */
public class TobbyAndSeven {

    private static class BitsSwappedCount {
        int zeroesSwapped;
        int onesSwapped;

        public BitsSwappedCount(int zeroesSwapped, int onesSwapped) {
            this.zeroesSwapped = zeroesSwapped;
            this.onesSwapped = onesSwapped;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BitsSwappedCount that = (BitsSwappedCount) o;
            return zeroesSwapped == that.zeroesSwapped && onesSwapped == that.onesSwapped;
        }

        @Override
        public int hashCode() {
            return Objects.hash(zeroesSwapped, onesSwapped);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();

        while (line != null) {
            long modifiedNumber = Long.parseLong(line);
            Integer[] positionsSwapped = new Integer[FastReader.nextInt()];
            for (int i = 0; i < positionsSwapped.length; i++) {
                positionsSwapped[i] = FastReader.nextInt();
            }

            long originalNumber = getOriginalNumber(modifiedNumber, positionsSwapped);
            outputWriter.printLine(originalNumber);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long getOriginalNumber(long modifiedNumber, Integer[] positionsSwapped) {
        BitsSwappedCount bitsSwappedCount = countBitsSwapped(modifiedNumber, positionsSwapped);
        Arrays.sort(positionsSwapped, Collections.reverseOrder());
        return getOriginalNumber(modifiedNumber, positionsSwapped, bitsSwappedCount, 0);
    }

    private static long getOriginalNumber(long number, Integer[] positionsSwapped,
                                          BitsSwappedCount bitsSwappedCount, int index) {
        if (index == positionsSwapped.length) {
            if (isOriginalNumber(number, positionsSwapped, bitsSwappedCount)) {
                return number;
            } else {
                return -1;
            }
        }

        int bitIndex = positionsSwapped[index];

        long numberWithBit1 = number | (1L << bitIndex);
        long result1 = getOriginalNumber(numberWithBit1, positionsSwapped, bitsSwappedCount, index + 1);

        if (result1 != -1) {
            return result1;
        }

        long numberWithBit0 = number & (~(1L << bitIndex));
        long result2 = getOriginalNumber(numberWithBit0, positionsSwapped, bitsSwappedCount, index + 1);
        return result2;
    }

    private static boolean isOriginalNumber(long number, Integer[] positionsSwapped,
                                            BitsSwappedCount bitsSwappedCount) {
        BitsSwappedCount currentBitsSwapped = countBitsSwapped(number, positionsSwapped);
        return (number % 7 == 0) && currentBitsSwapped.equals(bitsSwappedCount);
    }

    private static BitsSwappedCount countBitsSwapped(long number, Integer[] positionsSwapped) {
        int zeroesSwapped = 0;
        int onesSwapped = 0;

        for (int position : positionsSwapped) {
            if ((number & (1L << position)) != 0) {
                onesSwapped++;
            } else {
                zeroesSwapped++;
            }
        }
        return new BitsSwappedCount(zeroesSwapped, onesSwapped);
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
