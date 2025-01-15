package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;

/**
 * Created by Rene Argento on 29/12/24.
 */
public class SquaresListsAndDigitalSums {

    public static void main(String[] args) throws IOException {
        OutputWriter outputWriter = new OutputWriter(System.out);

        // Based on a comment in https://oeis.org/A053057
        for (int number = 9999; number <= 10005; number++) {
            long square = number * number;
            outputWriter.printLine(number + " " + square);
        }
        outputWriter.flush();
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
