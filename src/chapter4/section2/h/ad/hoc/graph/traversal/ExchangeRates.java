package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 13/05/23.
 */
@SuppressWarnings("unchecked")
public class ExchangeRates {

    private static class Edge {
        int otherItemID;
        double multiplier;

        public Edge(int otherItemID, double multiplier) {
            this.otherItemID = otherItemID;
            this.multiplier = multiplier;
        }
    }

    private static class Rate {
        int term1;
        int term2;

        public Rate(int term1, int term2) {
            this.term1 = term1;
            this.term2 = term2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Edge>[] adjacencyList = new List[60];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        Map<String, Integer> itemNameToIDMap = new HashMap<>();

        String line = FastReader.getLine();
        while (!line.equals(".")) {
            String[] data = line.split(" ");
            if (data[0].equals("!")) {
                double item1Quantity = Integer.parseInt(data[1]);
                double item2Quantity = Integer.parseInt(data[4]);
                double multiplier1To2 = item2Quantity / item1Quantity;
                double multiplier2To1 = item1Quantity / item2Quantity;

                int item1ID = getItemID(itemNameToIDMap, data[2]);
                int item2ID = getItemID(itemNameToIDMap, data[5]);
                Edge edge1 = new Edge(item2ID, multiplier1To2);
                Edge edge2 = new Edge(item1ID, multiplier2To1);

                adjacencyList[item1ID].add(edge1);
                adjacencyList[item2ID].add(edge2);
            } else {
                int item1ID = getItemID(itemNameToIDMap, data[1]);
                int item2ID = getItemID(itemNameToIDMap, data[3]);
                Rate exchangeRate = computeExchangeRate(adjacencyList, item1ID, item2ID);

                String quantity1 = exchangeRate != null ? String.valueOf(exchangeRate.term2) : "?";
                String quantity2 = exchangeRate != null ? String.valueOf(exchangeRate.term1) : "?";
                outputWriter.printLine(String.format("%s %s = %s %s", quantity1, data[1], quantity2, data[3]));
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Rate computeExchangeRate(List<Edge>[] adjacencyList, int item1ID, int item2ID) {
        boolean[] visited = new boolean[adjacencyList.length];
        return computeExchangeRate(adjacencyList, visited, item2ID, 1, item1ID);
    }

    private static Rate computeExchangeRate(List<Edge>[] adjacencyList, boolean[] visited, int targetItemID,
                                            double decimal, int itemID) {
        visited[itemID] = true;
        if (itemID == targetItemID) {
            return decimalToFraction(decimal);
        }

        for (Edge edge : adjacencyList[itemID]) {
            if (!visited[edge.otherItemID]) {
                double newDecimal = decimal * edge.multiplier;
                Rate rate = computeExchangeRate(adjacencyList, visited, targetItemID, newDecimal, edge.otherItemID);
                if (rate != null) {
                    return rate;
                }
            }
        }
        return null;
    }

    private static Rate decimalToFraction(double decimal) {
        double error = 0.000001;
        int numberFloor = (int) Math.floor(decimal);
        decimal -= numberFloor;

        if (decimal < error) {
            return new Rate(numberFloor, 1);
        } else if (1 - error < decimal) {
            return new Rate(numberFloor + 1, 1);
        }

        // The lower fraction is 0/1
        int lowerNominator = 0;
        int lowerDenominator = 1;
        // The upper fraction is 1/1
        int upperNominator = 1;
        int upperDenominator = 1;

        while (true) {
            // The middle fraction is (lowerNominator + upperNominator) / (lowerDenominator + upperDenominator)
            int middleNominator = lowerNominator + upperNominator;
            int middleDenominator = lowerDenominator + upperDenominator;
            if (middleDenominator * (decimal + error) < middleNominator) {
                // If decimal + error < middle
                // middle is our new upper
                upperNominator = middleNominator;
                upperDenominator = middleDenominator;
            } else if (middleNominator < middleDenominator * (decimal - error)) {
                // Else If middle < decimal - error
                // middle is our new lower
                lowerNominator = middleNominator;
                lowerDenominator = middleDenominator;
            } else {
                // Else middle is our best fraction
                return new Rate(numberFloor * middleDenominator + middleNominator, middleDenominator);
            }
        }
    }

    private static int getItemID(Map<String, Integer> itemNameToIDMap, String itemName) {
        if (!itemNameToIDMap.containsKey(itemName)) {
            itemNameToIDMap.put(itemName, itemNameToIDMap.size());
        }
        return itemNameToIDMap.get(itemName);
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
