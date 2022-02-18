package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/02/22.
 */
public class MappingTheSwaps {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int arraySize = FastReader.nextInt();
        int dataSet = 1;

        while (arraySize != 0) {
            int[] array = new int[arraySize];
            for (int i = 0; i < array.length; i++) {
                array[i] = FastReader.nextInt();
            }

            int minimumSwaps = countMinimumSwapMaps(array);
            outputWriter.printLine(String.format("There are %d swap maps for input data set %d.",
                    minimumSwaps, dataSet));
            dataSet++;
            arraySize = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int countMinimumSwapMaps(int[] array) {
        int minimumSwaps = Integer.MAX_VALUE;
        List<List<Integer>> minimumSwapsList = new ArrayList<>();

        for (int startIndex = 0; startIndex < array.length - 1; startIndex++) {
            int[] currentArray = new int[array.length];
            System.arraycopy(array, 0, currentArray, 0, array.length);
            List<Integer> currentSwaps = new ArrayList<>();

            List<List<Integer>> swaps = countMinimumSwapMaps(currentArray, startIndex, currentSwaps);
            if (swaps.get(0).size() < minimumSwaps) {
                minimumSwaps = swaps.get(0).size();
                minimumSwapsList = new ArrayList<>(swaps);
            } else if (swaps.get(0).size() == minimumSwaps) {
                for (List<Integer> swapList : swaps) {
                    boolean isNewSwapMap = isNewList(minimumSwapsList, swapList);
                    if (isNewSwapMap) {
                        minimumSwapsList.add(swapList);
                    }
                }
            }
        }

        if (minimumSwaps == 0) {
            return 0;
        }
        return minimumSwapsList.size();
    }

    private static List<List<Integer>> countMinimumSwapMaps(int[] currentArray, int index,
                                                            List<Integer> currentSwaps) {
        if (isSorted(currentArray)) {
            List<List<Integer>> swaps = new ArrayList<>();
            swaps.add(currentSwaps);
            return swaps;
        }

        while (true) {
            index = index % currentArray.length;

            int nextIndex;
            if (index == currentArray.length - 1) {
                index = 0;
                nextIndex = 1;
            } else {
                nextIndex = index + 1;
            }

            if (currentArray[index] > currentArray[nextIndex]) {
                int aux = currentArray[index];
                currentArray[index] = currentArray[nextIndex];
                currentArray[nextIndex] = aux;
                currentSwaps.add(index);
                break;
            }
            index++;
        }

        List<List<Integer>> bestSwaps = new ArrayList<>();
        int minimumSwaps = Integer.MAX_VALUE;

        for (int i = 0; i < currentArray.length - 1; i++) {
            List<Integer> copySwaps = new ArrayList<>(currentSwaps);
            int[] copyArray = new int[currentArray.length];
            System.arraycopy(currentArray, 0, copyArray, 0, currentArray.length);

            List<List<Integer>> swaps = countMinimumSwapMaps(copyArray, i, copySwaps);
            for (List<Integer> swapList : swaps) {
                if (swapList.size() < minimumSwaps) {
                    bestSwaps = new ArrayList<>();
                    minimumSwaps = swapList.size();
                    bestSwaps.add(swapList);
                } else if (swapList.size() == minimumSwaps) {
                    boolean isNewSwapMap = isNewList(bestSwaps, swapList);
                    if (isNewSwapMap) {
                        bestSwaps.add(swapList);
                    }
                }
            }
        }
        return bestSwaps;
    }

    private static boolean isNewList(List<List<Integer>> lists, List<Integer> candidateList) {
        for (List<Integer> list : lists) {
            boolean isDifferent = false;

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).compareTo(candidateList.get(i)) != 0) {
                    isDifferent = true;
                    break;
                }
            }

            if (!isDifferent) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
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
