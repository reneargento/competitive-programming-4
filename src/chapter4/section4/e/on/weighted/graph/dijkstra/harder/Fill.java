package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/01/24.
 */
public class Fill {

    private static class Result {
        int totalAmountPoured;
        int targetLiters;

        public Result(int totalAmountPoured, int targetLiters) {
            this.totalAmountPoured = totalAmountPoured;
            this.targetLiters = targetLiters;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] capacities = new int[3];
            for (int i = 0; i < capacities.length; i++) {
                capacities[i] = FastReader.nextInt();
            }
            int target = FastReader.nextInt();

            Result result = pourWater(capacities, target);
            outputWriter.printLine(String.format("%d %d", result.totalAmountPoured, result.targetLiters));
        }
        outputWriter.flush();
    }

    private static Result pourWater(int[] capacities, int target) {
        Dijkstra dijkstra = new Dijkstra(capacities);
        for (int volume = target; volume >= 0; volume--) {
            if (dijkstra.hasPathTo(volume)) {
                int minimumWater = Integer.MAX_VALUE;
                for (int jugID = 0; jugID < dijkstra.pouredWaterPerVolume[0].length; jugID++) {
                    minimumWater = Math.min(minimumWater, dijkstra.pouredWaterPerVolume[volume][jugID]);
                }
                return new Result(minimumWater, volume);
            }
        }
        return null;
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int totalAmountPoured;
            int[] currentVolumes;

            public Vertex(int totalAmountPoured, int[] currentVolumes) {
                this.totalAmountPoured = totalAmountPoured;
                this.currentVolumes = currentVolumes;
            }

            @Override
            public int compareTo(Vertex other) {
                return Integer.compare(totalAmountPoured, other.totalAmountPoured);
            }
        }

        private final int[][] pouredWaterPerVolume;
        private final PriorityQueue<Vertex> priorityQueue;

        public Dijkstra(int[] capacities) {
            pouredWaterPerVolume = new int[201][3];
            priorityQueue = new PriorityQueue<>();

            for (int jugID = 0; jugID < pouredWaterPerVolume.length; jugID++) {
                Arrays.fill(pouredWaterPerVolume[jugID], Integer.MAX_VALUE);
            }
            int[] volumes = { 0, 0, capacities[2] };

            pouredWaterPerVolume[0][0] = 0;
            pouredWaterPerVolume[0][1] = 0;
            pouredWaterPerVolume[capacities[2]][2] = 0;
            priorityQueue.offer(new Vertex(0, volumes));

            while (!priorityQueue.isEmpty()) {
                relax(priorityQueue.poll(), capacities);
            }
        }

        private void relax(Vertex vertex, int[] capacities) {
            int[] volumes = vertex.currentVolumes;
            for (int jug1 = 0; jug1 < volumes.length; jug1++) {

                if (volumes[jug1] > 0) {
                    for (int jug2 = 0; jug2 < volumes.length; jug2++) {
                        if (jug1 == jug2) {
                            continue;
                        }

                        int totalVolume = volumes[jug1] + volumes[jug2];
                        int jug2NewVolume = Math.min(capacities[jug2], totalVolume);
                        int pouredWater = jug2NewVolume - volumes[jug2];

                        int[] newVolumes = { volumes[0], volumes[1], volumes[2] };
                        newVolumes[jug1] = volumes[jug1] - pouredWater;
                        newVolumes[jug2] = jug2NewVolume;
                        int newPouredWater = vertex.totalAmountPoured + pouredWater;
                        boolean betterVolumeFound = false;

                        if (newPouredWater < pouredWaterPerVolume[newVolumes[jug1]][jug1]) {
                            pouredWaterPerVolume[newVolumes[jug1]][jug1] = newPouredWater;
                            betterVolumeFound = true;
                        }
                        if (newPouredWater < pouredWaterPerVolume[newVolumes[jug2]][jug2]) {
                            pouredWaterPerVolume[newVolumes[jug2]][jug2] = newPouredWater;
                            betterVolumeFound = true;
                        }
                        if (betterVolumeFound) {
                            priorityQueue.offer(new Vertex(newPouredWater, newVolumes));
                        }
                    }
                }
            }
        }

        public boolean hasPathTo(int vertex) {
            for (int jugID = 0; jugID < pouredWaterPerVolume[0].length; jugID++) {
                if (pouredWaterPerVolume[vertex][jugID] != Integer.MAX_VALUE) {
                    return true;
                }
            }
            return false;
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

        public void flush() {
            writer.flush();
        }
    }
}
