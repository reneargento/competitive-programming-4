package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class TennisChampionship {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            long players = Long.parseLong(line);
            long matches = computeMatches(players);
            outputWriter.printLine(matches);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeMatches(long players) {
        long matches = 0;

        while (players > 1) {
            matches += players / 2;
            int randomPlayerAdvancing = 0;

            if (players % 2 == 1) {
                randomPlayerAdvancing = 1;
            }
            players = (players / 2) + randomPlayerAdvancing;
        }
        return matches;
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
