package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/12/21.
 */
public class Zagrade {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String originalExpression = FastReader.getLine();
        Map<Integer, Integer> bracketMap = createBracketMap(originalExpression);

        List<String> validExpressions = computeValidExpressions(originalExpression, bracketMap);

        for (String expression : validExpressions) {
            outputWriter.printLine(expression);
        }
        outputWriter.flush();
    }

    private static Map<Integer, Integer> createBracketMap(String expression) {
        Map<Integer, Integer> bracketMap = new HashMap<>();
        Deque<Integer> openingsStack = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);

            if (symbol == '(') {
                openingsStack.push(i);
            } else if (symbol == ')') {
                int openingIndex = openingsStack.pop();
                bracketMap.put(i, openingIndex);
            }
        }
        return bracketMap;
    }

    private static List<String> computeValidExpressions(String expression, Map<Integer, Integer> bracketMap) {
        Set<String> validExpressions = new HashSet<>();
        char[] currentExpression = new char[expression.length()];

        computeValidExpressions(expression, currentExpression, bracketMap, validExpressions, 0);
        List<String> validExpressionsList = new ArrayList<>(validExpressions);
        Collections.sort(validExpressionsList);
        return validExpressionsList;
    }

    private static void computeValidExpressions(String expression, char[] currentExpression,
                                                Map<Integer, Integer> bracketMap, Set<String> validExpressions,
                                                int index) {
        if (index == expression.length()) {
            String finalExpression = new String(currentExpression);
            finalExpression = finalExpression.replace("R", "");
            if (!finalExpression.equals(expression)) {
                validExpressions.add(finalExpression);
            }
            return;
        }

        for (int i = index; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            currentExpression[i] = symbol;

            if (symbol == ')') {
                int openingBracket = bracketMap.get(i);

                // Keep brackets
                currentExpression[openingBracket] = '(';
                currentExpression[i] = ')';
                computeValidExpressions(expression, currentExpression, bracketMap, validExpressions, i + 1);

                // Remove brackets
                currentExpression[openingBracket] = 'R';
                currentExpression[i] = 'R';
                computeValidExpressions(expression, currentExpression, bracketMap, validExpressions, i + 1);
                return;
            }

            if (i == expression.length() - 1) {
                computeValidExpressions(expression, currentExpression, bracketMap, validExpressions, i + 1);
            }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
