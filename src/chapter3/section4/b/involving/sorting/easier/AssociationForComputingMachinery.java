package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/07/22.
 */
public class AssociationForComputingMachinery {

    private static final int MAX_TIME = 300;

    private static class Result {
        int problemsSolved;
        int penaltyTime;

        public Result(int problemsSolved, int penaltyTime) {
            this.problemsSolved = problemsSolved;
            this.penaltyTime = penaltyTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] timesToSolve = new int[FastReader.nextInt() - 1];
        int timeToSolveIndex = 0;
        int firstProblemIndex = FastReader.nextInt();
        int firstProblemTime = 0;

        for (int i = 0; i < timesToSolve.length + 1; i++) {
            if (i == firstProblemIndex) {
                firstProblemTime = FastReader.nextInt();
            } else {
                timesToSolve[timeToSolveIndex] = FastReader.nextInt();
                timeToSolveIndex++;
            }
        }
        Result result = compete(timesToSolve, firstProblemTime);
        outputWriter.printLine(String.format("%d %d", result.problemsSolved, result.penaltyTime));
        outputWriter.flush();
    }

    private static Result compete(int[] timesToSolve, int firstProblemTime) {
        int problemsSolved = 0;
        int penaltyTime = 0;
        int currentTime;

        if (firstProblemTime <= MAX_TIME) {
            problemsSolved++;
            penaltyTime += firstProblemTime;
            currentTime = firstProblemTime;
        } else {
            return new Result(problemsSolved, penaltyTime);
        }

        Arrays.sort(timesToSolve);
        for (int time : timesToSolve) {
            if (currentTime + time <= MAX_TIME) {
                problemsSolved++;
                currentTime += time;
                penaltyTime += currentTime;
            } else {
                break;
            }
        }
        return new Result(problemsSolved, penaltyTime);
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
