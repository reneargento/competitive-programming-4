package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/11/21.
 */
public class PrimaryRegister {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] registers = { 1, 2, 4, 6, 10, 12, 16, 18 };
        int[] states = new int[8];

        for (int i = 0; i < states.length; i++) {
            states[i] = FastReader.nextInt();
        }

        int totalOperations = 0;
        while (!isFilled(states, registers)) {
            totalOperations++;
            increment(states, registers);
        }

        outputWriter.printLine(totalOperations);
        outputWriter.flush();
    }

    private static boolean isFilled(int[] states, int[] registers) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != registers[i]) {
                return false;
            }
        }
        return true;
    }

    private static void increment(int[] states, int[] registers) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] == registers[i]) {
                states[i] = 0;
            } else {
                states[i]++;
                break;
            }
        }

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
