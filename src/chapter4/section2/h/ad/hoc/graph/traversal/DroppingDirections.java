package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 19/05/23.
 */
// Based on https://github.com/BrandonTang89/Competitive_Programming_4_Solutions/blob/main/Chapter_4_Graph/Graph_Traversal/kattis_droppingdirections.cpp
public class DroppingDirections {

    private static class SignpostsUsed {
        int signsUsedHorizontally;
        int signsUsedVertically;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int intersections = FastReader.nextInt();
            int[][] adjacencyList = new int[intersections][4];
            int goal = FastReader.nextInt() - 1;

            for (int intersectionID = 0; intersectionID < intersections; intersectionID++) {
                for (int i = 0; i < adjacencyList[intersectionID].length; i++) {
                    adjacencyList[intersectionID][i] = FastReader.nextInt() - 1;
                }
            }
            int signpostsNeeded = computeSignpostsNeeded(adjacencyList, goal);
            outputWriter.printLine(signpostsNeeded);
        }
        outputWriter.flush();
    }

    private static int computeSignpostsNeeded(int[][] adjacencyList, int goal) {
        int signpostsNeeded = 0;
        SignpostsUsed signpostsUsed = new SignpostsUsed();
        // 0 to n - 1 indexes are the horizontal intersections, n to 2n - 1 are the vertical intersections
        boolean[] visited = new boolean[adjacencyList.length * 2];

        for (int intersectionID = 0; intersectionID < adjacencyList.length * 2; intersectionID++) {
            if (!visited[intersectionID]) {
                breadthFirstSearch(adjacencyList, goal, visited, signpostsUsed, signpostsNeeded, intersectionID);
                signpostsNeeded++;
            }
        }

        if (signpostsUsed.signsUsedHorizontally != signpostsUsed.signsUsedVertically) {
            signpostsNeeded--;
        }
        return signpostsNeeded - 1;
    }

    private static void breadthFirstSearch(int[][] adjacencyList, int goal, boolean[] visited,
                                           SignpostsUsed signpostsUsed, int signpostsCurrentlyUsed, int intersectionID) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(intersectionID);
        visited[intersectionID] = true;

        while (!queue.isEmpty()) {
            int currentIntersectionID = queue.poll();
            if (currentIntersectionID == goal) {
                signpostsUsed.signsUsedHorizontally = signpostsCurrentlyUsed;
            } else if (currentIntersectionID == goal + adjacencyList.length) {
                signpostsUsed.signsUsedVertically = signpostsCurrentlyUsed;
            }

            boolean isHorizontal = currentIntersectionID < adjacencyList.length;
            if (currentIntersectionID >= adjacencyList.length) {
                currentIntersectionID -= adjacencyList.length;
            }

            for (int i = 0; i < adjacencyList[currentIntersectionID].length; i++) {
                boolean isNeighborHorizontal = (i % 2) == 0;
                if (isHorizontal ^ isNeighborHorizontal) {
                    int nextIntersectionID = adjacencyList[currentIntersectionID][i];
                    boolean isHorizontalIntersection = isHorizontalIntersection(adjacencyList, currentIntersectionID,
                            adjacencyList[currentIntersectionID][i]);
                    if (isHorizontalIntersection) {
                        nextIntersectionID += adjacencyList.length;
                    }

                    if (!visited[nextIntersectionID]) {
                        visited[nextIntersectionID] = true;
                        queue.offer(nextIntersectionID);
                    }
                }
            }
        }
    }

    private static boolean isHorizontalIntersection(int[][] adjacencyList, int origin, int destination) {
        for (int i = 0; i < adjacencyList[destination].length; i++) {
            if (adjacencyList[destination][i] == origin) {
                return i % 2 == 0;
            }
        }
        return true;
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
