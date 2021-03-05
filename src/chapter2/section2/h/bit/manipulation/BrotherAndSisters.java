package chapter2.section2.h.bit.manipulation;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/03/21.
 */
public class BrotherAndSisters {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int numbers = FastReader.nextInt();
            int queries = FastReader.nextInt();
            Set<Integer> relevantNumbers = new HashSet<>();

            for (int i = 0; i < numbers; i++) {
                int number = FastReader.nextInt();
                if (number <= 255) {
                    relevantNumbers.add(number);
                }
            }

            int[] andResults = precomputeAnds(relevantNumbers);

            for (int q = 0; q < queries; q++) {
                int query = FastReader.nextInt();
                outputWriter.printLine(andResults[query]);
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static int[] precomputeAnds(Set<Integer> numbers) {
        int[] andResults = new int[230];

        for (int query = 0; query < andResults.length; query++) {
            int maxAnd = 0;

            for (int number : numbers) {
                int andResult = query & number;
                maxAnd = Math.max(maxAnd, andResult);
            }
            andResults[query] = maxAnd;
        }
        return andResults;
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
