package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/05/21.
 */
public class CostumeContest {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int colleagues = FastReader.nextInt();
        Map<String, Integer> costumeFrequencies = new HashMap<>();

        for (int i = 0; i < colleagues; i++) {
            String costume = FastReader.next();
            int frequency = costumeFrequencies.getOrDefault(costume, 0);
            costumeFrequencies.put(costume, frequency + 1);
        }

        List<String> costumes = chooseCostumes(costumeFrequencies);
        for (String costume : costumes) {
            outputWriter.printLine(costume);
        }
        outputWriter.flush();
    }

    private static List<String> chooseCostumes(Map<String, Integer> costumeFrequencies) {
        List<String> costumes = new ArrayList<>();

        int lowestFrequency = Integer.MAX_VALUE;
        for (String costume : costumeFrequencies.keySet()) {
            int frequency = costumeFrequencies.get(costume);

            if (frequency < lowestFrequency) {
                lowestFrequency = frequency;
                costumes = new ArrayList<>();
                costumes.add(costume);
            } else if (frequency == lowestFrequency) {
                costumes.add(costume);
            }
        }
        Collections.sort(costumes);
        return costumes;
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
