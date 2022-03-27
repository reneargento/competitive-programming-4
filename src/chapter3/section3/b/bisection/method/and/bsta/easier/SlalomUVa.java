package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.Arrays;

/**
 * Note: this problem has I/O issues in UVa and is flagged in uDebug, the solution doesn't get accepted.
 * Source: https://www.udebug.com/UVa/11627
 *
 * Created by Rene Argento on 26/03/22.
 */
public class SlalomUVa {

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
        int tests = FastReader.getInteger(FastReader.getLine().trim());

        for (int t = 0; t < tests; t++) {
            try {
                String[] lineValues = FastReader.getLine().trim().split(" ");
                int distanceBetweenGates = FastReader.getInteger(lineValues[0]);
                int maxHorizontalSpeed = FastReader.getInteger(lineValues[1]);
                int gatesNumber = FastReader.getInteger(lineValues[2]);

                GateLocation[] gates = new GateLocation[gatesNumber];
                for (int i = 0; i < gates.length; i++) {
                    String[] gateValues = FastReader.getLine().trim().split(" ");
                    int x = FastReader.getInteger(gateValues[0]);
                    int y = FastReader.getInteger(gateValues[1]);
                    gates[i] = new GateLocation(x, x + distanceBetweenGates, y);
                }
                int skiSpeedsNumber = FastReader.getInteger(FastReader.getLine().trim());
                int[] skiSpeeds = new int[skiSpeedsNumber];
                for (int i = 0; i < skiSpeeds.length; i++) {
                    skiSpeeds[i] = FastReader.getInteger(FastReader.getLine().trim());
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
            } catch (Exception e) {
                outputWriter.printLine("IMPOSSIBLE");
            }
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

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static int getInteger(String string) {
            int number = 0;

            for (int i = 0; i < string.length(); i++) {
                char value = string.charAt(i);

                if (value >= '0' && value <= '9') {
                    number = number * 10 + (value - '0');
                } else {
                    break;
                }
            }
            return number;
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
