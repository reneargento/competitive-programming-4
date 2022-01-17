package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/01/22.
 */
public class InTheAirport {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int itemsNumber = FastReader.nextInt();
            int[] cakes = new int[FastReader.nextInt()];
            int[] drinks = new int[FastReader.nextInt()];

            long totalPrice = 0;
            int itemsIndex = 0;

            for (int i = 0; i < cakes.length; i++) {
                int price = FastReader.nextInt();
                totalPrice += price;
                cakes[i] = price;
                itemsIndex++;
            }
            for (int i = 0; i < drinks.length; i++) {
                int price = FastReader.nextInt();
                totalPrice += price;
                drinks[i] = price;
                itemsIndex++;
            }
            for (int i = itemsIndex; i < itemsNumber; i++) {
                totalPrice += FastReader.nextInt();
            }
            double averagePrice = totalPrice / (double) itemsNumber;

            int selectedCake = chooseClosestItem(cakes, averagePrice);
            int selectedDrink = chooseClosestItem(drinks, averagePrice);
            outputWriter.printLine(String.format("Case #%d: %d %d", t, selectedCake, selectedDrink));
        }
        outputWriter.flush();
    }

    private static int chooseClosestItem(int[] items, double averagePrice) {
        double minimumDifference = Double.MAX_VALUE;
        int selectedItemPrice = 0;

        for (int itemPrice : items) {
            double priceDifference = Math.abs(itemPrice - averagePrice);

            if (priceDifference < minimumDifference
                    || (priceDifference == minimumDifference && itemPrice < selectedItemPrice)) {
                minimumDifference = priceDifference;
                selectedItemPrice = itemPrice;
            }
        }
        return selectedItemPrice;
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
