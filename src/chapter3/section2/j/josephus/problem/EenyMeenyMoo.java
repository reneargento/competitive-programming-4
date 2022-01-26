package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/01/22.
 */
public class EenyMeenyMoo {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numberOfCities = FastReader.nextInt();

        while (numberOfCities != 0) {
            int skipLength = computeSkipLength(numberOfCities);
            outputWriter.printLine(skipLength);
            numberOfCities = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeSkipLength(int numberOfCities) {
        int skipLength = 1;
        while (true) {
            // Subtract one considering city 0 already eliminated
            if (josephus(numberOfCities - 1, skipLength) == 0) {
                return skipLength;
            }
            skipLength++;
        }
    }

    private static int josephus(int circleSize, int skip) {
        if (circleSize == 1) {
            return 0;
        }
        return (josephus(circleSize - 1, skip) + skip) % circleSize;
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
