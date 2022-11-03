package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;

/**
 * Created by Rene Argento on 26/09/22.
 */
public class LocalizedSummingForBlurring {

    private static class Result {
        int[][] blurredMatrix;
        long sum;

        public Result(int[][] blurredMatrix, long sum) {
            this.blurredMatrix = blurredMatrix;
            this.sum = sum;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int caseId = 1;

        while (line != null) {
            String[] data = line.split(" ");
            int size = Integer.parseInt(data[0]);
            int offset = Integer.parseInt(data[1]);
            line = FastReader.getLine();
            int[][] cumulativeSum = new int[size][size];
            int row = 0;
            int column = 0;

            while (line != null && !line.isEmpty()) {
                int value = Integer.parseInt(line);
                if (row > 0) {
                    value += cumulativeSum[row - 1][column];
                }
                if (column > 0) {
                    value += cumulativeSum[row][column - 1];
                }
                if (row > 0 && column > 0) {
                    value -= cumulativeSum[row - 1][column - 1];
                }
                cumulativeSum[row][column] = value;

                column++;
                if (column == size) {
                    row++;
                    column = 0;
                }
                line = FastReader.getLine();
            }

            if (caseId > 1) {
                outputWriter.printLine();
            }
            Result result = blurMatrix(cumulativeSum, offset);
            for (int blurredMatrixRow = 0; blurredMatrixRow < result.blurredMatrix.length; blurredMatrixRow++) {
                for (int blurredMatrixColumn = 0; blurredMatrixColumn < result.blurredMatrix[0].length; blurredMatrixColumn++) {
                    outputWriter.printLine(result.blurredMatrix[blurredMatrixRow][blurredMatrixColumn]);
                }
            }
            outputWriter.printLine(result.sum);

            if (line != null) {
                line = FastReader.getLine();
            }
            caseId++;
        }
        outputWriter.flush();
    }

    private static Result blurMatrix(int[][] cumulativeSum, int offset) {
        int blurredMatrixSize = cumulativeSum.length - offset + 1;
        int[][] blurredMatrix = new int[blurredMatrixSize][blurredMatrixSize];
        long sum = 0;

        for (int row = 0; row < cumulativeSum.length - offset + 1; row++) {
            for (int column = 0; column < cumulativeSum[0].length - offset + 1; column++) {
                int endRow = row + offset - 1;
                int endColumn = column + offset - 1;
                int localSum = cumulativeSum[endRow][endColumn];
                if (row > 0) {
                    localSum -= cumulativeSum[row - 1][endColumn];
                }
                if (column > 0) {
                    localSum -= cumulativeSum[endRow][column - 1];
                }
                if (row > 0 && column > 0) {
                    localSum += cumulativeSum[row - 1][column - 1];
                }
                blurredMatrix[row][column] = localSum;
                sum += localSum;
            }
        }
        return new Result(blurredMatrix, sum);
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
