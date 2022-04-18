package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 15/04/22.
 */
public class Cantor {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (!line.equals("END")) {
            if (line.equals("0") || line.equals("1")) {
                outputWriter.printLine("MEMBER");
            } else {
                int dotIndex = line.indexOf(".");
                String partialLine = line.substring(dotIndex + 1);
                int integerNumber = getIntegerNumber(partialLine);
                outputWriter.printLine(isInCantorSet(integerNumber) ? "MEMBER" : "NON-MEMBER");
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int getIntegerNumber(String number) {
        int integerNumber = 0;

        for (int i = 0; i < 6; i++) {
            integerNumber *= 10;
            if (i < number.length()) {
                integerNumber += Character.getNumericValue(number.charAt(i));
            }
        }
        return integerNumber;
    }

    private static boolean isInCantorSet(int number) {
        Set<Integer> visited = new HashSet<>();
        while (!visited.contains(number)) {
            visited.add(number);
            number *= 3;
            if (number / 1000000 == 1) {
                return false;
            }
            number %= 1000000;
        }
        return true;
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
