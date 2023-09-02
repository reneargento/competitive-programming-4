package chapter4.section3.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/08/23.
 */
@SuppressWarnings("unchecked")
public class Audiophobia {

    private static class Edge implements Comparable<Edge> {
        int vertex1;
        int vertex2;
        int soundLevel;

        Edge(int vertex1, int vertex2, int soundLevel) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.soundLevel = soundLevel;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(soundLevel, other.soundLevel);
        }

        private int otherVertex(int vertex) {
            if (vertex == vertex1) {
                return vertex2;
            }
            return vertex1;
        }
    }

    private static final int NO_PATH = -1;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int crossings = FastReader.nextInt();
        int streets = FastReader.nextInt();
        int queries = FastReader.nextInt();
        int caseID = 1;

        while (crossings != 0 || streets != 0 || queries != 0) {
            List<Edge> edges = new ArrayList<>();

            for (int i = 0; i < streets; i++) {
                int crossingID1 = FastReader.nextInt() - 1;
                int crossingID2 = FastReader.nextInt() - 1;
                int soundLevel = FastReader.nextInt();

                Edge edge = new Edge(crossingID1, crossingID2, soundLevel);
                edges.add(edge);
            }

            if (caseID > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Case #%d", caseID));
            answerQueries(queries, crossings, edges, outputWriter);

            caseID++;
            crossings = FastReader.nextInt();
            streets = FastReader.nextInt();
            queries = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void answerQueries(int queries, int crossings, List<Edge> edges, OutputWriter outputWriter)
            throws IOException {
        List<Edge>[] minimumSpanningTree = KruskalMinimumSpanningTree.getMinimumSpanningTree(crossings, edges,
                crossings);
        int[][] minimumSoundMap = new int[crossings][crossings];
        for (int[] values : minimumSoundMap) {
            Arrays.fill(values, NO_PATH);
        }

        for (int q = 0; q < queries; q++) {
            int crossingID1 = FastReader.nextInt() - 1;
            int crossingID2 = FastReader.nextInt() - 1;

            if (minimumSoundMap[crossingID1][crossingID2] != NO_PATH) {
                int minimumSound = minimumSoundMap[crossingID1][crossingID2];
                outputWriter.printLine(minimumSound);
            } else {
                boolean[] visited = new boolean[crossings];
                computeMinimumSound(minimumSpanningTree, visited, crossingID1, crossingID2, minimumSoundMap,
                        crossingID1, NO_PATH);
                if (minimumSoundMap[crossingID1][crossingID2] == NO_PATH) {
                    outputWriter.printLine("no path");
                } else {
                    int minimumSound = minimumSoundMap[crossingID1][crossingID2];
                    outputWriter.printLine(minimumSound);
                }
            }
        }
    }

    private static void computeMinimumSound(List<Edge>[] minimumSpanningTree, boolean[] visited, int originCrossingID,
                                            int targetCrossingID, int[][] minimumSoundMap, int currentCrossingID,
                                            int currentMinimumSound) {
        visited[currentCrossingID] = true;

        for (Edge edge : minimumSpanningTree[currentCrossingID]) {
            int nextCrossingID = edge.otherVertex(currentCrossingID);
            if (visited[nextCrossingID]) {
                continue;
            }

            int newMinimumSound = Math.max(edge.soundLevel, currentMinimumSound);
            if (currentMinimumSound == NO_PATH ||
                    newMinimumSound > minimumSoundMap[originCrossingID][nextCrossingID]) {
                minimumSoundMap[originCrossingID][nextCrossingID] = newMinimumSound;
                minimumSoundMap[nextCrossingID][originCrossingID] = newMinimumSound;
            }

            if (nextCrossingID == targetCrossingID) {
                return;
            }
            computeMinimumSound(minimumSpanningTree, visited, originCrossingID, targetCrossingID, minimumSoundMap,
                    nextCrossingID, newMinimumSound);
        }
    }

    private static class KruskalMinimumSpanningTree {

        private static class UnionFind {
            private final int[] leaders;
            private final int[] ranks;
            private int components;

            public UnionFind(int size) {
                leaders = new int[size];
                ranks = new int[size];
                components = size;

                for (int i = 0; i < size; i++) {
                    leaders[i] = i;
                    ranks[i] = 0;
                }
            }

            public boolean connected(int site1, int site2) {
                return find(site1) == find(site2);
            }

            public int find(int site) {
                if (site == leaders[site]) {
                    return site;
                }
                return leaders[site] = find(leaders[site]);
            }

            public void union(int site1, int site2) {
                int leader1 = find(site1);
                int leader2 = find(site2);
                if (leader1 == leader2) {
                    return;
                }

                if (ranks[leader1] < ranks[leader2]) {
                    leaders[leader1] = leader2;
                } else if (ranks[leader2] < ranks[leader1]) {
                    leaders[leader2] = leader1;
                } else {
                    leaders[leader1] = leader2;
                    ranks[leader2]++;
                }
                components--;
            }
        }

        private static List<Edge>[] getMinimumSpanningTree(int coordinates, List<Edge> edges, int crossings) {
            List<Edge>[] minimumSpanningTree = new ArrayList[crossings];
            for (int i = 0; i < minimumSpanningTree.length; i++) {
                minimumSpanningTree[i] = new ArrayList<>();
            }

            Collections.sort(edges);
            KruskalMinimumSpanningTree.UnionFind unionFind = new KruskalMinimumSpanningTree.UnionFind(coordinates);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    unionFind.union(edge.vertex1, edge.vertex2);
                    minimumSpanningTree[edge.vertex1].add(edge);
                    minimumSpanningTree[edge.vertex2].add(edge);
                }

                if (unionFind.components == 1) {
                    break;
                }
            }
            return minimumSpanningTree;
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
