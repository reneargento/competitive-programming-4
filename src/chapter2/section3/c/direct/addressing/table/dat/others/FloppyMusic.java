package chapter2.section3.c.direct.addressing.table.dat.others;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/05/21.
 */
// Based on https://nordic.icpc.io/ncpc2015/ncpc2015slides.pdf
public class FloppyMusic {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int frequencies = FastReader.nextInt();
        boolean possibleToPlay = true;

        for (int f = 0; f < frequencies; f++) {
            int floppysecondsToMove = FastReader.nextInt();
            int intervals = FastReader.nextInt();

            boolean[] time = new boolean[floppysecondsToMove + 1];
            boolean[] nextTime = new boolean[floppysecondsToMove + 1];

            Arrays.fill(time, true);

            for (int i = 0; i < intervals; i++) {
                int start = FastReader.nextInt();
                int end = FastReader.nextInt();
                int length = end - start;
                boolean possible = false;

                for (int t = 0; t < time.length; t++) {
                    boolean leftCheck;
                    boolean rightCheck;

                    if (t - length >= 0) {
                        leftCheck = time[t - length];
                    } else {
                        leftCheck = false;
                    }

                    if (t + length < time.length) {
                        rightCheck = time[t + length];
                    }
                    else {
                        rightCheck = false;
                    }

                    nextTime[t] = leftCheck | rightCheck;
                    possible |= nextTime[t];
                }

                if (!possible) {
                    possibleToPlay = false;
                    break;
                }
                boolean[] aux = time;
                time = nextTime;
                nextTime = aux;
            }
            if (!possibleToPlay) {
                break;
            }
        }

        if (possibleToPlay) {
            outputWriter.printLine("possible");
        } else {
            outputWriter.printLine("impossible");
        }
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
