package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 05/06/24.
 */
@SuppressWarnings("unchecked")
public class TVGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int positions = Integer.parseInt(line);
            List<Integer>[] adjacencyList = new List[positions];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            Set<Integer> specialPositions = new HashSet<>();

            for (int i = 0; i < adjacencyList.length; i++) {
                int locationId = getLocationId(FastReader.next().charAt(0));
                int path1Id = getLocationId(FastReader.next().charAt(0));
                int path2Id = getLocationId(FastReader.next().charAt(0));
                adjacencyList[locationId].add(path1Id);
                adjacencyList[locationId].add(path2Id);

                if (FastReader.next().charAt(0) == 'x') {
                    specialPositions.add(locationId);
                }
            }
            int moves = FastReader.nextInt();

            int waysToWin = countWaysToWin(adjacencyList, specialPositions, moves);
            outputWriter.printLine(waysToWin);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int countWaysToWin(List<Integer>[] adjacencyList, Set<Integer> specialPositions, int moves) {
        // dp[position id][moves left]
        int[][] dp = new int[adjacencyList.length][moves + 1];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return countWaysToWin(adjacencyList, specialPositions, dp, 0, moves);
    }

    private static int countWaysToWin(List<Integer>[] adjacencyList, Set<Integer> specialPositions, int[][] dp,
                                      int positionId, int movesLeft) {
        if (movesLeft == 0) {
            return specialPositions.contains(positionId) ? 1 : 0;
        }
        if (dp[positionId][movesLeft] != -1) {
            return dp[positionId][movesLeft];
        }

        int nextPositionId1 = adjacencyList[positionId].get(0);
        int nextPositionId2 = adjacencyList[positionId].get(1);
        int waysToWin = countWaysToWin(adjacencyList, specialPositions, dp, nextPositionId1, movesLeft - 1);
        waysToWin += countWaysToWin(adjacencyList, specialPositions, dp, nextPositionId2, movesLeft - 1);

        dp[positionId][movesLeft] = waysToWin;
        return dp[positionId][movesLeft];
    }

    private static int getLocationId(char location) {
        return (location - 'A');
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
