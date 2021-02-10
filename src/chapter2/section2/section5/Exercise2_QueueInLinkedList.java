package chapter2.section2.section5;

import java.util.LinkedList;

/**
 * Created by Rene Argento on 21/01/21.
 */
// A queue can be implemented efficiently using a linked list.
public class Exercise2_QueueInLinkedList {

    private static class QueueInLinkedList<Item> {
        private LinkedList<Item> linkedList;

        public QueueInLinkedList() {
            linkedList = new LinkedList<>();
        }

        // O(1)
        public boolean empty() {
            return linkedList.isEmpty();
        }

        // O(1)
        public Item front() {
            return linkedList.peekFirst();
        }

        // O(1)
        public Item back() {
            return linkedList.peekLast();
        }

        // O(1)
        public void push(Item item) {
            linkedList.offerLast(item);
        }

        // O(1)
        public Item pop() {
            return linkedList.pollFirst();
        }
    }

    public static void main(String[] args) {
        QueueInLinkedList<Integer> queue = new QueueInLinkedList<>();
        System.out.println("Empty: " + queue.empty());
        System.out.println("Expected: true");

        queue.push(1);
        queue.push(3);
        queue.push(5);
        queue.push(7);

        System.out.println("Empty: " + queue.empty());
        System.out.println("Expected: false");
        System.out.println("\nFront: " + queue.front());
        System.out.println("Expected: 1");
        System.out.println("Back: " + queue.back());
        System.out.println("Expected: 7");
        System.out.println("\nPop: " + queue.pop());
        System.out.println("Expected: 1");
        System.out.println("Pop: " + queue.pop());
        System.out.println("Expected: 3");
        System.out.println("Pop: " + queue.pop());
        System.out.println("Expected: 5");
        System.out.println("Pop: " + queue.pop());
        System.out.println("Expected: 7");
        System.out.println("\nEmpty: " + queue.empty());
        System.out.println("Expected: true");
    }
}
