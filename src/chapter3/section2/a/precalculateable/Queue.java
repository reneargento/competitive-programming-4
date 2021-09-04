package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/09/21.
 */
// Based on https://stackoverflow.com/questions/44984008/arranging-people-in-queue-uva-10128
public class Queue {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        long[][][] dp = precomputeValidQueues();

        for (int t = 0; t < tests; t++) {
            int queueSize = FastReader.nextInt();
            int seenInFront = FastReader.nextInt();
            int seenInBack = FastReader.nextInt();

            if (seenInFront > 13 || seenInBack > 13) {
                outputWriter.printLine(0);
            } else {
                outputWriter.printLine(dp[queueSize][seenInFront][seenInBack]);
            }
        }
        outputWriter.flush();
    }

    private static long[][][] precomputeValidQueues() {
        long[][][] dp = new long[14][14][14];
        dp[1][1][1] = 1;

        for (int queueSize = 2; queueSize <= 13; queueSize++) {
            for (int seenInFront = 1; seenInFront <= queueSize; seenInFront++) {
                for (int seenInBack = 1; seenInBack <= queueSize; seenInBack++) {
                    dp[queueSize][seenInFront][seenInBack] =
                            dp[queueSize - 1][seenInFront - 1][seenInBack] +
                            dp[queueSize - 1][seenInFront][seenInBack - 1] +
                            dp[queueSize - 1][seenInFront][seenInBack] * (queueSize - 2);
                }
            }
        }
        return dp;
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
