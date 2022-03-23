package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/03/22.
 */
public class TheMonkeyAndTheOiledBamboo {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] heights = new int[FastReader.nextInt()];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = FastReader.nextInt();
            }
            int minimumStrength = getMinimumStrength(heights);
            outputWriter.printLine(String.format("Case %d: %d", t, minimumStrength));
        }
        outputWriter.flush();
    }

    private static int getMinimumStrength(int[] heights) {
        int lowStrength = 1;
        int highStrength = 10000000;
        return getMinimumStrength(heights, lowStrength, highStrength);
    }

    private static int getMinimumStrength(int[] heights, int lowStrength, int highStrength) {
        while (lowStrength <= highStrength) {
            int strength = lowStrength + (highStrength - lowStrength) / 2;
            if (!canReachTheTop(heights, strength)) {
                lowStrength = strength + 1;
            } else {
                int result = strength;
                int candidateStrength = getMinimumStrength(heights, lowStrength, strength - 1);
                if (candidateStrength != -1) {
                    result = candidateStrength;
                }
                return result;
            }
        }
        return -1;
    }

    private static boolean canReachTheTop(int[] heights, int strength) {
        int currentHeight = 0;

        for (int nextRung : heights) {
            if (currentHeight + strength < nextRung) {
                return false;
            }
            if (currentHeight + strength == nextRung) {
                strength--;
            }
            currentHeight = nextRung;
        }
        return true;
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
