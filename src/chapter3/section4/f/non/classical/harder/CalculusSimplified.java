package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/08/22.
 */
public class CalculusSimplified {

    private static class State {
        int lowNumbersIndex;
        int highNumbersIndex;
        int expressionIndex;

        public State(int highNumbersIndex) {
            this.highNumbersIndex = highNumbersIndex;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String expression = FastReader.next();
            int[] values = new int[FastReader.nextInt()];
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.nextInt();
            }
            Arrays.sort(values);
            State state = new State(values.length - 1);

            long maxPossibleValue = getMaxPossibleValue(expression, values, true, state);
            outputWriter.printLine(maxPossibleValue);
        }
        outputWriter.flush();
    }

    private static long getMaxPossibleValue(String expression, int[] values, boolean isPositive, State state) {
        long value = 0;
        boolean isSum = true;

        while (state.expressionIndex < expression.length()) {
            char symbol = expression.charAt(state.expressionIndex);

            if (symbol == '(') {
                boolean nextPositive = isPositive;
                if (state.expressionIndex > 0 && expression.charAt(state.expressionIndex - 1) == '-') {
                    nextPositive = !nextPositive;
                }
                state.expressionIndex++;

                long subResult = getMaxPossibleValue(expression, values, nextPositive, state);
                if (isSum) {
                    value += subResult;
                } else {
                    value -= subResult;
                }
            } else if (symbol == ')') {
                break;
            } else if (symbol == '+') {
                isSum = true;
            } else if (symbol == '-') {
                isSum = false;
            } else if (symbol == 'x') {
                if (isSum) {
                    if (isPositive) {
                        value += values[state.highNumbersIndex];
                        state.highNumbersIndex--;
                    } else {
                        value += values[state.lowNumbersIndex];
                        state.lowNumbersIndex++;
                    }
                } else {
                    if (isPositive) {
                        value -= values[state.lowNumbersIndex];
                        state.lowNumbersIndex++;
                    } else {
                        value -= values[state.highNumbersIndex];
                        state.highNumbersIndex--;
                    }
                }
            }
            state.expressionIndex++;
        }
        return value;
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
