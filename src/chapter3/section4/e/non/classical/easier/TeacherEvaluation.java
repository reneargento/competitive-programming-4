package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 31/07/22.
 */
public class TeacherEvaluation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] scores = new int[FastReader.nextInt()];
        int targetAverage = FastReader.nextInt();

        for (int i = 0; i < scores.length; i++) {
            scores[i] = FastReader.nextInt();
        }

        int numberOfResultsToAdd = computeNumberOfResultsToAdd(scores, targetAverage);
        if (numberOfResultsToAdd == -1) {
            outputWriter.printLine("impossible");
        } else {
            outputWriter.printLine(numberOfResultsToAdd);
        }
        outputWriter.flush();
    }

    private static int computeNumberOfResultsToAdd(int[] scores, int targetAverage) {
        if (targetAverage == 100) {
            return -1;
        }

        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        int difference = (targetAverage * scores.length) - sum;
        double contributionPerExtraResult = 100.0 - targetAverage;
        return (int) Math.ceil(difference / contributionPerExtraResult);
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