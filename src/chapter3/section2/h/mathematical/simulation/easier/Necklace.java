package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/12/21.
 */
public class Necklace {

    private static final double EPSILON = .00000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int totalVolume = FastReader.nextInt();
        int volumeUsed = FastReader.nextInt();

        while (totalVolume != 0 || volumeUsed != 0) {
            int bestNumberOfDiscs = computeBestNumberOfDiscs(totalVolume, volumeUsed);
            outputWriter.printLine(bestNumberOfDiscs);

            totalVolume = FastReader.nextInt();
            volumeUsed = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeBestNumberOfDiscs(int totalVolume, int volumeUsed) {
        if (volumeUsed >= totalVolume) {
            return 0;
        }

        double bestNumberOfDiscs = 0;
        double highestLength = 0;

        for (double discs = 1; discs <= totalVolume; discs++) {
            double volumePerDisc = totalVolume / discs;

            if (volumeUsed >= volumePerDisc) {
                break;
            }
            double diameter = 0.3 * Math.sqrt(volumePerDisc - volumeUsed);
            double length = diameter * discs;
            double lengthDifference = length - highestLength - EPSILON;

            if (lengthDifference > 0) {
                highestLength = length;
                bestNumberOfDiscs = discs;
            } else if (Math.abs(lengthDifference) <= EPSILON) {
                bestNumberOfDiscs = 0;
            }
        }
        return (int) bestNumberOfDiscs;
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
