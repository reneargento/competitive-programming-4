package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by Rene Argento on 05/06/21.
 */
public class HoaxOrWhat {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int days = FastReader.nextInt();

        while (days != 0) {
            long amountPaid = 0;
            Multiset<Integer> bills = new Multiset<>();

            for (int d = 0; d < days; d++) {
                int billsNumber = FastReader.nextInt();

                for (int i = 0; i < billsNumber; i++) {
                    bills.add(FastReader.nextInt());
                }

                int lowestValueBill = bills.deleteFirstKey();
                int highestValueBill = bills.deleteLastKey();
                amountPaid += highestValueBill - lowestValueBill;
            }
            outputWriter.printLine(amountPaid);
            days = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static class Multiset<Key extends Comparable<Key>> {
        private final TreeMap<Key, Integer> multiset;

        Multiset() {
            multiset = new TreeMap<>();
        }

        public void add(Key key) {
            int keyFrequency = multiset.getOrDefault(key, 0);
            multiset.put(key, keyFrequency + 1);
        }

        public void delete(Key key) {
            int keyFrequency = multiset.get(key);
            if (keyFrequency == 1) {
                multiset.remove(key);
            } else {
                multiset.put(key, keyFrequency - 1);
            }
        }

        public Key firstKey() {
            return multiset.firstKey();
        }

        public Key lastKey() {
            return multiset.lastKey();
        }

        public Key deleteFirstKey() {
            Key firstKey = firstKey();
            delete(firstKey);
            return firstKey;
        }

        public Key deleteLastKey() {
            Key lastKey = lastKey();
            delete(lastKey);
            return lastKey;
        }
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
