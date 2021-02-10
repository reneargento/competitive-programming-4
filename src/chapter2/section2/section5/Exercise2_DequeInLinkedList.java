package chapter2.section2.section5;

import java.util.LinkedList;

/**
 * Created by Rene Argento on 21/01/21.
 */
// A deque can be implemented efficiently using a linked list.
public class Exercise2_DequeInLinkedList {

    private static class DequeInLinkedList<Item> {
        private LinkedList<Item> linkedList;

        public DequeInLinkedList() {
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
        public void pushFront(Item item) {
            linkedList.offerFirst(item);
        }

        // O(1)
        public void pushBack(Item item) {
            linkedList.offerLast(item);
        }

        // O(1)
        public Item popFront() {
            return linkedList.pollFirst();
        }

        // O(1)
        public Item popBack() {
            return linkedList.pollLast();
        }
    }

    public static void main(String[] args) {
        DequeInLinkedList<Integer> deque = new DequeInLinkedList<>();
        System.out.println("Empty: " + deque.empty());
        System.out.println("Expected: true");

        deque.pushFront(1);
        deque.pushBack(10);
        deque.pushFront(4);
        deque.pushBack(8);

        System.out.println("Empty: " + deque.empty());
        System.out.println("Expected: false");
        System.out.println("\nFront: " + deque.front());
        System.out.println("Expected: 4");
        System.out.println("Back: " + deque.back());
        System.out.println("Expected: 8");
        System.out.println("\nPop: " + deque.popFront());
        System.out.println("Expected: 4");
        System.out.println("Pop: " + deque.popBack());
        System.out.println("Expected: 8");
        System.out.println("Pop: " + deque.popBack());
        System.out.println("Expected: 10");
        System.out.println("Pop: " + deque.popFront());
        System.out.println("Expected: 1");
        System.out.println("\nEmpty: " + deque.empty());
        System.out.println("Expected: true");
    }
}
