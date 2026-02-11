package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/02/26.
 */
public class FirstOrchard {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int redFruits = FastReader.nextInt();
        int greenFruits = FastReader.nextInt();
        int blueFruits = FastReader.nextInt();
        int yellowFruits = FastReader.nextInt();
        int ravenSteps = FastReader.nextInt();

        double winProbability = computeWinProbability(redFruits, greenFruits, blueFruits, yellowFruits, ravenSteps);
        outputWriter.printLine(winProbability);
        outputWriter.flush();
    }

    private static double computeWinProbability(int redFruits, int greenFruits, int blueFruits, int yellowFruits,
                                                int ravenSteps) {
        double[][][][][] dp = new double[5][5][5][5][9];
        for (double[][][][] values1 : dp) {
            for (double[][][] values2 : values1) {
                for (double[][] values3 : values2) {
                    for (double[] values4 : values3) {
                        Arrays.fill(values4, -1);
                    }
                }
            }
        }
        return computeWinProbability(redFruits, greenFruits, blueFruits, yellowFruits, ravenSteps, dp);
    }

    private static double computeWinProbability(int redFruits, int greenFruits, int blueFruits, int yellowFruits,
                                                int ravenSteps, double[][][][][] dp) {
        if (gameWon(redFruits, greenFruits, blueFruits, yellowFruits)) {
            return 1;
        }
        if (ravenSteps == 0) {
            return 0;
        }
        if (dp[redFruits][greenFruits][blueFruits][yellowFruits][ravenSteps] != -1) {
            return dp[redFruits][greenFruits][blueFruits][yellowFruits][ravenSteps];
        }

        double diceRoll = 1.0 / computeValidSides(redFruits, greenFruits, blueFruits, yellowFruits);
        double totalProbability = 0;
        if (redFruits > 0) {
            totalProbability += diceRoll * computeWinProbability(redFruits - 1, greenFruits, blueFruits, yellowFruits, ravenSteps, dp);
        }
        if (greenFruits > 0) {
            totalProbability += diceRoll * computeWinProbability(redFruits, greenFruits - 1, blueFruits, yellowFruits, ravenSteps, dp);
        }
        if (blueFruits > 0) {
            totalProbability += diceRoll * computeWinProbability(redFruits, greenFruits, blueFruits - 1, yellowFruits, ravenSteps, dp);
        }
        if (yellowFruits > 0) {
            totalProbability += diceRoll * computeWinProbability(redFruits, greenFruits, blueFruits, yellowFruits - 1, ravenSteps, dp);
        }

        // Basket
        if (isHighestFrequency(redFruits, greenFruits, blueFruits, yellowFruits)) {
            totalProbability += diceRoll * computeWinProbability(redFruits - 1, greenFruits, blueFruits, yellowFruits, ravenSteps, dp);
        } else if (isHighestFrequency(greenFruits, redFruits, blueFruits, yellowFruits)) {
            totalProbability += diceRoll * computeWinProbability(redFruits, greenFruits - 1, blueFruits, yellowFruits, ravenSteps, dp);
        } else if (isHighestFrequency(blueFruits, redFruits, greenFruits, yellowFruits)) {
            totalProbability += diceRoll * computeWinProbability(redFruits, greenFruits, blueFruits - 1, yellowFruits, ravenSteps, dp);
        } else {
            totalProbability += diceRoll * computeWinProbability(redFruits, greenFruits, blueFruits, yellowFruits - 1, ravenSteps, dp);
        }
        totalProbability += diceRoll * computeWinProbability(redFruits, greenFruits, blueFruits, yellowFruits, ravenSteps - 1, dp);

        dp[redFruits][greenFruits][blueFruits][yellowFruits][ravenSteps] = totalProbability;
        return totalProbability;
    }

    private static int computeValidSides(int redFruits, int greenFruits, int blueFruits, int yellowFruits) {
        int validSides = 2;
        if (redFruits > 0) {
            validSides++;
        }
        if (greenFruits > 0) {
            validSides++;
        }
        if (blueFruits > 0) {
            validSides++;
        }
        if (yellowFruits > 0) {
            validSides++;
        }
        return validSides;
    }

    private static boolean isHighestFrequency(int target, int fruits2, int fruits3, int fruits4) {
        return target >= fruits2 && target >= fruits3 && target >= fruits4;
    }

    private static boolean gameWon(int redFruits, int greenFruits, int blueFruits, int yellowFruits) {
        return redFruits == 0 && greenFruits == 0 && blueFruits == 0 && yellowFruits == 0;
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
