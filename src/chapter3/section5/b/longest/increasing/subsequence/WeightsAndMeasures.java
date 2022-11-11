package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 11/11/22.
 */
public class WeightsAndMeasures {

    private static class Turtle implements Comparable<Turtle> {
        int weight;
        int strength;

        public Turtle(int weight, int strength) {
            this.weight = weight;
            this.strength = strength;
        }

        @Override
        public int compareTo(Turtle other) {
            if (strength != other.strength) {
                return Integer.compare(strength, other.strength);
            }
            return Integer.compare(weight, other.weight);
        }
    }

    private static final int INFINITE = 100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Turtle> turtles = new ArrayList<>();
        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int weight = Integer.parseInt(data[0]);
            int strength = Integer.parseInt(data[1]);
            turtles.add(new Turtle(weight, strength));

            line = FastReader.getLine();
        }

        int maximumTurtles = computeMaximumTurtles(turtles);
        outputWriter.printLine(maximumTurtles);
        outputWriter.flush();
    }

    private static int computeMaximumTurtles(List<Turtle> turtles) {
        Collections.sort(turtles);

        // int[steps][number of turtles in stack] = minimum weight
        int[][] minimumWeightStack = new int[2][turtles.size() + 1];
        for (int[] weights : minimumWeightStack) {
            Arrays.fill(weights, INFINITE);
        }

        minimumWeightStack[0][0] = 0;
        for (int i = 1; i <= turtles.size(); i++) {
            minimumWeightStack[1][0] = 0;
            for (int j = 1; j <= turtles.size(); j++) {
                minimumWeightStack[1][j] = minimumWeightStack[0][j];
                if (minimumWeightStack[0][j - 1] + turtles.get(i - 1).weight <= turtles.get(i - 1).strength) {
                    minimumWeightStack[1][j] = Math.min(minimumWeightStack[1][j],
                            minimumWeightStack[0][j - 1] + turtles.get(i - 1).weight);
                }
            }
            // Memory optimization
            System.arraycopy(minimumWeightStack[1], 0, minimumWeightStack[0], 0, minimumWeightStack[1].length);
        }

        int maximumTurtles = 0;
        for (int[] weights : minimumWeightStack) {
            for (int stackSize = 1; stackSize < minimumWeightStack[0].length; stackSize++) {
                if (weights[stackSize] < INFINITE) {
                    maximumTurtles = Math.max(maximumTurtles, stackSize);
                }
            }
        }
        return maximumTurtles;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void flush() {
            writer.flush();
        }
    }
}
