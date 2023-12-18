package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 17/12/23.
 */
@SuppressWarnings("unchecked")
public class ShippingRoutes {

    private static class State {
        int warehouseID;
        int shippingLegs;

        public State(int warehouseID, int shippingLegs) {
            this.warehouseID = warehouseID;
            this.shippingLegs = shippingLegs;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        outputWriter.printLine("SHIPPING ROUTES OUTPUT");

        for (int t = 1; t <= tests; t++) {
            Map<String, Integer> warehouseCodeToID = new HashMap<>();
            List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int shippingLegs = FastReader.nextInt();
            int shippingRequests = FastReader.nextInt();

            for (int i = 0; i < adjacencyList.length; i++) {
                getWarehouseID(warehouseCodeToID, FastReader.next());
            }

            for (int i = 0; i < shippingLegs; i++) {
                String warehouseCode1 = FastReader.next();
                String warehouseCode2 = FastReader.next();

                int warehouseID1 = getWarehouseID(warehouseCodeToID, warehouseCode1);
                int warehouseID2 = getWarehouseID(warehouseCodeToID, warehouseCode2);
                adjacencyList[warehouseID1].add(warehouseID2);
                adjacencyList[warehouseID2].add(warehouseID1);
            }

            outputWriter.printLine(String.format("\nDATA SET  %d\n", t));

            for (int i = 0; i < shippingRequests; i++) {
                int shipmentSize = FastReader.nextInt();
                int warehouseID1 = getWarehouseID(warehouseCodeToID, FastReader.next());
                int warehouseID2 = getWarehouseID(warehouseCodeToID, FastReader.next());
                int minimumShippingLegs = computeMinimumShippingLegs(adjacencyList, warehouseID1, warehouseID2);
                if (minimumShippingLegs != -1) {
                    int cheapestCost = shipmentSize * minimumShippingLegs * 100;
                    outputWriter.printLine(String.format("$%d", cheapestCost));
                } else {
                    outputWriter.printLine("NO SHIPMENT POSSIBLE");
                }
            }
        }
        outputWriter.printLine("\nEND OF OUTPUT");
        outputWriter.flush();
    }


    private static int computeMinimumShippingLegs(List<Integer>[] adjacencyList, int warehouseID1, int warehouseID2) {
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(warehouseID1, 0));
        boolean[] visited = new boolean[adjacencyList.length];

        while (!queue.isEmpty()) {
            State state = queue.poll();

            if (state.warehouseID == warehouseID2) {
                return state.shippingLegs;
            }

            for (int neighborID : adjacencyList[state.warehouseID]) {
                if (!visited[neighborID]) {
                    visited[neighborID] = true;
                    queue.offer(new State(neighborID, state.shippingLegs + 1));
                }
            }
        }
        return -1;
    }

    private static int getWarehouseID(Map<String, Integer> warehouseCodeToID, String warehouseCode) {
        if (!warehouseCodeToID.containsKey(warehouseCode)) {
            warehouseCodeToID.put(warehouseCode, warehouseCodeToID.size());
        }
        return warehouseCodeToID.get(warehouseCode);
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
