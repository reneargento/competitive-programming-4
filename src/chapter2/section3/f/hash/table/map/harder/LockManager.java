package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/05/21.
 */
public class LockManager {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            Map<String, String> itemIdsExclusiveLocks = new HashMap<>();
            Map<String, Set<String>> itemIdsSharedLocks = new HashMap<>();
            Set<String> blockedTransactions = new HashSet<>();

            FastReader.getLine();
            String request = FastReader.getLine();
            while (!request.equals("#")) {
                String[] data = request.split(" ");
                String transactionId = data[1];
                String itemId = data[2];

                if (blockedTransactions.contains(transactionId)) {
                    outputWriter.printLine("IGNORED");
                } else {
                    if ((itemIdsExclusiveLocks.containsKey(itemId) && !itemIdsExclusiveLocks.get(itemId).equals(transactionId)
                            || (data[0].equals("X") && doesSharedLockExists(itemIdsSharedLocks, itemId, transactionId)))) {
                        outputWriter.printLine("DENIED");
                        blockedTransactions.add(transactionId);
                    } else {
                        if (data[0].equals("X")) {
                            itemIdsExclusiveLocks.put(itemId, transactionId);
                        } else {
                            if (!itemIdsSharedLocks.containsKey(itemId)) {
                                itemIdsSharedLocks.put(itemId, new HashSet<>());
                            }
                            itemIdsSharedLocks.get(itemId).add(transactionId);
                        }
                        outputWriter.printLine("GRANTED");
                    }
                }
                request = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static boolean doesSharedLockExists(Map<String, Set<String>> itemIdsSharedLocks, String itemId,
                                                String transactionId) {
        return itemIdsSharedLocks.containsKey(itemId) &&
                !(itemIdsSharedLocks.get(itemId).size() == 1 && itemIdsSharedLocks.get(itemId).contains(transactionId));
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
