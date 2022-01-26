package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/01/22.
 */
public class Joseph {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int k = FastReader.nextInt();
        int[] dp = new int[14];

        while (k != 0) {
            int bestSkipLength = computeBestSkipLength(k, dp);
            outputWriter.printLine(bestSkipLength);
            k = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeBestSkipLength(int k, int[] dp) {
        if (dp[k] != 0) {
            return dp[k];
        }

        for (int skipLength = 1; true; skipLength++) {
            if (canKillAllBadGuysFirst(k, skipLength)) {
                dp[k] = skipLength;
                return skipLength;
            }
        }
    }

    private static boolean canKillAllBadGuysFirst(int k, int skipLength) {
        int circleSize = k * 2;
        for (int person = 0; circleSize > k; circleSize--) {
            person = (person + skipLength - 1) % circleSize;
            if (person < k) {
                return false;
            }
        }
        return true;
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
