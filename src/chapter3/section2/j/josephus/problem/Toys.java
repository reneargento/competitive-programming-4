package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/01/22.
 */
public class Toys {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int toys = FastReader.nextInt();
        int skipLength = FastReader.nextInt();
        int targetPosition = josephus(toys, skipLength);
        outputWriter.printLine(targetPosition);
        outputWriter.flush();
    }

    private static int josephus(int circleSize, int skip) {
        int position = 0;
        int currentCircleSize = 2;

        for (int i = 1; i < circleSize; i++) {
            position = (position + skip) % currentCircleSize;
            currentCircleSize++;
        }
        return position;
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
