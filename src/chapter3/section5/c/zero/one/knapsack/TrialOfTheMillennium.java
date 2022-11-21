package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Note: this problem is flagged in uDebug, it seems that the solution doesn't get accepted.
 * Source: https://www.udebug.com/UVa/431
 * I didn't find anything suspicious on my investigation other than cases of descriptions ending with 1 blank space.
 * There is no more than 1 space between any strings in the input.
 *
 * Created by Rene Argento on 20/11/22.
 */
public class TrialOfTheMillennium {

    private static class Evidence implements Comparable<Evidence> {
        int score;
        int timeRequired;
        String description;

        public Evidence(int score, int timeRequired, String description) {
            this.score = score;
            this.timeRequired = timeRequired;
            this.description = description;
        }

        @Override
        public int compareTo(Evidence other) {
            if (timeRequired != other.timeRequired) {
                return Integer.compare(timeRequired, other.timeRequired);
            }
            if (score != other.score) {
                return Integer.compare(other.score, score);
            }
            return description.compareTo(other.description);
        }
    }

    private static class Result {
        List<Evidence> selectedEvidence;
        int totalScore;
        int totalTime;

        public Result(List<Evidence> selectedEvidence, int totalScore, int totalTime) {
            this.selectedEvidence = selectedEvidence;
            this.totalScore = totalScore;
            this.totalTime = totalTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = Integer.parseInt(FastReader.getLine());
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            List<Evidence> evidenceList = new ArrayList<>();
            int timeAllowed = Integer.parseInt(FastReader.getLine());

            String line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(" ");
                int score = Integer.parseInt(data[0]);
                int timeRequired = Integer.parseInt(data[1]);
                String description = getDescription(line);
                evidenceList.add(new Evidence(score, timeRequired, description));

                line = FastReader.getLine();
            }

            Result result = chooseEvidence(evidenceList, timeAllowed);
            if (t > 0) {
                outputWriter.printLine();
            }
            if (result.selectedEvidence.isEmpty()) {
                outputWriter.printLine("There is not enough time to present any evidence. Drop the charges.");
            } else {
                outputWriter.printLine("Score  Time  Description");
                outputWriter.printLine("-----  ----  -----------");
                for (Evidence evidence : result.selectedEvidence) {
                    outputWriter.printLine(String.format("%3d    %3d   %s", evidence.score, evidence.timeRequired,
                            evidence.description));
                }
                outputWriter.printLine();
                outputWriter.printLine(String.format("Total score: %d points", result.totalScore));
                outputWriter.printLine(String.format(" Total time: %d hours", result.totalTime));
            }
        }
        outputWriter.flush();
    }

    private static String getDescription(String evidence) {
        int startIndex = 0;
        for (int i = 0; i < 2; i++) {
            startIndex = evidence.indexOf(" ", startIndex) + 1;
        }
        return evidence.substring(startIndex);
    }

    private static Result chooseEvidence(List<Evidence> evidenceList, int timeAllowed) {
        List<Evidence> selectedEvidence = new ArrayList<>();
        int totalScore = 0;
        int totalTime = 0;

        if (timeAllowed != 0) {
            int[][] dp = constructDp(evidenceList, timeAllowed);

            int currentTime = timeAllowed;
            for (int evidenceId = dp.length - 1; evidenceId > 0; evidenceId--) {
                if (dp[evidenceId][currentTime] != dp[evidenceId - 1][currentTime]) {
                    Evidence evidence = evidenceList.get(evidenceId - 1);
                    selectedEvidence.add(evidence);
                    totalTime += evidence.timeRequired;
                    totalScore += evidence.score;
                    currentTime -= evidence.timeRequired;
                }
            }
        } else {
            for (Evidence evidence : evidenceList) {
                if (evidence.timeRequired == 0) {
                    selectedEvidence.add(evidence);
                    totalScore += evidence.score;
                }
            }
        }
        Collections.sort(selectedEvidence);
        return new Result(selectedEvidence, totalScore, totalTime);
    }

    private static int[][] constructDp(List<Evidence> evidenceList, int timeAllowed) {
        int[][] dp = new int[evidenceList.size() + 1][timeAllowed + 1];

        for (int evidenceId = 1; evidenceId < dp.length; evidenceId++) {
            Evidence evidence = evidenceList.get(evidenceId - 1);
            for (int time = 1; time < dp[0].length; time++) {
                int scoreWithoutEvidence = dp[evidenceId - 1][time];
                int scoreWithEvidence = 0;
                if (time - evidence.timeRequired >= 0) {
                    scoreWithEvidence = dp[evidenceId - 1][time - evidence.timeRequired] + evidence.score;
                }
                dp[evidenceId][time] = Math.max(scoreWithoutEvidence, scoreWithEvidence);
            }
        }
        return dp;
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
