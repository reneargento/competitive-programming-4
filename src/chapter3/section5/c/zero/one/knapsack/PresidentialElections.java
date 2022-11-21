package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/11/22.
 */
public class PresidentialElections {

    private static class State {
        int delegates;
        long votesNeeded;

        public State(int delegates, int constituentsVotes, int federalsVotes, int undecidedVotes) {
            this.delegates = delegates;

            if (federalsVotes + undecidedVotes >= constituentsVotes) {
                long totalVotesPossible = constituentsVotes + undecidedVotes;
                if (totalVotesPossible <= federalsVotes) {
                    votesNeeded = Long.MAX_VALUE;
                } else {
                    long difference = Math.abs(federalsVotes - constituentsVotes);
                    long undecidedVotesRemaining = undecidedVotes - difference;
                    if (federalsVotes >= constituentsVotes) {
                        votesNeeded = difference + (undecidedVotesRemaining / 2) + 1;
                    } else {
                        votesNeeded = (undecidedVotesRemaining / 2) + 1;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int totalNumberOfDelegates = 0;
        int delegatesVotingInConstituents = 0;
        int numberOfStates = FastReader.nextInt();
        List<State> states = new ArrayList<>();

        for (int i = 0; i < numberOfStates; i++) {
            int delegates = FastReader.nextInt();
            totalNumberOfDelegates += delegates;
            State state = new State(delegates, FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            if (state.votesNeeded == 0) {
                delegatesVotingInConstituents += delegates;
            } else if (state.votesNeeded != Long.MAX_VALUE) {
                states.add(state);
            }
        }

        int halfDelegates = (totalNumberOfDelegates / 2) + 1;
        int delegatesNeeded = Math.max(halfDelegates - delegatesVotingInConstituents, 0);
        long minimumVotersToConvince = computeMinimumVotersToConvince(states, delegatesNeeded);
        if (minimumVotersToConvince == Long.MAX_VALUE) {
            outputWriter.printLine("impossible");
        } else {
            outputWriter.printLine(minimumVotersToConvince);
        }
        outputWriter.flush();
    }

    private static long computeMinimumVotersToConvince(List<State> states, int delegatesNeeded) {
        // dp[state][delegates] = number of votes
        long[][] dp = new long[states.size() + 1][2017];
        for (long[] votes : dp) {
            Arrays.fill(votes, -1);
        }
        return computeMinimumVotersToConvince(states, dp, 0, delegatesNeeded);
    }

    private static long computeMinimumVotersToConvince(List<State> states, long[][] dp, int index, int delegatesNeeded) {
        if (delegatesNeeded <= 0) {
            return 0;
        }
        if (index == states.size()) {
            return Long.MAX_VALUE;
        }
        if (dp[index][delegatesNeeded] != -1) {
            return dp[index][delegatesNeeded];
        }

        State state = states.get(index);

        long votesWithoutState = computeMinimumVotersToConvince(states, dp, index + 1, delegatesNeeded);
        long votesWithState = computeMinimumVotersToConvince(states, dp, index + 1, delegatesNeeded - state.delegates);
        if (votesWithState != Long.MAX_VALUE) {
            votesWithState += state.votesNeeded;
        }
        dp[index][delegatesNeeded] = Math.min(votesWithoutState, votesWithState);
        return dp[index][delegatesNeeded];
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
