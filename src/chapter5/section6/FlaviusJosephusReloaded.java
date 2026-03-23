package chapter5.section6;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/03/26.
 */
public class FlaviusJosephusReloaded {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int soldiers = FastReader.nextInt();
        while (soldiers != 0) {
            int a = FastReader.nextInt();
            int b = FastReader.nextInt();

            long survivors = computeSurvivors(soldiers, a, b);
            outputWriter.printLine(survivors);
            soldiers = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeSurvivors(long soldiers, long a, long b) {
        long tortoise = computeNextSoldier(0, soldiers, a, b);
        long hare = computeNextSoldier(computeNextSoldier(0, soldiers, a, b), soldiers, a, b);

        while (tortoise != hare) {
            tortoise = computeNextSoldier(tortoise, soldiers, a, b);
            hare = computeNextSoldier(computeNextSoldier(hare, soldiers, a, b), soldiers, a, b);
        }

        hare = 0;
        while (tortoise != hare) {
            tortoise = computeNextSoldier(tortoise, soldiers, a, b);
            hare = computeNextSoldier(hare, soldiers, a, b);
        }

        long cycleLength = 1;
        hare = computeNextSoldier(tortoise, soldiers, a, b);
        while (tortoise != hare) {
            hare = computeNextSoldier(hare, soldiers, a, b);
            cycleLength++;
        }
        return soldiers - cycleLength;
    }

    private static long computeNextSoldier(long soldierId, long soldiers, long a, long b) {
        return (((a * soldierId) % soldiers) * soldierId + b) % soldiers;
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
