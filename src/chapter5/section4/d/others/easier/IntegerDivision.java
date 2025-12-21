package chapter5.section4.d.others.easier;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/12/25.
 */
public class IntegerDivision {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int elements = FastReader.nextInt();
        int divisor = FastReader.nextInt();

        Map<Integer, Integer> resultToFrequencyMap = new HashMap<>();

        for (int i = 0; i < elements; i++) {
            int result = FastReader.nextInt() / divisor;
            if (!resultToFrequencyMap.containsKey(result)) {
                resultToFrequencyMap.put(result, 0);
            }

            int newFrequency = resultToFrequencyMap.get(result) + 1;
            resultToFrequencyMap.put(result, newFrequency);
        }

        long pairsWithSameResult = computePairsWithSameResult(resultToFrequencyMap);
        outputWriter.printLine(pairsWithSameResult);
        outputWriter.flush();
    }

    private static long computePairsWithSameResult(Map<Integer, Integer> resultToFrequencyMap) {
        long pairsWithSameResult = 0;

        for (int frequency : resultToFrequencyMap.values()) {
            pairsWithSameResult += frequency * (frequency - 1L) / 2;
        }
        return pairsWithSameResult;
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

        public void flush() {
            writer.flush();
        }
    }
}
