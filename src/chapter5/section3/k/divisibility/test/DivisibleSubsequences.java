package chapter5.section3.k.divisibility.test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/10/25.
 */
public class DivisibleSubsequences {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int divisor = FastReader.nextInt();
            int[] sequence = new int[FastReader.nextInt()];

            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = FastReader.nextInt();
            }

            int divisibleSubsequences = countDivisibleSubsequences(divisor, sequence);
            outputWriter.printLine(divisibleSubsequences);
        }
        outputWriter.flush();
    }

    private static int countDivisibleSubsequences(int divisor, int[] sequence) {
        int divisibleSubsequences = 0;
        long prefixSum = 0;
        Map<Long, Integer> modFrequencyMap = new HashMap<>();

        for (int value : sequence) {
            prefixSum += value;
            long mod = prefixSum % divisor;

            if (!modFrequencyMap.containsKey(mod)) {
                modFrequencyMap.put(mod, 0);
            }
            int frequency = modFrequencyMap.get(mod);

            divisibleSubsequences += frequency;
            if (mod == 0) {
                divisibleSubsequences++;
            }
            modFrequencyMap.put(mod, frequency + 1);
        }
        return divisibleSubsequences;
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
