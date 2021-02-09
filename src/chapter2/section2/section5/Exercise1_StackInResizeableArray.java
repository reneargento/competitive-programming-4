package chapter2.section2.section5;

/**
 * Created by Rene Argento on 21/01/21.
 */
// A stack can be implemented using a resizable array, but its operations will have the complexity:
// push: amortized O(1)
// pop: O(1)
// top: O(1)
// empty: O(1)
@SuppressWarnings("unchecked")
public class Exercise1_StackInResizeableArray {

    private static class StackInResizeableArray<Item> {
        private Item[] items;
        int size;

        public StackInResizeableArray() {
            items = (Item[]) new Object[1];
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

        // amortized O(1)
        public void push(Item item) {
            items[size] = item;
            size++;

            if (size > items.length / 2) {
                resize(items.length * 2);
            }
        }

        // O(1)
        public Item pop() {
            if (empty()) {
                return null;
            }
            Item item = items[size - 1];
            items[size - 1] = null;
            size--;

            if (size == items.length / 4) {
                resize(items.length / 2);
            }
            return item;
        }

        private void resize(int newSize) {
            Item[] newItems = (Item[]) new Object[newSize];
            System.arraycopy(items, 0, newItems, 0, size);
            items = newItems;
        }
    }

    public static void main(String[] args) {
        StackInResizeableArray<Integer> stack = new StackInResizeableArray<>();
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
