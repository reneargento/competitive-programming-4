package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/03/26.
 */
public class FleecingTheRaffle {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int names = FastReader.nextInt();
        int prizes = FastReader.nextInt();
        double maxWinProbability = computeMaxWinProbability(names, prizes);
        outputWriter.printLine(maxWinProbability);
        outputWriter.flush();
    }

    private static double computeMaxWinProbability(int names, int prizes) {
        double currentProbability = prizes / (names + 1.0);
        double maxWinProbability = currentProbability;

        for (int namesAdded = 2; namesAdded <= names; namesAdded++) {
            currentProbability /= namesAdded + names;
            currentProbability *= namesAdded + names - prizes;
            currentProbability *= namesAdded / (namesAdded - 1.0);

            if (currentProbability > maxWinProbability) {
                maxWinProbability = currentProbability;
            }
        }
        return maxWinProbability;
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
