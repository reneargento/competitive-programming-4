package chapter4.section6.d.tree;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/07/24.
 */
public class STrees {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int depth = FastReader.nextInt();
        int sTreeNumber = 1;

        while (depth != 0) {
            int[] variableOrdering = new int[depth];
            for (int i = 0; i < variableOrdering.length; i++) {
                String variable = FastReader.next();
                int variableId = Character.getNumericValue(variable.charAt(1)) - 1;
                variableOrdering[i] = variableId;
            }

            String terminalNodes = FastReader.next();
            int[] terminalNodeValues = new int[terminalNodes.length()];
            for (int i = 0; i < terminalNodeValues.length; i++) {
                terminalNodeValues[i] = Character.getNumericValue(terminalNodes.charAt(i));
            }

            outputWriter.printLine(String.format("S-Tree #%d:", sTreeNumber));
            int variableValueAssignments = FastReader.nextInt();
            for (int i = 0; i < variableValueAssignments; i++) {
                int[] variableValues = new int[depth];
                String values = FastReader.next();

                for (int d = 0; d < values.length(); d++) {
                    variableValues[d] = Character.getNumericValue(values.charAt(d));
                }

                int functionResult = applyFunction(variableOrdering, terminalNodeValues, variableValues);
                outputWriter.print(functionResult);
            }

            outputWriter.printLine();
            outputWriter.printLine();
            sTreeNumber++;
            depth = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int applyFunction(int[] variableOrdering, int[] terminalNodeValues, int[] variableValues) {
        int terminalLow = 0;
        int terminalHigh = terminalNodeValues.length - 1;

        for (int variableId : variableOrdering) {
            int terminalMiddle = terminalLow + (terminalHigh - terminalLow) / 2;
            int variableValue = variableValues[variableId];

            if (variableValue == 0) {
                terminalHigh = terminalMiddle;
            } else {
                terminalLow = terminalMiddle + 1;
            }
        }
        return terminalNodeValues[terminalLow];
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
