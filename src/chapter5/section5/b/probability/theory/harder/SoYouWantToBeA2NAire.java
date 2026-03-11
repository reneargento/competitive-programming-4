package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/03/26.
 */
public class SoYouWantToBeA2NAire {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int questions = FastReader.nextInt();
        double answerLowerProbability = FastReader.nextDouble();

        while (questions != 0 || answerLowerProbability != 0) {
            double expectedPrize = computeExpectedPrize(0, questions, answerLowerProbability);
            outputWriter.printLine(String.format("%.3f", expectedPrize));

            questions = FastReader.nextInt();
            answerLowerProbability = FastReader.nextDouble();
        }
        outputWriter.flush();
    }

    private static double computeExpectedPrize(int questionId, int questions, double answerLowerProbability) {
        if (questionId == questions) {
            return Math.pow(2, questions);
        }
        double expectedPrize = computeExpectedPrize(questionId + 1, questions, answerLowerProbability);
        double correctProbability = Math.pow(2, questionId) / expectedPrize;

        double answerProbability = 1 / (1 - answerLowerProbability);
        if (correctProbability < answerLowerProbability) {
            return answerProbability * (expectedPrize / 2 * (1 - Math.pow(answerLowerProbability, 2)));
        }
        return answerProbability * (Math.pow(2, questionId) * (correctProbability - answerLowerProbability)
                + expectedPrize / 2 * (1 - Math.pow(correctProbability, 2)));
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
