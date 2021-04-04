package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/04/21.
 */
@SuppressWarnings("unchecked")
public class TeamQueue {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int teams = FastReader.nextInt();
        int scenario = 1;

        while (teams != 0) {
            outputWriter.printLine(String.format("Scenario #%d", scenario));
            Map<Integer, Integer> teamMembership = new HashMap<>();
            for (int t = 0; t < teams; t++) {
                int members = FastReader.nextInt();

                for (int m = 0; m < members; m++) {
                    teamMembership.put(FastReader.nextInt(), t);
                }
            }
            DoublyLinkedList<Integer> mainQueue = new DoublyLinkedList<>();
            DoublyLinkedList<DoublyLinkedList<Integer>.DoubleNode>[] teamQueues = createTeamQueues(teams);

            while (true) {
                String command = FastReader.next();
                if (command.equals("ENQUEUE")) {
                    int element = FastReader.nextInt();
                    int team = teamMembership.get(element);

                    DoublyLinkedList<DoublyLinkedList<Integer>.DoubleNode> teamQueue = teamQueues[team];
                    if (teamQueue.isEmpty()) {
                        DoublyLinkedList<Integer>.DoubleNode newNode = mainQueue.insertAtTheEnd(element);
                        teamQueue.insertAtTheEnd(newNode);
                    } else {
                        DoublyLinkedList<DoublyLinkedList<Integer>.DoubleNode>.DoubleNode lastElement =
                                teamQueue.getLastNode();
                        DoublyLinkedList<Integer>.DoubleNode newElement =
                                mainQueue.insertAfterNode(lastElement.item, element);
                        teamQueue.insertAfterNode(lastElement, newElement);
                    }
                } else if (command.equals("DEQUEUE")) {
                    int element = mainQueue.removeFromTheBeginning();
                    int team = teamMembership.get(element);
                    teamQueues[team].removeFromTheBeginning();

                    outputWriter.printLine(element);
                } else {
                    break;
                }
            }

            outputWriter.printLine();
            scenario++;
            teams = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static DoublyLinkedList<DoublyLinkedList<Integer>.DoubleNode>[] createTeamQueues(int teams) {
        DoublyLinkedList<DoublyLinkedList<Integer>.DoubleNode>[] teamQueues = new DoublyLinkedList[teams];
        for (int i = 0; i < teams; i++) {
            teamQueues[i] = new DoublyLinkedList<>();
        }
        return teamQueues;
    }

    private static class DoublyLinkedList<Item> {
        public class DoubleNode {
            public Item item;
            public DoubleNode previous;
            public DoubleNode next;
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

        DoubleNode getLastNode() {
            return last;
        }

        public DoubleNode insertAtTheEnd(Item item) {
            DoubleNode oldLast = last;

            last = new DoubleNode();
            last.item = item;
            last.previous = oldLast;

            if (oldLast != null) {
                oldLast.next = last;
            }

            // If the list was empty before adding the new item:
            if (isEmpty()) {
                first = last;
            }

            size++;
            return last;
        }

        public DoubleNode insertAfterNode(DoubleNode afterNode, Item item) {
            if (afterNode == null) {
                return null;
            }
            DoubleNode newNode = new DoubleNode();
            newNode.item = item;

            DoubleNode nextNode = afterNode.next;
            newNode.next = nextNode;
            if (nextNode != null) {
                nextNode.previous = newNode;
            } else {
                // This is the last node
                last = newNode;
            }

            newNode.previous = afterNode;
            afterNode.next = newNode;
            size++;
            return newNode;
        }

        public Item removeFromTheBeginning() {
            if (isEmpty()) {
                return null;
            }

            Item item = first.item;

            if (first.next != null) {
                first.next.previous = null;
            } else { // There is only 1 element in the list
                last = null;
            }

            first = first.next;
            size--;
            return item;
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
