package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/07/2026.
 */
// Based on https://github.com/metaphysis/Code/blob/master/UVa%20Online%20Judge/volume100/10045%20Echo/program.cpp
public class Echo {

    private static class State {
        String buffer;
        int stringIndex;

        public State(String buffer, int stringIndex) {
            this.buffer = buffer;
            this.stringIndex = stringIndex;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return stringIndex == state.stringIndex && Objects.equals(buffer, state.buffer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(buffer, stringIndex);
        }
    }

    private static final String[] results = {
            "An echo string with buffer size ten",
            "Not an echo string, but still consistent with the theory",
            "Not consistent with the theory"
    };

    private static final int NOT_CONSISTENT = 0;
    private static final int ECHO = 1;
    private static final int NO_ECHO_BUT_CONSISTENT = 2;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.getLine();
            String result = analyzeString(string);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String analyzeString(String string) {
        Map<State, Integer> stateToResultMap = new HashMap<>();
        StringBuilder buffer = new StringBuilder();

        int resultIndex = analyzeString(string, stateToResultMap, buffer, 0);
        int adjustedIndex;
        if (resultIndex == NOT_CONSISTENT) {
            adjustedIndex = 2;
        } else {
            if (resultIndex == ECHO || resultIndex == 3) {
                adjustedIndex = 0;
            } else {
                adjustedIndex = 1;
            }
        }
        return results[adjustedIndex];
    }

    private static int analyzeString(String string, Map<State, Integer> stateToResultMap,
                                     StringBuilder buffer, int index) {
        if (index >= string.length()) {
            if (buffer.length() == 0) {
                return ECHO;
            } else {
                return NO_ECHO_BUT_CONSISTENT;
            }
        }

        State state = new State(buffer.toString(), index);
        if (stateToResultMap.containsKey(state)) {
            return stateToResultMap.get(state);
        }

        int result = NOT_CONSISTENT;
        if (buffer.length() > 0) {
            char firstCharacter = buffer.charAt(0);
            if (string.charAt(index) == firstCharacter) {
                buffer.deleteCharAt(0);
                result |= analyzeString(string, stateToResultMap, buffer, index + 1);
                buffer.insert(0, firstCharacter);
                if (result == ECHO) {
                    stateToResultMap.put(state, result);
                    return result;
                }
            }
        }
        if (buffer.length() <= 9) {
            buffer.append(string.charAt(index));
            result |= analyzeString(string, stateToResultMap, buffer, index + 1);
            buffer.deleteCharAt(buffer.length() - 1);
        }
        stateToResultMap.put(state, result);
        return result;
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