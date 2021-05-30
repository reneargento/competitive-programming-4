package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/05/21.
 */
public class Election2 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int candidates = FastReader.nextInt();
        Map<String, String> candidatesToParty = new HashMap<>();
        Map<String, Integer> candidatesVotes = new HashMap<>();

        for (int i = 0; i < candidates; i++) {
            String name = FastReader.getLine();
            String party = FastReader.getLine();
            candidatesToParty.put(name, party);
        }

        int votes = FastReader.nextInt();
        int highestVotes = 0;
        String winnerCandidate = null;

        for (int v = 0; v < votes; v++) {
            String candidate = FastReader.getLine();
            if (candidatesToParty.containsKey(candidate)) {
                int frequency = candidatesVotes.getOrDefault(candidate, 0);
                frequency++;
                candidatesVotes.put(candidate, frequency);

                if (frequency > highestVotes) {
                    highestVotes = frequency;
                    winnerCandidate = candidate;
                } else if (frequency == highestVotes) {
                    winnerCandidate = null;
                }
            }
        }

        if (winnerCandidate != null) {
            String winnerParty = candidatesToParty.get(winnerCandidate);
            outputWriter.printLine(winnerParty);
        } else {
            outputWriter.printLine("tie");
        }
        outputWriter.flush();
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
