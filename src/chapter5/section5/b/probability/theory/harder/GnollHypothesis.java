package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/03/26.
 */
public class GnollHypothesis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        double[] spawnChances = new double[FastReader.nextInt()];
        int poolSize = FastReader.nextInt();
        for (int i = 0; i < spawnChances.length; i++) {
            spawnChances[i] = FastReader.nextDouble();
        }

        double[] effectiveSpawns = computeEffectiveSpawns(spawnChances, poolSize);
        outputWriter.print(effectiveSpawns[0]);
        for (int i = 1; i < effectiveSpawns.length; i++) {
            outputWriter.print(" " + effectiveSpawns[i]);
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static double[] computeEffectiveSpawns(double[] spawnChances, int poolSize) {
        int monsterTypes = spawnChances.length;
        double[] effectiveSpawns = new double[monsterTypes];
        if (poolSize == 1) {
            Arrays.fill(effectiveSpawns, 100.0 / monsterTypes);
            return effectiveSpawns;
        }

        double[] weights = computeWeights(spawnChances, poolSize);
        spawnChances = duplicateArray(spawnChances);

        for (int spawnId = 0; spawnId < effectiveSpawns.length; spawnId++) {
            double sum = 0;
            double effectiveSpawn = 0;

            for (int i = 0; i < monsterTypes - poolSize + 1; i++) {
                sum += spawnChances[spawnId + monsterTypes - i];
                effectiveSpawn += sum * weights[i];
            }
            effectiveSpawns[spawnId] = effectiveSpawn;
        }
        return effectiveSpawns;
    }

    private static double[] computeWeights(double[] spawnChances, int poolSize) {
        double[] weights = new double[spawnChances.length];
        int monsterTypes = spawnChances.length;
        double[][] dp = new double[monsterTypes + 1][poolSize + 1];
        for (double[] values : dp) {
            Arrays.fill(values, -1.0);
        }

        weights[0] = combinations(monsterTypes - 2, poolSize - 2, dp) /
                combinations(monsterTypes, poolSize, dp);
        for (int i = 0; i < monsterTypes - poolSize; i++) {
            weights[i + 1] = weights[i] * (double) (monsterTypes - poolSize - i) / (monsterTypes - 2 - i);
        }
        return weights;
    }

    private static double combinations(int totalNumbers, int numbersToChoose, double[][] dp) {
        if (numbersToChoose == 0 || numbersToChoose == totalNumbers) {
            return 1;
        }
        if (totalNumbers < numbersToChoose) {
            return 0;
        }
        if (dp[totalNumbers][numbersToChoose] != -1) {
            return dp[totalNumbers][numbersToChoose];
        }

        dp[totalNumbers][numbersToChoose] =
                combinations(totalNumbers - 1, numbersToChoose - 1, dp) +
                combinations(totalNumbers - 1, numbersToChoose, dp);
        return dp[totalNumbers][numbersToChoose];
    }

    private static double[] duplicateArray(double[] values) {
        double[] newValues = new double[values.length * 2];
        for (int i = 0; i < values.length; i++) {
            newValues[i] = values[i];
            newValues[i + values.length] = values[i];
        }
        return newValues;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
