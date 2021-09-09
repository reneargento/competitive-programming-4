package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/09/21.
 */
public class FourThought {

    private static class Operation {
        char[] values;

        public Operation() {
            values = new char[3];
        }

        public Operation(Operation copy) {
            values = new char[3];
            System.arraycopy(copy.values, 0, values, 0, copy.values.length);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        Map<Integer, Operation> operations = computeAllOperations();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();

            if (operations.containsKey(number)) {
                Operation operation = operations.get(number);
                outputWriter.printLine(String.format("4 %c 4 %c 4 %c 4 = %d",
                        operation.values[0], operation.values[1], operation.values[2], number));
            } else {
                outputWriter.printLine("no solution");
            }
        }
        outputWriter.flush();
    }

    private static Map<Integer, Operation> computeAllOperations() {
        Map<Integer, Operation> operations = new HashMap<>();
        char[] operators = { '+', '-', '*', '/' };
        computeOperations(operations, 0, new Operation(), operators);
        return operations;
    }

    private static void computeOperations(Map<Integer, Operation> operations, int operationIndex,
                                         Operation currentOperation, char[] operators) {
        if (operationIndex == 3) {
            Operation operation = new Operation(currentOperation);
            int result = computeOperation(operation);
            operations.put(result, operation);
            return;
        }

        for (int i = 0; i < 4; i++) {
            currentOperation.values[operationIndex] = operators[i];
            computeOperations(operations, operationIndex + 1, currentOperation, operators);
        }
    }

    private static int computeOperation(Operation operation) {
        Deque<Integer> operandsStack = new ArrayDeque<>();
        Deque<Character> operatorsStack = new ArrayDeque<>();

        operandsStack.push(4);

        for (char operator : operation.values) {
            if (operator == '*' || operator == '/') {
                int operand1 = operandsStack.pop();
                int result;

                if (operator == '*') {
                    result = operand1 * 4;
                } else {
                    result = operand1 / 4;
                }
                operandsStack.push(result);
            } else {
                operatorsStack.push(operator);
                operandsStack.push(4);
            }
        }

        Deque<Character> reverseOperators = new ArrayDeque<>();
        while (!operatorsStack.isEmpty()) {
            reverseOperators.push(operatorsStack.pop());
        }

        Deque<Integer> reverseOperands = new ArrayDeque<>();
        while (!operandsStack.isEmpty()) {
            reverseOperands.push(operandsStack.pop());
        }

        while (!reverseOperators.isEmpty()) {
            char operator = reverseOperators.pop();
            int result;
            int operand1 = reverseOperands.pop();
            int operand2 = reverseOperands.pop();

            if (operator == '+') {
                result = operand1 + operand2;
            } else {
                result = operand1 - operand2;
            }
            reverseOperands.push(result);
        }
        return reverseOperands.pop();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
