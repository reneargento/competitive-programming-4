package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/04/22.
 */
// Based on https://github.com/mpfeifer1/Kattis/blob/master/a1paper.cpp
public class A1Paper {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] paperQuantities = new int[FastReader.nextInt() - 1];
        for (int i = 0; i < paperQuantities.length; i++) {
            paperQuantities[i] = FastReader.nextInt();
        }

        double smallestTapeLength = getSmallestTapeLength(paperQuantities);
        if (smallestTapeLength == -1) {
            outputWriter.printLine("impossible");
        } else {
            outputWriter.printLine(smallestTapeLength);
        }
        outputWriter.flush();
    }

    private static double getSmallestTapeLength(int[] paperQuantities) {
        double totalTape = 0;
        long paperNeeded = 1;
        boolean possible = false;

        double longSize = Math.pow(2, -3/4.0);
        double shortSize = Math.pow(2, -5/4.0);

        for (int paperQuantity : paperQuantities) {
            // Update cost
            totalTape += paperNeeded * longSize;

            longSize /= 2;
            double aux = shortSize;
            shortSize = longSize;
            longSize = aux;

            // Calculate current paper needed
            paperNeeded *= 2;
            paperNeeded -= paperQuantity;

            // Check if we have enough paper
            if (paperNeeded <= 0) {
                possible = true;
                break;
            }
        }

        if (!possible) {
            return -1;
        }
        return totalTape;
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
