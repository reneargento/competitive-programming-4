package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/02/21.
 */
public class JudgingTroubles {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int submissions = FastReader.nextInt();
        Map<String, Integer> DOMJudgeResults = getResults(submissions);
        Map<String, Integer> kattisResults = getResults(submissions);
        int maxEqualResults = getMaxEqualResults(DOMJudgeResults, kattisResults);
        System.out.println(maxEqualResults);
    }

    private static Map<String, Integer> getResults(int submissions) throws IOException {
        Map<String, Integer> results = new HashMap<>();

        for (int i = 0; i < submissions; i++) {
            String result = FastReader.next();
            int frequency = results.getOrDefault(result, 0);
            int newFrequency = frequency + 1;
            results.put(result, newFrequency);
        }
        return results;
    }

    private static int getMaxEqualResults(Map<String, Integer> DOMJudgeResults, Map<String, Integer> kattisResults) {
        int maxEqualResults = 0;

        for (String result : DOMJudgeResults.keySet()) {
            int frequencyOnDOMJudge = DOMJudgeResults.get(result);
            int frequencyOnKattis = kattisResults.getOrDefault(result, 0);
            maxEqualResults += Math.min(frequencyOnDOMJudge, frequencyOnKattis);
        }
        return maxEqualResults;
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
}
