package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/01/23.
 */
public class MarksDistribution {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int subjects = FastReader.nextInt();
            int marks = FastReader.nextInt();
            int minimumMarks = FastReader.nextInt();

            int ways = computeWays(subjects, marks, minimumMarks);
            outputWriter.printLine(ways);
        }
        outputWriter.flush();
    }

    private static int computeWays(int subjects, int marks, int minimumMarks) {
        // dp[subjects][marks]
        int[][] dp = new int[subjects + 1][marks + 1];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeWays(dp, minimumMarks, subjects, marks);
    }

    private static int computeWays(int[][] dp, int minimumMarks, int subjectIndex, int marksRemaining) {
        if (subjectIndex == 0) {
            if (marksRemaining == 0) {
                return 1;
            }
            return 0;
        }
        if (marksRemaining < minimumMarks) {
            return 0;
        }

        if (dp[subjectIndex][marksRemaining] != -1) {
            return dp[subjectIndex][marksRemaining];
        }

        int ways = 0;
        for (int mark = minimumMarks; mark <= marksRemaining; mark++) {
            ways += computeWays(dp, minimumMarks, subjectIndex - 1, marksRemaining - mark);
        }
        dp[subjectIndex][marksRemaining] = ways;
        return ways;
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
