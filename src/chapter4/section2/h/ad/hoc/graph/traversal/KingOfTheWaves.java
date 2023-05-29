package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/05/23.
 */
@SuppressWarnings("unchecked")
public class KingOfTheWaves {

    private static int numberOfNodesVisited = 0;
    private static int maxDepth = 0;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int participants = FastReader.nextInt();
        int[][] adjacencyMatrix = new int[participants][participants];

        for (int participantID = 0; participantID < adjacencyMatrix.length; participantID++) {
            String matchResults = FastReader.next();
            for (int opponentID = 0; opponentID < matchResults.length(); opponentID++) {
                if (matchResults.charAt(opponentID) == '1') {
                    adjacencyMatrix[participantID][opponentID] = 1;
                }
            }
        }

        List<Integer> challengesOrder = computeChallengesOrder(adjacencyMatrix);
        if (challengesOrder == null) {
            outputWriter.printLine("impossible");
        } else {
            for (int i = 0; i < challengesOrder.size(); i++) {
                outputWriter.print(challengesOrder.get(i));
                if (i != challengesOrder.size() - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeChallengesOrder(int[][] adjacencyMatrix) {
        List<Integer>[] nodesAtDepth = new List[adjacencyMatrix.length];
        for (int i = 0; i < nodesAtDepth.length; i++) {
            nodesAtDepth[i] = new ArrayList<>();
        }
        nodesAtDepth[0].add(0);
        int[] depths = new int[adjacencyMatrix.length];
        int[] parents = new int[adjacencyMatrix.length];
        Arrays.fill(parents, -1);
        parents[0] = 0;

        depthFirstSearch(adjacencyMatrix, depths, parents, nodesAtDepth, 0);
        if (numberOfNodesVisited != adjacencyMatrix.length) {
            return null;
        }

        List<Integer> challengesOrder = new ArrayList<>();
        int maxDepthListSize = nodesAtDepth[maxDepth].size();
        int king = nodesAtDepth[maxDepth].get(maxDepthListSize - 1);

        boolean[] visited = new boolean[adjacencyMatrix.length];
        visited[king] = true;
        challengesOrder.add(king);

        for (int depth = maxDepth; depth >= 0; depth--) {
            for (int contestantID : nodesAtDepth[depth]) {
                if (!visited[contestantID]) {
                    challengesOrder.add(contestantID);

                    if (adjacencyMatrix[contestantID][king] == 1) {
                        king = contestantID;
                    }
                }
            }

            if (depth == 0) {
                break;
            }
            king = parents[king];
            visited[king] = true;
            challengesOrder.add(king);
        }
        return challengesOrder;
    }

    private static void depthFirstSearch(int[][] adjacencyMatrix, int[] depths, int[] parents,
                                        List<Integer>[] nodesAtDepth, int participantID) {
        numberOfNodesVisited++;

        for (int neighborID = 0; neighborID < adjacencyMatrix[participantID].length; neighborID++) {
            if (adjacencyMatrix[participantID][neighborID] == 1) {
                if (parents[neighborID] == -1) {
                    parents[neighborID] = participantID;
                    depths[neighborID] = depths[participantID] + 1;
                    if (depths[neighborID] > maxDepth) {
                        maxDepth = depths[neighborID];
                    }

                    nodesAtDepth[depths[neighborID]].add(neighborID);
                    depthFirstSearch(adjacencyMatrix, depths, parents, nodesAtDepth, neighborID);
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

        public void flush() {
            writer.flush();
        }
    }
}
