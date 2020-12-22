package chapter1.section6.o.time.waster.problems.easier;

import java.util.*;

/**
 * Created by Rene Argento on 20/12/20.
 */
public class TransferableVotingII {

    private static class Candidate implements Comparable<Candidate> {
        int id;
        int votes;

        public Candidate(int id, int votes) {
            this.id = id;
            this.votes = votes;
        }

        @Override
        public int compareTo(Candidate otherCandidate) {
            return otherCandidate.votes - votes;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int candidates = scanner.nextInt();
        int voters = scanner.nextInt();
        int election = 1;

        while (candidates != 0 || voters != 0) {
            int[][] votes = new int[voters][candidates];
            Set<Integer> invalidVoters = new HashSet<>();
            Set<Integer> eliminatedCandidates = new HashSet<>();

            for (int v = 0; v < voters; v++) {
                Set<Integer> candidatesVoted = new HashSet<>();

                for (int c = 0; c < candidates; c++) {
                    int vote = scanner.nextInt();
                    if (vote < 1 || vote > candidates) {
                        invalidVoters.add(v);
                    } else {
                        candidatesVoted.add(vote);
                    }
                    votes[v][c] = vote;
                }
                if (candidatesVoted.size() != candidates) {
                    invalidVoters.add(v);
                }
            }

            int votesNeededToWin = ((voters - invalidVoters.size()) / 2) + 1;
            List<Integer> winners = null;
            while (winners == null) {
                winners = getWinners(votes, votesNeededToWin, invalidVoters, eliminatedCandidates);
            }

            System.out.printf("Election #%d\n", election);
            System.out.printf("   %d bad ballot(s)\n", invalidVoters.size());
            if (winners.size() == 2) {
                System.out.printf("   The following candidates are tied: %d %d\n", winners.get(0) + 1, winners.get(1) + 1);
            } else {
                System.out.printf("   Candidate %d is elected.\n", winners.get(0) + 1);
            }
            election++;
            candidates = scanner.nextInt();
            voters = scanner.nextInt();
        }
    }

    private static List<Integer> getWinners(int[][] votes, int votesNeededToWin, Set<Integer> invalidVoters,
                                            Set<Integer> eliminatedCandidates) {
        List<Candidate> candidates = new ArrayList<>();
        for (int c = 0; c < votes[0].length; c++) {
            if (!eliminatedCandidates.contains(c)) {
                candidates.add(new Candidate(c, 0));
            }
        }

        for (int i = 0; i < votes.length; i++) {
            if (invalidVoters.contains(i)) {
                continue;
            }

            for (int v = 0; v < votes[i].length; v++) {
                int currentVote = votes[i][v];
                if (!eliminatedCandidates.contains(currentVote - 1)) {
                    addVote(candidates, currentVote - 1);
                    break;
                }
            }
        }
        Collections.sort(candidates);

        List<Integer> winners = new ArrayList<>();
        if (candidates.size() == 2 && candidates.get(0).votes == candidates.get(1).votes) {
            winners.add(candidates.get(0).id);
            winners.add(candidates.get(1).id);
            return winners;
        }

        if (candidates.get(0).votes >= votesNeededToWin) {
            winners.add(candidates.get(0).id);
            return winners;
        }

        eliminatedCandidates.add(candidates.get(candidates.size() - 1).id);
        return null;
    }

    private static void addVote(List<Candidate> candidates, int candidateId) {
        for (Candidate candidate : candidates) {
            if (candidate.id == candidateId) {
                candidate.votes++;
                break;
            }
        }
    }
}
