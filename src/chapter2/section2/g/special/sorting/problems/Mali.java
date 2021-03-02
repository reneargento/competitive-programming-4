package chapter2.section2.g.special.sorting.problems;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/03/21.
 */
public class Mali {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int rounds = FastReader.nextInt();
        int[] aNumbers = new int[101];
        int[] bNumbers = new int[101];

        for (int r = 0; r < rounds; r++) {
            int aNumber = FastReader.nextInt();
            int bNumber = FastReader.nextInt();

            aNumbers[aNumber]++;
            bNumbers[bNumber]++;

            int smallestMaximalSum = getSmallestMaximalSum(aNumbers, bNumbers);
            outputWriter.printLine(smallestMaximalSum);
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static int getSmallestMaximalSum(int[] aNumbers, int[] bNumbers) {
        int[] aNumbersCopy = aNumbers.clone();
        int[] bNumbersCopy = bNumbers.clone();

        int smallestMaximalSum = 0;
        int bIndex = 100;

        for (int aIndex = 1; aIndex <= 100; aIndex++) {

            while (aNumbersCopy[aIndex] > 0) {
                while (bIndex >= 1) {
                    if (bNumbersCopy[bIndex] > 0) {
                        int matches = Math.min(aNumbersCopy[aIndex], bNumbersCopy[bIndex]);
                        smallestMaximalSum = Math.max(smallestMaximalSum, aIndex + bIndex);
                        aNumbersCopy[aIndex] -= matches;
                        bNumbersCopy[bIndex] -= matches;
                    }

                    if (aNumbersCopy[aIndex] == 0) {
                        break;
                    }
                    bIndex--;
                }
            }
        }
        return smallestMaximalSum;
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
