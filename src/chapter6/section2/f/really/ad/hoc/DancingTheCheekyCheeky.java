package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/07/2026.
 */
public class DancingTheCheekyCheeky {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String steps = FastReader.getLine();
            String remainingSteps = computeRemainingSteps(steps);
            outputWriter.printLine(remainingSteps);
        }
        outputWriter.flush();
    }

    private static String computeRemainingSteps(String steps) {
        for (int length = 1; length <= steps.length() / 2; length++) {
            String danceSteps = steps.substring(0, length);
            String doubleSequence = danceSteps + danceSteps;
            int remainingLength = steps.length() - doubleSequence.length();

            if (steps.startsWith(doubleSequence) && length > remainingLength) {
                StringBuilder remainingSteps = new StringBuilder();
                int endIndex = Math.min(danceSteps.length(), remainingLength + 8);
                remainingSteps.append(danceSteps, remainingLength, endIndex);

                int stepsMissing = 8 - remainingSteps.length();
                for (int i = 0; i < stepsMissing; i++) {
                    int danceIndex = i % danceSteps.length();
                    remainingSteps.append(danceSteps.charAt(danceIndex));
                }
                return remainingSteps.append("...").toString();
            }
        }
        return null;
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

        public void flush() {
            writer.flush();
        }
    }
}