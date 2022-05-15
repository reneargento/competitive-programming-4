package chapter3.section4.a.classical;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/05/22.
 */
public class SquarePegInARoundHole {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Integer[] plotRadius = new Integer[FastReader.nextInt()];
        int roundHouses = FastReader.nextInt();
        Double[] houseRadius = new Double[roundHouses + FastReader.nextInt()];

        for (int i = 0; i < plotRadius.length; i++) {
            plotRadius[i] = FastReader.nextInt();
        }
        int houseIndex;
        for (houseIndex = 0; houseIndex < roundHouses; houseIndex++) {
            houseRadius[houseIndex] = FastReader.nextDouble();
        }
        for (; houseIndex < houseRadius.length; houseIndex++) {
            int squareLength = FastReader.nextInt();
            double radius = maximumSquareLengthInscribedInCircle(squareLength) / 2;
            houseRadius[houseIndex] = radius;
        }

        int largestNumberOfPlotsFilled = computeLargestNumberOfPlotsFilled(plotRadius, houseRadius);
        outputWriter.printLine(largestNumberOfPlotsFilled);
        outputWriter.flush();
    }

    private static int computeLargestNumberOfPlotsFilled(Integer[] plotRadius, Double[] houseRadius) {
        int largestNumberOfPlotsFilled = 0;
        Arrays.sort(plotRadius, Collections.reverseOrder());
        Arrays.sort(houseRadius, Collections.reverseOrder());

        int houseIndex = 0;
        for (Integer radius : plotRadius) {
            while (houseIndex < houseRadius.length) {
                if (houseRadius[houseIndex] < radius) {
                    largestNumberOfPlotsFilled++;
                    houseIndex++;
                    break;
                }
                houseIndex++;
            }
        }
        return largestNumberOfPlotsFilled;
    }

    private static double maximumSquareLengthInscribedInCircle(double circleRadius) {
        return Math.sqrt(2) * circleRadius;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
