package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/06/21.
 */
public class Warehouse {

    private static class Shipment implements Comparable<Shipment> {
        String toy;
        int quantity;

        public Shipment(String toy, int quantity) {
            this.toy = toy;
            this.quantity = quantity;
        }

        @Override
        public int compareTo(Shipment other) {
            if (quantity != other.quantity) {
                return Integer.compare(other.quantity, quantity);
            }
            return toy.compareTo(other.toy);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int shipments = FastReader.nextInt();
            Map<String, Integer> toyQuantityMap = new HashMap<>();

            for (int i = 0; i < shipments; i++) {
                String toy = FastReader.next();
                int quantity = FastReader.nextInt();
                int currentQuantity = toyQuantityMap.getOrDefault(toy, 0);
                toyQuantityMap.put(toy, currentQuantity + quantity);
            }

            List<Shipment> shipmentList = new ArrayList<>();
            for (Map.Entry<String, Integer> shipmentEntry : toyQuantityMap.entrySet()) {
                shipmentList.add(new Shipment(shipmentEntry.getKey(), shipmentEntry.getValue()));
            }
            Collections.sort(shipmentList);

            outputWriter.printLine(shipmentList.size());
            for (Shipment shipment : shipmentList) {
                outputWriter.printLine(shipment.toy + " " + shipment.quantity);
            }
        }
        outputWriter.flush();
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
