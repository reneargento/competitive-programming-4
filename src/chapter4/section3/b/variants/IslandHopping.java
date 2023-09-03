package chapter4.section3.b.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/08/23.
 */
public class IslandHopping {

    private static class Island {
        int xPosition;
        int yPosition;
        int inhabitants;


        public Island(int xPosition, int yPosition, int inhabitants) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            this.inhabitants = inhabitants;
        }
    }

    private static class Edge implements Comparable<Edge> {
        int vertex1;
        int vertex2;
        double distance;

        Edge(int vertex1, int vertex2, double distance) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.distance = distance;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(distance, other.distance);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int islandsNumber = FastReader.nextInt();
        int islandsGroup = 1;

        while (islandsNumber != 0) {
            Island[] islands = new Island[islandsNumber];
            for (int i = 0; i < islands.length; i++) {
                islands[i] = new Island(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            }

            double averageDays = computeAverageDaysToConnect(islands);
            outputWriter.printLine(String.format("Island Group: %d Average %.2f\n", islandsGroup, averageDays));

            islandsGroup++;
            islandsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeAverageDaysToConnect(Island[] islands) {
        List<Edge> edges = computeEdges(islands);
        int totalInhabitants = 0;
        for (Island island : islands) {
            totalInhabitants += island.inhabitants;
        }
        return KruskalMinimumSpanningTree.computeAverageDays(islands, edges, totalInhabitants);
    }

    private static List<Edge> computeEdges(Island[] islands) {
        List<Edge> edges = new ArrayList<>();

        for (int islandID1 = 0; islandID1 < islands.length; islandID1++) {
            for (int islandID2 = islandID1 + 1; islandID2 < islands.length; islandID2++) {
                double distance = distanceBetweenPoints(islands[islandID1].xPosition, islands[islandID1].yPosition,
                        islands[islandID2].xPosition, islands[islandID2].yPosition);
                edges.add(new Edge(islandID1, islandID2, distance));
            }
        }
        return edges;
    }

    public static double distanceBetweenPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private static class KruskalMinimumSpanningTree {

        private static class UnionFind {
            private final int[] leaders;
            private final int[] ranks;
            private final int[] inhabitants;
            private int components;

            public UnionFind(int size, Island[] islands) {
                leaders = new int[size];
                ranks = new int[size];
                inhabitants = new int[size];

                components = size;

                for (int i = 0; i < size; i++) {
                    leaders[i] = i;
                    inhabitants[i] = islands[i].inhabitants;
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
                    inhabitants[leader2] += inhabitants[leader1];
                } else if (ranks[leader2] < ranks[leader1]) {
                    leaders[leader2] = leader1;
                    inhabitants[leader1] += inhabitants[leader2];
                } else {
                    leaders[leader1] = leader2;
                    ranks[leader2]++;
                    inhabitants[leader2] += inhabitants[leader1];
                }
                components--;
            }
        }

        private static double computeAverageDays(Island[] islands, List<Edge> edges, int totalInhabitants) {
            double averageDays = 0;
            Collections.sort(edges);
            KruskalMinimumSpanningTree.UnionFind unionFind = new KruskalMinimumSpanningTree.UnionFind(islands.length,
                    islands);

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                    int mainIslandLeader = unionFind.find(0);

                    if (unionFind.find(edge.vertex1) == mainIslandLeader
                            || unionFind.find(edge.vertex2) == mainIslandLeader) {
                        double daysNeeded = edge.distance;
                        int islandIDToUpdate = (unionFind.find(edge.vertex1) == mainIslandLeader) ?
                                edge.vertex2 :
                                edge.vertex1;
                        int leaderID = unionFind.find(islandIDToUpdate);
                        averageDays += (daysNeeded * unionFind.inhabitants[leaderID]);;
                    }
                    unionFind.union(edge.vertex1, edge.vertex2);
                }

                if (unionFind.components == 1) {
                    break;
                }
            }
            return averageDays / totalInhabitants;
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
