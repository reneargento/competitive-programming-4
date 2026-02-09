package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/02/26.
 */
public class CrossingRivers {

    private static class River implements Comparable<River> {
        int distanceA;
        int length;
        int boatSpeed;

        public River(int distanceA, int length, int boatSpeed) {
            this.distanceA = distanceA;
            this.length = length;
            this.boatSpeed = boatSpeed;
        }

        @Override
        public int compareTo(River other) {
            return Integer.compare(this.distanceA, other.distanceA);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int riversNumber = FastReader.nextInt();
        int distanceAB = FastReader.nextInt();
        int caseId = 1;

        while (riversNumber != 0 || distanceAB != 0) {
            River[] rivers = new River[riversNumber];
            for (int i = 0; i < riversNumber; i++) {
                rivers[i] = new River(FastReader.nextInt(), FastReader.nextInt(), FastReader.nextInt());
            }
            double expectedTime = computeExpectedTime(rivers, distanceAB);
            outputWriter.printLine(String.format("Case %d: %.3f\n", caseId, expectedTime));

            caseId++;
            riversNumber = FastReader.nextInt();
            distanceAB = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeExpectedTime(River[] rivers, int distanceAB) {
        Arrays.sort(rivers);
        double expectedTime = 0;
        int lastRiverLocation = 0;

        for (River river : rivers) {
            expectedTime += river.distanceA - lastRiverLocation;
            double speed = river.boatSpeed;
            lastRiverLocation = river.distanceA + river.length;

            double averageTime = 0;
            for (int location = 0; location < river.length; location++) {
                int distanceFromLeft = location;
                int distanceFromRight = river.length - location;

                // Moving left
                averageTime += (distanceFromLeft + river.length) / speed;
                // Moving right
                averageTime += distanceFromRight / speed;
            }
            expectedTime += averageTime / river.length;
        }
        expectedTime += distanceAB - lastRiverLocation;
        return expectedTime;
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

        public void flush() {
            writer.flush();
        }
    }
}
