package chapter4.section6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 17/05/24.
 */
@SuppressWarnings("unchecked")
public class LovelyHint {

    private static class Result {
        int longestLovelyString;
        long numberOfWays;

        public Result(int longestLovelyString, long numberOfWays) {
            this.longestLovelyString = longestLovelyString;
            this.numberOfWays = numberOfWays;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.getLine();
            List<Integer>[] adjacencyList = buildGraph(string);
            int validCharsLength = computeValidCharsLength(string);

            Result result = NumberOfPaths.countNumberOfPathsAndLength(adjacencyList, validCharsLength);
            outputWriter.printLine(String.format("%d %d", result.longestLovelyString, result.numberOfWays));
        }
        outputWriter.flush();
    }

    private static List<Integer>[] buildGraph(String string) {
        List<Integer>[] adjacencyList = new List[26];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < string.length(); i++) {
            for (int j = 0; j < string.length(); j++) {
                int vertexID1 = string.charAt(i) - 'A';
                int vertexID2 = string.charAt(j) - 'A';
                if (vertexID1 == vertexID2
                        || !Character.isLetter(string.charAt(i))
                        || !Character.isLetter(string.charAt(j))) {
                    continue;
                }
                if (5 * (vertexID1 + 1) <= 4 * (vertexID2 + 1)
                        && !adjacencyList[vertexID1].contains(vertexID2)) {
                    adjacencyList[vertexID1].add(vertexID2);
                }
            }
        }
        return adjacencyList;
    }

    private static int computeValidCharsLength(String string) {
        int validCharsLength = 0;
        for (char charValue : string.toCharArray()) {
            if (Character.isLetter(charValue)) {
                validCharsLength++;
            }
        }
        return validCharsLength;
    }

    private static class NumberOfPaths {

        public static Result countNumberOfPathsAndLength(List<Integer>[] adjacencyList, int validCharsLength) {
            int maxLength = 1;
            long numberOfPathsToMaxLength = validCharsLength;

            long[] numberOfPaths = new long[adjacencyList.length];
            int[] maxLengths = new int[adjacencyList.length];
            Arrays.fill(numberOfPaths, 1);
            Arrays.fill(maxLengths, 1);

            int[] inDegrees = computeInDegrees(adjacencyList);
            int[] topologicalOrder = khanTopologicalSort(adjacencyList, inDegrees);

            for (int vertexID : topologicalOrder) {
                for (int neighbor : adjacencyList[vertexID]) {
                    int candidateMaxLength = maxLengths[vertexID] + 1;
                    long currentPaths = numberOfPaths[vertexID];

                    if (candidateMaxLength > maxLengths[neighbor]) {
                        numberOfPaths[neighbor] = numberOfPaths[vertexID];
                    } else if (candidateMaxLength == maxLengths[neighbor]) {
                        numberOfPaths[neighbor] += numberOfPaths[vertexID];
                    }
                    maxLengths[neighbor] = Math.max(maxLengths[neighbor], candidateMaxLength);

                    if (candidateMaxLength == maxLength) {
                        numberOfPathsToMaxLength += currentPaths;
                    } else if (candidateMaxLength > maxLength) {
                        maxLength = candidateMaxLength;
                        numberOfPathsToMaxLength = currentPaths;
                    }
                }
            }
            return new Result(maxLength, numberOfPathsToMaxLength);
        }

        private static int[] computeInDegrees(List<Integer>[] adjacencyList) {
            int[] inDegrees = new int[adjacencyList.length];

            for (int i = 0; i < adjacencyList.length; i++) {
                for (int neighbor : adjacencyList[i]) {
                    inDegrees[neighbor]++;
                }
            }
            return inDegrees;
        }

        private static int[] khanTopologicalSort(List<Integer>[] adjacencyList, int[] inDegrees) {
            int[] inDegreesCopy = new int[inDegrees.length];
            System.arraycopy(inDegrees, 0, inDegreesCopy, 0, inDegrees.length);

            Queue<Integer> queue = new LinkedList<>();
            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                if (inDegreesCopy[vertexID] == 0) {
                    queue.add(vertexID);
                }
            }

            int[] topologicalOrder = new int[adjacencyList.length];
            int topologicalOrderIndex = 0;

            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();
                topologicalOrder[topologicalOrderIndex++] = currentVertex;

                for (int neighbor : adjacencyList[currentVertex]) {
                    inDegreesCopy[neighbor]--;

                    if (inDegreesCopy[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
            return topologicalOrder;
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
