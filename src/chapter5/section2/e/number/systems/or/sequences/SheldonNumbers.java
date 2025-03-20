package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/03/25.
 */
public class SheldonNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long start = FastReader.nextLong();
        long end = FastReader.nextLong();
        int count = countSheldonNumbers(start, end);

        outputWriter.printLine(count);
        outputWriter.flush();
    }

    private static int countSheldonNumbers(long start, long end) {
        int count = 0;
        Set<Long> sheldonNumbers = generateSheldonNumbers();
        for (long sheldonNumber : sheldonNumbers) {
            if (start <= sheldonNumber && sheldonNumber <= end) {
                count++;
            }
        }
        return count;
    }

    private static Set<Long> generateSheldonNumbers() {
        List<Long> sheldonNumbers = new ArrayList<>();
        long bitOnesValue = 0;

        for (int bitOnes = 1; bitOnes <= 64; bitOnes++) {
            bitOnesValue <<= 1;
            bitOnesValue++;

            for (int bitZeroes = 0; bitZeroes <= 64; bitZeroes++) {
                long oneZeroSequence = (bitOnesValue << bitZeroes);
                int oneZeroSequenceLength = bitOnes + bitZeroes;
                long oneZeroSequenceWithRepeats = 0;

                for (int repeats = 1; repeats <= 64; repeats++) {
                    oneZeroSequenceWithRepeats <<= oneZeroSequenceLength;
                    oneZeroSequenceWithRepeats += oneZeroSequence;
                    if (oneZeroSequenceWithRepeats < 0) {
                        // Overflow
                        break;
                    }

                    long sequenceWithRepeatsPlusOneBits = (oneZeroSequenceWithRepeats << bitOnes) + bitOnesValue;
                    sheldonNumbers.add(oneZeroSequenceWithRepeats);
                    sheldonNumbers.add(sequenceWithRepeatsPlusOneBits);
                }
            }
        }
        return new HashSet<>(sheldonNumbers);
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
