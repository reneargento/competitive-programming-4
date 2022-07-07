package chapter3.section4.b.involving.sorting.easier;

import java.io.*;

/**
 * Created by Rene Argento on 05/07/22.
 */
public class Fridge {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String digits = FastReader.getLine();

        String smallestImpossibleNumber = computeSmallestImpossibleNumber(digits);
        outputWriter.printLine(smallestImpossibleNumber);
        outputWriter.flush();
    }

    private static String computeSmallestImpossibleNumber(String digits) {
        int[] digitCounts = new int[10];
        for (int i = 0; i < digits.length(); i++) {
            int digit = Character.getNumericValue(digits.charAt(i));
            digitCounts[digit]++;
        }

        for (int copies = 1; ; copies++) {
            for (int i = 1; i < digitCounts.length; i++) {
                if (digitCounts[i] < copies) {
                    return assembleNumber(i, copies);
                }
            }

            if (digitCounts[0] < copies) {
                return 1 + assembleNumber(0, copies);
            }
        }
    }

    private static String assembleNumber(int digit, int copies) {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < copies; i++) {
            number.append(digit);
        }
        return number.toString();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
