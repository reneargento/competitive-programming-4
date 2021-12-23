package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;

/**
 * Created by Rene Argento on 21/12/21.
 */
public class Ratio {

    private static class Result {
        int nominator;
        double approximation;

        public Result(int nominator, double approximation) {
            this.nominator = nominator;
            this.approximation = approximation;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int caseNumber = 0;

        while (line != null) {
            if (caseNumber > 0) {
                outputWriter.printLine();
            }

            String[] data = line.split(" ");
            int gainers = Integer.parseInt(data[0]);
            int losers = Integer.parseInt(data[1]);

            int gcd = gcd(gainers, losers);
            int reducedGainers = gainers / gcd;
            int reducedLosers = losers / gcd;
            findApproximations(reducedGainers, reducedLosers, outputWriter);

            caseNumber++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void findApproximations(int reducedGainers, int reducedLosers, OutputWriter outputWriter) {
        double exactAnswer = reducedGainers / (double) reducedLosers;
        double bestDistance = Double.MAX_VALUE;

        for (int denominator = 1; denominator <= reducedLosers; denominator++) {
            Result result = computeNominatorAndApproximation(denominator, bestDistance, exactAnswer, reducedGainers);

            if (result != null) {
                bestDistance = result.approximation;
                outputWriter.printLine(String.format("%d/%d", result.nominator, denominator));
            }
        }
    }

    private static Result computeNominatorAndApproximation(double denominator, double bestDistance,
                                                           double exactAnswer, int maxNominator) {
        boolean foundImprovement = false;
        int bestNominator = 1;

        for (int nominator = 1; nominator <= maxNominator; nominator++) {
            double approximation = nominator / denominator;
            double distance = Math.abs(approximation - exactAnswer);

            if (distance < bestDistance) {
                foundImprovement = true;
                bestDistance = distance;
                bestNominator = nominator;
            }
        }

        if (foundImprovement) {
            return new Result(bestNominator, bestDistance);
        } else {
            return null;
        }
    }

    private static int gcd(int number1, int number2) {
        if (number2 == 0) {
            return number1;
        }
        return gcd(number2, number1 % number2);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
