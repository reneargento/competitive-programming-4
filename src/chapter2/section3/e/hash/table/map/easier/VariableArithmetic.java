package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 31/05/21.
 */
public class VariableArithmetic {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, Integer> variablesMap = new HashMap<>();
        String line = FastReader.getLine();
        while (!line.equals("0")) {
            String[] data = line.split(" ");
            if (data.length > 1 && data[1].equals("=")) {
                variablesMap.put(data[0], Integer.parseInt(data[2]));
            } else {
                String simplifiedStatement = simplifyStatement(variablesMap, data);
                outputWriter.printLine(simplifiedStatement);
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String simplifyStatement(Map<String, Integer> variablesMap, String[] statement) {
        int knownSum = 0;
        boolean hasUnknownTerm = false;

        for (String term : statement) {
            if (term.equals("+")) {
                continue;
            }
            if (variablesMap.containsKey(term)) {
                knownSum += variablesMap.get(term);
            } else if (Character.isDigit(term.charAt(0))) {
                knownSum += Integer.parseInt(term);
            } else {
                hasUnknownTerm = true;
            }
        }

        StringBuilder simplifiedStatement = new StringBuilder();
        boolean isFirstTerm = true;

        if (knownSum != 0 || !hasUnknownTerm) {
            simplifiedStatement.append(knownSum);
            isFirstTerm = false;
        }
        for (String term : statement) {
            if (term.equals("+") || Character.isDigit(term.charAt(0))) {
                continue;
            }
            if (!variablesMap.containsKey(term)) {
                if (!isFirstTerm) {
                    simplifiedStatement.append(" + ");
                }
                simplifiedStatement.append(term);
                isFirstTerm = false;
            }
        }
        return simplifiedStatement.toString();
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
