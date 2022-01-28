package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/01/22.
 */
public class TheHammingDistanceProblem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            int bitStringsLength = FastReader.nextInt();
            int hammingDistance = FastReader.nextInt();
            printBitStrings(bitStringsLength, hammingDistance,0,
                    bitStringsLength - 1, outputWriter);
        }
        outputWriter.flush();
    }

    private static void printBitStrings(int bitStringsLength, int hammingDistance, int mask,
                                        int bitIndex, OutputWriter outputWriter) {
        if (bitIndex == -1) {
            if (hasCorrectHammingDistance(mask, hammingDistance)) {
                printBits(mask, bitStringsLength, outputWriter);
            }
            return;
        }

        printBitStrings(bitStringsLength, hammingDistance, mask, bitIndex - 1, outputWriter);
        int maskWithBitSet = mask | (1 << bitIndex);
        printBitStrings(bitStringsLength, hammingDistance, maskWithBitSet, bitIndex - 1, outputWriter);
    }

    private static boolean hasCorrectHammingDistance(int mask, int hammingDistance) {
        int bitsSet = 0;
        while (mask > 0) {
            bitsSet++;
            mask = mask & (mask - 1);
        }
        return bitsSet == hammingDistance;
    }

    private static void printBits(int mask, int bitStringsLength, OutputWriter outputWriter) {
        for (int i = bitStringsLength - 1; i >= 0; i--) {
            if ((mask & (1 << i)) > 0) {
                outputWriter.print("1");
            } else {
                outputWriter.print("0");
            }
        }
        outputWriter.printLine();
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

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
