package chapter3.section5.e.traveling.salesman.problem;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/12/22.
 */
public class InPuzzlelandIII {

    private static final int IMPOSSIBLE = -100;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int nodesNumber = FastReader.nextInt();
            int edgesNumber = FastReader.nextInt();
            char[] nodes = new char[nodesNumber];
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = FastReader.next().charAt(0);
            }
            char source = nodes[0];
            char target = nodes[nodes.length - 1];
            Arrays.sort(nodes);
            Map<Character, Integer> nodeMap = buildNodeMap(nodes);

            boolean[][] edges = new boolean[nodesNumber][nodesNumber];
            for (int i = 0; i < edgesNumber; i++) {
                char node1 = FastReader.next().charAt(0);
                char node2 = FastReader.next().charAt(0);
                int node1Id = nodeMap.get(node1);
                int node2Id = nodeMap.get(node2);
                edges[node1Id][node2Id] = true;
                edges[node2Id][node1Id] = true;
            }

            String path = computePath(nodes, nodeMap, edges, source, target);
            if (path == null) {
                path = "impossible";
            }
            outputWriter.printLine(String.format("Case %d: %s", t, path));
        }
        outputWriter.flush();
    }

    private static Map<Character, Integer> buildNodeMap(char[] nodes) {
        Map<Character, Integer> nodeMap = new HashMap<>();
        for (int nodeId = 0; nodeId < nodes.length; nodeId++) {
            nodeMap.put(nodes[nodeId], nodeId);
        }
        return nodeMap;
    }

    private static String computePath(char[] nodes, Map<Character, Integer> nodeMap, boolean[][] edges, char source,
                                      char target) {
        int bitmaskSize = (int) Math.pow(2, nodes.length);
        int[][] dp = new int[nodes.length][bitmaskSize];
        for (int [] values : dp) {
            Arrays.fill(values, -1);
        }

        int targetId = nodeMap.get(target);
        int sourceId = nodeMap.get(source);
        int bitmask = (1 << sourceId);
        int pathLength = computePathLength(edges, dp, targetId, sourceId, bitmask);
        if (pathLength != nodes.length) {
            return null;
        }
        return buildPath(dp, nodes, edges, sourceId);
    }

    private static int computePathLength(boolean[][] edges, int[][] dp, int targetId, int currentNodeId, int bitmask) {
        if (currentNodeId == targetId) {
            if (bitmask == dp[0].length - 1) {
                return 1;
            } else {
                return -1;
            }
        }
        if (dp[currentNodeId][bitmask] != -1) {
            return dp[currentNodeId][bitmask];
        }

        int bestLength = 0;
        for (int nodeId = 0; nodeId < dp.length; nodeId++) {
            if (edges[currentNodeId][nodeId] && (bitmask & (1 << nodeId)) == 0) {
                int nextBitmask = bitmask | (1 << nodeId);
                int length = computePathLength(edges, dp, targetId, nodeId, nextBitmask);
                if (length == IMPOSSIBLE) {
                    continue;
                }
                length++;
                if (length > bestLength) {
                    bestLength = length;
                }
            }
        }
        dp[currentNodeId][bitmask] = bestLength;
        return dp[currentNodeId][bitmask];
    }

    private static String buildPath(int[][] dp, char[] nodes, boolean[][] edges, int sourceId) {
        StringBuilder path = new StringBuilder();
        int currentNode = sourceId;
        int bitmask = (1 << sourceId);
        path.append(nodes[sourceId]);

        for (int size = 0; size < nodes.length - 1; size++) {
            int nextNode = -1;
            int nextBitmask = -1;
            for (int nodeId = 0; nodeId < nodes.length; nodeId++) {
                if (nodeId == currentNode || !edges[currentNode][nodeId] || (bitmask & (1 << nodeId)) != 0) {
                    continue;
                }
                if (nextNode == -1) {
                    nextNode = nodeId;
                    nextBitmask = bitmask | (1 << nodeId);
                }

                int length1;
                if (dp[nextNode][nextBitmask] != -1) {
                    length1 = dp[nextNode][nextBitmask];
                } else {
                    length1 = -1;
                }

                int length2;
                int alternativeBitmask = bitmask | (1 << nodeId);
                if (dp[nodeId][alternativeBitmask] != -1) {
                    length2 = dp[nodeId][alternativeBitmask];
                } else {
                    length2 = -1;
                }

                if (length2 > length1) {
                    nextNode = nodeId;
                    nextBitmask = alternativeBitmask;
                }
            }
            path.append(nodes[nextNode]);
            currentNode = nextNode;
            bitmask = nextBitmask;
        }
        return path.toString();
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
