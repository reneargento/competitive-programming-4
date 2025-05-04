package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/05/25.
 */
public class TheTrip {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int students = FastReader.nextInt();
        while (students != 0) {
            int[] expenses = new int[students];
            long total = 0;
            for (int i = 0; i < expenses.length; i++) {
                expenses[i] = (int) (FastReader.nextDouble() * 100 + 0.5); // + 0.5 to round to avoid floating point errors
                total += expenses[i];
            }
            double minimumMoneyToExchange = computeMinimumMoneyToExchange(expenses, total);
            outputWriter.printLine(String.format("$%.2f", minimumMoneyToExchange));
            students = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeMinimumMoneyToExchange(int[] expenses, long total) {
        long minimumMoneyToExchange = 0;
        long average = total / expenses.length;
        long extraCents = total % expenses.length;

        for (long expense : expenses) {
            if (expense > average) {
                minimumMoneyToExchange += expense - average;
                if (extraCents > 0) {
                    extraCents--;
                    minimumMoneyToExchange--;
                }
            }
        }
        return minimumMoneyToExchange / 100.0;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
