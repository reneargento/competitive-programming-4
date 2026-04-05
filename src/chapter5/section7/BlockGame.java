package chapter5.section7;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/04/26.
 */
public class BlockGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long blocks1 = FastReader.nextLong();
        long blocks2 = FastReader.nextLong();

        boolean canWin = canWin(blocks1, blocks2);
        outputWriter.printLine(canWin ? "win" : "lose");
        outputWriter.flush();
    }

    private static boolean canWin(long blocks1, long blocks2) {
        long minBlocks = Math.min(blocks1, blocks2);
        long maxBlocks = Math.max(blocks1, blocks2);

        if (minBlocks == maxBlocks || maxBlocks % minBlocks == 0) {
            return true;
        }
        if (maxBlocks < 2 * minBlocks) {
            return !canWin(minBlocks, maxBlocks - minBlocks);
        }
        return true;
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
