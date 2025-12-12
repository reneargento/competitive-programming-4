package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/12/25.
 */
public class CombinationOnceAgain {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int objects = FastReader.nextInt();
        int queries = FastReader.nextInt();
        int caseId = 1;

        while (objects != 0) {
            int maxFrequency = 0;
            Map<Integer, Integer> frequencyMap = new HashMap<>();
            for (int i = 0; i < objects; i++) {
                int value = FastReader.nextInt();
                if (!frequencyMap.containsKey(value)) {
                    frequencyMap.put(value, 0);
                }
                int newFrequency = frequencyMap.get(value) + 1;
                frequencyMap.put(value, newFrequency);
                maxFrequency = Math.max(maxFrequency, newFrequency);
            }

            outputWriter.printLine(String.format("Case %d:", caseId));
            for (int r = 0; r < queries; r++) {
                int query = FastReader.nextInt();
                long groups = computeGroups(frequencyMap, maxFrequency, query);
                outputWriter.printLine(groups);
            }

            caseId++;
            objects = FastReader.nextInt();
            queries = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeGroups(Map<Integer, Integer> frequencyMap, int maxFrequency, int query) {
        int[] frequencyArray = buildFrequencyArray(frequencyMap);
        int distinctGroups = frequencyArray.length;

        // dp[group index][current group remaining][total objects remaining] = number of ways
        long[][][] dp = new long[distinctGroups + 1][maxFrequency + 1][query + 1];
        for (long[][] dpValues : dp) {
            for (long[] values : dpValues) {
                Arrays.fill(values, -1);
            }
        }
        return computeGroups(dp, frequencyArray, 0, query);
    }

    private static long computeGroups(long[][][] dp, int[] frequencyArray, int index, int objectsRemaining) {
        if (index == frequencyArray.length || frequencyArray[index] < 0) {
            return 0;
        }
        if (objectsRemaining == 0) {
            return 1;
        }
        int currentFrequency = frequencyArray[index];
        if (dp[index][currentFrequency][objectsRemaining] != -1) {
            return dp[index][currentFrequency][objectsRemaining];
        }

        frequencyArray[index]--;
        long result =
                computeGroups(dp, frequencyArray, index + 1, objectsRemaining)
                + computeGroups(dp, frequencyArray, index, objectsRemaining - 1);
        frequencyArray[index]++;

        dp[index][currentFrequency][objectsRemaining] = result;
        return result;
    }

    private static int[] buildFrequencyArray(Map<Integer, Integer> frequencyMap) {
        int[] frequencyArray = new int[frequencyMap.size()];
        int index = 0;

        for (int value : frequencyMap.values()) {
            frequencyArray[index] = value;
            index++;
        }
        return frequencyArray;
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
