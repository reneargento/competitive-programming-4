package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 04/11/21.
 */
public class DiceCup {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int test = 1;
        String line = FastReader.getLine();
        while (line != null) {
            if (test > 1) {
                outputWriter.printLine();
            }

            String[] input = line.split(" ");
            int faces1 = Integer.parseInt(input[0]);
            int faces2 = Integer.parseInt(input[1]);

            List<Integer> probabilities = computeProbabilities(faces1, faces2);
            for (int probability : probabilities) {
                outputWriter.printLine(probability);
            }

            test++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeProbabilities(int faces1, int faces2) {
        Map<Integer, Integer> sumFrequency = new HashMap<>();
        int highestFrequency = 0;

        for (int i = 1; i <= faces1; i++) {
            for (int j = 1; j <= faces2; j++) {
                int sum = i + j;
                int newFrequency = sumFrequency.getOrDefault(sum, 0) + 1;
                sumFrequency.put(sum, newFrequency);

                highestFrequency = Math.max(highestFrequency, newFrequency);
            }
        }

        List<Integer> probabilities = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : sumFrequency.entrySet()) {
            if (entry.getValue() == highestFrequency) {
                probabilities.add(entry.getKey());
            }
        }

        Collections.sort(probabilities);
        return probabilities;
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
