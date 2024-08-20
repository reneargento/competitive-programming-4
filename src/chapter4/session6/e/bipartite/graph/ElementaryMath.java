package chapter4.session6.e.bipartite.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/08/24.
 */
public class ElementaryMath {

    private static class Equation {
        long number1;
        long number2;

        public Equation(long number1, long number2) {
            this.number1 = number1;
            this.number2 = number2;
        }
    }

    private static class Result {
        int maximumCardinality;
        Map<Long, Long> match;

        public Result(int maximumCardinality, Map<Long, Long> match) {
            this.maximumCardinality = maximumCardinality;
            this.match = match;
        }
    }

    private static final int PARTITION_DIVIDER = 2500;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int pairs = FastReader.nextInt();
        Equation[] equations = new Equation[pairs];
        Map<Long, List<Long>> adjacencyMap = new HashMap<>();

        for (int equationId = 0; equationId < equations.length; equationId++) {
            long number1 = FastReader.nextInt();
            long number2 = FastReader.nextInt();
            equations[equationId] = new Equation(number1, number2);

            long sum = number1 + number2 + PARTITION_DIVIDER;
            long subtraction = number1 - number2 + PARTITION_DIVIDER;
            long multiplication = number1 * number2 + PARTITION_DIVIDER;

            adjacencyMap.put((long) equationId, new ArrayList<>());
            adjacencyMap.get((long) equationId).add(sum);
            adjacencyMap.get((long) equationId).add(subtraction);
            adjacencyMap.get((long) equationId).add(multiplication);
        }

        Result result = computeMaximumCardinality(adjacencyMap, equations.length);
        if (result.maximumCardinality == equations.length) {
            for (int equationId = 0; equationId < equations.length; equationId++) {
                Equation equation = equations[equationId];
                long resultValue = result.match.get((long) equationId) - PARTITION_DIVIDER;
                char operator = getOperator(equation.number1, equation.number2, resultValue);
                outputWriter.printLine(String.format("%d %c %d = %d", equation.number1, operator, equation.number2,
                        resultValue));
            }
        } else {
            outputWriter.print("impossible");
        }
        outputWriter.flush();
    }

    private static char getOperator(long number1, long number2, long result) {
        if (number1 + number2 == result) {
            return '+';
        } else if (number1 - number2 == result) {
            return '-';
        } else {
            return '*';
        }
    }

    private static Result computeMaximumCardinality(Map<Long, List<Long>> adjacencyMap, int leftPartitionMaxVertexID) {
        int maximumMatches = 0;
        Map<Long, Long> match = new HashMap<>();
        Map<Long, Long> reverseMatch = new HashMap<>();

        for (long vertexID = 0; vertexID < leftPartitionMaxVertexID; vertexID++) {
            Set<Long> visited = new HashSet<>();
            maximumMatches += tryToMatch(adjacencyMap, match, reverseMatch, visited, vertexID);
        }
        return new Result(maximumMatches, reverseMatch);
    }

    private static int tryToMatch(Map<Long, List<Long>> adjacencyMap, Map<Long, Long> match,
                                  Map<Long, Long> reverseMatch, Set<Long> visited, long vertexID) {
        if (visited.contains(vertexID) || !adjacencyMap.containsKey(vertexID)) {
            return 0;
        }
        visited.add(vertexID);

        for (long neighbor : adjacencyMap.get(vertexID)) {
            if (!match.containsKey(neighbor)
                    || tryToMatch(adjacencyMap, match, reverseMatch, visited, match.get(neighbor)) == 1) {
                match.put(neighbor, vertexID); // flip status
                reverseMatch.put(vertexID, neighbor);
                return 1;
            }
        }
        return 0;
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
