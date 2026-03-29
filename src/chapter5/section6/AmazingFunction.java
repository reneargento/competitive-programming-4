package chapter5.section6;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 29/03/26.
 */
public class AmazingFunction {

    private static class Result {
        List<Double> valuesBeforeCycle;
        List<Double> cycle;

        public Result(List<Double> valuesBeforeCycle, List<Double> cycle) {
            this.valuesBeforeCycle = valuesBeforeCycle;
            this.cycle = cycle;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Result result = computeCycle();
        BigInteger valuesBeforeCycleSize = BigInteger.valueOf(result.valuesBeforeCycle.size());
        BigInteger cycleSize = BigInteger.valueOf(result.cycle.size());

        String line = FastReader.getLine();
        while (line != null) {
            BigInteger index = new BigInteger(line);
            double value;
            if (index.compareTo(valuesBeforeCycleSize) < 0) {
                value = result.valuesBeforeCycle.get(index.intValue());
            } else {
                index = index.subtract(valuesBeforeCycleSize).mod(cycleSize);
                value = result.cycle.get(index.intValue());
            }
            outputWriter.printLine(value);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeCycle() {
        List<Double> allValues = new ArrayList<>();
        List<Double> valuesBeforeCycle = new ArrayList<>();
        List<Double> cycle = new ArrayList<>();
        Map<Double, Integer> valueIndexMap = new HashMap<>();

        double number = Math.sqrt(2) + Math.sqrt(3) + Math.sqrt(6);
        double roundedNumber = roundValuePrecisionDigits(number, 10);
        int index = 0;

        while (!valueIndexMap.containsKey(roundedNumber)) {
            allValues.add(roundedNumber);
            valueIndexMap.put(roundedNumber, index);

            index++;
            number = (number * number - 5) / (2 * number + 4);
            roundedNumber = roundValuePrecisionDigits(number, 10);
        }

        int cycleStartIndex = valueIndexMap.get(roundedNumber);
        for (int i = 0; i < cycleStartIndex; i++) {
            valuesBeforeCycle.add(allValues.get(i));
        }
        for (int i = cycleStartIndex; i < index; i++) {
            cycle.add(allValues.get(i));
        }
        return new Result(valuesBeforeCycle, cycle);
    }

    private static double roundValuePrecisionDigits(double value, int digits) {
        long valueToMultiply = (long) Math.pow(10, digits);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
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
