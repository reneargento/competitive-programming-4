package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 29/05/21.
 */
public class TheDepartmentOfRedundancyDepartment {

    public static void main(String[] args) throws IOException {
        FastReader.init();

        List<Integer> numbersList = new ArrayList<>();
        Set<Integer> uniqueNumbers = new HashSet<>();
        Map<Integer, Integer> frequencies = new HashMap<>();

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");

            for (String numberString : data) {
                int number = Integer.parseInt(numberString);
                int frequency = frequencies.getOrDefault(number, 0);
                frequencies.put(number, frequency + 1);

                if (!uniqueNumbers.contains(number)) {
                    uniqueNumbers.add(number);
                    numbersList.add(number);
                }
            }
            line = FastReader.getLine();
        }
        printFrequencies(numbersList, frequencies);
    }

    private static void printFrequencies(List<Integer> numbersList, Map<Integer, Integer> frequencies) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        for (int number : numbersList) {
            int frequency = frequencies.get(number);
            outputWriter.printLine(number + " " + frequency);
        }
        outputWriter.flush();
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
