package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/07/22.
 */
public class ShellSort {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int turtles = FastReader.nextInt();
            String[] originalTurtles = new String[turtles];
            String[] desiredTurtles = new String[turtles];
            readTurtles(originalTurtles);
            readTurtles(desiredTurtles);

            Deque<String> turtlesSequence = computeTurtlesSequence(originalTurtles, desiredTurtles);
            while (!turtlesSequence.isEmpty()) {
                outputWriter.printLine(turtlesSequence.pop());
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static void readTurtles(String[] turtles) throws IOException {
        for (int i = 0; i < turtles.length; i++) {
            turtles[i] = FastReader.getLine();
        }
    }

    private static Deque<String> computeTurtlesSequence(String[] originalTurtles, String[] desiredTurtles) {
        Deque<String> turtlesSequence = new ArrayDeque<>();
        List<String> matchedTurtles = new ArrayList<>();
        Set<String> movedTurtles = new HashSet<>();
        int originalTurtlesIndex = 0;
        int desiredTurtlesIndex = 0;

        while (originalTurtlesIndex < originalTurtles.length
                && desiredTurtlesIndex < desiredTurtles.length) {
            if (movedTurtles.contains(originalTurtles[originalTurtlesIndex])) {
                originalTurtlesIndex++;
                continue;
            }

            if (originalTurtles[originalTurtlesIndex].equals(desiredTurtles[desiredTurtlesIndex])) {
                matchedTurtles.add(originalTurtles[originalTurtlesIndex]);
                originalTurtlesIndex++;
            } else {
                for (String matchedTurtle : matchedTurtles) {
                    turtlesSequence.push(matchedTurtle);
                }
                matchedTurtles = new ArrayList<>();

                turtlesSequence.push(desiredTurtles[desiredTurtlesIndex]);
                movedTurtles.add(desiredTurtles[desiredTurtlesIndex]);
            }
            desiredTurtlesIndex++;
        }
        return turtlesSequence;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
