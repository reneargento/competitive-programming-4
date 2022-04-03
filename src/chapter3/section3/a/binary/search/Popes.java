package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/03/22.
 */
public class Popes {

    private static class Result {
        int largestNumberOfPopes;
        int firstPopeYear;
        int lastPopeYear;

        public Result(int largestNumberOfPopes, int firstPopeYear, int lastPopeYear) {
            this.largestNumberOfPopes = largestNumberOfPopes;
            this.firstPopeYear = firstPopeYear;
            this.lastPopeYear = lastPopeYear;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (true) {
            int yearRange = Integer.parseInt(line);
            int[] popeYears = new int[FastReader.nextInt()];
            for (int i = 0; i < popeYears.length; i++) {
                popeYears[i] = FastReader.nextInt();
            }
            Result result = computeMostPopesInYearRange(yearRange, popeYears);
            outputWriter.printLine(String.format("%d %d %d", result.largestNumberOfPopes,
                    result.firstPopeYear, result.lastPopeYear));

            line = FastReader.getLine();
            if (line == null) {
                break;
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeMostPopesInYearRange(int yearRange, int[] popeYears) {
        int largestNumberOfPopes = 0;
        int firstPopeYear = 0;
        int lastPopeYear = 0;

        for (int i = 0; i < popeYears.length; i++) {
            int startYear = popeYears[i];
            int endYear = startYear + yearRange - 1;
            int endYearIndex = binarySearchUpperBound(popeYears, endYear);

            if (endYearIndex != -1 && endYearIndex - i + 1 > largestNumberOfPopes) {
                largestNumberOfPopes = endYearIndex - i + 1;
                firstPopeYear = startYear;
                lastPopeYear = popeYears[endYearIndex];
            }
        }
        return new Result(largestNumberOfPopes, firstPopeYear, lastPopeYear);
    }

    private static int binarySearchUpperBound(int[] values, int target) {
        int low = 0;
        int high = values.length - 1;

        int result = -1;
        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (values[middle] == target) {
                return middle;
            } else if (values[middle] > target) {
                high = middle - 1;
            } else {
                result = middle;
                low = middle + 1;
            }
        }
        return result;
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
