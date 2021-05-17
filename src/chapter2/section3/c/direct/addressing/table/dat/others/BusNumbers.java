package chapter2.section3.c.direct.addressing.table.dat.others;

import java.io.*;
import java.util.StringJoiner;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/05/21.
 */
public class BusNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();

        int busesNumber = FastReader.nextInt();
        boolean[] buses = new boolean[1001];

        for (int i = 0; i < busesNumber; i++) {
            int bus = FastReader.nextInt();
            buses[bus] = true;
        }
        printShortestRepresentation(buses);
    }

    private static void printShortestRepresentation(boolean[] buses) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        StringJoiner busList = new StringJoiner(" ");

        for (int b = 1; b < buses.length; b++) {
            if (buses[b]) {
                int sequentialBuses = 1;
                int lastBus = b;

                for (int i = b + 1; i < buses.length; i++) {
                    if (buses[i]) {
                        sequentialBuses++;
                        lastBus = i;
                    } else {
                        break;
                    }
                }

                if (sequentialBuses >= 3) {
                    busList.add(b + "-" + lastBus);
                    b = lastBus;
                } else {
                    busList.add(String.valueOf(b));
                }
            }
        }
        outputWriter.printLine(busList.toString());
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
