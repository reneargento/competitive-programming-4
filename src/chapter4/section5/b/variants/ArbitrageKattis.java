package chapter4.section5.b.variants;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/03/24.
 */
public class ArbitrageKattis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int currencies = FastReader.nextInt();

        while (currencies != 0) {
            double[][] values = new double[currencies][currencies];
            for (int currencyCode = 0; currencyCode < values.length; currencyCode++) {
                values[currencyCode][currencyCode] = 1;
            }

            Map<String, Integer> currencyCodeToIDMap = new HashMap<>();
            for (int c = 0; c < currencies; c++) {
                getCurrencyID(currencyCodeToIDMap, FastReader.next());
            }

            int exchangeRates = FastReader.nextInt();
            for (int e = 0; e < exchangeRates; e++) {
                int currencyID1 = getCurrencyID(currencyCodeToIDMap, FastReader.next());
                int currencyID2 = getCurrencyID(currencyCodeToIDMap, FastReader.next());

                String[] rate = FastReader.next().split(":");
                double currency1Amount = Integer.parseInt(rate[0]);
                double currency2Amount = Integer.parseInt(rate[1]);
                values[currencyID1][currencyID2] = currency2Amount / currency1Amount;
            }

            outputWriter.printLine(isArbitragePossible(values) ? "Arbitrage" : "Ok");
            currencies = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean isArbitragePossible(double[][] values) {
        floydWarshall(values);
        for (int currencyID1 = 0; currencyID1 < values.length; currencyID1++) {
            if (values[currencyID1][currencyID1] > 1) {
                return true;
            }
        }
        return false;
    }

    private static void floydWarshall(double[][] distances) {
        int vertices = distances.length;

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    if (distances[vertex2][vertex3] < distances[vertex2][vertex1] * distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] * distances[vertex1][vertex3];
                    }
                }
            }
        }
    }

    private static int getCurrencyID(Map<String, Integer> currencyCodeToIDMap, String currencyCode) {
        if (!currencyCodeToIDMap.containsKey(currencyCode)) {
            currencyCodeToIDMap.put(currencyCode, currencyCodeToIDMap.size());
        }
        return currencyCodeToIDMap.get(currencyCode);
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
