package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/01/22.
 */
public class PrimaryArithmetic {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number1 = FastReader.nextInt();
        int number2 = FastReader.nextInt();

        while (number1 != 0 || number2 != 0) {
            int carryOperations = countCarryOperations(number1, number2);
            

            number1 = FastReader.nextInt();
            number2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int countCarryOperations(int number1, int number2) {


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
