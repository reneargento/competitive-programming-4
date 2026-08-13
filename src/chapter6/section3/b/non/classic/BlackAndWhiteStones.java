package chapter6.section3.b.non.classic;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/08/2026.
 */
public class BlackAndWhiteStones {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int swapCost = Integer.parseInt(data[0]);
            int refundValue = Integer.parseInt(data[1]);
            char[] stones = FastReader.next().toCharArray();

            long minimumCoins = computeMinimumCoins(stones, swapCost, refundValue);
            outputWriter.printLine(minimumCoins);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeMinimumCoins(char[] stones, int swapCost, int refundValue) {
        long minimumCoins = 0;
        int blackStones = countBlackStones(stones);

        int j = blackStones;
        for (int i = blackStones - 1; i >= 0; i--) {
            if (stones[i] == 'W') {
                while (j < stones.length) {
                    if (stones[j] == 'B') {
                        long stonesInvolved = j - i;
                        long costToMove = (stonesInvolved * swapCost) - (stonesInvolved * refundValue);
                        minimumCoins += Math.min(costToMove, swapCost);
                        j++;
                        break;
                    }
                    j++;
                }
            }
        }
        return minimumCoins;
    }

    private static int countBlackStones(char[] stones) {
        int blackStones = 0;
        for (char stone : stones) {
            if (stone == 'B') {
                blackStones++;
            }
        }
        return blackStones;
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

        public void flush() {
            writer.flush();
        }
    }
}