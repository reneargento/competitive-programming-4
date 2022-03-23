package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/03/22.
 */
public class CarefulAscent {

    private static final double EPSILON = .0000001;

    private static class Shield implements Comparable<Shield> {
        int lowerBoundary;
        int higherBoundary;
        double disturbanceFactor;

        public Shield(int lowerBoundary, int higherBoundary, double disturbanceFactor) {
            this.lowerBoundary = lowerBoundary;
            this.higherBoundary = higherBoundary;
            this.disturbanceFactor = disturbanceFactor;
        }

        @Override
        public int compareTo(Shield other) {
            return Integer.compare(lowerBoundary, other.lowerBoundary);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int xCoordinate = FastReader.nextInt();
        int yCoordinate = FastReader.nextInt();
        Shield[] shields = new Shield[FastReader.nextInt()];

        for (int i = 0; i < shields.length; i++) {
            shields[i] = new Shield(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextDouble());
        }
        double horizontalVelocity = getHorizontalVelocity(xCoordinate, yCoordinate, shields);
        outputWriter.printLine(horizontalVelocity);
        outputWriter.flush();
    }

    private static double getHorizontalVelocity(int xCoordinate, int yCoordinate, Shield[] shields) {
        Arrays.sort(shields);
        double lowVelocity = -10;
        double highVelocity = 10;

        while (lowVelocity < highVelocity) {
            double velocity = lowVelocity + (highVelocity - lowVelocity) / 2;
            double xReached = getFinalX(yCoordinate, shields, velocity);

            if (Math.abs(xReached - xCoordinate) < EPSILON) {
                return velocity;
            } else if (xReached < xCoordinate) {
                lowVelocity = velocity;
            } else {
                highVelocity = velocity;
            }
        }
        return -1;
    }

    private static double getFinalX(int yCoordinate, Shield[] shields, double horizontalVelocity) {
        double currentX = 0;
        int currentY = 0;

        for (Shield shield : shields) {
            int deltaY = shield.lowerBoundary - currentY;
            currentX += deltaY * horizontalVelocity;

            int shieldSize = shield.higherBoundary - shield.lowerBoundary;
            currentX += shieldSize * horizontalVelocity * shield.disturbanceFactor;
            currentY = shield.higherBoundary;
        }

        int remainingY = yCoordinate - currentY;
        currentX += remainingY * horizontalVelocity;
        return currentX;
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
