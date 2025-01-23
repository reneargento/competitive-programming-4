package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/01/25.
 */
public class LooRolls {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long rollLength = FastReader.nextLong();
        long centimetersUsed = FastReader.nextLong();

        int rollsNeeded = computeRollsNeeded(rollLength, centimetersUsed);
        outputWriter.printLine(rollsNeeded);
        outputWriter.flush();
    }

    private static int computeRollsNeeded(long rollLength, long centimetersUsed) {
        if (rollLength % centimetersUsed == 0) {
            return 1;
        }
        long nextCentimeters = rollLength % centimetersUsed;
        return 1 + computeRollsNeeded(rollLength, centimetersUsed - nextCentimeters);
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
