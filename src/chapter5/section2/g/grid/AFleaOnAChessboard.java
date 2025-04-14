package chapter5.section2.g.grid;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/04/25.
 */
public class AFleaOnAChessboard {

    private static class Result {
        int jumpsNeeded;
        long x;
        long y;

        public Result(int jumpsNeeded, long x, long y) {
            this.jumpsNeeded = jumpsNeeded;
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int sideSize = FastReader.nextInt();
        int x = FastReader.nextInt();
        int y = FastReader.nextInt();
        int jumpDx = FastReader.nextInt();
        int jumpDy = FastReader.nextInt();

        while (sideSize != 0 || x != 0 || y != 0 || jumpDx != 0 || jumpDy != 0) {
            Result result = computeMinimumJumpsNeeded(sideSize, x, y, jumpDx, jumpDy);
            if (result == null) {
                outputWriter.printLine("The flea cannot escape from black squares.");
            } else {
                outputWriter.printLine(String.format("After %d jumps the flea lands at (%d, %d).", result.jumpsNeeded,
                        result.x, result.y));
            }

            sideSize = FastReader.nextInt();
            x = FastReader.nextInt();
            y = FastReader.nextInt();
            jumpDx = FastReader.nextInt();
            jumpDy = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeMinimumJumpsNeeded(int sideSize, long x, long y, int jumpDx, int jumpDy) {
        if (isWhite(sideSize, x, y)) {
            return new Result(0, x, y);
        }

        for (int jumps = 1; jumps < 10000; jumps++) {
            x += jumpDx;
            y += jumpDy;

            if (isWhite(sideSize, x, y)) {
                return new Result(jumps, x, y);
            }
        }
        return null;
    }

    private static boolean isWhite(int sideSize, long x, long y) {
        long row = y / sideSize;
        long column = x / sideSize;

        boolean isRowBoundary = y % sideSize == 0;
        boolean isColumnBoundary = x % sideSize == 0;

        boolean isWhiteEvenRow = row % 2 == 0 && column % 2 == 1 && !isRowBoundary && !isColumnBoundary;
        boolean isWhiteOddRow = row % 2 == 1 && column % 2 == 0 && !isRowBoundary && !isColumnBoundary;
        return isWhiteEvenRow || isWhiteOddRow;
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
