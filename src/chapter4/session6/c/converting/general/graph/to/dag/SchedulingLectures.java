package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 03/06/24.
 */
public class SchedulingLectures {

    private static class Result {
        int lectures;
        int totalDissatisfactionIndex;

        public Result(int lectures, int totalDissatisfactionIndex) {
            this.lectures = lectures;
            this.totalDissatisfactionIndex = totalDissatisfactionIndex;
        }
    }

    private static final int INFINITE = 1000000000;

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int topics = inputReader.nextInt();
        int caseNumber = 1;

        while (topics != 0) {
            int lectureLength = inputReader.nextInt();
            int constant = inputReader.nextInt();

            int[] topicMinutes = new int[topics];
            for (int i = 0; i < topicMinutes.length; i++) {
                topicMinutes[i] = inputReader.nextInt();
            }

            Result result = computeSchedule(topicMinutes, lectureLength, constant);
            if (caseNumber > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Case %d:", caseNumber));
            outputWriter.printLine(String.format("Minimum number of lectures: %d", result.lectures));
            outputWriter.printLine(String.format("Total dissatisfaction index: %d", result.totalDissatisfactionIndex));

            caseNumber++;
            topics = inputReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeSchedule(int[] topicMinutes, int lectureLength, int constant) {
        // dp[current topic][current lecture]
        Result[][] dp = new Result[topicMinutes.length][topicMinutes.length + 1];
        return computeSchedule(topicMinutes, lectureLength, constant, dp, 0, 0);
    }

    private static Result computeSchedule(int[] topicMinutes, int lectureLength, int constant, Result[][] dp,
                                          int topicId, int lectureId) {
        if (topicId == topicMinutes.length) {
            return new Result(0, 0);
        }
        if (dp[topicId][lectureId] != null) {
            return dp[topicId][lectureId];
        }

        int minLectures = INFINITE;
        int minDissatisfactionIndex = INFINITE;
        int totalMinutes = 0;

        for (int nextTopicId = topicId; nextTopicId < topicMinutes.length; nextTopicId++) {
            totalMinutes += topicMinutes[nextTopicId];
            if (totalMinutes > lectureLength) {
                break;
            }

            int freeTime = lectureLength - totalMinutes;
            int nextDissatisfaction = computeDissatisfaction(freeTime, constant);

            Result result = computeSchedule(topicMinutes, lectureLength, constant, dp, nextTopicId + 1,
                    lectureId + 1);
            int candidateLectures = 1 + result.lectures;
            int candidateDissatisfaction = nextDissatisfaction + result.totalDissatisfactionIndex;
            if (candidateLectures < minLectures
                    || (candidateLectures == minLectures && candidateDissatisfaction < minDissatisfactionIndex)) {
                minLectures = candidateLectures;
                minDissatisfactionIndex = candidateDissatisfaction;
            }
        }

        dp[topicId][lectureId] = new Result(minLectures, minDissatisfactionIndex);
        return dp[topicId][lectureId];
    }

    private static int computeDissatisfaction(int freeTime, int constant) {
        if (freeTime == 0) {
            return 0;
        }
        if (1 <= freeTime && freeTime <= 10) {
            return -constant;
        }
        return (freeTime - 10) * (freeTime - 10);
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
