package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/05/23.
 */
@SuppressWarnings("unchecked")
public class Optimisation {

    private static class Descendant implements Comparable<Descendant> {
        int stepID1;
        int stepID2;

        public Descendant(int stepID1, int stepID2) {
            this.stepID1 = stepID1;
            this.stepID2 = stepID2;
        }

        @Override
        public int compareTo(Descendant other) {
            if (stepID1 != other.stepID1) {
                return Integer.compare(stepID1, other.stepID1);
            }
            return Integer.compare(stepID2, other.stepID2);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int steps = Integer.parseInt(line);
            List<Integer>[] adjacencyList = new List[steps];
            List<Integer>[] adjacencyListReverse = new List[steps];

            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
                adjacencyListReverse[i] = new ArrayList<>();
            }

            for (int stepID1 = 0; stepID1 < adjacencyList.length; stepID1++) {
                for (int stepID2 = 0; stepID2 < adjacencyList.length; stepID2++) {
                    int connection = FastReader.nextInt();
                    if (connection == 1) {
                        adjacencyList[stepID1].add(stepID2);
                        adjacencyListReverse[stepID2].add(stepID1);
                    }
                }
            }

            List<Descendant> descendants = buildDependencyTree(adjacencyList, adjacencyListReverse);
            for (Descendant descendant : descendants) {
                outputWriter.printLine(String.format("%d %d", descendant.stepID1 + 1, descendant.stepID2 + 1));
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Descendant> buildDependencyTree(List<Integer>[] adjacencyList,
                                                        List<Integer>[] adjacencyListReverse) {
        List<Descendant> descendants = new ArrayList<>();

        for (int stepID = 1; stepID < adjacencyList.length; stepID++) {
            int parentID = getParentID(adjacencyListReverse, stepID);
            descendants.add(new Descendant(parentID, stepID));
        }
        Collections.sort(descendants);
        return descendants;
    }

    private static int getParentID(List<Integer>[] adjacencyList, int sourceStepID) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        queue.offer(sourceStepID);
        visited[sourceStepID] = true;

        while (!queue.isEmpty()) {
            int stepID = queue.poll();

            if (stepID != sourceStepID) {
                boolean[] newVisited = new boolean[adjacencyList.length];
                boolean canReachSource = canReachSource(adjacencyList, newVisited, stepID, sourceStepID);
                if (!canReachSource) {
                    return stepID;
                }
            }

            for (int neighborID : adjacencyList[stepID]) {
                if (!visited[neighborID]) {
                    visited[neighborID] = true;
                    queue.offer(neighborID);
                }
            }
        }
        return 0;
    }

    private static boolean canReachSource(List<Integer>[] adjacencyList, boolean[] visited, int candidateParentID,
                                          int stepID) {
        visited[stepID] = true;

        for (int neighborID : adjacencyList[stepID]) {
            if (neighborID == candidateParentID) {
                continue;
            }

            if (neighborID == 0) {
                return true;
            } else if (!visited[neighborID]) {
                boolean isParentID = canReachSource(adjacencyList, visited, candidateParentID, neighborID);
                if (isParentID) {
                    return true;
                }
            }
        }
        return false;
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
