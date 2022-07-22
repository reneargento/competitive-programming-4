package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/07/22.
 */
public class Ants {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int poleLength = FastReader.nextInt();
            int ants = FastReader.nextInt();
            int middleLength = poleLength / 2;

            int leftmostAnt = Integer.MAX_VALUE;
            int rightmostAnt = 0;
            boolean closestToRightSide = false;
            int closestToMiddleDistance = Integer.MAX_VALUE;
            int closestToMiddleAnt = 0;

            for (int i = 0; i < ants; i++) {
                int location = FastReader.nextInt();
                int distanceFromMiddle = Math.abs(location - middleLength);
                if (distanceFromMiddle < closestToMiddleDistance) {
                    closestToMiddleDistance = distanceFromMiddle;
                    closestToMiddleAnt = location;
                    closestToRightSide = location > middleLength;
                }

                leftmostAnt = Math.min(leftmostAnt, location);
                rightmostAnt = Math.max(rightmostAnt, location);
            }

            int earliestTime = closestToMiddleAnt;
            if (closestToRightSide) {
                earliestTime = poleLength - closestToMiddleAnt;
            }
            int latestTime = Math.max(poleLength - leftmostAnt, rightmostAnt);
            outputWriter.printLine(earliestTime + " " + latestTime);
        }
        outputWriter.flush();
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
