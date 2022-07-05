package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/06/22.
 */
public class PerfectChoir {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int notesNumber = Integer.parseInt(line);
            int[] notes = new int[notesNumber];
            long notesSum = 0;

            for (int i = 0; i < notes.length; i++) {
                notes[i] = FastReader.nextInt();
                notesSum += notes[i];
            }
            long minimumBarMeasures = computeMinimumBarMeasures(notes, notesSum);
            outputWriter.printLine(minimumBarMeasures);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long computeMinimumBarMeasures(int[] notes, long notesSum) {
        if (notesSum % notes.length != 0) {
            return -1;
        }
        long minimumBarMeasures = 0;
        long average = notesSum / notes.length;

        int startIndex = 0;
        int endIndex = notes.length - 1;

        while (startIndex < endIndex) {
            long difference1 = Math.abs(notes[startIndex] - average);
            long difference2 = Math.abs(notes[endIndex] - average);

            if (difference1 <= difference2) {
                minimumBarMeasures += difference1;
                notes[endIndex] -= difference1;
                startIndex++;
            } else {
                minimumBarMeasures += difference2;
                notes[startIndex] += difference2;
                endIndex--;
            }
        }
        return minimumBarMeasures + 1;
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
