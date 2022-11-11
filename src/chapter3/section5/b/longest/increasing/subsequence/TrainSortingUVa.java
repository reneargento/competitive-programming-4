package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/11/22.
 */
public class TrainSortingUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int cars = FastReader.nextInt();
            int[] weights = new int[cars];
            for (int i = 0; i < weights.length; i++) {
                int reverseIndex = weights.length - 1 - i;
                weights[reverseIndex] = FastReader.nextInt();
            }

            int longestTrainLength = computeLongestTrainLength(weights);
            outputWriter.printLine(longestTrainLength);
        }
        outputWriter.flush();
    }

    private static int computeLongestTrainLength(int[] weights) {
        if (weights.length == 0) {
            return 0;
        }
        int[] lisLengths = computeLengths(weights, true);
        int[] ldsLengths = computeLengths(weights, false);

        int longestTrainLength = 0;
        for (int i = 0; i < lisLengths.length; i++) {
            int length = lisLengths[i] + ldsLengths[i] - 1;
            longestTrainLength = Math.max(longestTrainLength, length);
        }
        return longestTrainLength;
    }

    private static int[] computeLengths(int[] weights, boolean isLis) {
        int[] lis = new int[weights.length];
        Arrays.fill(lis, 1);

        for (int i = 0; i < weights.length; i++) {
            for (int j = i + 1; j < weights.length; j++) {
                if (((isLis && weights[j] > weights[i]) || (!isLis && weights[j] < weights[i]))
                        && lis[i] + 1 > lis[j]) {
                    lis[j] = lis[i] + 1;
                }
            }
        }
        return lis;
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
