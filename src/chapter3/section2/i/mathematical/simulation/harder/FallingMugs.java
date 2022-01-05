package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/01/22.
 */
// Based on https://challenge.csc.kth.se/2014/solutions.pdf
public class FallingMugs {

    private static class Frames {
        int frame1;
        int frame2;

        public Frames(int frame1, int frame2) {
            this.frame1 = frame1;
            this.frame2 = frame2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int distance = FastReader.nextInt();
        Frames frames = computeFrames(distance);
        if (frames != null) {
            outputWriter.printLine(frames.frame1 + " " + frames.frame2);
        } else {
            outputWriter.printLine("impossible");
        }
        outputWriter.flush();
    }

    // (frame + x)^2 - frame^2 = distance
    // (n + x)^2 - n^2 = d
    // n^2 + nx + nx + x^2 - n^2 = d
    // 2nx + x^2 = d
    // 2nx = d - x^2
    // n = (d - x^2) / 2x
    private static Frames computeFrames(int distance) {
        int upperBound = (int) Math.sqrt(distance);

        for (int x = 1; x <= upperBound; x++) {
            int dividend = distance - x * x;
            int divisor = 2 * x;

            if (dividend % divisor == 0) {
                int frame1 = dividend / divisor;
                int frame2 = frame1 + x;
                return new Frames(frame1, frame2);
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
