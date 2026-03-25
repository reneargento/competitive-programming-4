package chapter5.section6;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/03/26.
 */
public class ExpandingFractions {

    private static class Result {
        String decimalExpansion;
        int cycleLength;

        public Result(String decimalExpansion, int cycleLength) {
            this.decimalExpansion = decimalExpansion;
            this.cycleLength = cycleLength;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int numerator = FastReader.nextInt();
        int denominator = FastReader.nextInt();

        while (numerator != 0 || denominator != 0) {
            Result result = computeExpansion(numerator, denominator);
            printDecimalExpansion(result.decimalExpansion, outputWriter);
            if (result.cycleLength == 0) {
                outputWriter.printLine("This expansion terminates.");
            } else {
                outputWriter.printLine(String.format("The last %d digits repeat forever.", result.cycleLength));
            }
            outputWriter.printLine();

            numerator = FastReader.nextInt();
            denominator = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void printDecimalExpansion(String decimalExpansion, OutputWriter outputWriter) {
        for (int i = 0; i < decimalExpansion.length(); i += 50) {
            int endIndex = Math.min(decimalExpansion.length(), i + 50);
            outputWriter.printLine(decimalExpansion.substring(i, endIndex));
        }
    }

    private static Result computeExpansion(int numerator, int denominator) {
        StringBuilder result = new StringBuilder();
        Map<Long, Integer> remainderToIndexMap = new HashMap<>();
        long remainder = numerator % denominator;
        result.append(".");

        while (remainder != 0) {
            if (remainderToIndexMap.containsKey(remainder)) {
                int startIndex = remainderToIndexMap.get(remainder);
                int cycleSize = result.length() - startIndex;
                return new Result(result.toString(), cycleSize);
            }
            remainderToIndexMap.put(remainder, result.length());
            remainder *= 10;
            result.append(remainder / denominator);
            remainder %= denominator;
        }
        return new Result(result.toString(), 0);
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
