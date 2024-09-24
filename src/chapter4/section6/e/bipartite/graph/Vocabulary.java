package chapter4.section6.e.bipartite.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 30/07/24.
 */
@SuppressWarnings("unchecked")
public class Vocabulary {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            String[] jackVocabulary = new String[Integer.parseInt(data[0])];
            String[] jillChallenges = new String[Integer.parseInt(data[1])];

            for (int i = 0; i < jackVocabulary.length; i++) {
                jackVocabulary[i] = FastReader.getLine();
            }
            for (int i = 0; i < jillChallenges.length; i++) {
                jillChallenges[i] = FastReader.getLine();
            }

            int maxSolvableChallenges = computeMaxSolvableChallenges(jackVocabulary, jillChallenges);
            outputWriter.printLine(maxSolvableChallenges);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMaxSolvableChallenges(String[] jackVocabulary, String[] jillChallenges) {
        List<Integer>[] adjacencyList = buildGraph(jackVocabulary, jillChallenges);
        return computeMaximumCardinality(adjacencyList, jillChallenges.length);
    }

    private static List<Integer>[] buildGraph(String[] jackVocabulary, String[] jillChallenges) {
        List<Integer>[] adjacencyList = new List[jackVocabulary.length + jillChallenges.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int jillId = 0; jillId < jillChallenges.length; jillId++) {
            for (int jackId = 0; jackId < jackVocabulary.length; jackId++) {
                if (solvesChallenge(jillChallenges[jillId], jackVocabulary[jackId])) {
                    int jackVertexId = jillChallenges.length + jackId;
                    adjacencyList[jillId].add(jackVertexId);
                    adjacencyList[jackVertexId].add(jillId);
                }
            }
        }
        return adjacencyList;
    }

    private static boolean solvesChallenge(String challenge, String word) {
        int[] challengeCount = countLetterFrequency(challenge);
        int[] wordCount = countLetterFrequency(word);

        for (int letterId = 0; letterId < challengeCount.length; letterId++) {
            if (challengeCount[letterId] > wordCount[letterId]) {
                return false;
            }
        }
        return true;
    }

    private static int[] countLetterFrequency(String word) {
        int[] counter = new int[26];

        for (char letter : word.toCharArray()) {
            int letterId = letter - 'a';
            counter[letterId]++;
        }
        return counter;
    }

    private static int computeMaximumCardinality(List<Integer>[] adjacencyList, int leftPartitionVertexIDs) {
        int maximumMatches = 0;
        int[] match = new int[adjacencyList.length];
        Arrays.fill(match, -1);

        for (int vertexID = 0; vertexID < leftPartitionVertexIDs; vertexID++) {
            boolean[] visited = new boolean[adjacencyList.length];
            maximumMatches += tryToMatch(adjacencyList, match, visited, vertexID);
        }
        return maximumMatches;
    }

    private static int tryToMatch(List<Integer>[] adjacencyList, int[] match, boolean[] visited, int vertexID) {
        if (visited[vertexID]) {
            return 0;
        }
        visited[vertexID] = true;

        for (int neighbor : adjacencyList[vertexID]) {
            if (match[neighbor] == -1 || tryToMatch(adjacencyList, match, visited, match[neighbor]) == 1) {
                match[neighbor] = vertexID; // flip status
                return 1;
            }
        }
        return 0;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
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
