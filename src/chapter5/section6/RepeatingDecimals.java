package chapter5.section6;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 22/03/26.
 */
public class RepeatingDecimals {

    private static class Result {
        String decimalExpansion;
        int numberOfDigitsInCycle;

        public Result(String decimalExpansion, int numberOfDigitsInCycle) {
            this.decimalExpansion = decimalExpansion;
            this.numberOfDigitsInCycle = numberOfDigitsInCycle;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int numerator = Integer.parseInt(data[0]);
            int denominator = Integer.parseInt(data[1]);

            Result result = computeExpansion(numerator, denominator);
            outputWriter.printLine(String.format("%d/%d = %s", numerator, denominator, result.decimalExpansion));
            outputWriter.printLine(String.format("   %d = number of digits in repeating cycle",
                    result.numberOfDigitsInCycle));
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeExpansion(int numerator, int denominator) {
        StringBuilder result = new StringBuilder();

        // Integer part
        result.append(numerator / denominator);
        long remainder = numerator % denominator;
        if (remainder == 0) {
            return new Result(result.append(".(0)").toString(),1);
        }

        result.append(".");

        Map<Long, Integer> remainderToIndexMap = new HashMap<>();
        while (remainder != 0) {
            if (remainderToIndexMap.containsKey(remainder)) {
                int startIndex = remainderToIndexMap.get(remainder);
                int cycleSize = result.length() - startIndex;
                int periodIndex = result.toString().indexOf(".");
                int endIndex = 50 - result.length() + periodIndex;
                result.insert(startIndex, "(");
                if (endIndex >= 0) {
                    result.append(")");
                } else {
                    result.replace(periodIndex + 52, result.length(), "...)");
                }
                return new Result(result.toString(), cycleSize);
            }
            remainderToIndexMap.put(remainder, result.length());
            remainder *= 10;
            result.append(remainder / denominator);
            remainder %= denominator;
        }
        return new Result(result.append("(0)").toString(), 1);
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
