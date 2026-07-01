package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 01/07/2026.
 */
public class NumberChains {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String number = FastReader.getLine();
        while (!number.equals("0")) {
            outputWriter.printLine("Original number was " + number);
            printNumberChain(number, outputWriter);
            number = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void printNumberChain(String number, OutputWriter outputWriter) {
        Set<Integer> numbersInChain = new HashSet<>();
        numbersInChain.add(Integer.parseInt(number));

        while (true) {
            char[] digits = number.toCharArray();
            Arrays.sort(digits);
            String increasingDigitsString = new String(digits);
            int increasingDigitsNumber = Integer.parseInt(increasingDigitsString);
            String reversedNumber = new StringBuilder(increasingDigitsString).reverse().toString();
            int decreasingDigitsNumber = Integer.parseInt(reversedNumber);

            int nextNumber = decreasingDigitsNumber - increasingDigitsNumber;
            outputWriter.printLine(String.format("%d - %d = %d", decreasingDigitsNumber, increasingDigitsNumber,
                    nextNumber));

            if (numbersInChain.contains(nextNumber)) {
                break;
            }
            numbersInChain.add(nextNumber);
            number = String.valueOf(nextNumber);
        }
        outputWriter.printLine("Chain length " + numbersInChain.size());
        outputWriter.printLine();
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