package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/02/26.
 */
public class France98 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] idToNames = new String[16];
        for (int i = 0; i < idToNames.length; i++) {
            idToNames[i] = FastReader.next();
        }
        double[][] winProbabilities = new double[16][16];
        for (int teamId1 = 0; teamId1 < winProbabilities.length; teamId1++) {
            for (int teamId2 = 0; teamId2 < winProbabilities[0].length; teamId2++) {
                winProbabilities[teamId1][teamId2] = FastReader.nextInt() / 100.0;
            }
        }

        double[] cupWinProbabilities = computeCupWinProbabilities(winProbabilities);
        for (int teamId = 0; teamId < winProbabilities.length; teamId++) {
            outputWriter.printLine(String.format("%-10s p=%.2f%%", idToNames[teamId], cupWinProbabilities[teamId] * 100));
        }
        outputWriter.flush();
    }

    private static double[] computeCupWinProbabilities(double[][] winProbabilities) {
        int teams = winProbabilities.length;
        double[] cupWinProbabilitiesPrevious = new double[teams];
        double[] cupWinProbabilities = new double[teams];
        Arrays.fill(cupWinProbabilitiesPrevious, 1.0);

        for (int round = 0, groupSize = 1; round < 4; round++, groupSize *= 2) {
            for (int teamId = 0; teamId < teams; teamId++) {
                int groupId = teamId / groupSize;
                int startIndex;
                if (groupId % 2 == 0) {
                    startIndex = groupId * groupSize + groupSize;
                } else {
                    startIndex = groupId * groupSize - groupSize;
                }

                cupWinProbabilities[teamId] = 0;
                for (int otherTeamOffset = 0; otherTeamOffset < groupSize; otherTeamOffset++) {
                    int otherTeamId = startIndex + otherTeamOffset;
                    cupWinProbabilities[teamId] += cupWinProbabilitiesPrevious[otherTeamId] *
                            winProbabilities[teamId][otherTeamId];
                }
                cupWinProbabilities[teamId] *= cupWinProbabilitiesPrevious[teamId];
            }
            cupWinProbabilitiesPrevious = cupWinProbabilities.clone();
        }
        return cupWinProbabilitiesPrevious;
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
