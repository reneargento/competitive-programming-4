package chapter2.section2.section5;

/**
 * Created by Rene Argento on 21/01/21.
 */
// A deque can be implemented efficiently using a resizable array.
@SuppressWarnings("unchecked")
public class Exercise2_DequeInResizeableArray {

    private static class DequeInResizeableArray<Item> {
        private Item[] items;
        private int size;
        private int startIndex;

        private static final int DEFAULT_SIZE = 10;

        public DequeInResizeableArray() {
            items = (Item[]) new Object[DEFAULT_SIZE];
            startIndex = DEFAULT_SIZE / 2;
        }

        // O(1)
        public boolean empty() {
            return size == 0;
        }

        // O(1)
        public Item front() {
            if (empty()) {
                return null;
            }
            return items[startIndex + 1];
        }

        // O(1)
        public Item back() {
            if (empty()) {
                return null;
            }
            return items[startIndex + size];
        }

        // amortized O(1)
        public void pushFront(Item item) {
            items[startIndex] = item;
            startIndex--;
            size++;

            if (startIndex < 0) {
                if (size > items.length / 2) {
                    resize(items.length * 2);
                } else {
                    resize(items.length); // Shift items to center
                }
            }
        }

        // amortized O(1)
        public void pushBack(Item item) {
            int endIndex = startIndex + size + 1;
            items[endIndex] = item;
            size++;

            if (endIndex == items.length - 1) {
                if (size > items.length / 2) {
                    resize(items.length * 2);
                } else {
                    resize(items.length); // Shift items to center
                }
            }
        }

        // amortized O(1)
        public Item popFront() {
            if (empty()) {
                return null;
            }
            Item item = items[startIndex + 1];
            items[startIndex + 1] = null;
            startIndex++;
            size--;

            if (size == items.length / 4) {
                resize(items.length / 2);
            }
            return item;
        }

        // amortized O(1)
        public Item popBack() {
            if (empty()) {
                return null;
            }
            int endIndex = startIndex + size;
            Item item = items[endIndex];
            items[endIndex] = null;
            size--;

            if (size == items.length / 4) {
                resize(items.length / 2);
            }
            return item;
        }

        private void resize(int newSize) {
            int startPosition = (newSize / 2) - (size / 2);
            Item[] newItems = (Item[]) new Object[newSize];
            System.arraycopy(items, startIndex + 1, newItems, startPosition, size);
            items = newItems;
            startIndex = startPosition - 1;
        }
    }

    public static void main(String[] args) {
        DequeInResizeableArray<Integer> deque = new DequeInResizeableArray<>();
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
