package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/04/22.
 */
public class Zipline {

    private static final double EPSILON = 0.00000001;

    private static class Result {
        double minimumLength;
        double maximumLength;

        public Result(double minimumLength, double maximumLength) {
            this.minimumLength = minimumLength;
            this.maximumLength = maximumLength;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int width = FastReader.nextInt();
            int poleHeight1 = FastReader.nextInt();
            int poleHeight2 = FastReader.nextInt();
            int riderHeight = FastReader.nextInt();

            Result cables = computeCables(width, poleHeight1, poleHeight2, riderHeight);
            outputWriter.printLine(String.format("%.8f %.8f", cables.minimumLength, cables.maximumLength));
        }
        outputWriter.flush();
    }

    private static Result computeCables(int width, int poleHeight1, int poleHeight2, int targetDistanceFromGround) {
        double low = 0;
        double high = width;
        double minimumLength = computeMinimumLength(width, poleHeight1, poleHeight2);
        double maximumLength = minimumLength;

        while (low + EPSILON <= high) {
            double thirdLength = (high - low) / 3;
            double section1 = low + thirdLength;
            double section2 = high - thirdLength;

            double length1 = computeLengthWithMiddleX(width, poleHeight1, poleHeight2, targetDistanceFromGround,
                    section1);
            double length2 = computeLengthWithMiddleX(width, poleHeight1, poleHeight2, targetDistanceFromGround,
                    section2);

            if (length1 <= length2) {
                high = section2;
                maximumLength = length1;
            } else {
                low = section1;
                maximumLength = length2;
            }
        }
        return new Result(minimumLength, maximumLength);
    }

    private static double computeMinimumLength(int width, int poleHeight1, int poleHeight2) {
        double triangleHeight = Math.abs(poleHeight1 - poleHeight2);
        return Math.sqrt(Math.pow(triangleHeight, 2) + Math.pow(width, 2));
    }

    private static double computeLengthWithMiddleX(int width, int poleHeight1, int poleHeight2,
                                                   int targetDistanceFromGround, double triangle1Width) {
        double triangle1Height = poleHeight1 - targetDistanceFromGround;
        double triangle2Height = poleHeight2 - targetDistanceFromGround;
        double triangle2Width = width - triangle1Width;

        double distance1 = Math.sqrt(Math.pow(triangle1Height, 2) + Math.pow(triangle1Width, 2));
        double distance2 = Math.sqrt(Math.pow(triangle2Height, 2) + Math.pow(triangle2Width, 2));
        return distance1 + distance2;
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
