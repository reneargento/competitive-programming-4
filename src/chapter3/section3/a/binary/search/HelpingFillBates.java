package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/03/22.
 */
public class HelpingFillBates {

    private static class Result {
        int firstSerial;
        int lastSerial;

        public Result(int firstSerial, int lastSerial) {
            this.firstSerial = firstSerial;
            this.lastSerial = lastSerial;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String candidates = FastReader.getLine();
        List<Integer>[] stateLocations = getStateLocations(candidates);
        int queries = FastReader.nextInt();

        for (int q = 0; q < queries; q++) {
            char[] query = FastReader.getLine().toCharArray();
            Result result = searchCandidates(stateLocations, query);
            if (result != null) {
                outputWriter.printLine(String.format("Matched %d %d", result.firstSerial, result.lastSerial));
            } else {
                outputWriter.printLine("Not matched");
            }
        }
        outputWriter.flush();
    }

    @SuppressWarnings("unchecked")
    private static List<Integer>[] getStateLocations(String candidates) {
        List<Integer>[] stateLocations = new List[52];
        for (int i = 0; i < stateLocations.length; i++) {
            stateLocations[i] = new ArrayList<>();
        }

        for (int i = 0; i < candidates.length(); i++) {
            char state = candidates.charAt(i);
            int index = getStateIndex(state);
            stateLocations[index].add(i);
        }
        return stateLocations;
    }

    private static Result searchCandidates(List<Integer>[] stateLocations, char[] query) {
        int firstSerial = -1;
        int lastSerial = -1;
        int previousIndex = -1;

        for (char state : query) {
            int stateIndex = getStateIndex(state);
            List<Integer> locations = stateLocations[stateIndex];

            int serial = binarySearchUpperBound(locations, previousIndex, 0, locations.size() - 1);
            if (serial == -1) {
                return null;
            }
            if (firstSerial == -1) {
                firstSerial = serial;
            }
            lastSerial = serial;
            previousIndex = serial;
        }
        return new Result(firstSerial, lastSerial);
    }

    private static int getStateIndex(char state) {
        if (Character.isUpperCase(state)) {
            return state - 'A';
        } else {
           return state - 'a' + 26;
        }
    }

    private static int binarySearchUpperBound(List<Integer> locations, int target, int low, int high) {
        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (locations.get(middle) <= target) {
                low = middle + 1;
            } else {
                int result = locations.get(middle);
                int candidate = binarySearchUpperBound(locations, target, low, middle - 1);
                if (candidate != -1) {
                    result = candidate;
                }
                return result;
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
