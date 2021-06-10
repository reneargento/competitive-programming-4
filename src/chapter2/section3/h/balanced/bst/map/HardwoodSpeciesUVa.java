package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by Rene Argento on 09/06/21.
 */
public class HardwoodSpeciesUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            TreeMap<String, Integer> treesFrequencyMap = new TreeMap<>();
            double totalTrees = 0;

            String treeSpecie = FastReader.getLine();
            while (treeSpecie != null && !treeSpecie.isEmpty()) {
                int frequency = treesFrequencyMap.getOrDefault(treeSpecie, 0);
                treesFrequencyMap.put(treeSpecie, frequency + 1);

                totalTrees++;
                treeSpecie = FastReader.getLine();
            }

            for (Map.Entry<String, Integer> treeFrequency : treesFrequencyMap.entrySet()) {
                double frequency = treeFrequency.getValue() / totalTrees * 100;
                outputWriter.printLine(String.format("%s %.4f", treeFrequency.getKey(), frequency));
            }
        }
        outputWriter.flush();
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
