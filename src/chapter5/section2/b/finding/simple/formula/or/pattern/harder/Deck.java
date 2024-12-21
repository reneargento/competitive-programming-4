package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;

/**
 * Created by Rene Argento on 19/12/24.
 */
public class Deck {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        outputWriter.printLine("# Cards Overhang");
        while (line != null) {
            int cards = Integer.parseInt(line);
            double maxLength = computeMaxLength(cards);
            outputWriter.printLine(String.format("%5d %9.3f", cards, maxLength));

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double computeMaxLength(int cards) {
        double length = 0;
        for (int i = 1; i <= cards; i++) {
            length += 0.5 / i;
        }
        return length;
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
