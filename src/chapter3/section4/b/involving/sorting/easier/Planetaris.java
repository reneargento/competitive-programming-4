package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/07/22.
 */
public class Planetaris {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] finniShipsSent = new int[FastReader.nextInt()];
        int atliShips = FastReader.nextInt();

        for (int i = 0; i < finniShipsSent.length; i++) {
            finniShipsSent[i] = FastReader.nextInt();
        }
        int maximumPossibleWins = computeMaximumPossibleWins(finniShipsSent, atliShips);
        outputWriter.printLine(maximumPossibleWins);
        outputWriter.flush();
    }

    private static int computeMaximumPossibleWins(int[] finniShipsSent, int atliShips) {
        int maximumPossibleWins = 0;
        Arrays.sort(finniShipsSent);

        for (int shipsSent : finniShipsSent) {
            if (atliShips > shipsSent) {
                maximumPossibleWins++;
                atliShips -= (shipsSent + 1);
            } else {
                break;
            }
        }
        return maximumPossibleWins;
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
