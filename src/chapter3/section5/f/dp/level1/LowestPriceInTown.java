package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/01/23.
 */
public class LowestPriceInTown {

    private static class Pack {
        int quantity;
        double price;

        public Pack(int quantity, double price) {
            this.quantity = quantity;
            this.price = price;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int caseId = 1;

        while (line != null) {
            String[] data = line.split(" ");
            double unitPrice = Double.parseDouble(data[0]);
            Pack[] packs = new Pack[Integer.parseInt(data[1]) + 1];
            packs[0] = new Pack(1, unitPrice);
            for (int i = 1; i < packs.length; i++) {
                packs[i] = new Pack(FastReader.nextInt(), FastReader.nextDouble());
            }

            outputWriter.printLine(String.format("Case %d:", caseId));
            String[] queries = FastReader.getLine().split(" ");
            for (String query : queries) {
                int targetQuantity = Integer.parseInt(query);
                double[] dp = createDpArray();
                double lowestPrice = computeLowestPrice(dp, packs, targetQuantity, 0);
                outputWriter.printLine(String.format("Buy %d for $%.2f", targetQuantity, lowestPrice));
            }
            caseId++;
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double[] createDpArray() {
        // dp[quantity]
        double[] dp = new double[101];
        Arrays.fill(dp, -1);
        return dp;
    }

    private static double computeLowestPrice(double[] dp, Pack[] packs, int targetQuantity, int currentQuantity) {
        if (currentQuantity >= targetQuantity) {
            return 0;
        }
        if (dp[currentQuantity] != -1) {
            return dp[currentQuantity];
        }

        double lowestPrice = Double.POSITIVE_INFINITY;
        for (Pack pack : packs) {
            int newQuantity = currentQuantity + pack.quantity;
            double price = pack.price + computeLowestPrice(dp, packs, targetQuantity, newQuantity);
            lowestPrice = Math.min(lowestPrice, price);
        }
        dp[currentQuantity] = lowestPrice;
        return lowestPrice;
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
