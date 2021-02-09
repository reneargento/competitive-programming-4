package chapter2.section2.section5;

/**
 * Created by Rene Argento on 21/01/21.
 */
// A stack can be implemented efficiently using a static array, but it will be limited by its size since it cannot expand.
@SuppressWarnings("unchecked")
public class Exercise1_StackInStaticArray {

    private static class StackInStaticArray<Item> {
        private Item[] items;
        int size;

        public StackInStaticArray(int size) {
            items = (Item[]) new Object[size];
        }

        // O(1)
        public boolean empty() {
            return size == 0;
        }

        // O(1)
        public Item top() {
            if (empty()) {
                return null;
            }
            return items[size - 1];
        }

        // O(1)
        public void push(Item item) {
            if (size == items.length) {
                System.out.println("Stack is full");
                return;
            }
            items[size] = item;
            size++;
        }

        // O(1)
        public Item pop() {
            if (empty()) {
                return null;
            }
            Item item = items[size - 1];
            items[size - 1] = null;
            size--;
            return item;
        }
    }

    public static void main(String[] args) {
        StackInStaticArray<Integer> stack = new StackInStaticArray<>(3);
        System.out.println("Empty: " + stack.empty());
        System.out.println("Expected: true");

        stack.push(2);
        stack.push(4);
        stack.push(8);
        stack.push(16);
        System.out.println("Expected: Stack is full");

        System.out.println("Empty: " + stack.empty());
        System.out.println("Expected: false");
        System.out.println("\nTop: " + stack.top());
        System.out.println("Expected: 8");
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
