package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/02/25.
 */
public class CheapestBase {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            if (t > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Case %d:", t));

            int[] costs = new int[36];
            for (int i = 0; i < 36; i++) {
                costs[i] = FastReader.nextInt();
            }

            int queries = FastReader.nextInt();
            for (int q = 0; q < queries; q++) {
                int number = FastReader.nextInt();
                List<Integer> cheapestBases = computeCheapestBases(costs, number);
                outputWriter.print(String.format("Cheapest base(s) for number %d:", number));
                for (int base : cheapestBases) {
                    outputWriter.print(" " + base);
                }
                outputWriter.printLine();
            }
        }
        outputWriter.flush();
    }

    private static List<Integer> computeCheapestBases(int[] costs, int number) {
        List<Integer> cheapestBases = new ArrayList<>();
        long cheapestValue = Long.MAX_VALUE;

        for (int base = 2; base <= 36; base++) {
            String numberInBase = Integer.toString(number, base).toUpperCase();
            long cost = 0;

            for (char symbol : numberInBase.toCharArray()) {
                int symbolIndex = getSymbolIndex(symbol);
                cost += costs[symbolIndex];
            }

            if (cost < cheapestValue) {
                cheapestValue = cost;
                cheapestBases = new ArrayList<>();
                cheapestBases.add(base);
            } else if (cost == cheapestValue) {
                cheapestBases.add(base);
            }
        }
        return cheapestBases;
    }

    private static int getSymbolIndex(char symbol) {
        if (Character.isDigit(symbol)) {
            return Character.getNumericValue(symbol);
        }
        return 10 + (symbol - 'A');
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
