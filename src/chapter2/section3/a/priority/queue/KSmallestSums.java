package chapter2.section3.a.priority.queue;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/04/21.
 */
public class KSmallestSums {

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int size = inputReader.readInt();

        while (true) {
            int[][] arrays = new int[size][size];

            for (int i = 0; i < size; i++) {
                int[] array = new int[size];

                for (int j = 0; j < size; j++) {
                    array[j] = inputReader.readInt();
                }
                Arrays.sort(array);
                arrays[i] = array;
            }

            int[] smallestSums = computeSmallestSums(arrays, size);
            printSums(smallestSums, outputWriter);

            try {
                size = inputReader.readInt();
            } catch (Exception exception) {
                break;
            }
        }
        outputWriter.flush();
    }

    private static int[] computeSmallestSums(int[][] arrays, int size) {
        int[] mergedArray = arrays[0];
        for (int i = 1; i < size; i++) {
            mergedArray = mergeArrays(mergedArray, arrays[i]);
        }
        return mergedArray;
    }

    private static int[] mergeArrays(int[] array1, int[] array2) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());

        for (int element1 : array1) {
            for (int element2 : array2) {
                int sum = element1 + element2;

                if (priorityQueue.size() < array1.length) {
                    priorityQueue.offer(sum);
                } else {
                    if (sum >= priorityQueue.peek()) {
                        break;
                    } else {
                        priorityQueue.poll();
                        priorityQueue.offer(sum);
                    }
                }
            }
        }

        int[] mergedArray = new int[array1.length];
        int index = array1.length - 1;
        while (!priorityQueue.isEmpty()) {
            mergedArray[index--] = priorityQueue.poll();
        }
        return mergedArray;
    }

    private static void printSums(int[] smallestSums, OutputWriter outputWriter) {
        for (int i = 0; i < smallestSums.length; i++) {
            outputWriter.print(smallestSums[i]);

            if (i != smallestSums.length - 1) {
                outputWriter.print(" ");
            }
        }
        outputWriter.printLine();
    }

    private static class InputReader {
        private InputStream stream;
        private byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private interface SpaceCharFilter {
            boolean isSpaceChar(int ch);
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
