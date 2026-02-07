package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/02/26.
 */
public class ProbabilityGiven {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int friends = FastReader.nextInt();
        int peopleThatBought = FastReader.nextInt();
        int caseId = 1;

        while (friends != 0 || peopleThatBought != 0) {
            double[] buyProbabilities = new double[friends];
            for (int i = 0; i < buyProbabilities.length; i++) {
                buyProbabilities[i] = FastReader.nextDouble();
            }

            double[] probabilitiesGiven = computeProbabilitiesGiven(buyProbabilities, peopleThatBought);
            outputWriter.printLine(String.format("Case %d:", caseId));
            for (double probability : probabilitiesGiven) {
                outputWriter.printLine(String.format("%.6f", probability));
            }

            caseId++;
            friends = FastReader.nextInt();
            peopleThatBought = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double[] computeProbabilitiesGiven(double[] buyProbabilities, int peopleThatBought) {
        int people = buyProbabilities.length;
        double[] probabilityGiven = new double[people];
        double totalProbability = 0;
        int combinations = 1 << people;

        for (int bits = 0; bits < combinations; bits++) {
            if (count1Bits(bits) != peopleThatBought) {
                continue;
            }

            double probability = 1;
            for (int i = 0; i < people; i++) {
                if ((bits & (1 << i)) > 0) {
                    probability *= buyProbabilities[i];
                } else {
                    probability *= (1 - buyProbabilities[i]);
                }
            }

            for (int i = 0; i < people; i++) {
                if ((bits & (1 << i)) > 0) {
                    probabilityGiven[i] += probability;
                }
            }
            totalProbability += probability;
        }

        for (int i = 0; i < people; i++) {
            probabilityGiven[i] /= totalProbability;
        }
        return probabilityGiven;
    }

    private static int count1Bits(int value) {
        int count = 0;
        while (value > 0) {
            value = value & (value - 1);
            count++;
        }
        return count;
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
