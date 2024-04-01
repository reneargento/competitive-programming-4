package chapter4.section5.b.variants;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/03/24.
 */
public class ArbitrageII {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int currencies = FastReader.nextInt();
        int caseID = 1;

        while (currencies != 0) {
            Map<String, Integer> currencyNameToID = new HashMap<>();
            for (int i = 0; i < currencies; i++) {
                getCurrencyID(currencyNameToID, FastReader.next());
            }

            int tableLength = FastReader.nextInt();
            double[][] rates = new double[currencies][currencies];
            for (int currencyID = 0; currencyID < rates.length; currencyID++) {
               // Arrays.fill(rates[currencyID], Double.POSITIVE_INFINITY);
                rates[currencyID][currencyID] = 1;
            }

            for (int i = 0; i < tableLength; i++) {
                int currencyID1 = getCurrencyID(currencyNameToID, FastReader.next());
                double exchangeRate = FastReader.nextDouble();
                int currencyID2 = getCurrencyID(currencyNameToID, FastReader.next());
                rates[currencyID1][currencyID2] = exchangeRate;
            }
            outputWriter.print(String.format("Case %d: ", caseID));
            outputWriter.printLine(isArbitragePossible(rates) ? "Yes" : "No");

            caseID++;
            currencies = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean isArbitragePossible(double[][] rates) {
        floydWarshall(rates);

        for (int currencyID = 0; currencyID < rates.length; currencyID++) {
            if (rates[currencyID][currencyID] > 1) {
                return true;
            }
        }
        return false;
    }

    private static int getCurrencyID(Map<String, Integer> currencyNameToID, String currencyName) {
        if (!currencyNameToID.containsKey(currencyName)) {
            currencyNameToID.put(currencyName, currencyNameToID.size());
        }
        return currencyNameToID.get(currencyName);
    }

    private static void floydWarshall(double[][] distances) {
        for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < distances.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < distances.length; vertex3++) {
                    if (distances[vertex2][vertex3] < distances[vertex2][vertex1] * distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] * distances[vertex1][vertex3];
                    }
                }
            }
        }
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
