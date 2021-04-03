package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/04/21.
 */
public class Teque {

    private static class TequeDataStructure {
        private final int[] firstHalf;
        private final int[] secondHalf;

        private int firstHalfStartIndex;
        private int firstHalfEndIndex;
        private int secondHalfStartIndex;
        private int secondHalfEndIndex;

        private int firstHalfSize;
        private int secondHalfSize;

        public TequeDataStructure(int maxSize) {
            int arraysMaxSize = (maxSize + 1) * 2;
            firstHalf = new int[arraysMaxSize];
            secondHalf = new int[arraysMaxSize];

            firstHalfStartIndex = maxSize;
            firstHalfEndIndex = maxSize;
            secondHalfStartIndex = maxSize;
            secondHalfEndIndex = maxSize;
        }

        public int get(int index) {
            if (index < 0 || index >= firstHalfSize + secondHalfSize) {
                throw new IllegalArgumentException("Invalid index");
            }

            if (index < firstHalfSize) {
                return firstHalf[firstHalfStartIndex + index];
            } else {
                int secondHalfIndex = index - firstHalfSize;
                return secondHalf[secondHalfStartIndex + secondHalfIndex];
            }
        }

        public void insertFirst(int value) {
            if (firstHalfSize != 0) {
                firstHalfStartIndex--;
            }
            firstHalf[firstHalfStartIndex] = value;
            firstHalfSize++;
            balanceArrays();
        }

        public void insertLast(int value) {
            if (secondHalfSize != 0) {
                secondHalfEndIndex++;
            }
            secondHalf[secondHalfEndIndex] = value;
            secondHalfSize++;
            balanceArrays();
        }

        public void insertMiddle(int value) {
            if (secondHalfSize != 0) {
                secondHalfStartIndex--;
            }
            secondHalf[secondHalfStartIndex] = value;
            secondHalfSize++;
            balanceArrays();
        }

        private void balanceArrays() {
            int sizeDifference = firstHalfSize - secondHalfSize;

            if (sizeDifference > 1) {
                // Move element from left half to right half
                int valueToMove = firstHalf[firstHalfEndIndex];
                if (firstHalfSize != 1) {
                    firstHalfEndIndex--;
                }
                if (secondHalfSize != 0) {
                    secondHalfStartIndex--;
                }

                secondHalf[secondHalfStartIndex] = valueToMove;
                firstHalfSize--;
                secondHalfSize++;
            } else if (sizeDifference < 0) {
                // Move element from right half to left half
                int valueToMove = secondHalf[secondHalfStartIndex];
                if (secondHalfSize != 1) {
                    secondHalfStartIndex++;
                }
                if (firstHalfSize != 0) {
                    firstHalfEndIndex++;
                }

                firstHalf[firstHalfEndIndex] = valueToMove;
                firstHalfSize++;
                secondHalfSize--;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        TequeDataStructure teque = new TequeDataStructure(1000000);

        int operations = FastReader.nextInt();

        for (int i = 0; i < operations; i++) {
            String operation = FastReader.next();

            if (operation.equals("get")) {
                int index = FastReader.nextInt();
                outputWriter.printLine(teque.get(index));
            } else {
                int value = FastReader.nextInt();
                if (operation.equals("push_front")) {
                    teque.insertFirst(value);
                } else if (operation.equals("push_back")) {
                    teque.insertLast(value);
                } else {
                    teque.insertMiddle(value);
                }
            }
        }
        outputWriter.flush();
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
