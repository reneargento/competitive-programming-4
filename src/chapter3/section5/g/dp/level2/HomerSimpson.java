package chapter3.section5.g.dp.level2;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rene Argento on 23/01/23.
 */
public class HomerSimpson {

    private static class Result {
        int totalBurgers;
        int beerTime;

        public Result(int totalBurgers, int beerTime) {
            this.totalBurgers = totalBurgers;
            this.beerTime = beerTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int burger1Time = Integer.parseInt(data[0]);
            int burger2Time = Integer.parseInt(data[1]);
            int totalTime = Integer.parseInt(data[2]);

            Result result = eatBurgers(burger1Time, burger2Time, totalTime);
            outputWriter.print(result.totalBurgers);
            if (result.beerTime != 0) {
                outputWriter.print(" " + result.beerTime);
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result eatBurgers(int burger1Time, int burger2Time, int totalTime) {
        // dp[timeLeft] = minimum beer time
        int[] dp = new int[10001];
        Arrays.fill(dp, -1);

        int beerTime = computeBeerTime(burger1Time, burger2Time, dp, totalTime, 0);
        int totalBurgers = computeTotalBurgers(burger1Time, burger2Time, dp, totalTime, beerTime);
        return new Result(totalBurgers, beerTime);
    }

    private static int computeBeerTime(int burger1Time, int burger2Time, int[] dp, int timeLeft, int burgersEaten) {
        if (dp[timeLeft] != -1) {
            return dp[timeLeft];
        }

        int beerTimeEatingBurger1 = timeLeft;
        if (timeLeft - burger1Time >= 0) {
            beerTimeEatingBurger1 = computeBeerTime(burger1Time, burger2Time, dp, timeLeft - burger1Time, burgersEaten + 1);
        }
        int beerTimeEatingBurger2 = timeLeft;
        if (timeLeft - burger2Time >= 0) {
            beerTimeEatingBurger2 = computeBeerTime(burger1Time, burger2Time, dp, timeLeft - burger2Time, burgersEaten + 1);
        }
        dp[timeLeft] = Math.min(beerTimeEatingBurger1, beerTimeEatingBurger2);
        return dp[timeLeft];
    }

    private static int computeTotalBurgers(int burger1Time, int burger2Time, int[] dp, int timeLeft, int beerTime) {
        int totalBurgers = 0;
        int smallestTime = Math.min(burger1Time, burger2Time);
        int highestTime = Math.max(burger1Time, burger2Time);

        while (timeLeft != beerTime) {
            if (dp[timeLeft - smallestTime] == beerTime) {
                timeLeft -= smallestTime;
            } else {
                timeLeft -= highestTime;
            }
            totalBurgers++;
        }
        return totalBurgers;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
