package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/01/22.
 */
public class JustAnotherProblem {

    private static final int MOD = 100000007;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long soldiers = FastReader.nextLong();

        while (soldiers != 0) {
            computeMissingSoldiers(soldiers, outputWriter);
            soldiers = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    // soldiers + 2 * squareLength^2 = (3 * thickness + 2 * squareLength) * (2 * thickness + squareLength)
    // soldiers = 6 * thickness^2 + 7 * thickness * squareLength
    // squareLength = (soldiers - 6 * thickness^2) / (7 * thickness)
    private static void computeMissingSoldiers(long soldiers, OutputWriter outputWriter) {
        boolean possible = false;

        for (long thickness = 1; 6 * thickness * thickness < soldiers; thickness++) {
            long dividend = soldiers - 6 * thickness * thickness;
            long divisor = 7 * thickness;

            if (dividend % divisor == 0) {
                possible = true;
                long squareLength = (dividend / divisor) % MOD;
                long missingSoldiers = (2 * squareLength * squareLength) % MOD;
                outputWriter.printLine(String.format("Possible Missing Soldiers = %d", missingSoldiers));
            }
        }

        if (!possible) {
            outputWriter.printLine("No Solution Possible");
        }
        outputWriter.printLine();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
