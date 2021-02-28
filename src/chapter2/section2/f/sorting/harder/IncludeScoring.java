package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/02/21.
 */
public class IncludeScoring {

    private static class Contestant implements Comparable<Contestant> {
        int id;
        int problemsSolved;
        int timePenalty;
        int lastAcceptedSolution;
        int participatedOnSite;
        int score;

        public Contestant(int id, int problemsSolved, int timePenalty, int lastAcceptedSolution,
                          int participatedOnSite) {
            this.id = id;
            this.problemsSolved = problemsSolved;
            this.timePenalty = timePenalty;
            this.lastAcceptedSolution = lastAcceptedSolution;
            this.participatedOnSite = participatedOnSite;
        }

        public boolean hasSameRank(Contestant other) {
            return problemsSolved == other.problemsSolved
                    && timePenalty == other.timePenalty
                    && lastAcceptedSolution == other.lastAcceptedSolution;
        }

        @Override
        public int compareTo(Contestant other) {
            if (problemsSolved != other.problemsSolved) {
                return other.problemsSolved - problemsSolved;
            }
            if (timePenalty != other.timePenalty) {
                return timePenalty - other.timePenalty;
            }
            if (lastAcceptedSolution != other.lastAcceptedSolution) {
                return lastAcceptedSolution - other.lastAcceptedSolution;
            }
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int[] scorePerRank = {100, 75, 60, 50, 45, 40, 36, 32, 29, 26, 24, 22, 20, 18, 16, 15, 14, 13, 12, 11,
                10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int contestantsNumber = FastReader.nextInt();
        Contestant[] contestants = new Contestant[contestantsNumber];

        for (int i = 0; i < contestants.length; i++) {
            int problemsSolved = FastReader.nextInt();
            int timePenalty = FastReader.nextInt();
            int lastAcceptedSolution = FastReader.nextInt();
            int participatedOnSite = FastReader.nextInt();

            Contestant contestant = new Contestant(i, problemsSolved, timePenalty, lastAcceptedSolution,
                    participatedOnSite);
            contestants[i] = contestant;
        }

        assignScores(contestants, scorePerRank);
        printFinalScores(contestants);
    }

    private static void assignScores(Contestant[] contestants, int[] scorePerRank) {
        Arrays.sort(contestants);

        for (int i = 0; i < contestants.length && i < 30; i++) {
            Contestant contestant = contestants[i];
            int totalScore = scorePerRank[i];
            double contestantsWithSameRank = 1;

            for (int j = i + 1; j < contestants.length; j++) {
                if (contestants[j].hasSameRank(contestant)) {
                    contestantsWithSameRank++;

                    if (j < 30) {
                        totalScore += scorePerRank[j];
                    }
                } else {
                    break;
                }
            }

            int score = (int) Math.ceil(totalScore / contestantsWithSameRank);

            for (int j = i; j < i + contestantsWithSameRank; j++) {
                contestants[j].score = score;
            }
            i += (contestantsWithSameRank - 1);
        }
    }

    private static void printFinalScores(Contestant[] contestants) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        Arrays.sort(contestants, new Comparator<Contestant>() {
            @Override
            public int compare(Contestant contestant1, Contestant contestant2) {
                return contestant1.id - contestant2.id;
            }
        });

        for (Contestant contestant : contestants) {
            int finalScore = contestant.score + contestant.participatedOnSite;
            outputWriter.printLine(finalScore);
        }
        outputWriter.flush();
        outputWriter.close();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
