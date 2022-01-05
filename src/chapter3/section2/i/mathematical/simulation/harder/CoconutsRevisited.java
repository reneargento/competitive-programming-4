package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class CoconutsRevisited {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int coconuts = FastReader.nextInt();

        while (coconuts >= 0) {
            int people = computePeopleInIsland(coconuts);
            outputWriter.print(String.format("%d coconuts, ", coconuts));

            if (people == -1) {
                outputWriter.printLine("no solution");
            } else {
                outputWriter.printLine(String.format("%d people and 1 monkey", people));
            }
            coconuts = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computePeopleInIsland(int coconuts) {
        int upperBound = (int) Math.ceil(Math.sqrt(coconuts));

        for (int people = upperBound; people >= 2; people--) {
            if (isPossibleScenario(coconuts, people)) {
                return people;
            }
        }
        return -1;
    }

    private static boolean isPossibleScenario(int coconuts, int people) {
        for (int i = 0; i < people; i++) {
            int remaining = coconuts % people;
            if (remaining != 1) {
                return false;
            }
            coconuts -= (coconuts / people) + 1;
        }
        return coconuts % people == 0;
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
