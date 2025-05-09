package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 04/05/25.
 */
public class DivideButNotQuiteConquer {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int number = Integer.parseInt(data[0]);
            int divisor = Integer.parseInt(data[1]);

            List<Integer> sequence = computeSequence(number, divisor);
            if (sequence == null) {
                outputWriter.printLine("Boring!");
            } else {
                outputWriter.print(sequence.get(0));
                for (int i = 1; i < sequence.size(); i++) {
                    outputWriter.print(" " + sequence.get(i));
                }
                outputWriter.printLine();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeSequence(int number, int divisor) {
        if (divisor <= 1 || divisor > number) {
            return null;
        }
        List<Integer> sequence = new ArrayList<>();
        sequence.add(number);

        while (number > 1) {
            if (number % divisor == 0) {
                number /= divisor;
                sequence.add(number);
            } else {
                return null;
            }
        }
        return sequence;
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
