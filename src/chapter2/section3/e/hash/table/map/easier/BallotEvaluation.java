package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/05/21.
 */
public class BallotEvaluation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int parties = FastReader.nextInt();
        int guesses = FastReader.nextInt();

        Map<String, Integer> partiesResults = new HashMap<>();

        for (int i = 0; i < parties; i++) {
            String partyName = FastReader.next();
            int result = (int) (FastReader.nextDouble() * 10);
            partiesResults.put(partyName, result);
        }

        for (int guess = 1; guess <= guesses; guess++) {
            String[] guessData = FastReader.getLine().split(" ");

            boolean isGuessCorrect = evaluateGuess(partiesResults, guessData);
            outputWriter.print(String.format("Guess #%d was ", guess));
            outputWriter.printLine(isGuessCorrect ? "correct." : "incorrect.");
        }
        outputWriter.flush();
    }

    private static boolean evaluateGuess(Map<String, Integer> partiesResults, String[] guessData) {
        double totalPercentage = 0;

        for (int i = 0; i < guessData.length - 2; i++) {
            if (guessData[i].equals("+")) {
                continue;
            }
            totalPercentage += partiesResults.get(guessData[i]);
        }

        double guessedPercentage = (int) Double.parseDouble(guessData[guessData.length - 1]) * 10;
        String comparison = guessData[guessData.length - 2];
        switch (comparison) {
            case "<": return totalPercentage < guessedPercentage;
            case ">": return totalPercentage > guessedPercentage;
            case "<=": return totalPercentage <= guessedPercentage;
            case ">=": return totalPercentage >= guessedPercentage;
            default: return totalPercentage == guessedPercentage;
        }
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
