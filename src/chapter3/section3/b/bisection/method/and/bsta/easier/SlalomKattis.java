package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/03/22.
 */
public class SlalomKattis {

    private static class GateLocation {
        int x1;
        int x2;
        int y;

        public GateLocation(int x1, int x2, int y) {
            this.x1 = x1;
            this.x2 = x2;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int distanceBetweenGates = FastReader.nextInt();
        int maxHorizontalSpeed = FastReader.nextInt();
        GateLocation[] gates = new GateLocation[FastReader.nextInt()];
        for (int i = 0; i < gates.length; i++) {
            int x = FastReader.nextInt();
            int y = FastReader.nextInt();
            gates[i] = new GateLocation(x, x + distanceBetweenGates, y);
        }
        int[] skiSpeeds = new int[FastReader.nextInt()];
        for (int i = 0; i < skiSpeeds.length; i++) {
            skiSpeeds[i] = FastReader.nextInt();
        }

        Arrays.sort(skiSpeeds);
        int low = 0;
        int high = skiSpeeds.length - 1;
        int bestSpeedIndex = getBestSpeedIndex(gates, maxHorizontalSpeed, skiSpeeds, low, high);
        if (bestSpeedIndex != -1) {
            outputWriter.printLine(skiSpeeds[bestSpeedIndex]);
        } else {
            outputWriter.printLine("IMPOSSIBLE");
        }
        outputWriter.flush();
    }

    private static int getBestSpeedIndex(GateLocation[] gates, int maxHorizontalSpeed, int[] skiSpeeds,
                                    int low, int high) {
        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (canCompleteRace(gates, maxHorizontalSpeed, skiSpeeds[middle])) {
                int result = middle;
                int candidateResult = getBestSpeedIndex(gates, maxHorizontalSpeed, skiSpeeds, middle + 1, high);
                if (candidateResult != -1) {
                    result = candidateResult;
                }
                return result;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }

    private static boolean canCompleteRace(GateLocation[] gates, int maxHorizontalSpeed, long skiSpeed) {
        int y = 0;
        double left = 0;
        // 2*10^8 * 10^6 = 2*10^14
        double right = 200000000 * skiSpeed;

        for (GateLocation gate : gates) {
            double deltaVertical = gate.y - y;
            double seconds = deltaVertical / skiSpeed;

            left -= seconds * maxHorizontalSpeed;
            right += seconds * maxHorizontalSpeed;

            if (left < gate.x1) {
                left = gate.x1;
            }
            if (right > gate.x2) {
                right = gate.x2;
            }

            if (left > right) {
                return false;
            }
            y = gate.y;
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
