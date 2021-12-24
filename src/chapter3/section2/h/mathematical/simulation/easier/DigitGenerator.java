package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/12/21.
 */
public class DigitGenerator {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            int generator = computeSmallestGenerator(number);
            outputWriter.printLine(generator);
        }
        outputWriter.flush();
    }

    private static int computeSmallestGenerator(int number) {
        int digitLength = String.valueOf(number).length();
        int range = 9 * digitLength;
        int startCandidate = Math.max(number - range, 0);
        int endCandidate = number + range;

        for (int candidate = startCandidate; candidate <= endCandidate; candidate++) {
            int candidateCopy = candidate;
            int digitSum = candidateCopy;

            while (candidateCopy > 0) {
                digitSum += candidateCopy % 10;
                candidateCopy /= 10;
            }

            if (digitSum == number) {
                return candidate;
            }
        }
        return 0;
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
