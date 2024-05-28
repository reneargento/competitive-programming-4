package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/05/24.
 */
// Based on http://acmgnyr.org/year2016/solutions/C-mary.c
public class MAryPartitions {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = inputReader.nextInt();
        long[][] numberOfPartitionsWithOnes = new long[2][10001];

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = inputReader.nextInt();
            int m = inputReader.nextInt();
            int number = inputReader.nextInt();

            long mAryPartitions = countMAryPartitions(m, number, 0, numberOfPartitionsWithOnes);
            outputWriter.printLine(String.format("%d %d", dataSetNumber, mAryPartitions));
        }
        outputWriter.flush();
    }

    private static long countMAryPartitions(int m, int number, int level, long[][] numberOfPartitionsWithOnes) {
        long totalPartitions = 0;
        int n1 = number / m;
        int remaining = number % m;
        int currentArrayIndex = level & 1;
        int previousArrayIndex = (level + 1) & 1;

        if (number < m) {
            Arrays.fill(numberOfPartitionsWithOnes[currentArrayIndex], 0);
            numberOfPartitionsWithOnes[currentArrayIndex][number] = 1;
            return 1;
        }

        long subPartitions = countMAryPartitions(m, n1, level + 1, numberOfPartitionsWithOnes);
        totalPartitions += subPartitions;

        Arrays.fill(numberOfPartitionsWithOnes[currentArrayIndex], 0);
        // All the partitions that are just m * partition of n1 get remaining 1's at the end
        numberOfPartitionsWithOnes[currentArrayIndex][remaining] = subPartitions;
        // All the partitions of n1 with at least one 1 at the end, expand
        for (int i = 1; i <= n1; i++) {
            totalPartitions += i * numberOfPartitionsWithOnes[previousArrayIndex][i];

            if (numberOfPartitionsWithOnes[previousArrayIndex][i] > 0) {
                // For each j <= i, we replace j m's with j * m 1's and add remaining 1's
                for (int j = 1; j <= i; j++) {
                    int totalNumberOfOnes = j * m + remaining;
                    numberOfPartitionsWithOnes[currentArrayIndex][totalNumberOfOnes]
                            += numberOfPartitionsWithOnes[previousArrayIndex][i];
                }
            }
        }
        return totalPartitions;
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String next() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
