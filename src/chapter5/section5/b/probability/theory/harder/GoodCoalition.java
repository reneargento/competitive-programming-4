package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/02/26.
 */
public class GoodCoalition {

    private static class Party {
        int seats;
        double probabilityToComplete;

        public Party(int seats, double probabilityToComplete) {
            this.seats = seats;
            this.probabilityToComplete = probabilityToComplete;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Party[] parties = new Party[FastReader.nextInt()];
            for (int i = 0; i < parties.length; i++) {
                parties[i] = new Party(FastReader.nextInt(), FastReader.nextInt() / 100.0);
            }

            double mostStableProbability = computeMostStableProbability(parties) * 100;
            outputWriter.printLine(mostStableProbability);
        }
        outputWriter.flush();
    }

    private static double computeMostStableProbability(Party[] parties) {
        // dp[current party id][seats taken]
        double[][] dp = new double[parties.length][151];
        for (double[] values : dp) {
            Arrays.fill(values, -1.0);
        }
        return computeMostStableProbability(0, 0, dp, parties);
    }

    private static double computeMostStableProbability(int partyId, int seatsTaken, double[][] dp, Party[] parties) {
        if (seatsTaken > 75) {
            return 1;
        }
        if (partyId == parties.length) {
            return 0;
        }
        if (dp[partyId][seatsTaken] != -1.0) {
            return dp[partyId][seatsTaken];
        }

        double probabilityWithParty = parties[partyId].probabilityToComplete *
                computeMostStableProbability(partyId + 1, seatsTaken + parties[partyId].seats, dp, parties);
        double probabilityWithoutParty = computeMostStableProbability(partyId + 1, seatsTaken, dp, parties);
        dp[partyId][seatsTaken] = Math.max(probabilityWithParty, probabilityWithoutParty);
        return dp[partyId][seatsTaken];
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
