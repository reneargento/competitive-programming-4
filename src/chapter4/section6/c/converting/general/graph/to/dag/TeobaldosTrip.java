package chapter4.section6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/06/24.
 */
@SuppressWarnings("unchecked")
public class TeobaldosTrip {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();
        int links = FastReader.nextInt();

        while (cities != 0) {
            List<Integer>[] adjacent = new List[cities];
            for (int i = 0; i < adjacent.length; i++) {
                adjacent[i] = new ArrayList<>();
            }

            for (int i = 0; i < links; i++) {
                int cityId1 = FastReader.nextInt() - 1;
                int cityId2 = FastReader.nextInt() - 1;
                adjacent[cityId1].add(cityId2);
                adjacent[cityId2].add(cityId1);
            }

            int startCityId = FastReader.nextInt() - 1;
            int endCityId = FastReader.nextInt() - 1;
            int maxDays = FastReader.nextInt();

            boolean canTeobaldoTravel = canTeobaldoTravel(adjacent, startCityId, endCityId, maxDays);
            outputWriter.printLine(canTeobaldoTravel
                    ? "Yes, Teobaldo can travel."
                    : "No, Teobaldo can not travel.");

            cities = FastReader.nextInt();
            links = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean canTeobaldoTravel(List<Integer>[] adjacent, int startCityId, int endCityId,
                                             int maxDays) {
        // dp[city id][days left]
        Boolean[][] dp = new Boolean[adjacent.length][maxDays + 1];
        return canTeobaldoTravel(adjacent, endCityId, dp, startCityId, maxDays);
    }

    private static Boolean canTeobaldoTravel(List<Integer>[] adjacent, int endCityId, Boolean[][] dp, int currentCityId,
                                             int daysLeft) {
        if (daysLeft == 0) {
            return currentCityId == endCityId;
        }
        if (dp[currentCityId][daysLeft] != null) {
            return dp[currentCityId][daysLeft];
        }

        for (int nextCityId : adjacent[currentCityId]) {
            if (canTeobaldoTravel(adjacent, endCityId, dp, nextCityId, daysLeft - 1)) {
                dp[currentCityId][daysLeft] = true;
                return dp[currentCityId][daysLeft];
            }
        }
        dp[currentCityId][daysLeft] = false;
        return dp[currentCityId][daysLeft];
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
