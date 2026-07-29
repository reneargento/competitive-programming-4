package chapter6.section3.a.classic;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 29/07/2026.
 */
// Based on https://github.com/morris821028/UVa/blob/master/volume012/1244%20-%20Palindromic%20paths.cpp
public class PalindromicPaths {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int points = FastReader.nextInt();
            int[][] dp = new int[points][points];
            String[] paths = new String[points];

            List<Integer>[][] adjacencyList = new List[points][points];
            for (int vertexId1 = 0; vertexId1 < adjacencyList.length; vertexId1++) {
                for (int vertexId2 = 0; vertexId2 < adjacencyList[vertexId1].length; vertexId2++) {
                    adjacencyList[vertexId1][vertexId2] = new ArrayList<>();
                }
            }

            for (int p = 0; p < points; p++) {
                paths[p] = FastReader.getLine();

                for (int i = 0; i < paths[p].length(); i++) {
                    if (p < i) {
                        dp[p][i] = 1;
                        adjacencyList[p][i].add(getAdjustedId(p, i));
                    }
                }
            }

            String longestPath = computeLongestPath(adjacencyList, paths, dp);
            outputWriter.printLine(longestPath);
        }
        outputWriter.flush();
    }

    private static int getAdjustedId(int id, int neighborId) {
        return (id << 10) + neighborId;
    }

    private static String computeLongestPath(List<Integer>[][] adjacencyList, String[] paths, int[][] dp) {
        computeDpAndPathLengths(adjacencyList, paths, dp);
        if (dp[0][adjacencyList.length - 1] == 0) {
            return "NO PALINDROMIC PATH";
        }

        Queue<Integer> startQueue = new LinkedList<>();
        Queue<Integer> endQueue = new LinkedList<>();
        Queue<Integer> intermediateQueue1 = new LinkedList<>();
        Queue<Integer> intermediateQueue2 = new LinkedList<>();
        char[] longestPath = new char[adjacencyList.length];
        int longestPathIndex = 0;

        startQueue.offer(0);
        endQueue.offer(adjacencyList.length - 1);

        while (!startQueue.isEmpty()) {
            char roadValue = 127;

            while (!startQueue.isEmpty()) {
                int vertexId1 = startQueue.poll();
                int vertexId2 = endQueue.poll();
                intermediateQueue1.offer(vertexId1);
                intermediateQueue2.offer(vertexId2);

                for (int i = 0; i < adjacencyList[vertexId1][vertexId2].size(); i++) {
                    int adjustedVertexId1 = adjacencyList[vertexId1][vertexId2].get(i) >> 10;
                    if (vertexId1 == adjustedVertexId1) {
                        if (paths[vertexId1].charAt(vertexId2) < roadValue) {
                            roadValue = paths[vertexId1].charAt(vertexId2);
                        }
                    } else if (paths[vertexId1].charAt(adjustedVertexId1) < roadValue) {
                        roadValue = paths[vertexId1].charAt(adjustedVertexId1);
                    }
                }
            }

            longestPath[longestPathIndex] = roadValue;
            longestPathIndex++;

            while (!intermediateQueue1.isEmpty()) {
                int vertexId1 = intermediateQueue1.poll();
                int vertexId2 = intermediateQueue2.poll();

                for (int i = 0; i < adjacencyList[vertexId1][vertexId2].size(); i++) {
                    int adjustedVertexId1 = adjacencyList[vertexId1][vertexId2].get(i) >> 10;
                    int adjustedVertexId2 = adjacencyList[vertexId1][vertexId2].get(i) & 1023;
                    if (paths[vertexId1].charAt(adjustedVertexId1) == roadValue
                            && adjustedVertexId1 != adjustedVertexId2
                            && vertexId1 + 1 != vertexId2) {
                        startQueue.offer(adjustedVertexId1);
                        endQueue.offer(adjustedVertexId2);
                    }
                }
            }
        }

        StringBuilder longestPathResult = new StringBuilder();
        int backtrackIndex = dp[0][adjacencyList.length - 1] % 2 == 1 ? longestPathIndex - 2 : longestPathIndex - 1;
        for (int i = 0; i < longestPathIndex; i++) {
            longestPathResult.append(longestPath[i]);
        }
        for (int i = backtrackIndex; i >= 0; i--) {
            longestPathResult.append(longestPath[i]);
        }
        return longestPathResult.toString();
    }

    private static void computeDpAndPathLengths(List<Integer>[][] adjacencyList, String[] paths, int[][] dp) {
        for (int length = 2; length < adjacencyList.length; length++) {
            for (int startId = 0; startId + length < adjacencyList.length; startId++) {
                int endId = startId + length;
                for (int intermediateId1 = startId + 1; intermediateId1 < endId; intermediateId1++) {
                    for (int intermediateId2 = intermediateId1; intermediateId2 < endId; intermediateId2++) {
                        if (paths[startId].charAt(intermediateId1) == paths[intermediateId2].charAt(endId)
                                && paths[startId].charAt(intermediateId1) != '*'
                                && dp[intermediateId1][intermediateId2] + 2 >= dp[startId][endId]) {
                            if (dp[intermediateId1][intermediateId2] + 2 > dp[startId][endId]) {
                                adjacencyList[startId][endId] = new ArrayList<>();
                            }
                            dp[startId][endId] = dp[intermediateId1][intermediateId2] + 2;
                            adjacencyList[startId][endId].add(getAdjustedId(intermediateId1, intermediateId2));
                        }
                    }
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