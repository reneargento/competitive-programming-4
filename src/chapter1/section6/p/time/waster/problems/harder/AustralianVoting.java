package chapter1.section6.p.time.waster.problems.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 27/12/20.
 */
public class AustralianVoting {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
            }

            int candidatesNumber = Integer.parseInt(FastReader.getLine());
            String[] candidates = new String[candidatesNumber];
            for (int c = 0; c < candidatesNumber; c++) {
                candidates[c] = FastReader.getLine();
            }

            List<Queue<Integer>> ballots = new ArrayList<>();
            String ballot = FastReader.getLine();

            while (ballot != null && !ballot.isEmpty()) {
                Queue<Integer> votes = new LinkedList<>();
                String[] votesData = ballot.split(" ");
                for (String vote : votesData) {
                    votes.offer(Integer.parseInt(vote) - 1);
                }
                ballots.add(votes);

                ballot = FastReader.getLine();
            }

            List<String> winners = findWinners(ballots, candidates);
            for (String winner : winners) {
                System.out.println(winner);
            }
        }
    }

    private static List<String> findWinners(List<Queue<Integer>> ballots, String[] candidates) {
        Set<Integer> candidatesSet = new HashSet<>();
        for (int i = 0; i < candidates.length; i++) {
            candidatesSet.add(i);
        }

        List<Integer> winnerIndexes = null;
        while (winnerIndexes == null) {
            int[] votes = new int[candidates.length];

            for (Queue<Integer> ballot : ballots) {
                while (!ballot.isEmpty() && !candidatesSet.contains(ballot.peek())) {
                    ballot.poll();
                }
                votes[ballot.peek()]++;
            }

            winnerIndexes = eliminateCandidates(candidatesSet, votes, ballots.size());
        }

        Collections.sort(winnerIndexes);
        List<String> winners = new ArrayList<>();
        for (int index : winnerIndexes) {
            winners.add(candidates[index]);
        }
        return winners;
    }

    private static List<Integer> eliminateCandidates(Set<Integer> candidatesSet, int[] votes, int numberOfVotes) {
        int lowestVote = Integer.MAX_VALUE;
        List<Integer> candidatesToEliminate = new ArrayList<>();
        int votesNeededForMajority = numberOfVotes / 2 + 1;

        for (int candidate : candidatesSet) {
            if (votes[candidate] >= votesNeededForMajority) {
                List<Integer> winner = new ArrayList<>();
                winner.add(candidate);
                return winner;
            }

            if (votes[candidate] <= lowestVote) {
                if (votes[candidate] < lowestVote) {
                    candidatesToEliminate = new ArrayList<>();
                    lowestVote = votes[candidate];
                }
                candidatesToEliminate.add(candidate);
            }
        }

        if (candidatesToEliminate.size() == candidatesSet.size()) {
            return new ArrayList<>(candidatesSet);
        }
        for (int candidateToEliminate : candidatesToEliminate) {
            candidatesSet.remove(candidateToEliminate);
        }
        return null;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
