package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 13/01/24.
 */
// Based on https://www.cnblogs.com/staginner/archive/2011/12/07/2279783.html
public class OldWineIntoNewBottlesKattis {

    private static class Bottle {
        int minimumCapacity;
        int maximumCapacity;

        public Bottle(int minimumCapacity, int maximumCapacity) {
            this.minimumCapacity = minimumCapacity;
            this.maximumCapacity = maximumCapacity;
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = inputReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int wine = inputReader.nextInt() * 1000;
            Bottle[] sizes = new Bottle[inputReader.nextInt()];
            int lowerBound = Integer.MAX_VALUE;

            for (int i = 0; i < sizes.length; i++) {
                sizes[i] = new Bottle(inputReader.nextInt(), inputReader.nextInt());
                int difference = sizes[i].maximumCapacity - sizes[i].minimumCapacity;
                int lowerCandidate = (int) (sizes[i].minimumCapacity * Math.ceil(sizes[i].minimumCapacity / (double) difference));
                lowerBound = Math.min(lowerBound, lowerCandidate);
            }

            int wineToBottle = computeWineToBottle(wine, sizes, lowerBound);
            outputWriter.printLine(wineToBottle);
        }
        outputWriter.flush();
    }

    private static int computeWineToBottle(int wine, Bottle[] sizes, int lowerBound) {
        if (wine >= lowerBound) {
            return 0;
        }

        // [wine analyzed] = max wine bottled
        int[] dp = new int[450001];
        boolean[] hasCapacity = new boolean[4501];
        int[] volume = new int[4501];
        int volumeIndex = 0;

        for (Bottle bottle : sizes) {
            for (int capacity = bottle.minimumCapacity; capacity <= bottle.maximumCapacity; capacity++) {
                if (!hasCapacity[capacity]) {
                    hasCapacity[capacity] = true;
                    volume[volumeIndex] = capacity;
                    volumeIndex++;
                }
            }
        }

        for (int i = 0; i < volumeIndex; i++) {
            for (int j = volume[i]; j <= wine; j++) {
                int wineUsed = dp[j - volume[i]] + volume[i];
                dp[j] = Math.max(dp[j], wineUsed);
            }
        }
        return wine - dp[wine];
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        private InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int snext() throws IOException {
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

        private int nextInt() throws IOException {
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

        private boolean isSpaceChar(int c) {
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
