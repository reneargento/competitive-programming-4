package chapter5.section2.g.grid;

import java.io.*;

/**
 * Created by Rene Argento on 06/04/25.
 */
public class CountOnCantor {

    private static class Fraction {
        long nominator;
        long denominator;

        public Fraction(long nominator, long denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        long[] diagonals = computeDiagonals();

        while (line != null) {
            int position = Integer.parseInt(line);
            Fraction fraction = computeTerm(diagonals, position);

            outputWriter.printLine(String.format("TERM %d IS %d/%d", position, fraction.nominator, fraction.denominator));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long[] computeDiagonals() {
        long[] diagonals = new long[4500];
        diagonals[1] = 1;

        for (int i = 2; i < diagonals.length; i++) {
            diagonals[i] += diagonals[i - 1] + i;
        }
        return diagonals;
    }

    private static Fraction computeTerm(long[] diagonals, int position) {
        long nominator;
        long denominator;
        int diagonal;

        for (diagonal = 1; diagonal < diagonals.length; diagonal++) {
            if (diagonals[diagonal] >= position) {
                break;
            }
        }

        if (diagonal % 2 == 0) {
            nominator = position - diagonals[diagonal - 1];
            denominator = diagonals[diagonal] - position + 1;
        } else {
            nominator = diagonals[diagonal] - position + 1;
            denominator = position - diagonals[diagonal - 1];
        }
        return new Fraction(nominator, denominator);
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
