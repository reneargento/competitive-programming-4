package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 01/06/21.
 */
public class AddingWords {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, Integer> variableToValueMap = new HashMap<>();
        Map<Integer, String> valueToVariableMap = new HashMap<>();

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            switch (data[0]) {
                case "def":
                    String word = data[1];
                    int value = Integer.parseInt(data[2]);

                    if (variableToValueMap.containsKey(word)) {
                        int oldValue = variableToValueMap.get(word);
                        valueToVariableMap.remove(oldValue);
                    }
                    variableToValueMap.put(word, value);
                    valueToVariableMap.put(value, word);
                    break;
                case "calc":
                    calculate(variableToValueMap, valueToVariableMap, data, outputWriter);
                    break;
                default:
                    variableToValueMap = new HashMap<>();
                    valueToVariableMap = new HashMap<>();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void calculate(Map<String, Integer> variableToValueMap, Map<Integer, String> valueToVariableMap,
                                  String[] variablesData, OutputWriter outputWriter) {
        StringJoiner expression = new StringJoiner(" ");
        int result = 0;
        boolean unknown = false;

        for (int i = 1; i < variablesData.length; i++) {
            String variable = variablesData[i];

            if (!isOperator(variable) && variableToValueMap.containsKey(variable) && !unknown) {
                if (variablesData[i - 1].equals("-")) {
                    result -= variableToValueMap.get(variable);
                } else {
                    result += variableToValueMap.get(variable);
                }
            } else if (!isOperator(variable)) {
                unknown = true;
            }

            expression.add(variable);
        }

        if (unknown || !valueToVariableMap.containsKey(result)) {
            expression.add("unknown");
        } else {
            String resultVariable = valueToVariableMap.get(result);
            expression.add(resultVariable);
        }
        outputWriter.printLine(expression.toString());
    }

    private static boolean isOperator(String variable) {
        return variable.equals("+") || variable.equals("-") || variable.equals("=");
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
