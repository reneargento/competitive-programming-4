package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/07/22.
 */
public class AViciousPikemanEasy {

    private final static int MOD = 1000000007;

    private static class Result {
        int problemsSolved;
        long penaltyTime;

        public Result(int problemsSolved, long penaltyTime) {
            this.problemsSolved = problemsSolved;
            this.penaltyTime = penaltyTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long[] problemTimes = new long[FastReader.nextInt()];
        int contestTime = FastReader.nextInt();

        long a = FastReader.nextInt();
        long b = FastReader.nextInt();
        long c = FastReader.nextInt();
        problemTimes[0] = FastReader.nextInt();

        for (int i = 1; i < problemTimes.length; i++) {
            problemTimes[i] = ((a * problemTimes[i - 1] + b) % c) + 1;
        }
        Result result = computePikemenResult(problemTimes, contestTime);
        outputWriter.printLine(String.format("%d %d", result.problemsSolved, result.penaltyTime));
        outputWriter.flush();
    }

    private static Result computePikemenResult(long[] problemTimes, int contestTime) {
        int problemsSolved = 0;
        long penaltyTime = 0;
        long currentTime = 0;

        Arrays.sort(problemTimes);
        for (long problemTime : problemTimes) {
            if (currentTime + problemTime > contestTime) {
                break;
            }
            problemsSolved++;
            currentTime += problemTime;
            penaltyTime = (penaltyTime + currentTime) % MOD;
        }
        return new Result(problemsSolved, penaltyTime);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
