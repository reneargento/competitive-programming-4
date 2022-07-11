package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/07/22.
 */
public class ExtremeTerror {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] profits = new int[FastReader.nextInt()];
            int possibleToSkip = FastReader.nextInt();
            int[] moneyToClaim = new int[profits.length];
            int[] moneyPossible = new int[profits.length];

            for (int i = 0; i < moneyToClaim.length; i++) {
                moneyToClaim[i] = FastReader.nextInt();
            }
            for (int i = 0; i < moneyPossible.length; i++) {
                moneyPossible[i] = FastReader.nextInt();
            }
            for (int i = 0; i < profits.length; i++) {
                profits[i] = moneyPossible[i] - moneyToClaim[i];
            }

            long maxProfit = computeMaxProfit(profits, possibleToSkip);
            outputWriter.print(String.format("Case %d: ", t));
            if (maxProfit <= 0) {
                outputWriter.printLine("No Profit");
            } else {
                outputWriter.printLine(maxProfit);
            }
        }
        outputWriter.flush();
    }

    private static long computeMaxProfit(int[] profits, int possibleToSkip) {
        long maxProfit = 0;
        Arrays.sort(profits);

        for (int i = 0; i < profits.length; i++) {
            if (profits[i] < 0 && i < possibleToSkip) {
                continue;
            }
            maxProfit += profits[i];
        }
        return maxProfit;
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
