package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/03/21.
 */
public class JollybeeTournament {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int participantsPower = FastReader.nextInt();
            int participantsNumber = (int) Math.pow(2, participantsPower);
            int withdrawnNumber = FastReader.nextInt();

            boolean[] withdrawals = new boolean[participantsNumber];

            for (int i = 0; i < withdrawnNumber; i++) {
                int withdrawnId = FastReader.nextInt() - 1;
                withdrawals[withdrawnId] = true;
            }

            int walkovers = countWalkovers(withdrawals);
            System.out.println(walkovers);
        }
    }

    private static int countWalkovers(boolean[] withdrawals) {
        if (withdrawals.length == 1) {
            return 0;
        }

        int walkovers = 0;
        boolean[] nextRoundWithdrawals = new boolean[withdrawals.length / 2];

        for (int i = 0; i < withdrawals.length; i += 2) {
            if ((withdrawals[i] && !withdrawals[i + 1])
                    || (!withdrawals[i] && withdrawals[i + 1])) {
                walkovers++;
            }

            int nextRoundId = i / 2;
            if (withdrawals[i] && withdrawals[i + 1]) {
                nextRoundWithdrawals[nextRoundId] = true;
            }
        }
        return walkovers + countWalkovers(nextRoundWithdrawals);
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
}
