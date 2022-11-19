package chapter3.section5.c.zero.one.knapsack;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/11/22.
 */
// Based on https://github.com/morris821028/UVa/blob/master/volume118/11832%20-%20Account%20Book2.cpp
public class AccountBook {

    private static final int OFFSET = 40000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int transactionsNumber = FastReader.nextInt();
        int cashFlow = FastReader.nextInt();

        while (transactionsNumber != 0 || cashFlow != 0) {
            int[] transactions = new int[transactionsNumber];
            for (int i = 0; i < transactions.length; i++) {
                transactions[i] = FastReader.nextInt();
            }

            String transactionTypes = computeTransactionTypes(transactions, cashFlow);
            outputWriter.printLine(transactionTypes);

            transactionsNumber = FastReader.nextInt();
            cashFlow = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String computeTransactionTypes(int[] transactions, int cashFlow) {
        long[][] plus = new long[2][80001];
        long[][] minus = new long[2][80001];
        int left = -transactions[0] + OFFSET;
        int right = transactions[0] + OFFSET;

        int rowIndex = 0;
        plus[rowIndex][transactions[0] + OFFSET] = 1;
        minus[rowIndex][-transactions[0] + OFFSET] = 1;

        for (int i = 1; i < transactions.length; i++) {
            Arrays.fill(plus[1 - rowIndex], 0);
            Arrays.fill(minus[1 - rowIndex], 0);

            for (int value = left; value <= right; value++) {
                if (plus[rowIndex][value] != 0 || minus[rowIndex][value] != 0) {
                    int newPlusValue = value + transactions[i];
                    plus[1 - rowIndex][newPlusValue] |= plus[rowIndex][value] | (1L << i);
                    minus[1 - rowIndex][newPlusValue] |= minus[rowIndex][value];

                    int newMinusValue = value - transactions[i];
                    plus[1 - rowIndex][newMinusValue] |= plus[rowIndex][value];
                    minus[1 - rowIndex][newMinusValue] |= minus[rowIndex][value] | (1L << i);
                }
            }
            left -= transactions[i];
            right += transactions[i];
            rowIndex = 1 - rowIndex;
        }

        long plusCash = plus[rowIndex][cashFlow + OFFSET];
        long minusCash = minus[rowIndex][cashFlow + OFFSET];
        if (plusCash == 0 && minusCash == 0) {
            return "*";
        }

        StringBuilder transactionTypes = new StringBuilder();
        for (int i = 0; i < transactions.length; i++) {
            if ((plusCash & (1L << i)) != 0) {
                if ((minusCash & (1L << i)) != 0) {
                    transactionTypes.append("?");
                } else {
                    transactionTypes.append("+");
                }
            } else {
                transactionTypes.append("-");
            }
        }
        return transactionTypes.toString();
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
