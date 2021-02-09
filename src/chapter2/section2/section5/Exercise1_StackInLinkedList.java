package chapter2.section2.section5;

/**
 * Created by Rene Argento on 21/01/21.
 */
// A stack can be implemented efficiently using a linked list.
public class Exercise1_StackInLinkedList {

    private static class StackInLinkedList<Item> {
        private class Node {
            Node next;
            Item item;

            public Node(Item item) {
                this.item = item;
            }
        }

        private Node head;
        int size;

        // O(1)
        public boolean empty() {
            return size == 0;
        }

        // O(1)
        public Item top() {
            if (empty()) {
                return null;
            }
            return head.item;
        }

        // O(1)
        public void push(Item item) {
            Node newNode = new Node(item);
            newNode.next = head;
            head = newNode;
            size++;
        }

        // O(1)
        public Item pop() {
            if (empty()) {
                return null;
            }
            Item item = head.item;
            head = head.next;
            size--;
            return item;
        }
    }

    public static void main(String[] args) {
        StackInLinkedList<Integer> stack = new StackInLinkedList<>();
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
