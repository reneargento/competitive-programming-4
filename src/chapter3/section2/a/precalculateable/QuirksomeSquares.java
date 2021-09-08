package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 07/09/21.
 */
public class QuirksomeSquares {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Integer>[] allQuirksomeNumbers = generateAllQuirksomeNumbers();

        String line = FastReader.getLine();

        while (line != null) {
            int digits = Integer.parseInt(line);
            int index = (digits - 1) / 2;

            for (int quircksomeNumber : allQuirksomeNumbers[index]) {
                outputWriter.printLine(String.format("%0" + digits + "d", quircksomeNumber));
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    @SuppressWarnings("unchecked")
    private static List<Integer>[] generateAllQuirksomeNumbers() {
        List<Integer> possibleNumbers = new ArrayList<>();
        // The largest possible quirk number is 99999999
        for (int number = 0; number <= 9999; number++) {
            possibleNumbers.add(number * number);
        }

        List<Integer>[] allQuirksomeNumbers = new ArrayList[4];
        for (int i = 0; i < allQuirksomeNumbers.length; i++) {
            int digits = (i + 1) * 2;
            allQuirksomeNumbers[i] = generateQuirksomeNumbers(digits, possibleNumbers);
        }
        return allQuirksomeNumbers;
    }

    private static List<Integer> generateQuirksomeNumbers(int digits, List<Integer> possibleNumbers) {
        List<Integer> quirksomeNumbers = new ArrayList<>();
        int maxNumber = (int) Math.pow(10, digits);
        int halfDigits = digits / 2;
        int half = (int) Math.pow(10, halfDigits);

        for (int number : possibleNumbers) {
            if (number >= maxNumber) {
                break;
            }

            int firstPart = number / half;
            int secondPart = number % half;
            int squared = (firstPart + secondPart) * (firstPart + secondPart);
            if (squared == number) {
                quirksomeNumbers.add(number);
            }
        }
        return quirksomeNumbers;
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
