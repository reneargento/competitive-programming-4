package chapter5.section6;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/03/26.
 */
public class PartyGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] cardsGiven = new int[FastReader.nextInt()];
            for (int i = 0; i < cardsGiven.length; i++) {
                cardsGiven[i] = FastReader.nextInt() - 1;
            }
            String result = canAllEat(cardsGiven);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String canAllEat(int[] cardsGiven) {
        boolean[] isInRightPlace = new boolean[cardsGiven.length];

        for (int i = 0; i < cardsGiven.length; i++) {
            int[] nextValues = new int[cardsGiven.length];
            for (int j = 0; j < cardsGiven.length; j++) {
                nextValues[j] = cardsGiven[cardsGiven[j]];
                if (nextValues[j] == j) {
                    isInRightPlace[j] = true;
                }
            }
            cardsGiven = nextValues;
        }

        for (boolean isCorrect : isInRightPlace) {
            if (!isCorrect) {
                return "Some starve.";
            }
        }
        return "All can eat.";
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
