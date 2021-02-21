package chapter2.section2.e.sorting.easier;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Rene Argento on 13/02/21.
 */
public class WhatIsTheMedian {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer number1, Integer number2) {
                return number2 - number1;
            }
        });
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        String numberString = FastReader.getLine();
        while (numberString != null) {
            int number = Integer.parseInt(numberString.trim());

            if (!maxHeap.isEmpty() && number > maxHeap.peek()) {
                minHeap.offer(number);
                balanceHeaps(minHeap, maxHeap);
            } else {
                maxHeap.offer(number);
                balanceHeaps(maxHeap, minHeap);
            }

            int median;
            if (minHeap.size() > maxHeap.size()) {
                median = minHeap.peek();
            } else if (maxHeap.size() > minHeap.size()) {
                median = maxHeap.peek();
            } else {
                median = (minHeap.peek() + maxHeap.peek()) / 2;
            }
            outputWriter.printLine(median);

            numberString = FastReader.getLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void balanceHeaps(PriorityQueue<Integer> heap1, PriorityQueue<Integer> heap2) {
        if (heap1.size() > heap2.size() + 1) {
            heap2.offer(heap1.poll());
        }
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
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
                if (i != 0)
                    writer.print(' ');
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
