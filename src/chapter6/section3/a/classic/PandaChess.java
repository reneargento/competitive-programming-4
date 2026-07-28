package chapter6.section3.a.classic;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/07/2026.
 */
public class PandaChess {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int players = FastReader.nextInt();
        int matches = FastReader.nextInt();
        FastReader.nextInt();
        Map<Long, List<Long>> adjacencyList = new HashMap<>();

        for (int m = 0; m < matches; m++) {
            long icNumber1 = FastReader.nextLong();
            long icNumber2 = FastReader.nextLong();
            addEdge(adjacencyList, icNumber1, icNumber2);
        }
        Long[] rankingTyped = new Long[players];
        for (int p = 0; p < players; p++) {
            rankingTyped[p] = FastReader.nextLong();
        }

        int minimumEdits = computeMinimumEdits(adjacencyList, rankingTyped);
        outputWriter.printLine(minimumEdits);
        outputWriter.flush();
    }

    private static int computeMinimumEdits(Map<Long, List<Long>> adjacencyList, Long[] rankingTyped) {
        Set<Long> visited = new HashSet<>();
        List<Long> rankingReversed = new ArrayList<>();

        for (long vertexId : adjacencyList.keySet()) {
            if (!visited.contains(vertexId)) {
                topologicalSort(adjacencyList, visited, rankingReversed, vertexId);
            }
        }

        Collections.reverse(rankingReversed);
        Long[] ranking = rankingReversed.toArray(new Long[0]);
        int lcs = computeLCSDistinctValues(rankingTyped, ranking);
        return 2 * (ranking.length - lcs);
    }

    private static void topologicalSort(Map<Long, List<Long>> adjacencyList, Set<Long> visited,
                                        List<Long> rankingReversed, long vertexId) {
        visited.add(vertexId);

        if (adjacencyList.containsKey(vertexId)) {
            for (long neighbor : adjacencyList.get(vertexId)) {
                if (!visited.contains(neighbor)) {
                    topologicalSort(adjacencyList, visited, rankingReversed, neighbor);
                }
            }
        }
        rankingReversed.add(vertexId);
    }

    private static void addEdge(Map<Long, List<Long>> adjacencyList, long icNumber1, long icNumber2) {
        if (!adjacencyList.containsKey(icNumber1)) {
            adjacencyList.put(icNumber1, new ArrayList<>());
        }
        adjacencyList.get(icNumber1).add(icNumber2);
    }

    private static int computeLCSDistinctValues(Long[] sequence1, Long[] sequence2) {
        Map<Long, Integer> valueToIndexMap = computeValueToIndexMap(sequence2);
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < sequence1.length; i++) {
            long value = sequence1[i];
            Integer string2Index = valueToIndexMap.get(value);
            if (string2Index != null) {
                indexes.add(string2Index);
            }
        }

        Integer[] indexesArray = indexes.toArray(new Integer[0]);
        return longestIncreasingSubsequence(indexesArray);
    }

    private static Map<Long, Integer> computeValueToIndexMap(Long[] sequence) {
        Map<Long, Integer> valueToIndexMap = new HashMap<>();
        for (int i = 0; i < sequence.length; i++) {
            long value = sequence[i];
            valueToIndexMap.put(value, i);
        }
        return valueToIndexMap;
    }

    private static int longestIncreasingSubsequence(Integer[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int[] endIndexes = new int[array.length];
        int length = 1;

        for (int i = 1; i < array.length; i++) {
            // Case 1 - smallest end element
            if (array[i] <= array[endIndexes[0]]) {
                endIndexes[0] = i;
            } else if (array[i] > array[endIndexes[length - 1]]) {
                // Case 2 - highest end element - extends longest increasing subsequence
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(array, endIndexes, 0, length - 1, array[i]);
                endIndexes[indexToReplace] = i;
            }
        }
        return length;
    }

    private static int ceilIndex(Integer[] array, int[] endIndexes, int low, int high, int key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (array[endIndexes[middle]] >= key) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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