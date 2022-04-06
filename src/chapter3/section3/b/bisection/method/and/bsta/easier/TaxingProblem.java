package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/04/22.
 */
public class TaxingProblem {

    private static final double EPSILON = 0.0000001;

    private static class TaxBand {
        double size;
        double percentageTax;

        public TaxBand(double size, double percentageTax) {
            this.size = size;
            this.percentageTax = percentageTax / 100.00;
        }
    }

    private static class Friend {
        double moneyEarned;
        double targetMoney;

        public Friend(double moneyEarned, double targetMoney) {
            this.moneyEarned = moneyEarned;
            this.targetMoney = targetMoney;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        TaxBand[] taxBands = new TaxBand[FastReader.nextInt()];
        for (int i = 0; i < taxBands.length; i++) {
            taxBands[i] = new TaxBand(FastReader.nextDouble(), FastReader.nextDouble());
        }
        double percentageTaxAboveBands = FastReader.nextDouble() / 100;
        Friend[] friends = new Friend[FastReader.nextInt()];
        for (int i = 0; i < friends.length; i++) {
            friends[i] = new Friend(FastReader.nextDouble(), FastReader.nextDouble());
        }

        for (Friend friend : friends) {
            double moneyToPayFriend = computeMoneyToPayFriend(taxBands, percentageTaxAboveBands, friend);
            outputWriter.printLine(String.format("%.6f", moneyToPayFriend));
        }
        outputWriter.flush();
    }

    private static double computeMoneyToPayFriend(TaxBand[] taxBands, double percentageTaxAboveBands,
                                                  Friend friend) {
        double low = 0;
        double high = 1000000000000.00;
        double moneyToGive = -1;

        for (int i = 0; i < 100; i++) {
            double middle = low + (high - low) / 2;
            double moneyAfterTax = moneyAfterTax(taxBands, percentageTaxAboveBands, friend.moneyEarned, middle);
            moneyToGive = middle;

            if (Math.abs(moneyAfterTax - friend.targetMoney) < EPSILON) {
                return middle;
            } else if (moneyAfterTax < friend.targetMoney) {
                low = middle;
            } else {
                high = middle;
            }
        }
        return moneyToGive;
    }

    private static double moneyAfterTax(TaxBand[] taxBands, double percentageTaxAboveBands, double moneyEarned,
                                        double moneyToGive) {
        double moneyAfterTax = 0;

        for (TaxBand taxBand : taxBands) {
            if (taxBand.size <= moneyEarned) {
                moneyEarned -= taxBand.size;
                continue;
            }

            double taxBandSize = taxBand.size - moneyEarned;
            moneyEarned = 0;

            double moneyToTax = Math.min(taxBandSize, moneyToGive);
            moneyAfterTax += moneyToTax * (1 - taxBand.percentageTax);
            moneyToGive -= moneyToTax;
            if (moneyToGive == 0) {
                break;
            }
        }
        moneyAfterTax += moneyToGive * (1 - percentageTaxAboveBands);
        return moneyAfterTax;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
