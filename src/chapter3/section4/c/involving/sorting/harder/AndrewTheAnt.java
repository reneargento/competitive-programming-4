package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/07/22.
 */
public class AndrewTheAnt {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int length = Integer.parseInt(data[0]);
            int[] ants = new int[Integer.parseInt(data[1])];
            int furthestAntMovingLeft = -1;
            int furthestAntMovingRight = -1;
            int antsMovingLeft = 0;

            for (int i = 0; i < ants.length; i++) {
                ants[i] = FastReader.nextInt();
                char direction = FastReader.next().charAt(0);
                if (direction == 'L') {
                    antsMovingLeft++;
                    furthestAntMovingLeft = Math.max(furthestAntMovingLeft, ants[i]);
                } else {
                    int position = length - ants[i];
                    furthestAntMovingRight = Math.max(furthestAntMovingRight, position);
                }
            }

            Arrays.sort(ants);
            outputWriter.print(String.format("The last ant will fall down in %d seconds - started at ",
                    Math.max(furthestAntMovingLeft, furthestAntMovingRight)));
            if (furthestAntMovingLeft == furthestAntMovingRight) {
                outputWriter.printLine(String.format("%d and %d.", ants[antsMovingLeft - 1], ants[antsMovingLeft]));
            } else {
                int lastAntToFall;
                if (furthestAntMovingLeft > furthestAntMovingRight) {
                    lastAntToFall = ants[antsMovingLeft - 1];
                } else {
                    lastAntToFall = ants[antsMovingLeft];
                }
                outputWriter.printLine(String.format("%d.", lastAntToFall));
            }
            line = FastReader.getLine();
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
