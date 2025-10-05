package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/10/25.
 */
public class Parket {

    private static class Result {
        long dimension1;
        long dimension2;

        public Result(long dimension1, long dimension2) {
            this.dimension1 = dimension1;
            this.dimension2 = dimension2;
        }
    }

    private static final int MAX_DIMENSION = 5000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int redBlocks = FastReader.nextInt();
        int brownBlocks = FastReader.nextInt();

        Result result = computeRoomDimensions(redBlocks, brownBlocks);
        outputWriter.printLine(result.dimension1 + " " + result.dimension2);
        outputWriter.flush();
    }

    private static Result computeRoomDimensions(int redBlocks, int brownBlocks) {
        for (int dimension1 = 1; dimension1 <= MAX_DIMENSION; dimension1++) {
            for (int dimension2 = 1; dimension2 <= MAX_DIMENSION; dimension2++) {
                int redBlocksCandidate = (dimension1 * 2) + (dimension2 * 2) - 4;
                int brownBlocksCandidate = (dimension1 - 2) * (dimension2 - 2);

                if (redBlocksCandidate == redBlocks && brownBlocksCandidate == brownBlocks) {
                    int maxDimension = Math.max(dimension1, dimension2);
                    int minDimension = Math.min(dimension1, dimension2);
                    return new Result(maxDimension, minDimension);
                }
            }
        }
        return new Result(1, 1);
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
