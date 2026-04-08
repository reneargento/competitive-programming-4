package chapter5.section7;

import java.io.*;

/**
 * Created by Rene Argento on 05/04/26.
 */
public class AMultiplicationGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            long target = Long.parseLong(line);
            outputWriter.print(canWin(0, 1, target) ? "Stan" : "Ollie");
            outputWriter.printLine(" wins.");
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean canWin(int playerId, long p, long target) {
        if (p >= target) {
            return false;
        }

        int nextPlayerId = (playerId + 1) % 2;
        if (playerId == 0) {
            return !canWin(nextPlayerId, p * 9, target);
        } else {
            return !canWin(nextPlayerId, p * 2, target);
        }
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

        public void flush() {
            writer.flush();
        }
    }
}
