package chapter5.section6;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 24/03/26.
 */
public class DDF {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int[] factorDigitsSum = computeFactorDigitsSum();

        int caseId = 1;
        while (line != null) {
            String[] data = line.split(" ");
            int value1 = Integer.parseInt(data[0]);
            int value2 = Integer.parseInt(data[1]);

            List<Integer> ddf = computeDdf(value1, value2, factorDigitsSum);
            outputWriter.printLine(String.format("Input%d: %d %d", caseId, value1, value2));
            outputWriter.print(String.format("Output%d: ", caseId));
            outputWriter.print(ddf.get(0));
            for (int i = 1; i < ddf.size(); i++) {
                outputWriter.print(" " + ddf.get(i));
            }
            outputWriter.printLine();

            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int[] computeFactorDigitsSum() {
        int[] factorDigitsSum = new int[3001];
        for (int value = 0; value < 3001; value++) {
            factorDigitsSum[value] = getFactorsSum(value);
        }
        return factorDigitsSum;
    }

    private static List<Integer> computeDdf(int value1, int value2, int[] factorDigitsSum) {
        int start = Math.min(value1, value2);
        int end = Math.max(value1, value2);
        int longestCycleLength = 0;
        int bestValue = start;

        for (int value = start; value <= end; value++) {
            int cycleLength = computeCycleLength(value, factorDigitsSum);
            if (cycleLength > longestCycleLength) {
                longestCycleLength = cycleLength;
                bestValue = value;
            }
        }
        return computeCycle(bestValue, factorDigitsSum);
    }

    private static int computeCycleLength(int start, int[] factorDigitsSum) {
        int cycleLength = 0;
        boolean[] valuesSeen = new boolean[1001];
        int currentValue = start;

        while (currentValue >= valuesSeen.length || !valuesSeen[currentValue]) {
            if (currentValue < valuesSeen.length) {
                valuesSeen[currentValue] = true;
            }
            cycleLength++;
            currentValue = factorDigitsSum[currentValue];
        }
        return cycleLength;
    }

    private static List<Integer> computeCycle(int start, int[] factorDigitsSum) {
        boolean[] valuesSeen = new boolean[1001];
        List<Integer> cycle = new ArrayList<>();
        int currentValue = start;

        while (currentValue >= valuesSeen.length || !valuesSeen[currentValue]) {
            if (currentValue < valuesSeen.length) {
                valuesSeen[currentValue] = true;
            }
            cycle.add(currentValue);
            currentValue = factorDigitsSum[currentValue];
        }
        return cycle;
    }

    private static int getFactorsSum(int number) {
        int factorsSum = 0;
        int upperLimit = (int) Math.sqrt(number);

        for (int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                factorsSum += sumDigits(i);

                if (i != number / i) {
                    factorsSum += sumDigits(number / i);
                }
            }
        }
        return factorsSum;
    }

    private static int sumDigits(int value) {
        int digitsSum = 0;
        while (value > 0) {
            digitsSum += value % 10;
            value /= 10;
        }
        return digitsSum;
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
