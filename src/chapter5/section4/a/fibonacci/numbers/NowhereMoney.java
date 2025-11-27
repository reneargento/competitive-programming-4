package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 25/11/25.
 */
public class NowhereMoney {

    private static class Result {
        List<Long> slotSizes;
        List<Long> slotValues;

        public Result(List<Long> slotSizes, List<Long> slotValues) {
            this.slotSizes = slotSizes;
            this.slotValues = slotValues;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long[] fibonacci = computeFibonacci();

        String line = FastReader.getLine();
        while (line != null) {
            long moneyChange = Long.parseLong(line);
            Result result = computeSlots(fibonacci, moneyChange);

            outputWriter.printLine(moneyChange);
            for (long slotSize : result.slotSizes) {
                outputWriter.print(slotSize + " ");
            }
            outputWriter.printLine();
            for (long slotValue : result.slotValues) {
                outputWriter.print(slotValue + " ");
            }
            outputWriter.printLine();
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computeSlots(long[] fibonacci, long moneyChange) {
        List<Long> slotSizes = new ArrayList<>();
        List<Long> slotValues = new ArrayList<>();

        if (moneyChange == 0) {
            slotSizes.add(0L);
            slotValues.add(1L);
        }

        for (int i = fibonacci.length - 1; i >= 1; i--) {
            if (moneyChange >= fibonacci[i]) {
                moneyChange -= fibonacci[i];
                slotSizes.add((long) i);
                slotValues.add(fibonacci[i]);
            }
        }
        return new Result(slotSizes, slotValues);
    }

    private static long[] computeFibonacci() {
        long[] fibonacci = new long[91];
        fibonacci[1] = 1;
        fibonacci[2] = 2;

        for (int i = 3; i < fibonacci.length; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        }
        return fibonacci;
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
