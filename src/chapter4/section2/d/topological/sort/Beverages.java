package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/03/23.
 */
@SuppressWarnings("unchecked")
public class Beverages {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int caseNumber = 1;
        String line = FastReader.getLine();
        while (line != null) {
            Map<String, Integer> nameToIDMap = new HashMap<>();
            String[] beverageNames = new String[Integer.parseInt(line)];
            for (int i = 0; i < beverageNames.length; i++) {
                beverageNames[i] = FastReader.getLine();
                nameToIDMap.put(beverageNames[i], i);
            }

            List<Integer>[] adjacencyList = new ArrayList[beverageNames.length];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int[] inDegrees = new int[adjacencyList.length];

            int relations = FastReader.nextInt();
            for (int i = 0; i < relations; i++) {
                String beverageName1 = FastReader.next();
                String beverageName2 = FastReader.next();
                int beverageID1 = nameToIDMap.get(beverageName1);
                int beverageID2 = nameToIDMap.get(beverageName2);
                adjacencyList[beverageID1].add(beverageID2);
                inDegrees[beverageID2]++;
            }

            List<String> drinkOrder = computeDrinkOrder(adjacencyList, beverageNames, inDegrees);
            outputWriter.print(String.format("Case #%d: Dilbert should drink beverages in this order:", caseNumber));
            for (String beverageName : drinkOrder) {
                outputWriter.print(" " + beverageName);
            }
            outputWriter.printLine(".\n");

            caseNumber++;
            FastReader.getLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static List<String> computeDrinkOrder(List<Integer>[] adjacencyList, String[] beverageNames,
                                                  int[] inDegrees) {
        List<String> drinkOrder = new ArrayList<>();

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int beverageID = 0; beverageID < inDegrees.length; beverageID++) {
            if (inDegrees[beverageID] == 0) {
                priorityQueue.add(beverageID);
            }
        }

        while (!priorityQueue.isEmpty()) {
            int beverageID = priorityQueue.poll();
            drinkOrder.add(beverageNames[beverageID]);

            for (int neighborID : adjacencyList[beverageID]) {
                inDegrees[neighborID]--;

                if (inDegrees[neighborID] == 0) {
                    priorityQueue.offer(neighborID);
                }
            }
        }
        return drinkOrder;
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

        public void flush() {
            writer.flush();
        }
    }
}
