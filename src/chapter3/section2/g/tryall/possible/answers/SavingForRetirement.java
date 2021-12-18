package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/12/21.
 */
public class SavingForRetirement {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int bobAge = FastReader.nextInt();
        int bobRetirementAge = FastReader.nextInt();
        int bobYearlySaving = FastReader.nextInt();
        int aliceAge = FastReader.nextInt();
        int aliceYearlySaving = FastReader.nextInt();

        int bobMoney = (bobRetirementAge - bobAge) * bobYearlySaving;
        int requiredYearsToPassBob = (bobMoney / aliceYearlySaving) + 1;

        int aliceRetirementAge = aliceAge + requiredYearsToPassBob;
        outputWriter.printLine(aliceRetirementAge);
        outputWriter.flush();
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