package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/06/22.
 */
public class Elephants {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int elephants = FastReader.nextInt();
            int maximumWeight = FastReader.nextInt();
            int[] elephantWeights = new int[elephants];
            for (int i = 0; i < elephantWeights.length; i++) {
                elephantWeights[i] = FastReader.nextInt();
            }
            int maximumNumberOfElephants = computeMaximumNumberOfElephants(elephantWeights, maximumWeight);
            outputWriter.printLine(maximumNumberOfElephants);
        }
        outputWriter.flush();
    }

    private static int computeMaximumNumberOfElephants(int[] elephantWeights, int maximumWeight) {
        Arrays.sort(elephantWeights);
        int maximumNumberOfElephants = 0;
        int totalWeight = 0;

        for (int elephantWeight : elephantWeights) {
            if (totalWeight + elephantWeight > maximumWeight) {
                break;
            }
            totalWeight += elephantWeight;
            maximumNumberOfElephants++;
        }
        return maximumNumberOfElephants;
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