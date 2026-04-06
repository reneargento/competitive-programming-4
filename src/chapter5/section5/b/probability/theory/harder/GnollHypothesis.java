package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 14/03/26.
 */
public class GnollHypothesis {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        double[] spawnChances = new double[inputReader.nextInt()];
        int poolSize = inputReader.nextInt();
        for (int i = 0; i < spawnChances.length; i++) {
            spawnChances[i] = inputReader.nextDouble();
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

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        private InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        private int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private double nextDouble() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.') {
                res *= 10;
                res += c - '0';
                c = snext();
            }
            if (c == '.') {
                c = snext();
                double m = 1;
                while (!isSpaceChar(c)) {
                    if (c == 'e' || c == 'E') {
                        return res * Math.pow(10, nextInt());
                    }
                    m /= 10;
                    res += (c - '0') * m;
                    c = snext();
                }
            }
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
