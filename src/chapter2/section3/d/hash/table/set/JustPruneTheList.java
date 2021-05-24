package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/05/21.
 */
public class JustPruneTheList {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int firstListSize = FastReader.nextInt();
            int secondListSize = FastReader.nextInt();

            Map<Integer, Integer> list1Frequency = computeFrequency(firstListSize);
            Map<Integer, Integer> list2Frequency = computeFrequency(secondListSize);

            int elementsToRemove = countElementsToRemove(list1Frequency, list2Frequency);
            outputWriter.printLine(elementsToRemove);
        }
        outputWriter.flush();
    }

    private static Map<Integer, Integer> computeFrequency(int size) throws IOException {
        Map<Integer, Integer> listFrequency = new HashMap<>();

        for (int i = 0; i < size; i++) {
            int number = FastReader.nextInt();
            int frequency = listFrequency.getOrDefault(number, 0);
            listFrequency.put(number, frequency + 1);
        }
        return listFrequency;
    }

    private static int countElementsToRemove(Map<Integer, Integer> list1Frequency,
                                             Map<Integer, Integer> list2Frequency) {
        int elementsToRemove = 0;

        for (int number : list1Frequency.keySet()) {
            int frequencyList1 = list1Frequency.get(number);
            int frequencyList2 = list2Frequency.getOrDefault(number, 0);
            elementsToRemove += Math.abs(frequencyList1 - frequencyList2);

            list1Frequency.put(number, 0);
            list2Frequency.put(number, 0);
        }

        for (int number : list2Frequency.keySet()) {
            int frequencyList1 = list1Frequency.getOrDefault(number, 0);
            int frequencyList2 = list2Frequency.get(number);
            elementsToRemove += Math.abs(frequencyList1 - frequencyList2);
        }
        return elementsToRemove;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
