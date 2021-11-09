package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/11/21.
 */
public class DartAMania {

    private static class DartAnalysis {
        int combinations;
        int permutations;

        public DartAnalysis(int combinations, int permutations) {
            this.combinations = combinations;
            this.permutations = permutations;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int score = FastReader.nextInt();

        List<Integer> possibleScores = getPossibleScores();
        DartAnalysis[] results = new DartAnalysis[1000];

        while (score > 0) {
            DartAnalysis dartAnalysis = checkDarts(score, possibleScores, results);

            if (dartAnalysis.combinations == 0) {
                outputWriter.printLine(String.format("THE SCORE OF %d CANNOT BE MADE WITH THREE DARTS.", score));
            } else {
                outputWriter.printLine(String.format("NUMBER OF COMBINATIONS THAT SCORES %d IS %d.", score,
                        dartAnalysis.combinations));
                outputWriter.printLine(String.format("NUMBER OF PERMUTATIONS THAT SCORES %d IS %d.", score,
                        dartAnalysis.permutations));
            }
            outputWriter.printLine("**********************************************************************");

            score = FastReader.nextInt();
        }
        outputWriter.printLine("END OF OUTPUT");
        outputWriter.flush();
    }

    private static List<Integer> getPossibleScores() {
        List<Integer> possibleScores = new ArrayList<>();
        possibleScores.add(0);
        possibleScores.add(50);

        for (int i = 1; i <= 20; i++) {
            possibleScores.add(i);
            possibleScores.add(i * 2);
            possibleScores.add(i * 3);
        }
        return possibleScores;
    }

    private static DartAnalysis checkDarts(int score, List<Integer> possibleScores, DartAnalysis[] results) {
        if (results[score] != null) {
            return results[score];
        }

        Set<String> permutations = new HashSet<>();
        Set<String> combinations = new HashSet<>();

        for (int possibleScore1 : possibleScores) {
            for (int possibleScore2 : possibleScores) {
                for (int possibleScore3 : possibleScores) {
                    if (score - possibleScore1 - possibleScore2 - possibleScore3 == 0) {
                        String permutation = possibleScore1 + "-" + possibleScore2 + "-" + possibleScore3;
                        permutations.add(permutation);

                        int[] darts = new int[3];
                        darts[0] = possibleScore1;
                        darts[1] = possibleScore2;
                        darts[2] = possibleScore3;
                        Arrays.sort(darts);

                        String combination = darts[0] + "-" + darts[1] + "-" + darts[2];
                        combinations.add(combination);
                    }
                }
            }
        }
        results[score] = new DartAnalysis(combinations.size(), permutations.size());
        return results[score];
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
