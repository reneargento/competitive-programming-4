package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/24.
 */
public class KoernersPub {

    private static class Result {
        int lowest;
        int highest;

        public Result(int lowest, int highest) {
            this.lowest = lowest;
            this.highest = highest;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int aEqualCTimes = FastReader.nextInt();
            int cEqualMTimes = FastReader.nextInt();
            int aEqualMTimes = FastReader.nextInt();

            Result result = computeEqualCases(aEqualCTimes, cEqualMTimes, aEqualMTimes);
            outputWriter.print("Case #" + t + ": ");
            if (result != null) {
                outputWriter.printLine(String.format("Between %d and %d times.", result.lowest, result.highest));
            } else {
                outputWriter.printLine("The records are faulty.");
            }
        }
        outputWriter.flush();
    }

    private static Result computeEqualCases(int aEqualCTimes, int cEqualMTimes, int aEqualMTimes) {
        int highest = Math.min(aEqualCTimes, cEqualMTimes);
        highest = Math.min(highest, aEqualMTimes);
        int lowest;

        int sum = aEqualCTimes + cEqualMTimes + aEqualMTimes;
        if (sum <= 100) {
            lowest = 0;
        } else {
            lowest = (sum - 99) / 2;
        }

        if (lowest > highest) {
            return null;
        }
        return new Result(lowest, highest);
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
