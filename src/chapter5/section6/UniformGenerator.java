package chapter5.section6;

import java.io.*;

/**
 * Created by Rene Argento on 24/03/26.
 */
public class UniformGenerator {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int step = Integer.parseInt(data[0]);
            int mod = Integer.parseInt(data[1]);

            outputWriter.print(String.format("%10d%10d%4s", step, mod, " "));
            if (isGoodChoice(step, mod)) {
                outputWriter.printLine("Good Choice");
            } else {
                outputWriter.printLine("Bad Choice");
            }
            outputWriter.printLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isGoodChoice(int step, int mod) {
        long tortoise = generatePseudoRandomNumber(0, step, mod);
        long hare = generatePseudoRandomNumber(0, step, mod);
        hare = generatePseudoRandomNumber(hare, step, mod);

        while (tortoise != hare) {
            tortoise = generatePseudoRandomNumber(tortoise, step, mod);
            hare = generatePseudoRandomNumber(hare, step, mod);
            hare = generatePseudoRandomNumber(hare, step, mod);
        }

        int cycleLength = 1;
        hare = generatePseudoRandomNumber(hare, step, mod);
        while (tortoise != hare) {
            hare = generatePseudoRandomNumber(hare, step, mod);
            cycleLength++;
        }
        return cycleLength == mod;
    }

    private static long generatePseudoRandomNumber(long seed, int step, int mod) {
        return (seed + step) % mod;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void flush() {
            writer.flush();
        }
    }
}
