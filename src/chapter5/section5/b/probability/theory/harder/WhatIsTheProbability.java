package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/02/26.
 */
public class WhatIsTheProbability {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int players = FastReader.nextInt();
            double successProbability = FastReader.nextDouble();
            int playerId = FastReader.nextInt();

            double winProbability = computeWinProbability(players, successProbability, playerId);
            outputWriter.printLine(String.format("%.4f", winProbability));
        }
        outputWriter.flush();
    }

    private static double computeWinProbability(int players, double successProbability, int playerId) {
        if (successProbability == 0) {
            return 0;
        }
        double failureProbability = 1 - successProbability;
        double probabilityAllFailOneSuccess = Math.pow(failureProbability, playerId - 1) * successProbability;
        double probabilityAtLeastOneSuccess = 1 - Math.pow(failureProbability, players);
        return probabilityAllFailOneSuccess / probabilityAtLeastOneSuccess;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
