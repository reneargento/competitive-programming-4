package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/02/25.
 */
public class CandleBoxUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int ageDifference = Integer.parseInt(line);
            int candlesInRitasBox = FastReader.nextInt();
            int candlesInTheosBox = FastReader.nextInt();

            int candlesToRemove = computeCandlesToRemove(ageDifference, candlesInRitasBox, candlesInTheosBox);
            outputWriter.printLine(candlesToRemove);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeCandlesToRemove(int ageDifference, int candlesInRitasBox, int candlesInTheosBox) {
        int candles = 0;
        int totalCandlesInBox = candlesInRitasBox + candlesInTheosBox;
        int correctRitasBox = 0;
        int nextIncrementRita = 4;
        int nextIncrementTheo = 3;

        for (int i = 0; i < ageDifference - 1; i++) {
            candles += nextIncrementRita;
            correctRitasBox += nextIncrementRita;
            nextIncrementRita++;
        }

        while (candles < totalCandlesInBox) {
            candles += nextIncrementRita;
            candles += nextIncrementTheo;

            correctRitasBox += nextIncrementRita;

            nextIncrementRita++;
            nextIncrementTheo++;
        }
        return candlesInRitasBox - correctRitasBox;
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
