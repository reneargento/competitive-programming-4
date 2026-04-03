package chapter5.section7;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/04/26.
 */
public class EuclidsGame {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int number1 = FastReader.nextInt();
        int number2 = FastReader.nextInt();
        while (number1 != 0 || number2 != 0) {
            String winner = computeWinner(0, number1, number2);
            outputWriter.printLine(winner + " wins");

            number1 = FastReader.nextInt();
            number2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String computeWinner(int playerId, int number1, int number2) {
        int minNumber = Math.min(number1, number2);
        int maxNumber = Math.max(number1, number2);

        // Check if winning move exists
        if (maxNumber % minNumber == 0) {
            return playerId == 0 ? "Stan" : "Ollie";
        }

        if (maxNumber / minNumber == 1) {
            String winner = computeWinner(playerId, minNumber, maxNumber - minNumber);
            if (winner.equals("Stan")) {
                return "Ollie";
            } else {
                return "Stan";
            }
        }
        return "Stan";
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
