package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/02/21.
 */
public class HelpCupid {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String candidatesLine = FastReader.getLine();

        while (candidatesLine != null) {
            int candidates = Integer.parseInt(candidatesLine);
            int[] timezones = new int[candidates];
            for (int i = 0; i < timezones.length; i++) {
                timezones[i] = FastReader.nextInt();
            }

            Arrays.sort(timezones);

            int timezoneDifferences = sumTimezoneDifferences(timezones);
            System.out.println(timezoneDifferences);

            candidatesLine = FastReader.getLine();
        }
    }

    private static int sumTimezoneDifferences(int[] timezones) {
        int timezoneDifferencesFromStart = sumTimezoneDifferences(timezones, 1);

        int timezoneDifferencesFromEnd = getTimezoneDifference(timezones[0], timezones[timezones.length - 1]);
        timezoneDifferencesFromEnd += sumTimezoneDifferences(timezones, 2);

        return Math.min(timezoneDifferencesFromStart, timezoneDifferencesFromEnd);
    }

    private static int sumTimezoneDifferences(int[] timezones, int startIndex) {
        int timezoneDifferences = 0;

        for (int i = startIndex; i < timezones.length; i += 2) {
            timezoneDifferences += getTimezoneDifference(timezones[i], timezones[i - 1]);
        }
        return timezoneDifferences;
    }

    private static int getTimezoneDifference(int timezone1, int timezone2) {
        int difference = Math.abs(timezone1 - timezone2);
        return Math.min(difference, 24 - difference);
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
