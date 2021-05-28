package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 28/05/21.
 */
public class Recount {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Map<String, Integer> votes = new HashMap<>();
        String winner = null;
        int highestVotesNumber = 0;

        String candidate = FastReader.getLine();
        while (!candidate.equals("***")) {
            int votesNumber = votes.getOrDefault(candidate, 0);
            votesNumber++;
            votes.put(candidate, votesNumber);

            if (votesNumber == highestVotesNumber) {
                winner = null;
            }
            if (votesNumber > highestVotesNumber) {
                highestVotesNumber = votesNumber;
                winner = candidate;
            }

            candidate = FastReader.getLine();
        }

        outputWriter.printLine(winner != null ? winner : "Runoff!");
        outputWriter.flush();
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
