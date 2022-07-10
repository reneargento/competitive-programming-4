package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/07/22.
 */
public class Football {

    private static class Match implements Comparable<Match> {
        int goalsScored;
        int goalsReceived;

        public Match(int goalsScored, int goalsReceived) {
            this.goalsScored = goalsScored;
            this.goalsReceived = goalsReceived;
        }

        @Override
        public int compareTo(Match other) {
            int teamScoreDelta = goalsReceived - goalsScored;
            int otherTeamScoreDelta = other.goalsReceived - other.goalsScored;
            return Integer.compare(teamScoreDelta, otherTeamScoreDelta);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            Match[] matches = new Match[Integer.parseInt(data[0])];
            int extraGoals = Integer.parseInt(data[1]);
            for (int i = 0; i < matches.length; i++) {
                matches[i] = new Match(FastReader.nextInt(), FastReader.nextInt());
            }

            int maximumPoints = computeMaximumPoints(matches, extraGoals);
            outputWriter.printLine(maximumPoints);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMaximumPoints(Match[] matches, int extraGoals) {
        int maximumPoints = 0;
        Arrays.sort(matches);

        for (Match match : matches) {
            if (match.goalsScored > match.goalsReceived) {
                maximumPoints += 3;
            } else {
                int goalsNeeded = match.goalsReceived - match.goalsScored + 1;
                if (goalsNeeded <= extraGoals) {
                    extraGoals -= goalsNeeded;
                    maximumPoints += 3;
                } else if (goalsNeeded == extraGoals + 1) {
                    maximumPoints++;
                    extraGoals = 0;
                }
            }
        }
        return maximumPoints;
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
