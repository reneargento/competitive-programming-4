package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/12/21.
 */
public class BlockVoting {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int totalVotes = 0;
            int[] parties = new int[FastReader.nextInt()];
            for (int i = 0; i < parties.length; i++) {
                parties[i] = FastReader.nextInt();
                totalVotes += parties[i];
            }

            int requiredVotes = (totalVotes / 2) + 1;
            int[] powerIndexes = computePowerIndexes(parties, requiredVotes);
            for (int i = 0; i < powerIndexes.length; i++) {
                outputWriter.printLine(String.format("party %d has power index %d", i + 1, powerIndexes[i]));
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static int[] computePowerIndexes(int[] parties, int requiredVotes) {
        int[] powerIndexes = new int[parties.length];
        computePowerIndexes(parties, requiredVotes, powerIndexes, 0, 0);
        return powerIndexes;
    }

    private static void computePowerIndexes(int[] parties, int requiredVotes, int[] powerIndexes,
                                            int mask, int index) {
        if (index == parties.length) {
            evaluateCoalition(parties, requiredVotes, powerIndexes, mask);
            return;
        }

        int maskWithParty = mask | (1 << index);
        computePowerIndexes(parties, requiredVotes, powerIndexes, maskWithParty, index + 1);
        computePowerIndexes(parties, requiredVotes, powerIndexes, mask, index + 1);
    }

    private static void evaluateCoalition(int[] parties, int requiredVotes, int[] powerIndexes, int mask) {
        int votes = 0;

        for (int i = 0; i < parties.length; i++) {
            if ((mask & (1 << i)) > 0) {
                votes += parties[i];
            }
        }

        if (votes >= requiredVotes) {
            for (int i = 0; i < parties.length; i++) {
                if ((mask & (1 << i)) > 0
                        && votes - parties[i] < requiredVotes) {
                    powerIndexes[i]++;
                }
            }
        }
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
