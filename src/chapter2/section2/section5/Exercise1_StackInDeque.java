package chapter2.section2.section5;

/**
 * Created by Rene Argento on 21/01/21.
 */
// A stack can be implemented efficiently using a deque.
public class Exercise1_StackInDeque {

    private static class Deque<Item> {
        private class Node {
            Node previous;
            Node next;
            Item item;

            public Node(Item item) {
                this.item = item;
            }
        }

        private Node head;
        private Node tail;
        private int size;

        // O(1)
        public boolean empty() {
            return size == 0;
        }

        // O(1)
        public Item front() {
            if (empty()) {
                return null;
            }
            return head.item;
        }

        // O(1)
        public Item back() {
            if (empty()) {
                return null;
            }
            return tail.item;
        }

        // O(1)
        public void pushFront(Item item) {
            Node newNode = new Node(item);
            newNode.next = head;

            if (!empty()) {
                head.previous = newNode;
            } else {
                tail = newNode;
            }
            head = newNode;
            size++;
        }

        // O(1)
        public void pushBack(Item item) {
            Node newNode = new Node(item);
            newNode.previous = tail;

            if (!empty()) {
                tail.next = newNode;
            } else {
                head = newNode;
            }
            tail = newNode;
            size++;
        }

        // O(1)
        public Item popFront() {
            if (empty()) {
                return null;
            }
            Item item = head.item;
            head = head.next;
            size--;

            if (!empty()) {
                head.previous = null;
            } else {
                tail = null;
            }
            return item;
        }

        // O(1)
        public Item popBack() {
            if (empty()) {
                return null;
            }
            Item item = tail.item;
            tail = tail.previous;
            size--;

            if (!empty()) {
                tail.next = null;
            } else {
                head = null;
            }
            return item;
        }
    }

    private static class StackInDeque<Item> {
        private Deque<Item> deque;

        public StackInDeque() {
            this.deque = new Deque<>();
        }

        // O(1)
        public boolean empty() {
            return deque.empty();
        }

        // O(1)
        public Item top() {
            return deque.back();
        }

        // O(1)
        public void push(Item item) {
            deque.pushBack(item);
        }

        // O(1)
        public Item pop() {
            return deque.popBack();
        }
    }

    public static void main(String[] args) {
        StackInDeque<Integer> stack = new StackInDeque<>();
        System.out.println("Empty: " + stack.empty());
        System.out.println("Expected: true");

        stack.push(2);
        stack.push(4);
        stack.push(8);
        stack.push(16);

        System.out.println("Empty: " + stack.empty());
        System.out.println("Expected: false");
        System.out.println("\nTop: " + stack.top());
        System.out.println("Expected: 16");
        System.out.println("Pop: " + stack.pop());
        System.out.println("Expected: 16");
        System.out.println("Pop: " + stack.pop());
        System.out.println("Expected: 8");
        System.out.println("Pop: " + stack.pop());
        System.out.println("Expected: 4");
        System.out.println("Pop: " + stack.pop());
        System.out.println("Expected: 2");
        System.out.println("\nEmpty: " + stack.empty());
        System.out.println("Expected: true");
    }
}
