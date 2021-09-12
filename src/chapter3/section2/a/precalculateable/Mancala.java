package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/09/21.
 */
public class Mancala {

    private static class WinningBoard {
        int[] bins;
        int lastBin;

        public WinningBoard(int[] bins, int lastBin) {
            this.bins = bins;
            this.lastBin = lastBin;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        WinningBoard[] winningBoards = computeAllWinningBoards();

        for (int t = 0; t < tests; t++) {
            int dataSetNumber = FastReader.nextInt();
            int binsNumber = FastReader.nextInt();

            WinningBoard winningBoard = winningBoards[binsNumber - 1];
            outputWriter.printLine(String.format("%d %d", dataSetNumber, winningBoard.lastBin));
            int[] bins = winningBoard.bins;

            for (int i = 1; i <= winningBoard.lastBin; i++) {
                outputWriter.print(bins[i]);

                if (i != winningBoard.lastBin) {
                    if (i % 10 == 0) {
                        outputWriter.printLine();
                    } else {
                        outputWriter.print(" ");
                    }
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static WinningBoard[] computeAllWinningBoards() {
        WinningBoard[] winningBoards = new WinningBoard[2001];
        int[] bins = new int[82];
        int lastBin = 0;

        for (int i = 0; i < winningBoards.length; i++) {
            for (int bin = 1; bin < bins.length; bin++) {
                lastBin = Math.max(lastBin, bin);

                if (bins[bin] == 0) {
                    bins[bin] = bin;
                    break;
                } else {
                    bins[bin]--;
                }
            }

            int[] binsCopy = new int[bins.length];
            System.arraycopy(bins, 0, binsCopy, 0, bins.length);
            winningBoards[i] = new WinningBoard(binsCopy, lastBin);
        }
        return winningBoards;
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
