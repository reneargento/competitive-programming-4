package chapter2.section2.e.sorting.easier;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/02/21.
 */
public class PresidentialElection {

    private static class Candidate implements Comparable<Candidate> {
        int id;
        double votes;

        public Candidate(int id) {
            this.id = id;
        }

        @Override
        public int compareTo(Candidate other) {
            if (votes != other.votes) {
                return Double.compare(other.votes, votes);
            }
            return id - other.id;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            int candidatesNumber = FastReader.nextInt();
            int states = FastReader.nextInt();

            Candidate[] candidates = initializeCandidates(candidatesNumber);
            long countryPopulation = 0;

            for (int i = 0; i < states; i++) {
                double[] votePercentages = new double[candidatesNumber];

                for (int c = 0; c < candidates.length; c++) {
                    votePercentages[c] = FastReader.nextDouble();
                }
                int population = FastReader.nextInt();
                countryPopulation += population;

                for (int c = 0; c < candidates.length; c++) {
                    candidates[c].votes += votePercentages[c] * population;
                }
            }

            List<Candidate> winners = computeWinners(candidates, countryPopulation);
            for (Candidate winner : winners) {
                int votes = (int) (winner.votes / 100);
                outputWriter.printLine(winner.id + " " + votes);
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static List<Candidate> computeWinners(Candidate[] candidates, long countryPopulation) {
        List<Candidate> winners = new ArrayList<>();
        Arrays.sort(candidates);
        double requiredVotesToWin = countryPopulation * 50.1;
        winners.add(candidates[0]);

        if (candidates[0].votes < requiredVotesToWin) {
            winners.add(candidates[1]);
        }
        return winners;
    }

    private static Candidate[] initializeCandidates(int candidatesNumber) {
        Candidate[] candidates = new Candidate[candidatesNumber];
        for (int c = 0; c < candidates.length; c++) {
            candidates[c] = new Candidate(c + 1);
        }
        return candidates;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
