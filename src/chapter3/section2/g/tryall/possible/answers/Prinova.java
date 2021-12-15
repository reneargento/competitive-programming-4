package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/12/21.
 */
public class Prinova {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long[] names = new long[FastReader.nextInt()];
        for (int i = 0; i < names.length; i++) {
            names[i] = FastReader.nextInt();
        }
        int rangeStart = FastReader.nextInt();
        int rangeEnd = FastReader.nextInt();
        Arrays.sort(names);

        long girlsName = computeGirlsName(names, rangeStart, rangeEnd);
        outputWriter.printLine(girlsName);
        outputWriter.flush();
    }

    private static long computeGirlsName(long[] names, int rangeStart, int rangeEnd) {
        long bestName = 0;
        long bestDistance = 0;

        for (int i = 0; i < names.length - 1; i++) {
            long middle = (names[i] + names[i + 1]) / 2;

            if (middle % 2 == 0) {
                middle++;
            }

            if (middle < rangeStart) {
                continue;
            }
            if (middle > rangeEnd) {
                break;
            }

            long distance = names[i + 1] - middle;

            if (distance > bestDistance) {
                bestDistance = distance;
                bestName = middle;
            }
        }

        // Check rangeStart
        if (rangeStart % 2 == 0) {
            rangeStart++;
        }
        if (rangeStart <= rangeEnd) {
            long distance = getClosestDistance(names, rangeStart);
            if (distance > bestDistance) {
                bestDistance = distance;
                bestName = rangeStart;
            }
        }

        // Check rangeEnd
        if (rangeEnd % 2 == 0) {
            rangeEnd--;
        }
        if (rangeEnd >= rangeStart) {
            long distance = getClosestDistance(names, rangeEnd);
            if (distance > bestDistance) {
                bestName = rangeEnd;
            }
        }
        return bestName;
    }

    private static long getClosestDistance(long[] names, int position) {
        long closestDistance = Integer.MAX_VALUE;

        for (long name : names) {
            long distance = Math.abs(name - position);
            closestDistance = Math.min(closestDistance, distance);
        }
        return closestDistance;
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
