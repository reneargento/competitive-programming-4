package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/01/23.
 */
public class SqrtLogSin {

    private static final int MOD = 1000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int index = FastReader.nextInt();
        int[] dp = new int[MOD + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;

        while (index != -1) {
            int x = computeX(dp, index);
            outputWriter.printLine(x);
            index = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeX(int[] dp, int index) {
        if (index < 0) {
            return 0;
        }
        if (dp[index] != -1) {
            return dp[index];
        }

        int index1 = (int) Math.floor(index - Math.sqrt(index));
        int index2 = (int) Math.floor(Math.log(index));
        int index3 = (int) Math.floor(index * Math.sin(index) * Math.sin(index));
        int sum = (computeX(dp, index1) + computeX(dp, index2) + computeX(dp, index3)) % MOD;
        dp[index] = sum;
        return sum;
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
