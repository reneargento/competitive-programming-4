package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class Zamka {

    private static class TrapCodes {
        int lowestResult;
        int highestResult;

        public TrapCodes(int lowestResult, int highestResult) {
            this.lowestResult = lowestResult;
            this.highestResult = highestResult;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int start = FastReader.nextInt();
        int end = FastReader.nextInt();
        int targetSum = FastReader.nextInt();

        TrapCodes trapCodes = computeCodes(start, end, targetSum);
        outputWriter.printLine(trapCodes.lowestResult);
        outputWriter.printLine(trapCodes.highestResult);
        outputWriter.flush();
    }

    private static TrapCodes computeCodes(int start, int end, int targetSum) {
        int lowestResult = -1;
        int highestResult = -1;

        for (int i = start; i <= end; i++) {
            if (computeDigitsSum(i) == targetSum) {
                lowestResult = i;
                break;
            }
        }

        for (int i = end; i >= start; i--) {
            if (computeDigitsSum(i) == targetSum) {
                highestResult = i;
                break;
            }
        }
        return new TrapCodes(lowestResult, highestResult);
    }

    private static int computeDigitsSum(int number) {
        int digitsSum = 0;

        while (number > 0) {
            digitsSum += number % 10;
            number /= 10;
        }
        return digitsSum;
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
