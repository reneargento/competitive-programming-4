package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/06/21.
 */
@SuppressWarnings("unchecked")
public class MinistryOfMagic {

    private static class CandidateVotes implements Comparable<CandidateVotes> {
        int candidate;
        int votes;

        public CandidateVotes(int candidate, int votes) {
            this.candidate = candidate;
            this.votes = votes;
        }

        @Override
        public int compareTo(CandidateVotes other) {
            if (votes != other.votes) {
                return Integer.compare(other.votes, votes);
            }
            return Integer.compare(other.candidate, candidate);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int candidates = FastReader.nextInt();
        int parties = FastReader.nextInt();
        int totalVotes = 0;

        int[] partyVotes = new int[parties];
        Queue<Integer>[] partyRankings = new LinkedList[parties];

        for (int i = 0; i < partyVotes.length; i++) {
            partyVotes[i] = FastReader.nextInt();
            totalVotes += partyVotes[i];
            partyRankings[i] = new LinkedList<>();

            for (int c = 0; c < candidates; c++) {
                partyRankings[i].offer(FastReader.nextInt());
            }
        }
        int winner = computeWinner(partyVotes, partyRankings, totalVotes);
        outputWriter.printLine(winner);
        outputWriter.flush();
    }

    private static int computeWinner(int[] partyVotes, Queue<Integer>[] partyRankings, int totalVotes) {
        int candidates = partyRankings[0].size();
        int votesNeededForMajority = (totalVotes / 2) + 1;
        Set<Integer> eliminatedCandidates = new HashSet<>();
        int[] candidateToVotes = new int[candidates + 1];

        TreeSet<CandidateVotes> candidateVotes = new TreeSet<>();
        for (int candidate = 1; candidate <= candidates; candidate++) {
            candidateVotes.add(new CandidateVotes(candidate, 0));
        }

        for (int round = 0; round < candidates - 1; round++) {
            for (int party = 0; party < partyRankings.length; party++) {
                Queue<Integer> partyRanking = partyRankings[party];
                boolean updateVote = false;
                if (round == 0) {
                    updateVote = true;
                }

                while (eliminatedCandidates.contains(partyRanking.peek())) {
                    partyRanking.poll();
                    updateVote = true;
                }

                if (updateVote) {
                    int newCandidate = partyRanking.peek();
                    int currentVotes = candidateToVotes[newCandidate];
                    CandidateVotes candidateVotesKey = new CandidateVotes(newCandidate, currentVotes);

                    candidateVotes.remove(candidateVotesKey);
                    candidateVotesKey.votes += partyVotes[party];
                    candidateVotes.add(candidateVotesKey);
                    candidateToVotes[newCandidate] += partyVotes[party];
                }
            }
            if (candidateVotes.first().votes >= votesNeededForMajority) {
                return candidateVotes.first().candidate;
            }
            CandidateVotes eliminatedCandidate = candidateVotes.pollLast();
            eliminatedCandidates.add(eliminatedCandidate.candidate);
        }
        return candidateVotes.first().candidate;
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
