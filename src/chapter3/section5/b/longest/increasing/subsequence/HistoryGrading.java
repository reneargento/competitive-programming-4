package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 09/11/22.
 */
public class HistoryGrading {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            List<String> correctAnswers = getStrings(FastReader.getLine());
            int[] eventRank = computeEventToRankArray(correctAnswers);

            line = FastReader.getLine();
            while (line != null) {
                List<String> integers = getStrings(line);
                if (integers.size() == 1) {
                    break;
                }
                int[] studentAnswers = computeStudentAnswers(integers, eventRank);
                int score = computeScore(studentAnswers);
                outputWriter.printLine(score);
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static int[] computeEventToRankArray(List<String> correctAnswers) {
        int[] eventRank = new int[correctAnswers.size() + 1];
        for (int i = 0; i < correctAnswers.size(); i++) {
            int event = Integer.parseInt(correctAnswers.get(i));
            eventRank[i + 1] = event;
        }
        return eventRank;
    }

    private static int[] computeStudentAnswers(List<String> integers, int[] eventRank) {
        int[] studentAnswers = new int[integers.size() + 1];
        for (int i = 0; i < integers.size(); i++) {
            int rank = Integer.parseInt(integers.get(i));
            int event = eventRank[i + 1];
            studentAnswers[rank] = event;
        }
        return studentAnswers;
    }

    private static int computeScore(int[] studentAnswers) {
        int score = 1;
        int[] lis = new int[studentAnswers.length - 1];
        Arrays.fill(lis, 1);

        for (int i = 0; i < lis.length; i++) {
            for (int j = i + 1; j < lis.length; j++) {
                if (studentAnswers[j + 1] > studentAnswers[i + 1] && lis[i] + 1 > lis[j]) {
                    lis[j] = lis[i] + 1;
                    score = Math.max(score, lis[j]);
                }
            }
        }
        return score;
    }

    private static List<String> getStrings(String line) {
        List<String> strings = new ArrayList<>();
        StringBuilder string = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ' || character == '\t') {
                if (string.length() > 0) {
                    strings.add(string.toString());
                    string = new StringBuilder();
                }
            } else {
                string.append(character);
            }
        }
        if (string.length() > 0) {
            strings.add(string.toString());
        }
        return strings;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
