package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/06/24.
 */
@SuppressWarnings("unchecked")
public class TravelingPolitician {

    private static final int INFINITE = 10000000;
    private static final int MAX_DAYS = 50;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int cities = FastReader.nextInt();
        int roads = FastReader.nextInt();
        int minimumSpeeches = FastReader.nextInt();

        while (true) {
            List<Integer>[] adjacent = new List[cities];
            for (int i = 0; i < adjacent.length; i++) {
                adjacent[i] = new ArrayList<>();
            }
            for (int i = 0; i < roads; i++) {
                int cityID1 = FastReader.nextInt();
                int cityID2 = FastReader.nextInt();
                adjacent[cityID1].add(cityID2);
            }

            int totalSpeeches = computeTotalSpeeches(adjacent, minimumSpeeches);
            if (totalSpeeches > 20) {
                outputWriter.printLine("LOSER");
            } else {
                outputWriter.printLine(totalSpeeches);
            }

            cities = FastReader.nextInt();
            if (cities == 0) {
                // Required to handle UVa input issue
                break;
            }
            roads = FastReader.nextInt();
            minimumSpeeches = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeTotalSpeeches(List<Integer>[] adjacent, int minimumSpeeches) {
        // dp[day id][city id]
        int[][] dp = new int[MAX_DAYS][adjacent.length];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }
        return computeTotalSpeeches(adjacent, dp, minimumSpeeches, 0, 0);
    }

    private static int computeTotalSpeeches(List<Integer>[] adjacent, int[][] dp, int minimumSpeeches,
                                            int day, int cityId) {
        if (cityId == adjacent.length - 1
                && day >= minimumSpeeches - 1) {
            return 1;
        }
        if (day == dp.length) {
            return INFINITE;
        }
        if (dp[day][cityId] != -1) {
            return dp[day][cityId];
        }

        int speeches = INFINITE;
        for (int nextCity : adjacent[cityId]) {
            int speechesWithCity = 1 +
                    computeTotalSpeeches(adjacent, dp, minimumSpeeches, day + 1, nextCity);
            speeches = Math.min(speeches, speechesWithCity);
        }

        dp[day][cityId] = speeches;
        return speeches;
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
