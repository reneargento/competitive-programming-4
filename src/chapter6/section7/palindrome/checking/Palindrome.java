package chapter6.section7.palindrome.checking;

import java.io.*;

/**
 * Created by Rene Argento on 20/10/2026.
 */
public class Palindrome {

    private static class State {
        int palindromeLength;
        int positionsUsed;

        public State(int palindromeLength, int positionsUsed) {
            this.palindromeLength = palindromeLength;
            this.positionsUsed = positionsUsed;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String string = FastReader.getLine();
        while (string != null) {
            String positionsString = FastReader.getLine();
            String[] data = positionsString.split(" ");
            boolean[] positions = new boolean[string.length()];
            for (int i = 0; i < data.length - 1; i++) {
                int position = Integer.parseInt(data[i + 1]) - 1;
                positions[position] = true;
            }

            int longestPalindrome = computeLongestPalindrome(string, positions);
            outputWriter.printLine(longestPalindrome);
            string = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeLongestPalindrome(String string, boolean[] positions) {
        // dp[start index][end index]
        State[][] dp = new State[string.length()][string.length()];
        State state = computeLongestPalindrome(string, positions, dp, 0, string.length() - 1);
        return state.palindromeLength;
    }

    private static State computeLongestPalindrome(String string, boolean[] positions, State[][] dp, int startIndex,
                                                  int endIndex) {
        if (endIndex < startIndex) {
            return new State(0, 0);
        }
        if (startIndex == endIndex) {
            int positionsUsed = 0;
            if (positions[startIndex]) {
                positionsUsed = 1;
            }
            return new State(1, positionsUsed);
        }
        if (dp[startIndex][endIndex] != null) {
            return dp[startIndex][endIndex];
        }

        State bestState = null;
        if (string.charAt(startIndex) == string.charAt(endIndex)) {
            State result = computeLongestPalindrome(string, positions, dp, startIndex + 1, endIndex - 1);
            bestState = new State(result.palindromeLength, result.positionsUsed);
            if (positions[startIndex]) {
                bestState.positionsUsed++;
            }
            if (positions[endIndex]) {
                bestState.positionsUsed++;
            }
            bestState.palindromeLength += 2;
        }
        State state2 = computeLongestPalindrome(string, positions, dp, startIndex + 1, endIndex);
        State state3 = computeLongestPalindrome(string, positions, dp, startIndex, endIndex - 1);

        bestState = getBestState(bestState, state2);
        bestState = getBestState(bestState, state3);

        dp[startIndex][endIndex] = bestState;
        return bestState;
    }

    private static State getBestState(State state1, State state2) {
        if (state1 == null && state2 == null) {
            return null;
        }
        State state2Copy = new State(state2.palindromeLength, state2.positionsUsed);

        State bestState;
        if (state1 == null
                || state2Copy.positionsUsed > state1.positionsUsed
                || (state2Copy.positionsUsed == state1.positionsUsed && state2Copy.palindromeLength > state1.palindromeLength)) {
            bestState = state2Copy;
        } else {
            bestState = state1;
        }
        return bestState;
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

        public void flush() {
            writer.flush();
        }
    }
}