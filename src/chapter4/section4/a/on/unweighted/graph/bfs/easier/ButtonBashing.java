package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/10/23.
 */
public class ButtonBashing {

    private static class State {
        int buttonPresses;
        int cookingTime;

        public State(int buttonPresses, int cookingTime) {
            this.buttonPresses = buttonPresses;
            this.cookingTime = cookingTime;
        }
    }

    private static final int OFFSET = 3600;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();
        for (int t = 0; t < tests; t++) {
            int[] values = new int[FastReader.nextInt()];
            int cookingTime = FastReader.nextInt();
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.nextInt();
            }

            State finalState = computeMinimumButtonPresses(values, cookingTime);
            int extraSeconds = finalState.cookingTime - cookingTime;
            outputWriter.printLine(finalState.buttonPresses + " " + extraSeconds);
        }
        outputWriter.flush();
    }

    private static State computeMinimumButtonPresses(int[] values, int cookingTime) {
        State bestStateSoFar = null;
        State state = new State(0, 0);
        Queue<State> queue = new LinkedList<>();
        queue.offer(state);

        boolean[] visited = new boolean[7201];
        visited[OFFSET] = true;

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.cookingTime == cookingTime) {
                return currentState;
            }
            if ((bestStateSoFar == null && currentState.cookingTime > cookingTime) ||
                    (bestStateSoFar != null
                            && currentState.cookingTime > cookingTime
                            && currentState.cookingTime < bestStateSoFar.cookingTime)) {
                bestStateSoFar = currentState;
            }

            for (int value : values) {
                int nextCookingTime = currentState.cookingTime + value;
                if (nextCookingTime > 3600 && !visited[OFFSET + OFFSET]) {
                    visited[OFFSET + OFFSET] = true;
                    queue.offer(new State(currentState.buttonPresses + 1, 3600));
                    continue;
                } else if (nextCookingTime < 0 || nextCookingTime > 3600 || visited[nextCookingTime + OFFSET]) {
                    continue;
                }
                visited[nextCookingTime + OFFSET] = true;
                queue.offer(new State(currentState.buttonPresses + 1, nextCookingTime));
            }
        }
        return bestStateSoFar;
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
