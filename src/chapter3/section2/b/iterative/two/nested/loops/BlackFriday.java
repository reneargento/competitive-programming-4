package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/09/21.
 */
public class BlackFriday {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int participants = FastReader.nextInt();
        Map<Integer, Integer> resultsMap = new HashMap<>();
        Map<Integer, Integer> resultToIndexMap = new HashMap<>();

        for (int i = 0; i < participants; i++) {
            int dieRoll = FastReader.nextInt();
            int frequency = resultsMap.getOrDefault(dieRoll, 0);
            frequency++;
            resultsMap.put(dieRoll, frequency);
            resultToIndexMap.put(dieRoll, i + 1);
        }

        int highestUniqueOutcome = -1;
        for (int result = 6; result >= 1; result--) {
            if (resultsMap.containsKey(result) && resultsMap.get(result) == 1) {
                highestUniqueOutcome = result;
                break;
            }
        }

        if (highestUniqueOutcome == -1) {
            outputWriter.printLine("none");
        } else {
            outputWriter.printLine(resultToIndexMap.get(highestUniqueOutcome));
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
