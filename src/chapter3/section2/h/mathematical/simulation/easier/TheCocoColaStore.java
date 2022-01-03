package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class TheCocoColaStore {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int bottles = FastReader.nextInt();

        while (bottles != 0) {
            int bottlesDrank = computeBottlesDrank(bottles);
            outputWriter.printLine(bottlesDrank);
            bottles = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeBottlesDrank(int bottles) {
        int bottlesDrank = 0;

        while (bottles >= 3) {
            int newBottlesDrank = bottles / 3;
            bottlesDrank += newBottlesDrank;

            bottles = newBottlesDrank + (bottles % 3);
        }

        if (bottles == 2) {
            bottlesDrank++;
        }
        return bottlesDrank;
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
