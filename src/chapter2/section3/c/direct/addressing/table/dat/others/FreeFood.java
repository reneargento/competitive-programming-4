package chapter2.section3.c.direct.addressing.table.dat.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/05/21.
 */
public class FreeFood {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int eventsNumber = FastReader.nextInt();
        boolean[] events = new boolean[366];

        for (int i = 0; i < eventsNumber; i++) {
            int startDay = FastReader.nextInt();
            int endDay = FastReader.nextInt();

            for (int d = startDay; d <= endDay; d++) {
                events[d] = true;
            }
        }
        int freeFoodDays = countFreeFoodDays(events);
        outputWriter.printLine(freeFoodDays);
        outputWriter.flush();
    }

    private static int countFreeFoodDays(boolean[] events) {
        int freeFoodDays = 0;
        for (int d = 1; d < events.length; d++) {
            if (events[d]) {
                freeFoodDays++;
            }
        }
        return freeFoodDays;
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
