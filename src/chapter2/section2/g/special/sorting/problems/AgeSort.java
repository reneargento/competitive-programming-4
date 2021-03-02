package chapter2.section2.g.special.sorting.problems;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/02/21.
 */
public class AgeSort {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int population = FastReader.nextInt();

        while (population != 0) {
            int[] ages = new int[100];

            for (int i = 0; i < population; i++) {
                ages[FastReader.nextInt()]++;
            }
            printSortedAges(ages, outputWriter);
            population = FastReader.nextInt();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void printSortedAges(int[] ages, OutputWriter outputWriter) {
        boolean isFirstAge = true;

        for (int i = 1; i < ages.length; i++) {
            while (ages[i] > 0) {
                if (!isFirstAge) {
                    outputWriter.print(" ");
                }

                outputWriter.print(i);
                isFirstAge = false;
                ages[i]--;
            }
        }
        outputWriter.printLine();
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
