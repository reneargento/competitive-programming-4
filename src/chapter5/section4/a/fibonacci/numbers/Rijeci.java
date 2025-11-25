package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/11/25.
 */
public class Rijeci {

    private static class Result {
        int aFrequency;
        int bFrequency;

        public Result(int aFrequency, int bFrequency) {
            this.aFrequency = aFrequency;
            this.bFrequency = bFrequency;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int buttonPresses = FastReader.nextInt();
        Result lettersFrequency = computeLetters(buttonPresses);
        outputWriter.printLine(lettersFrequency.aFrequency + " " + lettersFrequency.bFrequency);
        outputWriter.flush();
    }

    private static Result computeLetters(int buttonPresses) {
        int aFrequency = 0;
        int bFrequency = 1;

        for (int i = 2; i <= buttonPresses; i++) {
            int aCopy = aFrequency;
            aFrequency = bFrequency;
            bFrequency += aCopy;
        }
        return new Result(aFrequency, bFrequency);
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
