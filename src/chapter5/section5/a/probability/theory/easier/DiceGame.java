package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/02/26.
 */
public class DiceGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int[] gunnarDice = readInput();
        int[] emmaDice = readInput();

        String winner = computeWinner(gunnarDice, emmaDice);
        outputWriter.printLine(winner);
        outputWriter.flush();
    }

    private static int[] readInput() throws IOException {
        int[] input = new int[4];
        for (int i = 0; i < input.length; i++) {
            input[i] = FastReader.nextInt();
        }
        return input;
    }

    private static String computeWinner(int[] gunnarDice, int[] emmaDice) {
        double gunnarAverage = (getSidesAverage(gunnarDice[0], gunnarDice[1]) +
                getSidesAverage(gunnarDice[2], gunnarDice[3])) / 2;
        double emmaAverage = (getSidesAverage(emmaDice[0], emmaDice[1]) +
                getSidesAverage(emmaDice[2], emmaDice[3])) / 2;

        if (gunnarAverage > emmaAverage) {
            return "Gunnar";
        } else if (emmaAverage > gunnarAverage) {
            return "Emma";
        }
        return "Tie";
    }

    private static double getSidesAverage(int start, int end) {
        return (start + end) / (double) 2;
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
