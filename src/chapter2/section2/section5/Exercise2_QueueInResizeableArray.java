package chapter2.section2.section5;

/**
 * Created by Rene Argento on 21/01/21.
 */
// A queue can be implemented efficiently using a resizable array.
@SuppressWarnings("unchecked")
public class Exercise2_QueueInResizeableArray {

    private static class QueueInResizeableArray<Item> {
        private Item[] items;
        private int size;
        private int startIndex;

        public QueueInResizeableArray() {
            items = (Item[]) new Object[1];
            startIndex = -1;
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
        public void push(Item item) {
            int endIndex = startIndex + size + 1;
            items[endIndex] = item;
            size++;

            if (endIndex == items.length - 1) {
                if (size > items.length / 2) {
                    resize(items.length * 2);
                } else {
                    resize(items.length); // Shift items to the beginning
                }
            }
        }

        // amortized O(1)
        public Item pop() {
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

        private void resize(int newSize) {
            Item[] newItems = (Item[]) new Object[newSize];
            System.arraycopy(items, startIndex + 1, newItems, 0, size);
            items = newItems;
            startIndex = -1;
        }
    }

    public static void main(String[] args) {
        QueueInResizeableArray<Integer> queue = new QueueInResizeableArray<>();
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
