package chapter5.section2.a.finding.simple.formula.or.pattern.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/12/24.
 */
public class CombinationLock {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            FastReader.nextInt(); // Unused value
            String initialState = FastReader.next();
            String targetState = FastReader.next();

            int minimumSteps = computeMinimumSteps(initialState, targetState);
            outputWriter.printLine(String.format("Case %d: %d", t, minimumSteps));
        }
        outputWriter.flush();
    }

    private static int computeMinimumSteps(String initialState, String targetState) {
        int minimumSteps = 0;
        for (int i = 0; i < initialState.length(); i++) {
            int initialValue = Character.getNumericValue(initialState.charAt(i));
            int targetValue = Character.getNumericValue(targetState.charAt(i));

            int stepsClockwise;
            int stepsCounterClockwise;
            if (targetValue > initialValue) {
                stepsClockwise = targetValue - initialValue;
                stepsCounterClockwise = initialValue + (10 - targetValue);
            } else {
                stepsClockwise = (10 - initialValue) + targetValue;
                stepsCounterClockwise = initialValue - targetValue;
            }
            minimumSteps += Math.min(stepsClockwise, stepsCounterClockwise);
        }
        return minimumSteps;
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
