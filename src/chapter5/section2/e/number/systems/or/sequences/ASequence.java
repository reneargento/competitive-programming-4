package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;

/**
 * Created by Rene Argento on 11/03/25.
 */
public class ASequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseNumber = 1;
        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int sum = 0;

            int[] values = new int[data.length - 1];
            for (int i = 1; i < data.length; i++) {
                values[i - 1] = Integer.parseInt(data[i]);
                sum += values[i - 1];
            }

            boolean isASequence = isASequence(values, sum);

            outputWriter.print(String.format("Case #%d:", caseNumber));
            for (int value : values) {
                outputWriter.print(" " + value);
            }
            outputWriter.printLine();
            if (isASequence) {
                outputWriter.printLine("This is an A-sequence.");
            } else {
                outputWriter.printLine("This is not an A-sequence.");
            }

            caseNumber++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isASequence(int[] values, int sum) {
        for (int i = 1; i < values.length; i++) {
            if (values[i] <= values[i - 1]) {
                return false;
            }
        }

        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;

        for (int i = 0; i < values.length; i++) {
            if (dp[values[i]]) {
                return false;
            }

            for (int j = sum; j >= values[i]; j--) {
                int newSum = j - values[i];
                if (dp[newSum]) {
                    dp[j] = true;
                }
            }
        }
        return true;
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