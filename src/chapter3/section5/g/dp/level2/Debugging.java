package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/01/23.
 */
public class Debugging {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int codeLines = FastReader.nextInt();
        int compileRunTime = FastReader.nextInt();
        int addPrintTime = FastReader.nextInt();

        long worstTime = computeWorstCaseTime(codeLines, compileRunTime, addPrintTime);
        outputWriter.printLine(worstTime);
        outputWriter.flush();
    }

    private static long computeWorstCaseTime(int codeLines, int compileRunTime, int addPrintTime) {
        // dp[numberOfLines] = minimum time spent
        long[] dp = new long[codeLines + 1];
        Arrays.fill(dp, -1);
        return computeWorstCaseTime(compileRunTime, addPrintTime, dp, codeLines);
    }

    private static long computeWorstCaseTime(int compileRunTime, int addPrintTime, long[] dp, int numberOfLines) {
        if (numberOfLines <= 1) {
            return 0;
        }
        if (dp[numberOfLines] != -1) {
            return dp[numberOfLines];
        }

        long minimumTime = Long.MAX_VALUE;
        int previousBlockSize = 0;
        for (int divider = 2; divider <= numberOfLines; divider++) {
            int blockSize = (int) Math.ceil(numberOfLines / (double) divider);
            if (blockSize == previousBlockSize) {
                continue;
            }

            long time = (divider - 1L) * addPrintTime +
                    computeWorstCaseTime(compileRunTime, addPrintTime, dp, blockSize);
            minimumTime = Math.min(minimumTime, time);
            previousBlockSize = blockSize;
        }
        dp[numberOfLines] = minimumTime + compileRunTime;
        return dp[numberOfLines];
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
