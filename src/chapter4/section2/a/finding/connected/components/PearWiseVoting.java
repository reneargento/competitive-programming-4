package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/03/23.
 */
@SuppressWarnings("unchecked")
public class PearWiseVoting {

    private static class Ballot {
        int voters;
        String ballot;

        public Ballot(int voters, String ballot) {
            this.voters = voters;
            this.ballot = ballot;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int candidates = FastReader.nextInt();
        Ballot[] ballots = new Ballot[FastReader.nextInt()];

        for (int i = 0; i < ballots.length; i++) {
            ballots[i] = new Ballot(FastReader.nextInt(), FastReader.next());
        }
        List<Integer>[] adjacencyList = createGraph(candidates, ballots);
        for (int candidateID = 0; candidateID < candidates; candidateID++) {
            char candidateName = (char) ('A' + candidateID);
            outputWriter.print(candidateName + ": ");

            boolean canWin = canWin(adjacencyList, candidateID);
            outputWriter.printLine(canWin ? "can win" : "can't win");
        }
        outputWriter.flush();
    }

    private static List<Integer>[] createGraph(int candidates, Ballot[] ballots) {
        List<Integer>[] adjacencyList = new List[candidates];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int candidate1 = 0; candidate1 < candidates; candidate1++) {
            for (int candidate2 = candidate1 + 1; candidate2 < candidates; candidate2++) {
                int candidate1ExtraVotes = 0;

                for (Ballot ballot : ballots) {
                    int candidate1Index = ballot.ballot.indexOf('A' + candidate1);
                    int candidate2Index = ballot.ballot.indexOf('A' + candidate2);
                    if (candidate1Index < candidate2Index) {
                        candidate1ExtraVotes += ballot.voters;
                    } else {
                        candidate1ExtraVotes -= ballot.voters;
                    }
                }

                if (candidate1ExtraVotes > 0) {
                    adjacencyList[candidate1].add(candidate2);
                } else {
                    adjacencyList[candidate2].add(candidate1);
                }
            }
        }
        return adjacencyList;
    }

    private static boolean canWin(List<Integer>[] adjacencyList, int candidateID) {
        boolean[] visited = new boolean[adjacencyList.length];
        int totalVisited = depthFirstSearch(adjacencyList, visited, candidateID);
        return totalVisited == adjacencyList.length;
    }

    private static int depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int candidateID) {
        visited[candidateID] = true;
        int totalVisited = 1;
        for (int neighborID : adjacencyList[candidateID]) {
            if (!visited[neighborID]) {
                totalVisited += depthFirstSearch(adjacencyList, visited, neighborID);
            }
        }
        return totalVisited;
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
