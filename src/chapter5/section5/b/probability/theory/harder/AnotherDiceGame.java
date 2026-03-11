package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/03/26.
 */
// Based on https://github.com/BrandonTang89/Competitive_Programming_4_Solutions/blob/main/Chapter_5_Mathematics/Probability/kattis_anotherdice.cpp
public class AnotherDiceGame {

    private static class State {
        int valueNeeded;
        int dicesLeft;
        int dicesPutAsideMask;
        double factor;
        double probability;
        int[] dicesSelected;

        public State(int valueNeeded, int dicesLeft, int dicesPutAsideMask, double factor, double probability) {
            this.valueNeeded = valueNeeded;
            this.dicesLeft = dicesLeft;
            this.dicesPutAsideMask = dicesPutAsideMask;
            this.factor = factor;
            this.probability = probability;
            dicesSelected = new int[6];
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int targetValue = FastReader.nextInt();
            double probability = computeProbability(targetValue);
            outputWriter.printLine(probability);
        }
        outputWriter.flush();
    }

    private static double computeProbability(int targetValue) {
        // dp[value needed][dices left][dices put aside bitmask]
        double[][][] dp = new double[targetValue + 1][9][1 << 6];
        for (double[][] values1 : dp) {
            for (double[] values2 : values1) {
                Arrays.fill(values2, -1);
            }
        }
        Deque<State> statesStack = new ArrayDeque<>();
        int initialBitmask = (1 << 6) - 1;
        return computeProbability(targetValue, 8, initialBitmask, dp, statesStack);
    }

    private static double computeProbability(int valueNeeded, int dicesLeft, int dicesPutAsideMask, double[][][] dp,
                                             Deque<State> statesStack) {
        if (valueNeeded < 0) {
            // Keep minimum value as 0 for dp array index
            return computeProbability(0, dicesLeft, dicesPutAsideMask, dp, statesStack);
        }
        if (valueNeeded == 0 && (dicesPutAsideMask & 1) == 0) {
            // Finished and have taken worm
            return 1;
        }
        if (dicesLeft == 0) {
            return 0;
        }
        if (dp[valueNeeded][dicesLeft][dicesPutAsideMask] != -1) {
            return dp[valueNeeded][dicesLeft][dicesPutAsideMask];
        }

        double factor = Math.pow(1 / 6.0, dicesLeft) * factorial(dicesLeft);
        State state = new State(valueNeeded, dicesLeft, dicesPutAsideMask, factor, 0);
        statesStack.push(state);
        transition(dicesLeft, 0, dp, statesStack);
        state = statesStack.pop();

        dp[valueNeeded][dicesLeft][dicesPutAsideMask] = state.probability;
        return dp[valueNeeded][dicesLeft][dicesPutAsideMask];
    }

    private static void transition(int dicesLeft, int diceValue, double[][][] dp, Deque<State> statesStack) {
        State state = statesStack.peek();
        if (diceValue == 6) {
            double probability = 0;
            int dicesPutAsideMask = state.dicesPutAsideMask;

            while (dicesPutAsideMask != 0) {
                for (int i = 0; i < 6; i++) {
                    if (((1 << i) & dicesPutAsideMask) > 0) {
                        dicesPutAsideMask -= (1 << i);
                        if (state.dicesSelected[i] == 0) {
                            continue;
                        }

                        int score = (i == 0) ? 5 : i;
                        int dicesSelected = state.dicesSelected[i];
                        int nextValue = state.valueNeeded - (dicesSelected * score);
                        int nextBitmask = state.dicesPutAsideMask ^ (1 << i);
                        double candidateProbability =
                                computeProbability(nextValue, state.dicesLeft - dicesSelected, nextBitmask,
                                        dp, statesStack);
                        probability = Math.max(probability, candidateProbability);
                    }
                }
            }
            state.probability += probability * state.factor;
            return;
        }
        if (diceValue == 5) {
            // Last number
            state.factor /= factorial(dicesLeft);
            state.dicesSelected[diceValue] = dicesLeft;
            transition(0, diceValue + 1, dp, statesStack);
            state.factor *= factorial(dicesLeft);
            return;
        }

        for (int i = 0; i <= dicesLeft; i++) {
            state.factor /= factorial(i);
            state.dicesSelected[diceValue] = i;
            transition(dicesLeft - i, diceValue + 1, dp, statesStack);
            state.factor *= factorial(i);
        }
    }

    private static double factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
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
