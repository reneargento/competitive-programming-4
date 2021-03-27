package chapter2.section2.k.special.stack.based.problems;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/03/21.
 */
public class Equation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int expression = FastReader.nextInt();
        FastReader.getLine();

        for (int i = 0; i < expression; i++) {
            if (i > 0) {
                outputWriter.printLine();
            }

            String line = FastReader.getLine();
            Deque<Character> operators = new ArrayDeque<>();
            StringBuilder postfixExpression = new StringBuilder();

            while (line != null && !line.isEmpty()) {
                char symbol = line.charAt(0);

                if (Character.isDigit(symbol)) {
                    postfixExpression.append(symbol);
                } else {
                     if (isLowPriorityOperator(symbol) || isHighPriorityOperator(symbol)) {
                        if (!operators.isEmpty() && isHighPriorityOperator(operators.peek())) {
                            postfixExpression.append(operators.pop());
                        }
                        if (!operators.isEmpty() && isLowPriorityOperator(symbol)) {
                            addOperatorsInReverseOrder(operators, postfixExpression);
                        }
                        operators.push(symbol);
                    } else if (symbol == ')') {
                        addOperatorsInReverseOrder(operators, postfixExpression);
                        operators.pop();
                    } else {
                         operators.push(symbol);
                     }
                }
                line = FastReader.getLine();
            }

            while (!operators.isEmpty()) {
                postfixExpression.append(operators.pop());
            }

            outputWriter.printLine(postfixExpression);
        }
        outputWriter.flush();
    }

    private static void addOperatorsInReverseOrder(Deque<Character> operators, StringBuilder postfixExpression) {
        Deque<Character> auxStackHighPriority = new ArrayDeque<>();
        Deque<Character> auxStackLowPriority = new ArrayDeque<>();

        while (!operators.isEmpty() && operators.peek() != '(') {
            char symbol = operators.pop();
            if (isHighPriorityOperator(symbol)) {
                auxStackHighPriority.push(symbol);
            } else {
                auxStackLowPriority.push(symbol);
            }
        }

        while (!auxStackHighPriority.isEmpty()) {
            postfixExpression.append(auxStackHighPriority.pop());
        }
        while (!auxStackLowPriority.isEmpty()) {
            postfixExpression.append(auxStackLowPriority.pop());
        }
    }

    private static boolean isLowPriorityOperator(char symbol) {
        return symbol == '+' || symbol == '-';
    }

    private static boolean isHighPriorityOperator(char symbol) {
        return symbol == '*' || symbol == '/';
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
