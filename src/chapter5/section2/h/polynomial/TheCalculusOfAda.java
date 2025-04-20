package chapter5.section2.h.polynomial;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/04/25.
 */
public class TheCalculusOfAda {

    private static class Result {
        int minimumDegree;
        int nextValue;

        public Result(int minimumDegree, int nextValue) {
            this.minimumDegree = minimumDegree;
            this.nextValue = nextValue;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] evaluations = new int[FastReader.nextInt()];
        for (int i = 0; i < evaluations.length; i++) {
            evaluations[i] = FastReader.nextInt();
        }

        Result result = analyzeEvaluations(evaluations);
        outputWriter.printLine(result.minimumDegree + " " + result.nextValue);
        outputWriter.flush();
    }

    private static Result analyzeEvaluations(int[] evaluations) {
        int minimumDegree = 1;
        Deque<Integer> lastElementsStack = new ArrayDeque<>();

        while (true) {
            boolean areAllDifferencesEqual = true;
            int[] nextEvaluations = new int[evaluations.length - 1];
            nextEvaluations[0] = evaluations[1] - evaluations[0];
            lastElementsStack.push(evaluations[evaluations.length - 1]);

            for (int i = 2; i < evaluations.length; i++) {
                int difference = evaluations[i] - evaluations[i - 1];

                if (difference != nextEvaluations[i - 2]) {
                    areAllDifferencesEqual = false;
                }
                nextEvaluations[i - 1] = difference;
            }

            if (areAllDifferencesEqual) {
                int nextValue = nextEvaluations[nextEvaluations.length - 1];
                while (!lastElementsStack.isEmpty()) {
                    nextValue += lastElementsStack.pop();
                }
                return new Result(minimumDegree, nextValue);
            }
            evaluations = nextEvaluations;
            minimumDegree++;
        }
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
