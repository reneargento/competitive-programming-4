package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 04/05/25.
 */
public class NumbersThatCount {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String number = FastReader.getLine();
        while (!number.equals("-1")) {
            String result = analyzeNumber(number);
            outputWriter.printLine(result);
            number = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String analyzeNumber(String number) {
        Map<String, Integer> numberToIndexMap = new HashMap<>();
        numberToIndexMap.put(number, 0);
        String originalNumberInventorized = inventorize(number);
        String currentNumber = number;

        for (int steps = 1; steps <= 15; steps++) {
            String inventorizedNumber = inventorize(currentNumber);
            if (inventorizedNumber.equals(currentNumber)) {
                String result = number + " is self-inventorying";

                if (!inventorizedNumber.equals(originalNumberInventorized)) {
                    result += " after " + (steps - 1) + " steps";
                }
                return result;
            }
            if (numberToIndexMap.containsKey(inventorizedNumber)) {
                int k = steps - numberToIndexMap.get(inventorizedNumber);
                return number + " enters an inventory loop of length " + k;
            }
            numberToIndexMap.put(inventorizedNumber, steps);
            currentNumber = inventorizedNumber;
        }
        return number + " can not be classified after 15 iterations";
    }

    private static String inventorize(String number) {
        StringBuilder inventorizedNumber = new StringBuilder();

        int[] digitCount = new int[10];
        for (char digit : number.toCharArray()) {
            int value = Character.getNumericValue(digit);
            digitCount[value]++;
        }

        for (int i = 0; i < digitCount.length; i++) {
            if (digitCount[i] > 0) {
                inventorizedNumber.append(digitCount[i]).append(i);
            }
        }
        return inventorizedNumber.toString();
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
