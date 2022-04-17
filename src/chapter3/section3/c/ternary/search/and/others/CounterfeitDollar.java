package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/04/22.
 */
public class CounterfeitDollar {

    private static final int UNWEIGHTED = 0;
    private static final int LEGIT = 1;
    private static final int POSSIBLY_LIGHT = 2;
    private static final int POSSIBLY_HEAVY = 3;

    private static class Result {
        char counterfeitCoin;
        String status;

        public Result(char counterfeitCoin, String status) {
            this.counterfeitCoin = counterfeitCoin;
            this.status = status;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] coinsStatus = new int[12];

            for (int i = 0; i < 3; i++) {
                String leftSideCoins = FastReader.next();
                String rightSideCoins = FastReader.next();
                String result = FastReader.next();
                updateCoinsStatus(coinsStatus, leftSideCoins, rightSideCoins, result);
            }
            Result result = getCounterfeitCoin(coinsStatus);
            outputWriter.printLine(String.format("%s is the counterfeit coin and it is %s.",
                    result.counterfeitCoin, result.status));
        }
        outputWriter.flush();
    }

    private static void updateCoinsStatus(int[] coinsStatus, String leftSideCoins, String rightSideCoins,
                                          String result) {
        if (result.equals("even")) {
            updateStatus(coinsStatus, leftSideCoins, LEGIT, -1);
            updateStatus(coinsStatus, rightSideCoins, LEGIT, -1);
        } else {
            if (result.equals("up")) {
                updateStatus(coinsStatus, leftSideCoins, POSSIBLY_HEAVY, LEGIT);
                updateStatus(coinsStatus, rightSideCoins, POSSIBLY_LIGHT, LEGIT);
            } else {
                updateStatus(coinsStatus, leftSideCoins, POSSIBLY_LIGHT, LEGIT);
                updateStatus(coinsStatus, rightSideCoins, POSSIBLY_HEAVY, LEGIT);
            }
            updateAllOtherCoinsToLegit(coinsStatus, leftSideCoins, rightSideCoins);
        }
    }

    private static void updateStatus(int[] coinsStatus, String coins, int status, int ignoreIfStatus) {
        for (char coin : coins.toCharArray()) {
            int index = coin - 'A';
            if ((coinsStatus[index] == POSSIBLY_LIGHT && status == POSSIBLY_HEAVY)
                    || (coinsStatus[index] == POSSIBLY_HEAVY && status == POSSIBLY_LIGHT)) {
                coinsStatus[index] = LEGIT;
            } else if (coinsStatus[index] != ignoreIfStatus) {
                coinsStatus[index] = status;
            }
        }
    }

    private static void updateAllOtherCoinsToLegit(int[] coinsStatus, String leftSideCoins, String rightSideCoins) {
        Set<Character> coins = new HashSet<>();
        for (char coin : leftSideCoins.toCharArray()) {
            coins.add(coin);
        }
        for (char coin : rightSideCoins.toCharArray()) {
            coins.add(coin);
        }

        for (int i = 0; i < coinsStatus.length; i++) {
            char coin = (char) ('A' + i);
            if (!coins.contains(coin)) {
                coinsStatus[i] = LEGIT;
            }
        }
    }

    private static Result getCounterfeitCoin(int[] coinsStatus) {
        for (int i = 0; i < coinsStatus.length; i++) {
            if (coinsStatus[i] != LEGIT && coinsStatus[i] != UNWEIGHTED) {
                char counterfeitCoin = (char) ('A' + i);
                String status = coinsStatus[i] == POSSIBLY_LIGHT ? "light" : "heavy";
                return new Result(counterfeitCoin, status);
            }
        }
        return null;
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
