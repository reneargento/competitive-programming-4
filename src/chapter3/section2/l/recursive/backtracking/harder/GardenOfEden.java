package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;

/**
 * Created by Rene Argento on 19/02/22.
 */
public class GardenOfEden {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int automatonIdentifier = Integer.parseInt(data[0]);
            String targetState = data[2];

            outputWriter.printLine(isReachable(automatonIdentifier, targetState) ? "REACHABLE"
                    : "GARDEN OF EDEN");
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isReachable(int automatonIdentifier, String targetStateString) {
        // Check identity automaton
        if (automatonIdentifier == 204) {
            return true;
        }

        int[] targetState = new int[targetStateString.length()];
        for (int i = 0; i < targetStateString.length(); i++) {
            if (targetStateString.charAt(i) == '0') {
                targetState[i] = 0;
            } else {
                targetState[i] = 1;
            }
        }

        int[] rules = getRules(automatonIdentifier);
        int[] currentState = new int[targetState.length];
        return isReachable(targetState, rules, currentState, 0);
    }

    private static boolean isReachable(int[] targetState, int[] rules, int[] currentState, int index) {
        if (index >= 3 && index <= currentState.length) {
            if (getNewState(rules, currentState[index - 3], currentState[index - 2],
                    currentState[index - 1]) != targetState[index - 2]) {
                return false;
            }
        }

        if (index == targetState.length) {
            if (getNewState(rules, currentState[currentState.length - 1], currentState[0],
                    currentState[1]) != targetState[0]) {
                return false;
            }
            return getNewState(rules, currentState[currentState.length - 2], currentState[currentState.length - 1],
                    currentState[0]) == targetState[currentState.length - 1];
        }

        currentState[index] = 1;
        boolean isReachableWithBit1 = isReachable(targetState, rules, currentState, index + 1);
        if (isReachableWithBit1) {
            return true;
        }

        currentState[index] = 0;
        return isReachable(targetState, rules, currentState, index + 1);
    }

    private static int[] getRules(int automatonIdentifier) {
        int[] rules = new int[8];
        int index = 0;

        while (automatonIdentifier > 0) {
            int state = automatonIdentifier % 2;
            int ruleIndex = 8 - index - 1;
            rules[ruleIndex] = state;
            index++;
            automatonIdentifier /= 2;
        }
        return rules;
    }

    private static int getNewState(int[] rules, int leftBit, int currentBit, int rightBit) {
        if (leftBit == 0 && currentBit == 0 && rightBit == 0) {
            return rules[0];
        }
        if (leftBit == 0 && currentBit == 0 && rightBit == 1) {
            return rules[1];
        }
        if (leftBit == 0 && currentBit == 1 && rightBit == 0) {
            return rules[2];
        }
        if (leftBit == 0 && currentBit == 1 && rightBit == 1) {
            return rules[3];
        }
        if (leftBit == 1 && currentBit == 0 && rightBit == 0) {
            return rules[4];
        }
        if (leftBit == 1 && currentBit == 0 && rightBit == 1) {
            return rules[5];
        }
        if (leftBit == 1 && currentBit == 1 && rightBit == 0) {
            return rules[6];
        }
        return rules[7];
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
