package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/06/21.
 */
public class BaconEggsAndSpam {

    private static class ItemReport implements Comparable<ItemReport> {
        String menuItem;
        List<String> customers;

        public ItemReport(String menuItem, List<String> customers) {
            this.menuItem = menuItem;
            this.customers = customers;
        }

        @Override
        public int compareTo(ItemReport other) {
            return menuItem.compareTo(other.menuItem);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int customers = FastReader.nextInt();

        while (customers != 0) {
            Map<String, List<String>> menuItemToCustomersMap = new HashMap<>();

            for (int i = 0; i < customers; i++) {
                String[] data = FastReader.getLine().split(" ");
                String customer = data[0];

                for (int itemIndex = 1; itemIndex < data.length; itemIndex++) {
                    String menuItem = data[itemIndex];
                    if (!menuItemToCustomersMap.containsKey(menuItem)) {
                        menuItemToCustomersMap.put(menuItem, new ArrayList<>());
                    }
                    menuItemToCustomersMap.get(menuItem).add(customer);
                }
            }
            printReport(menuItemToCustomersMap, outputWriter);
            customers = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void printReport(Map<String, List<String>> menuItemToCustomersMap,
                                    OutputWriter outputWriter) {
        List<ItemReport> reports = new ArrayList<>();
        for (Map.Entry<String, List<String>> reportEntry : menuItemToCustomersMap.entrySet()) {
            reports.add(new ItemReport(reportEntry.getKey(), reportEntry.getValue()));
        }
        Collections.sort(reports);

        for (ItemReport itemReport : reports) {
            Collections.sort(itemReport.customers);
            outputWriter.print(itemReport.menuItem);

            for (int i = 0; i < itemReport.customers.size(); i++) {
                outputWriter.print(" " + itemReport.customers.get(i));
            }
            outputWriter.printLine();
        }
        outputWriter.printLine();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
