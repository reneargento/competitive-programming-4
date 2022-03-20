package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/03/22.
 */
public class SynchronizingLists {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int listSizes = FastReader.nextInt();
        int caseId = 1;

        while (listSizes != 0) {
            if (caseId > 1) {
                outputWriter.printLine();
            }
            int[] list1 = readList(listSizes);
            int[] list2 = readList(listSizes);
            Map<Integer, Integer> orderMap = getOrderMap(list1, list2);
            for (int list1Value : list1) {
                int list2Value = orderMap.get(list1Value);
                outputWriter.printLine(list2Value);
            }

            caseId++;
            listSizes = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[] readList(int size) throws IOException {
        int[] list = new int[size];
        for (int i = 0; i < list.length; i++) {
            list[i] = FastReader.nextInt();
        }
        return list;
    }

    private static Map<Integer, Integer> getOrderMap(int[] list1, int[] list2) {
        Map<Integer, Integer> orderMap = new HashMap<>();
        int[] list1Sorted = copyList(list1);
        Arrays.sort(list1Sorted);
        int[] list2Sorted = copyList(list2);
        Arrays.sort(list2Sorted);

        for (int i = 0; i < list1Sorted.length; i++) {
            orderMap.put(list1Sorted[i], list2Sorted[i]);
        }
        return orderMap;
    }

    private static int[] copyList(int[] list) {
        int[] copyList = new int[list.length];
        System.arraycopy(list, 0, copyList, 0, list.length);
        return copyList;
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
