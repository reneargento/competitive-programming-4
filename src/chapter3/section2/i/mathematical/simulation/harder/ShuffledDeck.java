package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;

/**
 * Created by Rene Argento on 15/01/22.
 */
public class ShuffledDeck {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int cards = Integer.parseInt(line);
            int shufflesNeeded = computeShufflesNeeded(cards);
            outputWriter.printLine(shufflesNeeded);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeShufflesNeeded(int cards) {
        int positionOf1 = 2;

        int shuffles = 1;
        int half = cards / 2;

        while (positionOf1 != 1) {
            if (positionOf1 <= half) {
                positionOf1 = (positionOf1 - 1) * 2 + 2;
            } else {
                int numbersBeforeHalf = positionOf1 - half;
                positionOf1 = (numbersBeforeHalf - 1) * 2 + 1;
            }
            shuffles++;
        }
        return shuffles;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
