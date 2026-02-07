package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/02/26.
 */
public class BobbysBet {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int minimumValue = FastReader.nextInt();
            int sides = FastReader.nextInt();
            int minimumRolls = FastReader.nextInt();
            int totalRolls = FastReader.nextInt();
            int multiplier = FastReader.nextInt();

            String result = shouldBobbyBet(minimumValue, sides, minimumRolls, totalRolls, multiplier);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String shouldBobbyBet(int minimumValue, double sides, int minimumRolls, int totalRolls, int multiplier) {
        double successProbability = (sides - minimumValue + 1) / sides;
        double failureProbability = 1 - successProbability;
        double winProbability = countWins(0, 0, minimumRolls, totalRolls, multiplier,
                successProbability, failureProbability);
        return winProbability > 1 ? "yes" : "no";
    }

    private static double countWins(int currentRolls, int winRows, int minimumRolls, int totalRolls,
                                    int multiplier, double successProbability, double failureProbability) {
        if (currentRolls == totalRolls) {
            if (winRows >= minimumRolls) {
                return multiplier;
            } else {
                return 0;
            }
        }

        return successProbability * countWins(currentRolls + 1, winRows + 1, minimumRolls, totalRolls,
                multiplier, successProbability, failureProbability)
                + failureProbability * countWins(currentRolls + 1, winRows, minimumRolls, totalRolls,
                multiplier, successProbability, failureProbability);
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
