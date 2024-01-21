package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 19/01/24.
 */
public class Babel {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int words = FastReader.nextInt();

        while (words != 0) {
            Map<String, Integer> languageNameToIDMap = new HashMap<>();
            List<List<Edge>> adjacencyList = new ArrayList<>();
            adjacencyList.add(new ArrayList<>());
            adjacencyList.add(new ArrayList<>());

            int startLanguageID = getLanguageID(languageNameToIDMap, FastReader.next());
            int endLanguageID = getLanguageID(languageNameToIDMap, FastReader.next());

            for (int w = 0; w < words; w++) {
                int languageID1 = getLanguageID(languageNameToIDMap, FastReader.next());
                int languageID2 = getLanguageID(languageNameToIDMap, FastReader.next());
                String word = FastReader.next();
                int weight = word.length();
                char firstCharacter = word.charAt(0);

                if (languageID1 == adjacencyList.size()) {
                    adjacencyList.add(new ArrayList<>());
                }
                if (languageID2 == adjacencyList.size()) {
                    adjacencyList.add(new ArrayList<>());
                }

                adjacencyList.get(languageID1).add(new Edge(languageID2, weight, firstCharacter));
                adjacencyList.get(languageID2).add(new Edge(languageID1, weight, firstCharacter));
            }

            Dijkstra dijkstra = new Dijkstra(adjacencyList, startLanguageID, endLanguageID);
            if (dijkstra.hasPathTo(endLanguageID)) {
                int minLength = Integer.MAX_VALUE;
                for (int charID = 0; charID < dijkstra.distTo[endLanguageID].length; charID++) {
                    if (dijkstra.distTo[endLanguageID][charID] != Integer.MAX_VALUE) {
                        minLength = Math.min(minLength, dijkstra.distTo[endLanguageID][charID]);
                    }
                }
                outputWriter.printLine(minLength);
            } else {
                outputWriter.printLine("impossivel");
            }
            words = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int getLanguageID(Map<String, Integer> languageNameToIDMap, String languageName) {
        if (!languageNameToIDMap.containsKey(languageName)) {
            languageNameToIDMap.put(languageName, languageNameToIDMap.size());
        }
        return languageNameToIDMap.get(languageName);
    }

    private static class Edge {
        private final int vertex2;
        private final int distance;
        private final char startCharacter;

        public Edge(int vertex2, int distance, char startCharacter) {
            this.vertex2 = vertex2;
            this.distance = distance;
            this.startCharacter = startCharacter;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            int distance;
            char lastStartCharacter;

            public Vertex(int id, int distance, char lastStartCharacter) {
                this.id = id;
                this.distance = distance;
                this.lastStartCharacter = lastStartCharacter;
            }

            @Override
            public int compareTo(Vertex other) {
                return Integer.compare(distance, other.distance);
            }
        }

        private final int[][] distTo;
        private final PriorityQueue<Vertex> priorityQueue;

        public Dijkstra(List<List<Edge>> adjacencyList, int source, int target) {
            distTo = new int[adjacencyList.size()][26];
            priorityQueue = new PriorityQueue<>(adjacencyList.size());

            for (int[] values : distTo) {
                Arrays.fill(values, Integer.MAX_VALUE);
            }
            distTo[source][0] = 0;
            priorityQueue.offer(new Vertex(source, 0, '*'));

            while (!priorityQueue.isEmpty()) {
                Vertex vertex = priorityQueue.poll();
                if (vertex.id == target) {
                    return;
                }
                relax(adjacencyList, vertex);
            }
        }

        private void relax(List<List<Edge>> adjacencyList, Vertex vertex) {
            int charID = vertex.lastStartCharacter - 'a';
            if (charID >= 0 && distTo[vertex.id][charID] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList.get(vertex.id)) {
                int neighbor = edge.vertex2;
                if (vertex.lastStartCharacter == edge.startCharacter) {
                    continue;
                }
                int nextCharID = edge.startCharacter - 'a';
                int currentDistance;
                if (charID >= 0) {
                    currentDistance = distTo[vertex.id][charID];
                } else {
                    currentDistance = 0;
                }

                if (distTo[neighbor][nextCharID] > currentDistance + edge.distance) {
                    distTo[neighbor][nextCharID] = currentDistance + edge.distance;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor][nextCharID], edge.startCharacter));
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            for (int charID = 0; charID < distTo[vertex].length; charID++) {
                if (distTo[vertex][charID] != Integer.MAX_VALUE) {
                    return true;
                }
            }
            return false;
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
