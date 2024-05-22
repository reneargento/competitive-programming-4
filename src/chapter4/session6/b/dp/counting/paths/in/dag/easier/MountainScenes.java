package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/05/24.
 */
public class MountainScenes {

    private static final long MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int ribbonLength = FastReader.nextInt();
        int widthFrame = FastReader.nextInt();
        int heightFrame = FastReader.nextInt();

        long totalScenes = computeScenes(ribbonLength, widthFrame, heightFrame);
        outputWriter.printLine(totalScenes);
        outputWriter.flush();
    }

    private static long computeScenes(int ribbonLength, int widthFrame, int heightFrame) {
        long[][] dp = new long[widthFrame][ribbonLength + 1];
        for (long[] values : dp) {
            Arrays.fill(values, -1);
        }
        int frameSize = widthFrame * heightFrame;
        int maxK = Math.min(ribbonLength, frameSize);
        long scenes = computeScenes(heightFrame, dp, 0, ribbonLength);

        scenes -= ((maxK / widthFrame) + 1);
        return scenes;
    }

    private static long computeScenes(int heightFrame, long[][] dp, int position, int ribbonLeft) {
        if (position == dp.length) {
            return 1;
        }
        if (dp[position][ribbonLeft] != -1) {
            return dp[position][ribbonLeft];
        }
        long scenes = 0;

        for (int height = 0; height <= heightFrame; height++) {
            if (height <= ribbonLeft) {
                scenes += computeScenes(heightFrame, dp, position + 1, ribbonLeft - height);
                scenes %= MOD;
            }
        }
        dp[position][ribbonLeft] = scenes;
        return dp[position][ribbonLeft];
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
