package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/06/21.
 */
public class BASICInterpreter {

    private static class Statement implements Comparable<Statement> {
        int id;
        String type;
        String[] parsedStatement;
        String fullStatement;

        public Statement(String fullStatement) {
            String[] parsedStatement = fullStatement.split(" ");
            id = Integer.parseInt(parsedStatement[0]);
            type = parsedStatement[1];
            this.parsedStatement = parsedStatement;
            this.fullStatement = fullStatement;
        }

        @Override
        public int compareTo(Statement other) {
            return Integer.compare(id, other.id);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        List<Statement> statements = new ArrayList<>();

        String statementLine = FastReader.getLine();
        while (statementLine != null) {
            statements.add(new Statement(statementLine));
            statementLine = FastReader.getLine();
        }
        Collections.sort(statements);
        executeProgram(statements);
    }

    private static void executeProgram(List<Statement> statements) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        Map<Integer, Integer> labelToStatementIndex = createLabelToStatementIndexMap(statements);
        Map<Character, Integer> variables = initVariables();
        int statementIndexToExecute = 0;

        while (statementIndexToExecute < statements.size()) {
            Statement statement = statements.get(statementIndexToExecute);
            boolean goToExecuted = false;

            switch (statement.type) {
                case "LET": assignVariable(statement.parsedStatement, variables); break;
                case "IF":
                    int resultLabel = processCondition(statement.parsedStatement, variables);
                    if (resultLabel != -1) {
                        statementIndexToExecute = labelToStatementIndex.get(resultLabel);
                        goToExecuted = true;
                    }
                    break;
                default: processPrintOutput(statement, variables, outputWriter);
            }
            if (!goToExecuted) {
                statementIndexToExecute++;
            }
        }
        outputWriter.flush();
    }

    private static Map<Integer, Integer> createLabelToStatementIndexMap(List<Statement> statements) {
        Map<Integer, Integer> labelToStatementIndex = new HashMap<>();
        for (int i = 0; i < statements.size(); i++) {
            labelToStatementIndex.put(statements.get(i).id, i);
        }
        return labelToStatementIndex;
    }

    private static Map<Character, Integer> initVariables() {
        Map<Character, Integer> variables = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            char variable = (char) ('A' + i);
            variables.put(variable, 0);
        }
        return variables;
    }

    private static void assignVariable(String[] statement, Map<Character, Integer> variables) {
        if (isNumber(statement[2])) {
            return;
        }
        char variable = statement[2].charAt(0);
        int value1 = getValue(statement[4], variables);
        int result;

        if (statement.length == 5) {
            result = value1;
        } else {
            int value2 = getValue(statement[6], variables);

            switch (statement[5]) {
                case "+": result = value1 + value2; break;
                case "-": result = value1 - value2; break;
                case "*": result = value1 * value2; break;
                default: result = value1 / value2;
            }
        }
        variables.put(variable, result);
    }

    private static int getValue(String variable, Map<Character, Integer> variables) {
        if (isNumber(variable)) {
            return Integer.parseInt(variable);
        } else {
            return variables.get(variable.charAt(0));
        }
    }

    private static boolean isNumber(String value) {
        return value.charAt(0) == '-' || Character.isDigit(value.charAt(0));
    }

    private static int processCondition(String[] statement, Map<Character, Integer> variables) {
        int value1 = getValue(statement[2], variables);
        int value2 = getValue(statement[4], variables);
        boolean goTo;

        switch (statement[3]) {
            case "=": goTo = value1 == value2; break;
            case ">": goTo = value1 > value2; break;
            case "<": goTo = value1 < value2; break;
            case "<>": goTo = value1 != value2; break;
            case "<=": goTo = value1 <= value2; break;
            default: goTo = value1 >= value2; break;
        }

        if (goTo) {
            return Integer.parseInt(statement[7]);
        }
        return -1;
    }

    private static void processPrintOutput(Statement statement, Map<Character, Integer> variables,
                                           OutputWriter outputWriter) {
        String fullStatement = statement.fullStatement;
        String[] parsedStatement = statement.parsedStatement;

        if (fullStatement.contains("\"")) {
            String[] parsedOutput = fullStatement.split("\"");
            if (parsedOutput.length > 1) {
                outputWriter.print(parsedOutput[1]);
            }
        } else {
            int value = variables.get(parsedStatement[2].charAt(0));
            outputWriter.print(value);
        }

        if (statement.type.equals("PRINTLN")) {
            outputWriter.printLine();
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
