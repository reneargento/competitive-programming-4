package chapter5.section7;

import java.io.*;

/**
 * Created by Rene Argento on 03/04/26.
 */
public class BachetsGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        // !line.isEmpty() is required for UVa to pass, but not Kattis
        while (line != null && !line.isEmpty()) {
            String[] data = line.split(" ");
            int stones = Integer.parseInt(data[0]);
            int[] numbers = new int[Integer.parseInt(data[1])];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Integer.parseInt(data[i + 2]);
            }

            boolean doesSWin = doesSWin(stones, numbers);
            outputWriter.print(doesSWin ? "Stan" : "Ollie");
            outputWriter.printLine(" wins");
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean doesSWin(int stones, int[] numbers) {
        boolean[] dp = new boolean[stones + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int number : numbers) {
                if (i - number < 0) {
                    continue;
                }
                if (!dp[i - number]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[stones];
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
