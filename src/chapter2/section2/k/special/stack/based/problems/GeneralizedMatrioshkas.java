package chapter2.section2.k.special.stack.based.problems;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Rene Argento on 25/03/21.
 */
public class GeneralizedMatrioshkas {

    private static class Toy {
        long size;
        long innerToysSize;

        public Toy(long size) {
            this.size = size;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] values = line.split(" ");
            Deque<Toy> matrioshkas = new ArrayDeque<>();
            boolean isMatrioshka = true;

            for (String value : values) {
                int size = Integer.parseInt(value);

                if (!matrioshkas.isEmpty() && size == matrioshkas.peek().size) {
                    matrioshkas.pop();

                    if (!matrioshkas.isEmpty()) {
                        matrioshkas.peek().innerToysSize += size;
                        if (matrioshkas.peek().size <= matrioshkas.peek().innerToysSize) {
                            isMatrioshka = false;
                            break;
                        }
                    }
                } else {
                    matrioshkas.push(new Toy(Math.abs(size)));
                }
            }

            if (!matrioshkas.isEmpty()) {
                isMatrioshka = false;
            }
            outputWriter.printLine(isMatrioshka ? ":-) Matrioshka!" : ":-( Try again.");

            line = FastReader.getLine();
        }
        outputWriter.flush();
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
