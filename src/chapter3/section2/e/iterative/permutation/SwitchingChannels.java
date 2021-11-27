package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/11/21.
 */
public class SwitchingChannels {

    private static class Programmes implements Comparable<Programmes> {
        int[] values;
        Map<Integer, Integer> missPointsPerImportance;

        public Programmes(int[] values, Map<Integer, Integer> missPointsPerImportance) {
            this.values = values;
            this.missPointsPerImportance = new HashMap<>();

            for (int importance = 1; importance <= 5; importance++) {
                this.missPointsPerImportance.put(importance, missPointsPerImportance.getOrDefault(importance, 0));
            }
        }

        @Override
        public int compareTo(Programmes other) {
            int maxImportance = getMaxImportance();
            int otherMaxImportance = other.getMaxImportance();
            int totalMaxImportance = Math.max(maxImportance, otherMaxImportance);

            for (int importance = 1; importance <= totalMaxImportance; importance++) {
                int misses = missPointsPerImportance.get(importance);
                int otherMisses = other.missPointsPerImportance.get(importance);
                if (misses != otherMisses) {
                    return Integer.compare(misses, otherMisses);
                }
            }
            return 0;
        }

        private int getMaxImportance() {
            int maxImportance = 0;
            for (int importance : missPointsPerImportance.keySet()) {
                maxImportance = Math.max(maxImportance, importance);
            }
            return maxImportance;
        }
    }

    private static class AlignmentPoint {
        int importance;
        int time;

        public AlignmentPoint(int importance, int time) {
            this.importance = importance;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int dataSet = 1;
        int programmesNumber = FastReader.nextInt();

        while (programmesNumber != 0) {
            int[] programmes = new int[programmesNumber];
            for (int i = 0; i < programmes.length; i++) {
                programmes[i] = FastReader.nextInt();
            }

            AlignmentPoint[] alignmentPoints = new AlignmentPoint[FastReader.nextInt()];
            for (int i = 0; i < alignmentPoints.length; i++) {
                alignmentPoints[i] = new AlignmentPoint(FastReader.nextInt(), FastReader.nextInt());
            }

            Programmes bestOrder = computeBestOrder(programmes, alignmentPoints);
            long totalMissTimes = computeTotalMissTimes(bestOrder);

            outputWriter.printLine(String.format("Data set %d", dataSet));
            outputWriter.print("Order: ");
            for (int i = 0; i < bestOrder.values.length; i++) {
                outputWriter.print(bestOrder.values[i]);

                if (i != bestOrder.values.length - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
            outputWriter.printLine(String.format("Error: %d", totalMissTimes));

            dataSet++;
            programmesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Programmes computeBestOrder(int[] programmes, AlignmentPoint[] alignmentPoints) {
        int[] values = new int[programmes.length];
        List<Programmes> programmesList = new ArrayList<>();

        computeBestOrder(programmes, alignmentPoints, 0, 0, values, programmesList);
        Collections.sort(programmesList);
        return programmesList.get(0);
    }

    private static void computeBestOrder(int[] programmes, AlignmentPoint[] alignmentPoints, int index,
                                         int mask, int[] values, List<Programmes> programmesList) {
        if (mask == (1 << programmes.length) - 1) {
            int[] copyValues = new int[values.length];
            System.arraycopy(values, 0, copyValues, 0, values.length);
            Programmes currentProgrammes = computeMisses(copyValues, alignmentPoints);
            programmesList.add(currentProgrammes);
            return;
        }

        for (int i = 0; i < programmes.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                values[index] = programmes[i];
                computeBestOrder(programmes, alignmentPoints, index + 1, newMask, values, programmesList);
            }
        }
    }

    private static Programmes computeMisses(int[] programmes, AlignmentPoint[] alignmentPoints) {
        Map<Integer, Integer> missTimes = new HashMap<>();

        int[] prefixSum = new int[programmes.length];
        prefixSum[0] = programmes[0];
        for (int i = 1; i < programmes.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + programmes[i];
        }

        for (AlignmentPoint alignmentPoint : alignmentPoints) {
            int shortestMissTime = Integer.MAX_VALUE;

            for (int time : prefixSum) {
                int missTime = Math.abs(alignmentPoint.time - time);
                shortestMissTime = Math.min(shortestMissTime, missTime);
            }

            if (!missTimes.containsKey(alignmentPoint.importance)) {
                missTimes.put(alignmentPoint.importance, 0);
            }
            int importanceMissTime = missTimes.get(alignmentPoint.importance) + shortestMissTime;
            missTimes.put(alignmentPoint.importance, importanceMissTime);
        }
        return new Programmes(programmes, missTimes);
    }

    private static long computeTotalMissTimes(Programmes programmes) {
        long totalMissTimes = 0;
        for (int missTimes : programmes.missPointsPerImportance.values()) {
            totalMissTimes += missTimes;
        }
        return totalMissTimes;
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
