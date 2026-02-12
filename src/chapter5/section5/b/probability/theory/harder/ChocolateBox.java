package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/02/26.
 */
public class ChocolateBox {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int chocolates = FastReader.nextInt();
        int caseId = 1;

        while (chocolates != -1) {
            int boxes = FastReader.nextInt();
            double probability = computeProbability(chocolates, boxes);
            outputWriter.printLine(String.format("Case %d: %.7f", caseId, probability));

            caseId++;
            chocolates = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeProbability(int chocolates, int boxes) {
        // dp[chocolates left][box id] = probability of no empty boxes
        double[][] dp = new double[chocolates + 1][boxes + 1];
        dp[1][1] = 1;

        for (int chocolate = 2; chocolate <= chocolates; chocolate++) {
            for (int box = 1; box <= boxes; box++) {
                double placeChocolateProbability = box / (double) boxes;
                dp[chocolate][box] = dp[chocolate - 1][box] * placeChocolateProbability
                        + dp[chocolate - 1][box - 1] * (boxes - box + 1) / boxes;
            }
        }
        return 1 - dp[chocolates][boxes];
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
