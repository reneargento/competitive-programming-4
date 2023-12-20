package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/12/23.
 */
public class PlayingWithWheels {

    private static class State {
        int configuration;
        int buttonPresses;

        public State(int configuration, int buttonPresses) {
            this.configuration = configuration;
            this.buttonPresses = buttonPresses;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();
        for (int t = 0; t < tests; t++) {
            int initialConfiguration = readConfiguration();
            int targetConfiguration = readConfiguration();

            int forbiddenConfigurationsNumber = FastReader.nextInt();
            Set<Integer> forbiddenConfigurations = new HashSet<>();;
            for (int i = 0; i < forbiddenConfigurationsNumber; i++) {
                int configuration = readConfiguration();
                forbiddenConfigurations.add(configuration);
            }

            int minimumButtonPresses = computeMinimumButtonPresses(initialConfiguration, targetConfiguration,
                    forbiddenConfigurations);
            outputWriter.printLine(minimumButtonPresses);
        }
        outputWriter.flush();
    }

    private static int computeMinimumButtonPresses(int initialConfiguration, int targetConfiguration,
                                                   Set<Integer> forbiddenConfigurations) {
        Queue<State> queue = new LinkedList<>();
        boolean[] visited = new boolean[10000];
        visited[initialConfiguration] = true;
        queue.offer(new State(initialConfiguration, 0));

        while (!queue.isEmpty()) {
            State state = queue.poll();
            int configuration = state.configuration;

            if (configuration == targetConfiguration) {
                return state.buttonPresses;
            }

            for (int rotation = 0; rotation <= 1; rotation++) {
                boolean rotateLeft = rotation == 0;
                for (int wheel = 0; wheel <= 3; wheel++) {
                    int nextConfiguration = rotate(configuration, wheel, rotateLeft);
                    if (!visited[nextConfiguration] && !forbiddenConfigurations.contains(nextConfiguration)) {
                        visited[nextConfiguration] = true;
                        queue.offer(new State(nextConfiguration, state.buttonPresses + 1));
                    }
                }
            }
        }
        return -1;
    }

    private static int rotate(int configuration, int wheel, boolean rotateLeft) {
        int newConfiguration;
        int digitIndex = 3 - wheel;
        int configurationCopy = configuration;

        for (int i = 0; i < digitIndex; i++) {
            configurationCopy /= 10;
        }
        int currentDigitValue = configurationCopy % 10;

        if (rotateLeft) {
            if (currentDigitValue != 9) {
                int valueToAdd = getValueMultiplier(1, digitIndex);
                newConfiguration = configuration + valueToAdd;
            } else {
                int valueToSubtract = getValueMultiplier(9, digitIndex);
                newConfiguration = configuration - valueToSubtract;
            }
        } else {
            if (currentDigitValue != 0) {
                int valueToSubtract = getValueMultiplier(1, digitIndex);
                newConfiguration = configuration - valueToSubtract;
            } else {
                int valueToAdd = getValueMultiplier(9, digitIndex);
                newConfiguration = configuration + valueToAdd;
            }
        }
        return newConfiguration;
    }

    private static int getValueMultiplier(int value, int digitIndex) {
        for (int i = 0; i < digitIndex; i++) {
            value *= 10;
        }
        return value;
    }

    private static int readConfiguration() throws IOException {
        int configuration = FastReader.nextInt() * 1000;
        configuration += FastReader.nextInt() * 100;
        configuration += FastReader.nextInt() * 10;
        configuration += FastReader.nextInt();
        return configuration;
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