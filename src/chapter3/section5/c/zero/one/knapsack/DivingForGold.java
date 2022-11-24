package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/11/22.
 */
public class DivingForGold {

    private static class Treasure {
        int depth;
        int gold;
        int timeToGet;

        public Treasure(int depth, int gold, int timeToGet) {
            this.depth = depth;
            this.gold = gold;
            this.timeToGet = timeToGet;
        }
    }

    private static class Result {
        long totalGold;
        List<Treasure> treasureList;

        public Result(long totalGold, List<Treasure> treasureList) {
            this.totalGold = totalGold;
            this.treasureList = treasureList;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int testId = 0;
        String line = FastReader.getLine();
        while (line != null) {
            if (line.isEmpty()) {
                line = FastReader.getLine();
            }

            String[] data = line.split(" ");
            int totalSeconds = Integer.parseInt(data[0]);
            int multiplier = Integer.parseInt(data[1]);

            Treasure[] treasures = new Treasure[FastReader.nextInt()];
            for (int i = 0; i < treasures.length; i++) {
                int depth = FastReader.nextInt();
                int timeToGet = multiplier * depth + 2 * multiplier * depth;
                treasures[i] = new Treasure(depth, FastReader.nextInt(), timeToGet);
            }

            if (testId > 0) {
                outputWriter.printLine();
            }
            Result result = getMaximumGold(treasures, totalSeconds);
            outputWriter.printLine(result.totalGold);
            outputWriter.printLine(result.treasureList.size());
            for (Treasure treasure : result.treasureList) {
                outputWriter.printLine(String.format("%d %d", treasure.depth, treasure.gold));
            }

            testId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result getMaximumGold(Treasure[] treasures, int totalSeconds) {
        int[][] dp = new int[treasures.length + 1][totalSeconds + 1];

        for (int treasureId = 1; treasureId < dp.length; treasureId++) {
            for (int second = 1; second < dp[0].length; second++) {
                int goldWithoutTreasure = dp[treasureId - 1][second];
                int goldWithTreasure = 0;
                if (second >= treasures[treasureId - 1].timeToGet) {
                    goldWithTreasure = dp[treasureId - 1][second - treasures[treasureId - 1].timeToGet]
                            + treasures[treasureId - 1].gold;
                }
                dp[treasureId][second] = Math.max(goldWithoutTreasure, goldWithTreasure);
            }
        }

        long totalGold = 0;
        List<Treasure> treasureList = new ArrayList<>();

        int currentSeconds = totalSeconds;
        for (int treasureId = dp.length - 1; treasureId > 0; treasureId--) {
            Treasure treasure = treasures[treasureId - 1];
            if (dp[treasureId][currentSeconds] != dp[treasureId - 1][currentSeconds]) {
                totalGold += treasure.gold;
                treasureList.add(treasure);
                currentSeconds -= treasure.timeToGet;
            }
        }
        Collections.reverse(treasureList);
        return new Result(totalGold, treasureList);
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

        public void flush() {
            writer.flush();
        }
    }
}
