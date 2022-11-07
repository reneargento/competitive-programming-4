package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;

/**
 * Created by Rene Argento on 06/11/22.
 */
public class PurpleRain {

    private static class Result {
        int maxDifference;
        int startPosition;
        int endPosition;

        public Result(int maxDifference, int startPosition, int endPosition) {
            this.maxDifference = maxDifference;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }

        private boolean isBetterResult(Result otherResult) {
            return maxDifference > otherResult.maxDifference
                    || (maxDifference == otherResult.maxDifference && startPosition < otherResult.startPosition)
                    || (maxDifference == otherResult.maxDifference && startPosition == otherResult.startPosition
                        && endPosition < otherResult.endPosition);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        Result result = computeMaxDifference(line);
        outputWriter.printLine(String.format("%d %d", result.startPosition, result.endPosition));
        outputWriter.flush();
    }

    private static Result computeMaxDifference(String line) {
        int[] redRainArray = new int[line.length()];
        int[] blueRainArray = new int[line.length()];

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == 'R') {
                redRainArray[i] = 1;
                blueRainArray[i] = -1;
            } else {
                redRainArray[i] = -1;
                blueRainArray[i] = 1;
            }
        }

        Result result1 = kadane(redRainArray);
        Result result2 = kadane(blueRainArray);
        if (result1.isBetterResult(result2)) {
            return result1;
        } else {
            return result2;
        }
    }

    private static Result kadane(int[] array) {
        int startPosition = 0;
        int currentStartPosition = 0;
        int endPosition = 0;
        int maxSum = 0;
        int currentSum = 0;

        for (int i = 0; i < array.length; i++) {
            currentSum += array[i];
            if (currentSum > maxSum) {
                maxSum = currentSum;
                startPosition = currentStartPosition;
                endPosition = i;
            }
            if (currentSum < 0) {
                currentSum = 0;
                currentStartPosition = i + 1;
            }
        }
        return new Result(maxSum, startPosition + 1, endPosition + 1);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
