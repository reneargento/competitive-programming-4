package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/04/22.
 */
public class Svada {

    private static class Monkey {
        int searchTime;
        int actionTime;

        public Monkey(int searchTime, int actionTime) {
            this.searchTime = searchTime;
            this.actionTime = actionTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int totalTime = FastReader.nextInt();
        Monkey[] type1Monkeys = new Monkey[FastReader.nextInt()];
        for (int i = 0; i < type1Monkeys.length; i++) {
            type1Monkeys[i] = new Monkey(FastReader.nextInt(), FastReader.nextInt());
        }

        Monkey[] type2Monkeys = new Monkey[FastReader.nextInt()];
        for (int i = 0; i < type2Monkeys.length; i++) {
            type2Monkeys[i] = new Monkey(FastReader.nextInt(), FastReader.nextInt());
        }

        long timeBetweenMonkeys = computeTimeBetweenMonkeys(totalTime, type1Monkeys, type2Monkeys);
        outputWriter.printLine(timeBetweenMonkeys);
        outputWriter.flush();
    }

    private static long computeTimeBetweenMonkeys(int totalTime, Monkey[] type1Monkeys, Monkey[] type2Monkeys) {
        long low = 0;
        long high = 1000000000;
        long solution = -1;

        while (low <= high) {
            long middle = low + (high - low) / 2;
            long coconutsRemaining = computeCoconutsRemaining(totalTime, type1Monkeys, type2Monkeys, middle);

            if (coconutsRemaining <= 0) {
                solution = middle;
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return solution;
    }

    private static long computeCoconutsRemaining(long totalTime, Monkey[] type1Monkeys, Monkey[] type2Monkeys,
                                                 long timeBetweenMonkeys) {
        long totalCoconuts = 0;

        for (Monkey monkey : type1Monkeys) {
            if (monkey.searchTime <= timeBetweenMonkeys) {
                totalCoconuts++;
            }
            long timeSpentProducingCoconuts = timeBetweenMonkeys - monkey.searchTime;
            long coconuts = Math.max(timeSpentProducingCoconuts / monkey.actionTime, 0);
            totalCoconuts += coconuts;
        }

        for (Monkey monkey : type2Monkeys) {
            if (timeBetweenMonkeys + monkey.searchTime <= totalTime) {
                totalCoconuts--;
            }
            long timeSpentOpeningCoconuts = totalTime - timeBetweenMonkeys - monkey.searchTime;
            long coconutsOpened = Math.max(timeSpentOpeningCoconuts / monkey.actionTime, 0);
            totalCoconuts -= coconutsOpened;
        }
        return totalCoconuts;
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
