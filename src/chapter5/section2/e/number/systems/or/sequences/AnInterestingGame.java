package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/03/25.
 */
public class AnInterestingGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int marbles = FastReader.nextInt();
            int k = FastReader.nextInt();
            int startMarble = FastReader.nextInt();
            int endMarble = startMarble + k - 1;

            int remainingWeight = computeRemainingWeight(marbles, startMarble, endMarble);
            outputWriter.printLine(String.format("Case %d: %d", t, remainingWeight));
        }
        outputWriter.flush();
    }

    private static int computeRemainingWeight(int marbles, int startMarble, int endMarble) {
        int totalWeight = computeSumFrom1(marbles);
        int totalWeightUntilEndMarble = computeSumFrom1(endMarble);
        int totalWeightBeforeStartMarble = computeSumFrom1(startMarble - 1);
        return totalWeight - totalWeightUntilEndMarble + totalWeightBeforeStartMarble;
    }

    private static int computeSumFrom1(int marbles) {
        return (marbles * (marbles + 1)) / 2;
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
