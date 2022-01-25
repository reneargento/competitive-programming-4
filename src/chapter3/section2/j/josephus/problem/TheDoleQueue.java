package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/01/22.
 */
public class TheDoleQueue {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int circleSize = FastReader.nextInt();
        int k = FastReader.nextInt();
        int m = FastReader.nextInt();

        while (circleSize != 0 || k != 0 || m != 0) {
            choosePeople(circleSize, k, m, outputWriter);

            circleSize = FastReader.nextInt();
            k = FastReader.nextInt();
            m = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void choosePeople(int circleSize, int k, int m, OutputWriter outputWriter) {
        DoublyLinkedListCircular<Integer> peopleList = new DoublyLinkedListCircular<>();
        for (int i = 0; i < circleSize; i++) {
            peopleList.insertAtTheEnd(i);
        }
        DoublyLinkedListCircular<Integer>.DoubleNode counterClockwiseNode = peopleList.last;
        DoublyLinkedListCircular<Integer>.DoubleNode clockwiseNode = peopleList.first;

        while (!peopleList.isEmpty()) {
            int counterClockwiseMoves = (m - 1) % peopleList.size();
            for (int i = 0; i < counterClockwiseMoves; i++) {
                counterClockwiseNode = counterClockwiseNode.previous;
            }

            int clockwiseMoves = (k - 1) % peopleList.size();
            for (int i = 0; i < clockwiseMoves; i++) {
                clockwiseNode = clockwiseNode.next;
            }

            int person1 = counterClockwiseNode.item + 1;
            int person2 = clockwiseNode.item + 1;

            DoublyLinkedListCircular<Integer>.DoubleNode currentCounterClockwiseNode = counterClockwiseNode;
            counterClockwiseNode = counterClockwiseNode.previous;
            peopleList.removeItemWithNode(currentCounterClockwiseNode);

            outputWriter.print(String.format("%3d", person2));
            if (person1 != person2) {
                outputWriter.print(String.format("%3d", person1));

                DoublyLinkedListCircular<Integer>.DoubleNode currentClockwiseNode = clockwiseNode;
                clockwiseNode = clockwiseNode.next;

                if (currentClockwiseNode == counterClockwiseNode) {
                    counterClockwiseNode = counterClockwiseNode.previous;
                }
                peopleList.removeItemWithNode(currentClockwiseNode);
            } else {
                clockwiseNode = counterClockwiseNode.next;
            }

            if (!peopleList.isEmpty()) {
                outputWriter.print(",");
            }
        }
        outputWriter.printLine();
    }

    private static class DoublyLinkedListCircular<Item> {
        public class DoubleNode {
            public Item item;
            DoubleNode previous;
            DoubleNode next;
        }

        private int size;
        private DoubleNode first;
        private DoubleNode last;

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public DoubleNode first() {
            return first;
        }

        public DoubleNode last() {
            return last;
        }

        public DoubleNode get(int index) {
            if (isEmpty()) {
                return null;
            }

            if (index < 0 || index >= size) {
                throw new IllegalArgumentException("Index must be between 0 and " + (size() - 1));
            }

            DoubleNode current = first;
            int count = 0;

            while (count != index) {
                current = current.next;
                count++;
            }

            return current;
        }

        public void insertAtTheEnd(Item item) {
            DoubleNode oldLast = last;

            last = new DoubleNode();
            last.item = item;
            last.previous = oldLast;

            if (!isEmpty()) {
                last.next = oldLast.next;
                oldLast.next = last;
            } else {
                first = last;
                last.next = first;
            }

            first.previous = last;
            size++;
        }

        public void removeItemWithNode(DoubleNode doubleNode) {
            // Base case 1: empty list
            if (isEmpty()) {
                return;
            }

            // Base case 2: size 1 list
            if (doubleNode == first && first == last) {
                first = null;
                last = null;
                size--;
                return;
            }

            DoubleNode previousNode = doubleNode.previous;
            DoubleNode nextNode = doubleNode.next;

            previousNode.next = nextNode;
            nextNode.previous = previousNode;

            if (doubleNode == first) {
                first = nextNode;
            }
            if (doubleNode == last) {
                last = previousNode;
            }
            size--;
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
