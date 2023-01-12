package chapter3.section5.f.dp.level1;

import java.io.*;

/**
 * Created by Rene Argento on 11/01/23.
 */
public class BarCodes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int units = Integer.parseInt(data[0]);
            int bars = Integer.parseInt(data[1]);
            int maxWidth = Integer.parseInt(data[2]);

            long[][] dp = computeDp(maxWidth);
            outputWriter.printLine(dp[units][bars]);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long[][] computeDp(int maxWidth) {
        long[][] dp = new long[51][51];
        dp[0][0] = 1;

        for (int unit = 1; unit < dp.length; unit++) {
            for (int bars = 1; bars < dp[0].length; bars++) {
                for (int width = 1; width <= maxWidth; width++) {
                    if (unit >= width) {
                        dp[unit][bars] += dp[unit - width][bars - 1];
                    }
                }
            }
        }
        return dp;
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

        public void flush() {
            writer.flush();
        }
    }
}
