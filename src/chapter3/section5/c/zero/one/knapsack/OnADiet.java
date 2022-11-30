package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/11/22.
 */
public class OnADiet {

    private static final int INFINITE = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int minimumCalories = FastReader.nextInt() / 10;
            int totalPossibleCalories = 0;
            int[] courseCalories = new int[FastReader.nextInt()];
            for (int i = 0; i < courseCalories.length; i++) {
                courseCalories[i] = FastReader.nextInt() / 10;
                totalPossibleCalories += courseCalories[i];
            }

            int totalCalories = selectCalories(courseCalories, minimumCalories, totalPossibleCalories);
            if (totalCalories != INFINITE) {
                outputWriter.printLine(totalCalories * 10);
            } else {
                outputWriter.printLine("NO SOLUTION");
            }
        }
        outputWriter.flush();
    }

    private static int selectCalories(int[] courseCalories, int minimumCalories, int totalPossibleCalories) {
        int[][] dp = new int[courseCalories.length][totalPossibleCalories + 1];
        for (int[] calories : dp) {
            Arrays.fill(calories, -1);
        }
        return selectCalories(courseCalories, minimumCalories, dp, 0, 0);
    }

    private static int selectCalories(int[] courseCalories, int minimumCalories, int[][] dp, int courseId,
                                      int currentCalories) {
        if (courseId == courseCalories.length) {
            if (currentCalories >= minimumCalories) {
                return currentCalories;
            } else {
                return INFINITE;
            }
        }

        if (dp[courseId][currentCalories] != -1) {
            return dp[courseId][currentCalories];
        }
        int caloriesWithoutCourse = selectCalories(courseCalories, minimumCalories, dp, courseId + 1, currentCalories);
        int caloriesWithCourse = selectCalories(courseCalories, minimumCalories, dp, courseId + 1,
                currentCalories + courseCalories[courseId]);
        dp[courseId][currentCalories] = Math.min(caloriesWithoutCourse, caloriesWithCourse);
        return dp[courseId][currentCalories];
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
