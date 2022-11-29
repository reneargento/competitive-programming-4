package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/11/22.
 */
public class TermStrategy {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int courses = FastReader.nextInt();
            int timeAvailable = FastReader.nextInt();

            int[][] studyTime = new int[courses][timeAvailable];
            for (int course = 0; course < studyTime.length; course++) {
                for (int time = 0; time < studyTime[course].length; time++) {
                    studyTime[course][time] = FastReader.nextInt();
                }
            }

            double maximumAverageMark = computeMaximumAverageMark(studyTime);
            if (maximumAverageMark != -1) {
                outputWriter.printLine(String.format("Maximal possible average mark - %.2f.", maximumAverageMark));
            } else {
                outputWriter.printLine("Peter, you shouldn't have played billiard that much.");
            }
        }
        outputWriter.flush();
    }

    private static double computeMaximumAverageMark(int[][] studyTime) {
        int timeAvailable = studyTime[0].length;
        // dp[courseId][hoursUsed] = maximum_mark
        int[][] dp = new int[studyTime.length][timeAvailable + 1];

        boolean hasTime = false;
        // Base case
        for (int examId = 0; examId < studyTime[0].length; examId++) {
            int time = examId + 1;
            if (studyTime[0][examId] >= 5 && time <= timeAvailable) {
                dp[0][time] = studyTime[0][examId];
                hasTime = true;
            }
        }
        for (int time = studyTime[0].length; time < dp[0].length; time++) {
            dp[0][time] = studyTime[0][studyTime[0].length - 1];
        }

        if (!hasTime) {
            return -1;
        }

        for (int courseId = 1; courseId < dp.length; courseId++) {
            hasTime = false;
            for (int hour = 1; hour < dp[0].length; hour++) {
                for (int examId = 0; examId < studyTime[courseId].length; examId++) {
                    int time = examId + 1;
                    if (studyTime[courseId][examId] >= 5 && hour - time >= 0
                            && dp[courseId - 1][hour - time] > 0) {
                        int marksWithoutExam = dp[courseId][hour];
                        int marksWithExam = dp[courseId - 1][hour - time] + studyTime[courseId][examId];
                        dp[courseId][hour] = Math.max(marksWithoutExam, marksWithExam);
                        hasTime = true;
                    }
                }

            }
            if (!hasTime) {
                return -1;
            }
        }
        return dp[studyTime.length - 1][timeAvailable] / (double) studyTime.length;
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