package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/04/23.
 */
@SuppressWarnings("unchecked")
public class BreakingBad {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        Map<String, Integer> itemNameToIDMap = new HashMap<>();
        String[] itemIDToName = new String[adjacencyList.length];
        for (int itemID = 0; itemID < adjacencyList.length; itemID++) {
            String itemName = FastReader.next();
            itemNameToIDMap.put(itemName, itemID);
            itemIDToName[itemID] = itemName;
        }

        int suspiciousPairs = FastReader.nextInt();
        for (int i = 0; i < suspiciousPairs; i++) {
            String itemName1 = FastReader.next();
            String itemName2 = FastReader.next();

            int itemID1 = itemNameToIDMap.get(itemName1);
            int itemID2 = itemNameToIDMap.get(itemName2);
            adjacencyList[itemID1].add(itemID2);
            adjacencyList[itemID2].add(itemID1);
        }

        List<List<String>> itemsDivided = divideItems(adjacencyList, itemIDToName);
        if (itemsDivided != null) {
            for (List<String> itemList : itemsDivided) {
                if (itemList.isEmpty()) {
                    outputWriter.printLine();
                    continue;
                }
                outputWriter.print(itemList.get(0));
                for (int i = 1; i < itemList.size(); i++) {
                    outputWriter.print(" " + itemList.get(i));
                }
                outputWriter.printLine();
            }
        } else {
            outputWriter.printLine("impossible");
        }
        outputWriter.flush();
    }

    private static List<List<String>> divideItems(List<Integer>[] adjacencyList, String[] itemIDToName) {
        Boolean[] colors = new Boolean[adjacencyList.length];
        for (int itemID = 0; itemID < adjacencyList.length; itemID++) {
            if (colors[itemID] == null) {
                boolean possible = colorGraph(adjacencyList, colors, itemID, true);
                if (!possible) {
                    return null;
                }
            }
        }

        List<String> walterItems = new ArrayList<>();
        List<String> jesseItems = new ArrayList<>();
        List<List<String>> itemsDivided = new ArrayList<>();
        itemsDivided.add(walterItems);
        itemsDivided.add(jesseItems);

        for (int itemID = 0; itemID < colors.length; itemID++) {
            String itemName = itemIDToName[itemID];
            if (colors[itemID]) {
                walterItems.add(itemName);
            } else {
                jesseItems.add(itemName);
            }
        }
        return itemsDivided;
    }

    private static boolean colorGraph(List<Integer>[] adjacencyList, Boolean[] colors, int itemID, boolean color) {
        boolean possibleToColor = true;

        colors[itemID] = color;
        for (int neighborItem : adjacencyList[itemID]) {
            if (colors[neighborItem] == null) {
                possibleToColor &= colorGraph(adjacencyList, colors, neighborItem, !color);
            } else if (colors[neighborItem] == colors[itemID]) {
                return false;
            }
        }
        return possibleToColor;
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
