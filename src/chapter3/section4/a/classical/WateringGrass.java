package chapter3.section4.a.classical;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 15/05/22.
 */
public class WateringGrass {

    private static class Sprinkler implements Comparable<Sprinkler> {
        double start;
        double end;

        public Sprinkler(double start, double end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Sprinkler other) {
            if (start != other.start) {
                return Double.compare(start, other.start);
            }
            return Double.compare(other.end, end);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            Sprinkler[] sprinklers = new Sprinkler[Integer.parseInt(data[0])];
            int grassMetersLong = Integer.parseInt(data[1]);
            double grassMetersWide = Integer.parseInt(data[2]);

            for (int i = 0; i < sprinklers.length; i++) {
                int position = FastReader.nextInt();
                int radius = FastReader.nextInt();
                double horizontalDistance = computeHorizontalDistance(radius, grassMetersWide);
                double start = position - horizontalDistance;
                double end = position + horizontalDistance;
                sprinklers[i] = new Sprinkler(start, end);
            }

            int minimumSprinklersNeeded = computeMinimumSprinklersNeeded(sprinklers, grassMetersLong);
            outputWriter.printLine(minimumSprinklersNeeded);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumSprinklersNeeded(Sprinkler[] sprinklers, int grassMetersLong) {
        int minimumSprinklersNeeded = 0;
        Arrays.sort(sprinklers);
        double end = 0;

        for (int i = 0; i < sprinklers.length; i++) {
            if (sprinklers[i].start > end) {
                return -1;
            }

            double highestEnd = end;
            if (sprinklers[i].end >= end) {
                for (int j = i; j < sprinklers.length; j++) {
                    if (sprinklers[j].start > end) {
                        break;
                    }
                    if (sprinklers[j].end > end) {
                        highestEnd = Math.max(highestEnd, sprinklers[j].end);
                        i = j;
                    }
                }

                minimumSprinklersNeeded++;
                end = highestEnd;
            }

            if (end >= grassMetersLong) {
                break;
            }
        }

        if (end < grassMetersLong) {
            return -1;
        }
        return minimumSprinklersNeeded;
    }

    private static double computeHorizontalDistance(double circleRadius, double grassMetersWide) {
        return Math.sqrt(Math.pow(circleRadius, 2) - Math.pow(grassMetersWide / 2, 2));
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
