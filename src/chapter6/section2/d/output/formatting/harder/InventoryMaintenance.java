package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/06/2026.
 */
public class InventoryMaintenance {

    private static class Item implements Comparable<Item> {
        String name;
        double cost;
        double sellingPrice;
        int quantity;
        boolean writtenOff;

        public Item(String name, double cost, double sellingPrice) {
            this.name = name;
            this.cost = cost;
            this.sellingPrice = sellingPrice;
        }

        @Override
        public int compareTo(Item other) {
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        Map<String, Item> itemMap = new HashMap<>();
        double profit = 0;
        while (!line.equals("*")) {
            String[] data = line.split("\\s+");
            switch (data[0]) {
                case "new": {
                    Item item = new Item(data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]));
                    itemMap.put(data[1], item);
                }
                break;
                case "delete": {
                    Item item = itemMap.get(data[1]);
                    item.writtenOff = true;
                    profit -= item.quantity * item.cost;
                }
                break;
                case "buy": {
                    Item item = itemMap.get(data[1]);
                    int quantity = Integer.parseInt(data[2]);
                    item.quantity += quantity;
                }
                break;
                case "sell": {
                    Item item = itemMap.get(data[1]);
                    int quantity = Integer.parseInt(data[2]);
                    int quantitySold = Math.min(quantity, item.quantity);
                    profit += (quantitySold * (item.sellingPrice - item.cost));
                    item.quantity -= quantitySold;
                }
                break;
                case "report": {
                    report(itemMap, profit, outputWriter);
                    profit = 0;
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void report(Map<String, Item> itemMap, double profit, OutputWriter outputWriter) {
        double totalValue = 0;
        outputWriter.printLine(String.format("%34s", "INVENTORY REPORT"));
        String headerFormat = "%-14s%-12s%-13s%-15s%-5s";
        outputWriter.printLine(String.format(headerFormat, "Item Name", "Buy At", "Sell At", "On Hand", "Value"));
        outputWriter.printLine(String.format(headerFormat, "---------", "------", "-------", "-------", "-----"));

        List<Item> items = new ArrayList<>(itemMap.values());
        Collections.sort(items);

        for (Item item : items) {
            if (item.writtenOff) {
                continue;
            }
            double value = item.quantity * item.cost;
            totalValue += value;
            outputWriter.printLine(String.format("%-14s%6.2f%13.2f%13s%13.2f", item.name, item.cost, item.sellingPrice,
                    item.quantity, value));
        }
        outputWriter.printLine("------------------------");
        outputWriter.printLine(String.format("%-45s%14.2f", "Total value of inventory", totalValue));
        outputWriter.printLine(String.format("%-45s%14.2f", "Profit since last report", profit));
        outputWriter.printLine();
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