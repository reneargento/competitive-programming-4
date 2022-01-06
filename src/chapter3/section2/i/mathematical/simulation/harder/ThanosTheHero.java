package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/01/22.
 */
public class ThanosTheHero {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] worlds = new int[FastReader.nextInt()];

        for (int i = 0; i < worlds.length; i++) {
            worlds[i] = FastReader.nextInt();
        }
        long livesTaken = computeLivesTaken(worlds);
        outputWriter.printLine(livesTaken);

        outputWriter.flush();
    }

    private static long computeLivesTaken(int[] worlds) {
        long livesTaken = 0;

        for (int i = worlds.length - 2; i >= 0; i--) {
            long difference = worlds[i] - worlds[i + 1];
            if (difference >= 0) {
                livesTaken += difference + 1;
                worlds[i] -= difference + 1;

                if (worlds[i] < 0) {
                    return 1;
                }
            }
        }
        return livesTaken;
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
