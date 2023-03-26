package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/03/23.
 */
@SuppressWarnings("unchecked")
public class FollowingOrders {

    private static class Order implements Comparable<Order> {
        List<Character> variables;

        public Order() {
            variables = new ArrayList<>();
        }

        @Override
        public int compareTo(Order other) {
            for (int i = 0; i < variables.size(); i++) {
                if (variables.get(i) != other.variables.get(i)) {
                    return Integer.compare(variables.get(i), other.variables.get(i));
                }
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int specificationID = 1;

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            char[] idToVariable = new char[data.length];
            Map<Character, Integer> variableToIDMap = new HashMap<>();
            int[] inDegrees = new int[data.length];

            List<Integer>[] adjacencyList = new List[data.length];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int variableID = 0; variableID < data.length; variableID++) {
                char variable = data[variableID].charAt(0);
                idToVariable[variableID] = variable;
                variableToIDMap.put(variable, variableID);
            }

            String[] constraints = FastReader.getLine().split(" ");
            for (int i = 0; i < constraints.length; i += 2) {
                char variable1 = constraints[i].charAt(0);
                char variable2 = constraints[i + 1].charAt(0);

                int variableID1 = variableToIDMap.get(variable1);
                int variableID2 = variableToIDMap.get(variable2);
                adjacencyList[variableID1].add(variableID2);
                inDegrees[variableID2]++;
            }

            if (specificationID > 1) {
                outputWriter.printLine();
            }
            List<Order> orders = computeOrders(adjacencyList, idToVariable, inDegrees);
            for (Order order : orders) {
                List<Character> variables = order.variables;
                for (char variable : variables) {
                    outputWriter.print(variable);
                }
                outputWriter.printLine();
            }
            specificationID++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Order> computeOrders(List<Integer>[] adjacencyList, char[] idToVariable, int[] inDegrees) {
        List<Order> orders = new ArrayList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        Deque<Integer> stack = new ArrayDeque<>();

        computeOrders(adjacencyList, orders, idToVariable, inDegrees, visited, stack);
        Collections.sort(orders);
        return orders;
    }

    private static void computeOrders(List<Integer>[] adjacencyList, List<Order> orders, char[] idToVariable,
                                      int[] inDegrees, boolean[] visited, Deque<Integer> stack) {
        for (int variableID = 0; variableID < inDegrees.length; variableID++) {
            if (inDegrees[variableID] == 0 && !visited[variableID]) {
                stack.push(variableID);
                visited[variableID] = true;
                for (int neighborID : adjacencyList[variableID]) {
                    inDegrees[neighborID]--;
                }

                computeOrders(adjacencyList, orders, idToVariable, inDegrees, visited, stack);

                stack.pop();
                visited[variableID] = false;
                for (int neighborID : adjacencyList[variableID]) {
                    inDegrees[neighborID]++;
                }
            }
        }

        if (stack.size() == adjacencyList.length) {
            Order order = new Order();
            for (int variableID : stack) {
                char variable = idToVariable[variableID];
                order.variables.add(variable);
            }
            Collections.reverse(order.variables);
            orders.add(order);
        }
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
