package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/03/23.
 */
@SuppressWarnings("unchecked")
public class Ordering {

    private static class OrderingValue implements Comparable<OrderingValue> {
        List<Character> ordering;

        public OrderingValue(List<Character> ordering) {
            this.ordering = ordering;
        }

        @Override
        public int compareTo(OrderingValue other) {
            for (int i = 0; i < ordering.size(); i++) {
                if (ordering.get(i) != other.ordering.get(i)) {
                    return Character.compare(ordering.get(i), other.ordering.get(i));
                }
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            String[] variables = FastReader.getLine().split(" ");
            List<Integer>[] adjacencyList = new ArrayList[variables.length];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            Map<Character, Integer> variableToIDMap = new HashMap<>();
            char[] idToVariable = new char[variables.length];
            int[] inDegrees = new int[variables.length];

            String[] constraints = FastReader.getLine().split(" ");
            for (String constraint : constraints) {
                char variable1 = constraint.charAt(0);
                char variable2 = constraint.charAt(2);
                int variableID1 = getVariableID(variableToIDMap, idToVariable, variable1);
                int variableID2 = getVariableID(variableToIDMap, idToVariable, variable2);
                adjacencyList[variableID1].add(variableID2);
                inDegrees[variableID2]++;
            }

            for (String variable : variables) {
                getVariableID(variableToIDMap, idToVariable, variable.charAt(0));
            }

            List<OrderingValue> allOrderings = computeAllOrderings(adjacencyList, inDegrees, idToVariable);
            if (t > 0) {
                outputWriter.printLine();
            }
            if (allOrderings.isEmpty()) {
                outputWriter.printLine("NO");
            } else {
                for (OrderingValue orderingValue : allOrderings) {
                    List<Character> ordering = orderingValue.ordering;
                    outputWriter.print(ordering.get(0));
                    for (int i = 1; i < ordering.size(); i++) {
                        outputWriter.print(" " + ordering.get(i));
                    }
                    outputWriter.printLine();
                }
            }
        }
        outputWriter.flush();
    }

    private static List<OrderingValue> computeAllOrderings(List<Integer>[] adjacencyList, int[] inDegrees,
                                                           char[] idToVariable) {
        List<OrderingValue> allOrderings = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        boolean[] visited = new boolean[inDegrees.length];
        computeAllOrderings(adjacencyList, inDegrees, visited, stack, idToVariable, allOrderings);
        Collections.sort(allOrderings);
        return allOrderings;
    }

    private static void computeAllOrderings(List<Integer>[] adjacencyList, int[] inDegrees, boolean[] visited,
                                            Deque<Integer> stack, char[] idToVariable,
                                            List<OrderingValue> allOrderings) {
        for (int variableID = 0; variableID < adjacencyList.length; variableID++) {
            if (inDegrees[variableID] == 0 && !visited[variableID]) {
                visited[variableID] = true;
                stack.push(variableID);
                for (int neighborID : adjacencyList[variableID]) {
                    inDegrees[neighborID]--;
                }

                computeAllOrderings(adjacencyList, inDegrees, visited, stack, idToVariable, allOrderings);

                visited[variableID] = false;
                stack.pop();
                for (int neighborID : adjacencyList[variableID]) {
                    inDegrees[neighborID]++;
                }
            }
        }

        if (stack.size() == adjacencyList.length) {
            List<Character> ordering = new ArrayList<>();
            for (Integer variableID : stack) {
                ordering.add(idToVariable[variableID]);
            }
            Collections.reverse(ordering);
            allOrderings.add(new OrderingValue(ordering));
        }
    }

    private static int getVariableID(Map<Character, Integer> variableToIDMap, char[] idToVariable, char variable) {
        if (!variableToIDMap.containsKey(variable)) {
            int ID = variableToIDMap.size();
            variableToIDMap.put(variable, ID);
            idToVariable[ID] = variable;
        }
        return variableToIDMap.get(variable);
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
