package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/12/21.
 */
public class VideoSpeedup {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int events = FastReader.nextInt();
        int speedUp = FastReader.nextInt();
        int videoLength = FastReader.nextInt();

        int lastTimestamp = 0;
        double currentSpeed = 1;
        double originalVideoTime = 0;

        for (int e = 1; e <= events; e++) {
            int timestamp = FastReader.nextInt();
            originalVideoTime += (timestamp - lastTimestamp) * currentSpeed;
            currentSpeed = (100 + (speedUp * e)) / 100.0;
            lastTimestamp = timestamp;
        }
        originalVideoTime += (videoLength - lastTimestamp) * currentSpeed;

        outputWriter.printLine(originalVideoTime);
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
