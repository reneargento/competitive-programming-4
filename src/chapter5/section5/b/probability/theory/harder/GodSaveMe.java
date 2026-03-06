package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/02/26.
 */
public class GodSaveMe {

    private static class Door {
        int hours;
        double probability;
        boolean isExit;

        public Door(int hours, double probability, boolean isExit) {
            this.hours = hours;
            this.probability = probability;
            this.isExit = isExit;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int doorsNumber = FastReader.nextInt();
            Door[] doors = new Door[doorsNumber];
            boolean isPossibleToReachSafePlace = false;

            for (int d = 0; d < doors.length; d++) {
                List<String> words = getWords(FastReader.getLine());

                int hours = Integer.parseInt(words.get(0));
                double probability = Double.parseDouble(words.get(1));
                doors[d] = new Door(Math.abs(hours), probability, hours > 0);

                if (doors[d].isExit) {
                    isPossibleToReachSafePlace = true;
                }
            }

            outputWriter.print(String.format("Case %d: ", t));
            if (!isPossibleToReachSafePlace) {
                outputWriter.printLine("God! Save me");
            } else {
                double safePlaceProbability = computeSafePlaceProbability(doors);
                outputWriter.printLine(String.format("%.2f", safePlaceProbability));
            }
        }
        outputWriter.flush();
    }

    private static double computeSafePlaceProbability(Door[] doors) {
        // dp[doors taken]
        double[] dp = new double[10000];
        Arrays.fill(dp, -1);
        return computeSafePlaceProbability(0, dp, doors);
    }

    private static double computeSafePlaceProbability(int doorsTaken, double[] dp, Door[] doors) {
        if (doorsTaken == dp.length) {
            return 0;
        }
        if (dp[doorsTaken] != -1) {
            return dp[doorsTaken];
        }

        double safePlaceProbability = 0;
        for (Door door : doors) {
            if (door.isExit) {
                safePlaceProbability += door.probability * door.hours;
            } else {
                safePlaceProbability += door.probability * Math.abs(door.hours) +
                        door.probability * computeSafePlaceProbability(doorsTaken + 1, dp, doors);
            }
        }

        dp[doorsTaken] = safePlaceProbability;
        return safePlaceProbability;
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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