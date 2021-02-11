package chapter2.section2.section5;

/**
 * Created by Rene Argento on 21/01/21.
 */
@SuppressWarnings("unchecked")
public class Exercise3_QueueInTwoResizeableArrays {

    private static class QueueInTwoResizeableArrays<Item> {
        private Item[] front;
        private Item[] back;
        private int frontSize;
        private int backSize;
        private int startIndex;

        public QueueInTwoResizeableArrays() {
            front = (Item[]) new Object[1];
            back = (Item[]) new Object[1];
            startIndex = -1;
        }

        // O(1)
        public boolean empty() {
            return frontSize == 0 && backSize == 0;
        }

        // O(1)
        public Item front() {
            if (empty()) {
                return null;
            }
            if (frontSize > 0) {
                return front[startIndex + 1];
            } else {
                return back[0];
            }
        }

        // O(1)
        public Item back() {
            if (empty()) {
                return null;
            }
            if (backSize > 0) {
                return back[backSize - 1];
            } else {
                return front[startIndex + frontSize];
            }
        }

        // amortized O(1)
        public void push(Item item) {
            back[backSize] = item;
            backSize++;

            if (backSize == back.length) {
                resizeAndMoveItemsForward(back.length * 2);
            }
        }

        // amortized O(1)
        public Item pop() {
            if (empty()) {
                return null;
            }
            if (frontSize == 0) {
                // Move items forward and keep the same size
                resizeAndMoveItemsForward(front.length);
            }

            Item item = front[startIndex + 1];
            front[startIndex + 1] = null;
            startIndex++;
            frontSize--;

            if (frontSize <= front.length / 4
                    && backSize <= back.length / 4) {
                resizeAndMoveItemsForward(front.length / 2);
            }
            return item;
        }

        private void resizeAndMoveItemsForward(int newSize) {
            Item[] newItemsFront = (Item[]) new Object[newSize];
            System.arraycopy(front, startIndex + 1, newItemsFront, 0, frontSize);
            System.arraycopy(back, 0, newItemsFront, frontSize, backSize);

            front = newItemsFront;
            back = (Item[]) new Object[newSize];
            startIndex = -1;
            frontSize = frontSize + backSize;
            backSize = 0;
        }
    }

    public static void main(String[] args) {
        QueueInTwoResizeableArrays<Integer> queue = new QueueInTwoResizeableArrays<>();
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
