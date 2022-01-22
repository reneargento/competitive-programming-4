package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/01/22.
 */
public class PowerCrisis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int regions = FastReader.nextInt();

        while (regions != 0) {
            int offset = computeOffset(regions);
            outputWriter.printLine(offset);
            regions = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeOffset(int regions) {
        for (int offset = 1; offset <= regions; offset++) {
            if (finishesInWellington(regions, offset)) {
                return offset;
            }
        }
        return -1;
    }

    private static boolean finishesInWellington(int regions, int offset) {
        boolean[] powerOff = new boolean[regions];
        int region = 0;

        for (int i = 0; i < regions - 1; i++) {
            powerOff[region] = true;
            region = getNextValidRegion(region, offset, powerOff);
        }
        return region == 12;
    }

    private static int getNextValidRegion(int currentRegion, int offset, boolean[] powerOff) {
        int regionsWithoutPower = 0;
        int nextRegion;
        for (nextRegion = currentRegion + 1; regionsWithoutPower < offset; nextRegion++) {
            if (nextRegion == powerOff.length) {
                nextRegion = 0;
            }
            if (!powerOff[nextRegion]) {
                regionsWithoutPower++;
            }
        }
        return nextRegion - 1;
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
