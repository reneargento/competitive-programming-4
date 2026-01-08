package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/01/26.
 */
public class TriTiling {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int columns = FastReader.nextInt();
        long[] tileWays = countTileWays();

        while (columns != -1) {
            outputWriter.printLine(tileWays[columns]);
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long[] countTileWays() {
        long[] tileWays = new long[31];
        tileWays[0] = 1;
        tileWays[1] = 0;
        tileWays[2] = 3;

        for (int column = 3; column < tileWays.length; column++) {
            long ways = 3 * tileWays[column - 2];
            for (int horizontalBlocks = 1; horizontalBlocks < tileWays.length; horizontalBlocks++) {
                if (2 * horizontalBlocks + 2 > column) {
                    break;
                }
                ways += 2 * tileWays[column - 2 * horizontalBlocks - 2];
            }
            tileWays[column] = ways;
        }
        return tileWays;
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

        public void flush() {
            writer.flush();
        }
    }
}
