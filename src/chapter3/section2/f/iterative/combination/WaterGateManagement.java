package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/12/21.
 */
public class WaterGateManagement {

    private static class WaterGate {
        int flowRate;
        int damageCost;

        public WaterGate(int flowRate, int damageCost) {
            this.flowRate = flowRate;
            this.damageCost = damageCost;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        WaterGate[] waterGates = new WaterGate[FastReader.nextInt()];
        for (int i = 0; i < waterGates.length; i++) {
            waterGates[i] = new WaterGate(FastReader.nextInt(), FastReader.nextInt());
        }

        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int volume = FastReader.nextInt();
            int hours = FastReader.nextInt();

            long minimumCost = computeMinimumCost(waterGates, volume, hours, 0, 0);
            outputWriter.print(String.format("Case %d: ", t));
            if (minimumCost == Long.MAX_VALUE) {
                outputWriter.printLine("IMPOSSIBLE");
            } else {
                outputWriter.printLine(minimumCost);
            }
        }
        outputWriter.flush();
    }

    private static long computeMinimumCost(WaterGate[] waterGates, int volume, int hours, int activeMask,
                                           int index) {
        if (index == waterGates.length) {
            return computeCost(waterGates, volume, hours, activeMask);
        }

        int maskWithWaterGateActive = activeMask | (1 << index);
        long cost1 = computeMinimumCost(waterGates, volume, hours, maskWithWaterGateActive, index + 1);
        long cost2 = computeMinimumCost(waterGates, volume, hours, activeMask, index + 1);
        return Math.min(cost1, cost2);
    }

    private static long computeCost(WaterGate[] waterGates, int volume, long hours, int activeMask) {
        long volumeFlushed = 0;
        long cost = 0;

        for (int i = 0; i < waterGates.length; i++) {
            if ((activeMask & (1 << i)) > 0) {
                volumeFlushed += waterGates[i].flowRate * hours;
                cost += waterGates[i].damageCost;
            }
        }

        if (volumeFlushed >= volume) {
            return cost;
        } else {
            return Long.MAX_VALUE;
        }
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
