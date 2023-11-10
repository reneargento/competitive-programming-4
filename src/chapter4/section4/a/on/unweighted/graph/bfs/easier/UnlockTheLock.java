package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/10/23.
 */
public class UnlockTheLock {

    private static class State {
        int code;
        int buttonPresses;

        public State(int code, int buttonPresses) {
            this.code = code;
            this.buttonPresses = buttonPresses;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int lockCode = FastReader.nextInt();
        int unlockCode = FastReader.nextInt();
        int buttonsAvailable = FastReader.nextInt();
        int caseID = 1;

        while (lockCode != 0 || unlockCode != 0 || buttonsAvailable != 0) {
            int[] values = new int[buttonsAvailable];
            for (int i = 0; i < values.length; i++) {
                values[i] = FastReader.nextInt();
            }
            int minimumButtonPresses = computeMinimumButtonPresses(lockCode, unlockCode, values);
            outputWriter.print(String.format("Case %d: ", caseID));
            if (minimumButtonPresses == -1) {
                outputWriter.printLine("Permanently Locked");
            } else {
                outputWriter.printLine(minimumButtonPresses);
            }

            lockCode = FastReader.nextInt();
            unlockCode = FastReader.nextInt();
            buttonsAvailable = FastReader.nextInt();
            caseID++;
        }
        outputWriter.flush();
    }

    private static int computeMinimumButtonPresses(int lockCode, int unlockCode, int[] values) {
        State state = new State(lockCode, 0);
        Queue<State> queue = new LinkedList<>();
        queue.offer(state);

        Set<Integer> visited = new HashSet<>();
        visited.add(lockCode);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.code == unlockCode) {
                return currentState.buttonPresses;
            }

            for (int value : values) {
                int nextCode = currentState.code + value;
                int nextCode4Digits = nextCode % 10000;
                if (visited.contains(nextCode4Digits)) {
                    continue;
                }
                visited.add(nextCode4Digits);
                queue.offer(new State(nextCode4Digits, currentState.buttonPresses + 1));
            }
        }
        return -1;
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
